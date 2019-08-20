/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.declarations.impl

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.declarations.FirFunction
import org.jetbrains.kotlin.fir.declarations.FirValueParameter
import org.jetbrains.kotlin.fir.expressions.FirBlock
import org.jetbrains.kotlin.fir.references.FirControlFlowGraphReference
import org.jetbrains.kotlin.fir.references.FirEmptyControlFlowGraphReference
import org.jetbrains.kotlin.fir.symbols.FirSymbolOwner
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertyAccessorSymbol
import org.jetbrains.kotlin.fir.transformInplace
import org.jetbrains.kotlin.fir.transformSingle
import org.jetbrains.kotlin.fir.visitors.FirTransformer
import org.jetbrains.kotlin.fir.visitors.FirVisitor

abstract class FirAbstractFunction(
    session: FirSession,
    psi: PsiElement?,
    final override val symbol: FirPropertyAccessorSymbol = FirPropertyAccessorSymbol()
) : FirAbstractAnnotatedDeclaration(session, psi), FirFunction, FirSymbolOwner<FirAbstractFunction> {
    init {
        symbol.bind(this)
    }

    final override val valueParameters = mutableListOf<FirValueParameter>()

    final override var body: FirBlock? = null

    final override var cfgReference: FirControlFlowGraphReference = FirEmptyControlFlowGraphReference()

    override fun <R, D> acceptChildren(visitor: FirVisitor<R, D>, data: D) {
        super<FirAbstractAnnotatedDeclaration>.acceptChildren(visitor, data)
        cfgReference.accept(visitor, data)
    }

    override fun <D> transformChildren(transformer: FirTransformer<D>, data: D): FirElement {
        valueParameters.transformInplace(transformer, data)
        body = body?.transformSingle(transformer, data)
        cfgReference = cfgReference.transformSingle(transformer, data)

        return super<FirAbstractAnnotatedDeclaration>.transformChildren(transformer, data)
    }
}