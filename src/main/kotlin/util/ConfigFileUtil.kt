package util

import config.Config
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import rules.BaseRule
import rules.analyser.MostCommon
import rules.analyser.MostCommonInner
import rules.analyser.MostCommonOuter
import rules.creator.BlankImage
import rules.placer.ApplyMask
import rules.transformer.ColourMatch
import java.io.File
import java.nio.file.Paths


class ConfigFileUtil {

    private val jsonConfig = Json {
        serializersModule = SerializersModule {
            // Analyser
            polymorphic(BaseRule::class, MostCommon::class, MostCommon.serializer())
            polymorphic(BaseRule::class, MostCommonInner::class, MostCommonInner.serializer())
            polymorphic(BaseRule::class, MostCommonOuter::class, MostCommonOuter.serializer())

            // Creator
            polymorphic(BaseRule::class, BlankImage::class, BlankImage.serializer())

            // Placer
            polymorphic(BaseRule::class, ApplyMask::class, ApplyMask.serializer())

            // Transformer
            polymorphic(BaseRule::class, ColourMatch::class, ColourMatch.serializer())
        }
    }

    fun loadConfig(): Config? {
        val configDir = getConfigDir()
        val validFile = getFirstValidFile(configDir) ?: return null
        val configStream = validFile.inputStream()
        val configText = configStream.bufferedReader().use { it.readText() }
        return jsonConfig.decodeFromString(Config.serializer(), configText)
    }

    fun saveConfig(config: Config) {
        val configText = jsonConfig.encodeToString(config)
        val configFile = getConfigDir("myconfig.cfg")
        configFile.writeText(configText)
    }

    private fun getConfigDir(filename: String? = null): File {
        val projectDir = Paths.get("").toAbsolutePath().toString()
        val configDir = "/configs/"
        return if (!filename.isNullOrEmpty()) {
            File(projectDir + configDir, filename)
        } else {
            File(projectDir + configDir)
        }
    }

    private fun getFirstValidFile(directory: File): File? {
        if (!directory.isDirectory) return null

        return directory.listFiles()
            ?.firstOrNull { file ->
                file.isFile && file.extension == "cfg"
            }
    }
}