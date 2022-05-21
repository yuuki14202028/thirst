package com.github.yuuki14202028.minecraft.thirst.thirst.config

import com.electronwill.nightconfig.core.Config
import com.electronwill.nightconfig.core.InMemoryCommentedFormat

data class ThirstPropertiesConfig(
    val identity: String,
    val hydration: Int,
    val saturation: Float,
    val isAlways: Boolean,
    val isFast: Boolean
){
    fun toConfig(): Config {
        val config = Config.of(InMemoryCommentedFormat.withUniversalSupport())
        config.add("identity", identity)
        config.add("hydration", hydration)
        config.add("saturationModifier",saturation)
        config.add("canAlwaysDrink",isAlways)
        config.add("fastDrink",isFast)
        return config
    }
}