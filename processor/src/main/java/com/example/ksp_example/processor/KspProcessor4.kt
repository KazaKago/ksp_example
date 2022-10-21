package com.example.ksp_example.processor

import com.example.ksp_example.annotation.KspExample
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.writeTo

// ファイルの出力にKotlinPoetを使って堅牢に実装
class KspProcessor4(
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
        val fileSpec = FileSpec.builder(packageName, className)
            .addType(
                TypeSpec.classBuilder(className)
                    .addFunction(
                        FunSpec.builder("hello")
                            .addStatement("println(\"Hello ${symbol.simpleName.asString()}!\")")
                            .build()
                    ).build()
            ).build()

        fileSpec.writeTo(codeGenerator, false)
    }
}
