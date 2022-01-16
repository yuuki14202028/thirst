package com.github.yuuki14202028.minecraft.thirst.thirst.mixins;

import com.github.yuuki14202028.minecraft.thirst.thirst.ThirstProperties;
import com.github.yuuki14202028.minecraft.thirst.thirst.interfaces.ItemAdditionalData;
import com.github.yuuki14202028.minecraft.thirst.thirst.interfaces.ItemPropertiesAdditionalData;
import com.github.yuuki14202028.minecraft.thirst.thirst.interfaces.PlayerAdditionalData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin implements ItemAdditionalData {

    private ThirstProperties thirstProperties;

    @Nullable
    @Override
    public ThirstProperties getThirstProperties() {
        return thirstProperties;
    }

    @Override
    public void setThirstProperties(ThirstProperties properties) {
        thirstProperties = properties;
    }

    @Redirect(method = "<init>", at = @At(value = "FIELD",target = "Lnet/minecraft/world/item/Item$Properties;foodProperties:Lnet/minecraft/world/food/FoodProperties;"))
    private FoodProperties injectInit(Item.Properties properties) {
        this.thirstProperties = ((ItemPropertiesAdditionalData)properties).getThirstProperties();
        return ((PropertiesAccessor) properties).getFoodProperties();
    }

    @Inject(method = "use",at = @At(value = "HEAD"),cancellable = true)
    private void injectUse(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (this.isDrinkable()) {
            ItemStack itemstack = player.getItemInHand(hand);
            if ((this.thirstProperties.canAlwaysDrink() || ((PlayerAdditionalData)player).getThirstData().needsThirst()) && !this.thirstProperties.isThirst()) {
                player.startUsingItem(hand);
                cir.setReturnValue(InteractionResultHolder.consume(itemstack));
            }
        }
    }

    @Inject(method = "finishUsingItem",at = @At(value = "HEAD"),cancellable = true)
    private void injectFinishUsingItem(ItemStack stack, Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
        if (this.isDrinkable()) {
            cir.setReturnValue(entity.eat(level, stack));
        }
    }

    @Inject(method = "getUseDuration",at = @At(value = "HEAD"),cancellable = true)
    private void injectGetUseDuration(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (((ItemAdditionalData)stack.getItem()).isDrinkable()) {
            cir.setReturnValue(this.thirstProperties.isFastFood() ? 12 : 24);
        }
    }

    @Mixin(Item.Properties.class)
    static class PropertiesMixin implements ItemPropertiesAdditionalData {

        ThirstProperties thirstPropertiesImpl;

        @Override
        public ThirstProperties getThirstProperties() {
            return thirstPropertiesImpl;
        }

        @Override
        public void setThirstProperties(ThirstProperties properties) {
            thirstPropertiesImpl = properties;
        }
    }

    @Mixin(Item.Properties.class)
    interface PropertiesAccessor {

        @Accessor("foodProperties")
        public FoodProperties getFoodProperties();

    }
}
