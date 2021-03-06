package com.squareup.sqldelight.test.util

import com.alecstrong.sql.psi.core.DialectPreset
import com.alecstrong.sql.psi.core.SqlAnnotationHolder
import com.alecstrong.sql.psi.core.SqlCoreEnvironment
import com.intellij.psi.PsiElement
import com.squareup.sqldelight.core.SqlDelightDatabaseProperties
import com.squareup.sqldelight.core.SqlDelightEnvironment
import com.squareup.sqldelight.core.SqldelightParserUtil
import java.io.File

internal class TestEnvironment(private val outputDirectory: File = File("output")) {
  fun build(root: String): SqlCoreEnvironment {
    return build(root, object : SqlAnnotationHolder {
      override fun createErrorAnnotation(element: PsiElement, s: String) {
        throw IllegalStateException(s)
      }
    })
  }

  fun build(root: String, annotationHolder: SqlAnnotationHolder): SqlDelightEnvironment {
    val environment = SqlDelightEnvironment(
        sourceFolders = listOf(File(root)),
        dependencyFolders = emptyList(),
        properties = SqlDelightDatabaseProperties(
            packageName = "com.example",
            className = "TestDatabase",
            dependencies = emptyList(),
            compilationUnits = emptyList(),
            outputDirectory = outputDirectory.absolutePath,
            dialectPreset = DialectPreset.SQLITE_3_18
        ),
        outputDirectory = outputDirectory,
        moduleName = "testmodule"
    )
    environment.annotate(annotationHolder)
    return environment
  }
}