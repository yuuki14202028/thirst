package com.github.yuuki14202028.minecraft.thirst.thirst.mixins;

import com.github.yuuki14202028.minecraft.thirst.thirst.ThirstProperties;
import com.github.yuuki14202028.minecraft.thirst.thirst.interfaces.ItemPropertiesAdditionalData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Items.class)
public class ItemsMixin {
    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item$Properties;food(Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/Item$Properties;",
                    ordinal = 0
            )
    )
    private static Item.Properties injectInitApple(Item.Properties instance, FoodProperties properties) {
        ((ItemPropertiesAdditionalData)instance.food(properties)).setThirstProperties(
                new ThirstProperties.Builder()
                        .hydration(4)
                        .saturationMod(0.6f)
                        .build()
        );
        return instance;
    }
    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item$Properties;food(Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/Item$Properties;",
                    ordinal = 11
            )
    )
    private static Item.Properties injectInitCookedCod(Item.Properties instance, FoodProperties properties) {
        ((ItemPropertiesAdditionalData)instance.food(properties)).setThirstProperties(
                new ThirstProperties.Builder()
                        .hydration(-2)
                        .saturationMod(1f)
                        .build()
        );
        return instance;
    }
    @Redirect(method = "<clinit>",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item$Properties;food(Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/Item$Properties;",
                    ordinal = 12
            )
    )
    private static Item.Properties injectInitCookedSalmon(Item.Properties instance, FoodProperties properties) {
        ((ItemPropertiesAdditionalData)instance.food(properties)).setThirstProperties(
                new ThirstProperties.Builder()
                        .hydration(-2)
                        .saturationMod(1f)
                        .build()
        );
        return instance;
    }
}
