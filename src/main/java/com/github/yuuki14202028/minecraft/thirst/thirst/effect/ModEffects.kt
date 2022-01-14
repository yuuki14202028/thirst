package com.github.yuuki14202028.minecraft.thirst.thirst.effect

import com.github.yuuki14202028.minecraft.thirst.thirst.Thirst
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.RegistryObject
import net.minecraftforge.versions.forge.ForgeVersion
import java.util.function.Supplier


object ModEffects {
    private val EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Thirst.MOD_ID)

    val THIRST: RegistryObject<ModEffect> = EFFECTS.register("thirst") {
        ModEffect(MobEffectCategory.HARMFUL, 5797459)
    }

    fun registerBus(eventBus: IEventBus?) {
        EFFECTS.register(eventBus)
    }
}