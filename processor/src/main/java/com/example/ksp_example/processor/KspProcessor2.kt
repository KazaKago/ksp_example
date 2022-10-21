package com.example.ksp_example.processor

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration

// 固定化した名前のクラスを見つけ出し、それに応じたファイルを出力する
class KspProcessor2(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    private var invoked = false

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (invoked) return emptyList()

        val ksName = resolver.getKSNameFromString("com.example.ksp_example.MainActivity")
        val symbol = resolver.getClassDeclarationByName(ksName)
        symbol?.let { createFile(symbol) }

        invoked = true
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
