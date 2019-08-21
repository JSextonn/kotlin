/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

@file:Suppress("DEPRECATION")

package org.jetbrains.kotlin.idea.debugger.coroutines

import com.intellij.debugger.DebuggerManagerEx
import com.intellij.debugger.actions.DebuggerActions
import com.intellij.debugger.actions.GotoFrameSourceAction
import com.intellij.debugger.engine.DebugProcessImpl
import com.intellij.debugger.engine.JavaExecutionStack
import com.intellij.debugger.engine.events.DebuggerCommandImpl
import com.intellij.debugger.impl.DebuggerContextImpl
import com.intellij.debugger.impl.DebuggerContextListener
import com.intellij.debugger.impl.DebuggerSession
import com.intellij.debugger.impl.DebuggerStateManager
import com.intellij.debugger.jdi.ThreadReferenceProxyImpl
import com.intellij.debugger.ui.impl.DebuggerTreePanel
import com.intellij.debugger.ui.impl.watch.DebuggerTree
import com.intellij.debugger.ui.impl.watch.DebuggerTreeNodeImpl
import com.intellij.debugger.ui.tree.StackFrameDescriptor
import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.ui.DoubleClickListener
import com.intellij.ui.ScrollPaneFactory
import com.intellij.util.Alarm
import com.intellij.xdebugger.XDebuggerUtil
import com.intellij.xdebugger.XSourcePosition
import com.intellij.xdebugger.frame.XExecutionStack
import com.intellij.xdebugger.impl.ui.DebuggerUIUtil
import com.sun.jdi.ClassType
import org.jetbrains.annotations.NonNls
import org.jetbrains.kotlin.idea.debugger.KotlinCoroutinesAsyncStackTraceProvider
import org.jetbrains.kotlin.idea.debugger.evaluate.createExecutionContext
import java.awt.BorderLayout
import java.awt.event.MouseEvent
import java.util.*
import javax.swing.JTree

/**
 * Actually added into ui in [CoroutinesDebugConfigurationExtension.registerCoroutinesPanel]
 * Some methods are copied from [com.intellij.debugger.ui.impl.ThreadsPanel]
 */
class CoroutinesPanel(project: Project, stateManager: DebuggerStateManager) : DebuggerTreePanel(project, stateManager) {
    private val myUpdateLabelsAlarm = Alarm(Alarm.ThreadToUse.SWING_THREAD)

    init {
        val disposable = installAction(getCoroutinesTree(), DebuggerActions.EDIT_FRAME_SOURCE)
        registerDisposable(disposable)
        add(ScrollPaneFactory.createScrollPane(getCoroutinesTree()), BorderLayout.CENTER)
        stateManager.addListener(object : DebuggerContextListener {
            override fun changeEvent(newContext: DebuggerContextImpl, event: DebuggerSession.Event) {
                if (DebuggerSession.Event.ATTACHED == event || DebuggerSession.Event.RESUME == event) {
                    startLabelsUpdate()
                } else if (DebuggerSession.Event.PAUSE == event
                    || DebuggerSession.Event.DETACHED == event
                    || DebuggerSession.Event.DISPOSE == event
                ) {
                    myUpdateLabelsAlarm.cancelAllRequests()
                }
                if (DebuggerSession.Event.DETACHED == event || DebuggerSession.Event.DISPOSE == event) {
                    stateManager.removeListener(this)
                }
            }
        })
        startLabelsUpdate()
    }

    @Suppress("SameParameterValue")
    private fun installAction(tree: JTree, actionName: String): () -> Unit {
        val listener = object : DoubleClickListener() {
            override fun onDoubleClick(e: MouseEvent): Boolean {
                val location = tree.getPathForLocation(e.x, e.y)
                    ?.lastPathComponent as? DebuggerTreeNodeImpl ?: return false

                val dataContext = DataManager.getInstance().getDataContext(tree)
                val context = DebuggerManagerEx.getInstanceEx(project).context
                when (val descriptor = location.userObject) {
                    is CoroutinesDebuggerTree.SuspendStackFrameDescriptor -> {
                        buildSuspendStackFrameChildren(descriptor)
                        return true
                    }
                    is CoroutinesDebuggerTree.AsyncStackFrameDescriptor -> {
                        buildAsyncStackFrameChildren(descriptor, context.debugProcess ?: return false)
                        // TODO add creation to async
                        return true
                    }
                    is CoroutinesDebuggerTree.EmptyStackFrameDescriptor -> {
                        buildEmptyStackFrameChildren(descriptor)
                        return true
                    }
                    is StackFrameDescriptor -> {
                        GotoFrameSourceAction.doAction(dataContext)
                        return true
                    }
                    else -> return true
                }
            }
        }
        listener.installOn(tree)

        val disposable = { listener.uninstall(tree) }
        DebuggerUIUtil.registerActionOnComponent(actionName, tree, disposable)

        return disposable
    }

    private fun getPosition(frame: StackTraceElement): XSourcePosition? {
        val psiFacade = JavaPsiFacade.getInstance(project)
        val psiClass = psiFacade.findClass(
            frame.className.substringBefore("$"), // find outer class, for which psi exists
            GlobalSearchScope.everythingScope(project)
        )
        val classFile = psiClass?.containingFile?.virtualFile
        return XDebuggerUtil.getInstance().createPosition(classFile, frame.lineNumber)
    }

    private fun buildSuspendStackFrameChildren(descriptor: CoroutinesDebuggerTree.SuspendStackFrameDescriptor) {
        val pos = getPosition(descriptor.frame) ?: return
        context.debugProcess?.managerThread?.schedule(object : DebuggerCommandImpl() {
            override fun action() {
                val (stack, stackFrame) = createFakeStackFrame(descriptor, pos) ?: return
                val action: () -> Unit = { context.debuggerSession?.xDebugSession?.setCurrentStackFrame(stack, stackFrame) }
                ApplicationManager.getApplication()
                    .invokeLater(action, ModalityState.stateForComponent(this@CoroutinesPanel))
            }
        })
    }

    private fun buildAsyncStackFrameChildren(descriptor: CoroutinesDebuggerTree.AsyncStackFrameDescriptor, process: DebugProcessImpl) {
        process.managerThread?.schedule(object : DebuggerCommandImpl() {
            override fun action() {
                val proxy = ThreadReferenceProxyImpl(
                    process.virtualMachineProxy,
                    descriptor.state.thread // is not null because it's a running coroutine
                )
                val executionStack = JavaExecutionStack(proxy, process, false)
                executionStack.initTopFrame()
                val frame = descriptor.frame.createFrame(process)
                DebuggerUIUtil.invokeLater {
                    context.debuggerSession?.xDebugSession?.setCurrentStackFrame(
                        executionStack,
                        frame
                    )
                }
            }
        })
    }

    private fun buildEmptyStackFrameChildren(descriptor: CoroutinesDebuggerTree.EmptyStackFrameDescriptor) {
        val position = getPosition(descriptor.frame) ?: return
        val suspendContext = context.suspendContext ?: return
        val proxy = suspendContext.thread ?: return
        context.debugProcess?.managerThread?.schedule(object : DebuggerCommandImpl() {
            override fun action() {
                val executionStack =
                    JavaExecutionStack(proxy, context.debugProcess!!, false)
                executionStack.initTopFrame()
                val frame = FakeStackFrame(descriptor, emptyList(), position)
                val action: () -> Unit =
                    { context.debuggerSession?.xDebugSession?.setCurrentStackFrame(executionStack, frame) }
                ApplicationManager.getApplication()
                    .invokeLater(action, ModalityState.stateForComponent(this@CoroutinesPanel))
            }
        })
    }

    /**
     * Should be invoked on manager thread
     */
    private fun createFakeStackFrame(
        descriptor: CoroutinesDebuggerTree.SuspendStackFrameDescriptor,
        pos: XSourcePosition
    ): Pair<XExecutionStack, FakeStackFrame>? {
        val proxy = context.suspendContext?.thread ?: return null
        val executionStack =
            JavaExecutionStack(proxy, context.debugProcess!!, false)
        executionStack.initTopFrame()
        val execContext = context.createExecutionContext() ?: return null
        val continuation = descriptor.continuation // guaranteed that it is a BaseContinuationImpl
        val aMethod = (continuation.type() as ClassType).concreteMethodByName(
            "getStackTraceElement",
            "()Ljava/lang/StackTraceElement;"
        )
        val debugMetadataKtType = execContext
            .findClass("kotlin.coroutines.jvm.internal.DebugMetadataKt") as ClassType
        val vars = with(KotlinCoroutinesAsyncStackTraceProvider()) {
            KotlinCoroutinesAsyncStackTraceProvider.AsyncStackTraceContext(
                execContext,
                aMethod,
                debugMetadataKtType
            ).getSpilledVariables(continuation)
        } ?: return null
        return executionStack to FakeStackFrame(descriptor, vars, pos)
    }

    private fun startLabelsUpdate() {
        if (myUpdateLabelsAlarm.isDisposed) return
        myUpdateLabelsAlarm.cancelAllRequests()
        myUpdateLabelsAlarm.addRequest(object : Runnable {
            override fun run() {
                var updateScheduled = false
                try {
                    if (isUpdateEnabled) {
                        val tree = getCoroutinesTree()
                        val root = tree.model.root as DebuggerTreeNodeImpl
                        val process = context.debugProcess
                        if (process != null) {
                            process.managerThread.schedule(object : DebuggerCommandImpl() {
                                override fun action() {
                                    try {
                                        updateNodeLabels(root)
                                    } finally {
                                        reschedule()
                                    }
                                }

                                override fun commandCancelled() {
                                    reschedule()
                                }
                            })
                            updateScheduled = true
                        }
                    }
                } finally {
                    if (!updateScheduled) {
                        reschedule()
                    }
                }
            }

            private fun reschedule() {
                val session = context.debuggerSession
                if (session != null && session.isAttached && !session.isPaused && !myUpdateLabelsAlarm.isDisposed) {
                    myUpdateLabelsAlarm.addRequest(
                        this,
                        LABELS_UPDATE_DELAY_MS, ModalityState.NON_MODAL
                    )
                }
            }

        }, LABELS_UPDATE_DELAY_MS, ModalityState.NON_MODAL)
    }

    override fun dispose() {
        Disposer.dispose(myUpdateLabelsAlarm)
        super.dispose()
    }

    private fun updateNodeLabels(from: DebuggerTreeNodeImpl) {
        val children = from.children()
        try {
            while (children.hasMoreElements()) {
                val child = children.nextElement() as DebuggerTreeNodeImpl
                child.descriptor.updateRepresentation(
                    null
                ) { child.labelChanged() }
                updateNodeLabels(child)
            }
        } catch (ignored: NoSuchElementException) { // children have changed - just skip
        }

    }

    override fun createTreeView(): DebuggerTree {
        return CoroutinesDebuggerTree(project)
    }

    override fun createPopupMenu(): ActionPopupMenu {
        val group = EmptyActionGroup()
        return ActionManager.getInstance().createActionPopupMenu("Debugger.CoroutinesPanelPopup", group)
    }

    override fun getData(dataId: String): Any? {
        return if (PlatformDataKeys.HELP_ID.`is`(dataId)) {
            HELP_ID
        } else super.getData(dataId)
    }

    fun getCoroutinesTree(): CoroutinesDebuggerTree = tree as CoroutinesDebuggerTree

    companion object {
        @NonNls
        private val HELP_ID = "debugging.debugCoroutines"
        private const val LABELS_UPDATE_DELAY_MS = 200
    }

}