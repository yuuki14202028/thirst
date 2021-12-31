package com.github.yuuki14202028.minecraft.thirst.thirst.mixins;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FoodData.class)
public class FoodDataMixin {
    @Redirect(method = "tick",
            at = @At(value = "INVOKE",target = "Lnet/minecraft/world/entity/player/Player;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean injectTick(Player player, DamageSource source, float amount) {
        return false;
    }
}
