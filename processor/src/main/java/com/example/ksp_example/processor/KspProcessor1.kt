package com.example.ksp_example.processor

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated

// 完全に固定化されたファイルを出力する
class KspProcessor1(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    private var invoked = false

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (invoked) return emptyList()

        createFile()

        invoked = true
        return emptyList()
    }

    private fun createFile() {
        logger.warn("createFile")

        val code = """
            package com.example

            class Sample {
                fun hello() {
                    println("Hello World!")
                }
            }
        """.trimIndent()
        val file = codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = "com.example",
            fileName = "Sample",
        )

        file.write(code.toByteArray())
        file.close()
    }
}
