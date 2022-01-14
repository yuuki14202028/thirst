package com.github.yuuki14202028.minecraft.thirst.thirst.mixins;

import com.github.yuuki14202028.minecraft.thirst.thirst.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Foods.class)
public abstract class FoodsMixin {
    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/food/FoodProperties$Builder;saturationMod(F)Lnet/minecraft/world/food/FoodProperties$Builder;",
                    ordinal = 0
            )
    )
    private static FoodProperties.Builder injectClinit(FoodProperties.Builder builder, float mod) {
        return builder
                .saturationMod(mod)
                .effect(() -> new MobEffectInstance(ModEffects.INSTANCE.getTHIRST().get(), 600, 0), 1.0F);
    }
}
