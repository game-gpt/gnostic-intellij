@file:Suppress("DEPRECATION")

package com.github.game_gpt.ide.file_type

import com.intellij.openapi.fileTypes.FileTypeConsumer
import com.intellij.openapi.fileTypes.FileTypeFactory

class GnosticFileTypeFactory : FileTypeFactory() {
    override fun createFileTypes(consumer: FileTypeConsumer) {
        consumer.consume(GnosticFileType)
        // v
        consumer.consume(GnosticScriptFileType)
        consumer.consume(GnosticShaderFileType)
        consumer.consume(GnosticSchemaFileType)
        // voc
        consumer.consume(GnosticWidgetFileType)
        // von
        consumer.consume(GnosticObjectFileType)
        consumer.consume(GnosticConfigFileType)
        consumer.consume(GnosticMetaFileType)
        // von extensions
        consumer.consume(GnosticMeshFileType)
        consumer.consume(GnosticSkeletonFileType)
        consumer.consume(GnosticAnimationFileType)
        consumer.consume(GnosticAnimatorFileType)
        consumer.consume(GnosticLocaleFileType)
        consumer.consume(GnosticMaterialFileType)
        consumer.consume(GnosticPrefabFileType)
        consumer.consume(GnosticSceneFileType)
        consumer.consume(GnosticStoryFileType)
        // unity-like extensions
        consumer.consume(GnosticAssetFileType)
        consumer.consume(GnosticMixerFileType)
        consumer.consume(GnosticBrushFileType)
        consumer.consume(GnosticBundleFileType)
        consumer.consume(GnosticTerrainFileType)
        consumer.consume(GnosticNavigationFileType)
    }
}
