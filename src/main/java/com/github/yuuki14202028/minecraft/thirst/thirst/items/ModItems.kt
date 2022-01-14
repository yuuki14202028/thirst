package com.github.yuuki14202028.minecraft.thirst.thirst.items

import com.github.yuuki14202028.minecraft.thirst.thirst.Thirst
import com.github.yuuki14202028.minecraft.thirst.thirst.ThirstProperties
import com.github.yuuki14202028.minecraft.thirst.thirst.interfaces.ItemAdditionalData
import net.minecraft.world.item.Items
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

object ModItems {
    private val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Thirst.MOD_ID)

    fun registerBus(eventBus: IEventBus?) {
        ModItems.ITEMS.register(eventBus)
    }

    fun changeVanillaItemProperties() {
        (Items.APPLE as ItemAdditionalData).thirstProperties = ThirstProperties.Builder()
            .hydration(4)
            .saturationMod(0.3f)
            .build()
        (Items.BEETROOT as ItemAdditionalData).thirstProperties = ThirstProperties.Builder()
            .hydration(1)
            .saturationMod(0f)
            .build()
        (Items.BEETROOT_SOUP as ItemAdditionalData).thirstProperties = ThirstProperties.Builder()
            .hydration(4)
            .saturationMod(0.6f)
            .build()
        (Items.CARROT as ItemAdditionalData).thirstProperties = ThirstProperties.Builder()
            .hydration(2)
            .saturationMod(0.6f)
            .build()
        (Items.GOLDEN_APPLE as ItemAdditionalData).thirstProperties = ThirstProperties.Builder()
            .hydration(4)
            .saturationMod(1.2f)
            .build()
        (Items.ENCHANTED_GOLDEN_APPLE as ItemAdditionalData).thirstProperties = ThirstProperties.Builder()
            .hydration(4)
            .saturationMod(1.2f)
            .build()
        (Items.GOLDEN_CARROT as ItemAdditionalData).thirstProperties = ThirstProperties.Builder()
            .hydration(4)
            .saturationMod(1.2f)
            .build()
        (Items.MELON_SLICE as ItemAdditionalData).thirstProperties = ThirstProperties.Builder()
            .hydration(2)
            .saturationMod(0.3f)
            .build()
        (Items.MUSHROOM_STEW as ItemAdditionalData).thirstProperties = ThirstProperties.Builder()
            .hydration(3)
            .saturationMod(0.6f)
            .build()
        (Items.RABBIT_STEW as ItemAdditionalData).thirstProperties = ThirstProperties.Builder()
            .hydration(5)
            .saturationMod(0.6f)
            .build()
        (Items.SUSPICIOUS_STEW as ItemAdditionalData).thirstProperties = ThirstProperties.Builder()
            .hydration(3)
            .saturationMod(0.6f)
            .build()
        (Items.COOKED_COD as ItemAdditionalData).thirstProperties = ThirstProperties.Builder()
            .hydration(-1)
            .saturationMod(0.5f)
            .build()
        (Items.COOKED_SALMON as ItemAdditionalData).thirstProperties = ThirstProperties.Builder()
            .hydration(-1)
            .saturationMod(0.5f)
            .build()
    }
}