package com.github.yuuki14202028.minecraft.thirst.thirst.effect

import com.github.yuuki14202028.minecraft.thirst.thirst.interfaces.PlayerAdditionalData
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.Difficulty
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player


class ModEffect(effectType: MobEffectCategory, liquidColorIn: Int) : MobEffect(effectType, liquidColorIn) {
    override fun applyEffectTick(entity: LivingEntity, amplifier: Int) {
        if (this === ModEffects.THIRST.get() && entity is ServerPlayer) {
            (entity as PlayerAdditionalData).thirstData.addExhaustion(0.005f * (amplifier + 1).toFloat())
        }
        super.applyEffectTick(entity, amplifier)
    }

    override fun isDurationEffectTick(duration: Int, amplifier: Int): Boolean {
        return this === ModEffects.THIRST.get()
    }
}