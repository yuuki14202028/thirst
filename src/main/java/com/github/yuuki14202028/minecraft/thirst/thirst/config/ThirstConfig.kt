package com.github.yuuki14202028.minecraft.thirst.thirst.config

import com.electronwill.nightconfig.core.Config
import com.github.yuuki14202028.minecraft.thirst.thirst.ThirstProperties
import com.github.yuuki14202028.minecraft.thirst.thirst.interfaces.ItemAdditionalData
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue


object ThirstConfig {
    private val BUILDER = ForgeConfigSpec.Builder()
    var SPEC: ForgeConfigSpec? = null
    var configValue: ConfigValue<List<Config>>

    init {
        BUILDER.push("drink_items")
        val thirstProperties = listOf(
            ThirstPropertiesConfig("minecraft:apple",3,0.4f,false,false),
            ThirstPropertiesConfig("minecraft:beetroot",1,0.0f,false,false),
            ThirstPropertiesConfig("minecraft:beetroot_soup",4,0.6f,false,false),
            ThirstPropertiesConfig("minecraft:carrot",2,0.6f,false,false),
            ThirstPropertiesConfig("minecraft:golden_apple",4,1.2f,false,false),
            ThirstPropertiesConfig("minecraft:enchanted_golden_apple",4,1.2f,false,false),
            ThirstPropertiesConfig("minecraft:golden_carrot",4,1.2f,false,false),
            ThirstPropertiesConfig("minecraft:melon_slice",2,0.3f,false,false),
            ThirstPropertiesConfig("minecraft:mushroom_stew",3,0.6f,false,false),
            ThirstPropertiesConfig("minecraft:rabbit_stew",5,0.6f,false,false),
            ThirstPropertiesConfig("minecraft:suspicious_stew",3,0.6f,false,false),
            ThirstPropertiesConfig("minecraft:cooked_cod",-1,0.5f,false,false),
            ThirstPropertiesConfig("minecraft:cooked_salmon",-1,0.5f,false,false)
        )
        configValue = BUILDER.define("element", thirstProperties.map { it.toConfig() }) { list ->
            if (list !is List<*>) return@define false
            for (element in list) {
                if (element !is Config) return@define false
                if (element.contains("identity").not()) return@define false
                if (element.get<Any>("identity") !is String) return@define false
                if (element.contains("hydration").not()) return@define false
                if (element.get<Any>("hydration") !is Int) return@define false
                if (element.contains("saturationModifier").not()) return@define false
                if (element.get<Any>("saturationModifier") !is Double) return@define false
                if (element.contains("canAlwaysDrink").not()) return@define false
                if (element.get<Any>("canAlwaysDrink") !is Boolean) return@define false
                if (element.contains("fastDrink").not()) return@define false
                if (element.get<Any>("fastDrink") !is Boolean) return@define false
            }
            return@define true
        }
        BUILDER.pop()
        SPEC = BUILDER.build()
    }

    fun register() {
        configValue.get().forEach { config ->
            val builder = ThirstProperties.Builder()
                .hydration(config.get("hydration"))
                .saturationMod(config.get("saturationModifier"))
                .alwaysEat(config.get("canAlwaysDrink"))
                .fast(config.get("fastDrink"))
            (Registry.ITEM.get(ResourceLocation(config.get("identity"))) as ItemAdditionalData).thirstProperties = builder.build()
        }
    }
}