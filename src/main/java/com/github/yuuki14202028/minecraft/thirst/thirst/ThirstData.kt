package com.github.yuuki14202028.minecraft.thirst.thirst

import com.github.yuuki14202028.minecraft.thirst.thirst.interfaces.ItemAdditionalData
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.Difficulty
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.GameRules

class ThirstData {
    var thirstLevel = 20
    var saturationLevel = 5.0f
        private set
    var exhaustionLevel = 0f
        private set
    private var tickTimer = 0
    var lastThirstLevel = 20
        private set

    fun drink(thirstLevel: Int, saturationLevel: Float) {
        this.thirstLevel = (thirstLevel + this.thirstLevel).coerceAtMost(20)
        this.saturationLevel = (this.saturationLevel + thirstLevel.toFloat() * saturationLevel * 2.0f).coerceAtMost(this.thirstLevel.toFloat())
    }

    fun drink(item: Item, stack: ItemStack?) {
        if ((item as ItemAdditionalData).isDrinkable) {
            val thirstProperties = item.thirstProperties!!
            this.drink(thirstProperties.getHydration(), thirstProperties.getSaturationModifier())
        }
    }

    fun tick(player: Player) {
        val difficulty = player.level.difficulty
        lastThirstLevel = thirstLevel
        if (exhaustionLevel > 4.0f) {
            exhaustionLevel -= 4.0f
            if (saturationLevel > 0.0f) {
                saturationLevel = (saturationLevel - 1.0f).coerceAtLeast(0.0f)
            } else if (difficulty != Difficulty.PEACEFUL) {
                thirstLevel = (thirstLevel - 1).coerceAtLeast(0)
            }
        }
        val flag = player.level.gameRules.getBoolean(GameRules.RULE_NATURAL_REGENERATION)
        if (this.thirstLevel <= 0) {
            tickTimer += 1
            if (tickTimer >= 80) {
                if (player.health > 10.0f || difficulty == Difficulty.HARD || player.health > 1.0f && difficulty == Difficulty.NORMAL) {
                    player.hurt(DamageSource.STARVE, 1.0f)
                }
                tickTimer = 0
            }
        }
    }

    fun readAdditionalSaveData(nbt: CompoundTag) {
        if (nbt.contains("thirstLevel", 99)) {
            thirstLevel = nbt.getInt("thirstLevel")
            tickTimer = nbt.getInt("thirstTickTimer")
            saturationLevel = nbt.getFloat("thirstSaturationLevel")
            exhaustionLevel = nbt.getFloat("thirstExhaustionLevel")
        }
    }

    fun addAdditionalSaveData(nbt: CompoundTag) {
        nbt.putInt("thirstLevel", thirstLevel)
        nbt.putInt("thirstTickTimer", tickTimer)
        nbt.putFloat("thirstSaturationLevel", saturationLevel)
        nbt.putFloat("thirstExhaustionLevel", exhaustionLevel)
    }

    fun needsThirst(): Boolean {
        return thirstLevel < 20
    }

    fun addExhaustion(amount: Float) {
        exhaustionLevel = Math.min(exhaustionLevel + amount, 40.0f)
    }

    fun setSaturation(amount: Float) {
        saturationLevel = amount
    }

    fun setExhaustion(amount: Float) {
        exhaustionLevel = amount
    }
}