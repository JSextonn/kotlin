// IGNORE_BACKEND: JVM_IR
//ALLOW_AST_ACCESS
package test

annotation class Anno(val t: String)
@Anno("foo") suspend fun foo() {}
