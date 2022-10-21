package com.example.ksp_example.processor

import com.example.ksp_example.annotation.KspExample
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration

// ファイルを探し出す仕組みを独自Annotationを付与したクラスを探すように変更
class KspProcessor3(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(KspExample::class.qualifiedName!!)
        symbols.forEach { symbol ->
            when (symbol) {
                is KSClassDeclaration -> createFile(symbol)
            }
        }

        return emptyList()
    }

    private fun createFile(symbol: KSClassDeclaration) {
        logger.warn("createFile", symbol)

        val packageName = symbol.packageName.asString()
        val className = "${symbol.simpleName.asString()}Processor"
        val code = """
            package $packageName

            class $className {
                fun hello() {
                    println("Hello ${symbol.simpleName.asString()}!")
                }
            }
        """.trimIndent()
        val file = codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = packageName,
            fileName = className,
        )

        file.write(code.toByteArray())
        file.close()
    }
}
