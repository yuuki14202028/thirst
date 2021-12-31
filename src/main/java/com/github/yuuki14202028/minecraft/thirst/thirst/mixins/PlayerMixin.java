package com.github.yuuki14202028.minecraft.thirst.thirst.mixins;

import com.github.yuuki14202028.minecraft.thirst.thirst.interfaces.PlayerAdditionalData;
import com.github.yuuki14202028.minecraft.thirst.thirst.ThirstData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Player.class)
public abstract class PlayerMixin extends LivingEntity implements PlayerAdditionalData {

    @Shadow @Final private Abilities abilities;

    @Shadow protected FoodData foodData;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    private ThirstData thirstDataImpl = new ThirstData();

    @Override
    public ThirstData getThirstData() {
        return thirstDataImpl;
    }

    @Override
    public void setThirstData(ThirstData data) {
        thirstDataImpl = data;
    }

    @Inject(method = "tick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/food/FoodData;tick(Lnet/minecraft/world/entity/player/Player;)V"))
    private void injectTick(CallbackInfo ci) {
        this.getThirstData().tick((Player)(Object) this);
    }

    @Inject(method = "readAdditionalSaveData",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
    private void injectReadAdditionalSaveData(CompoundTag nbt, CallbackInfo ci) {
        getThirstData().readAdditionalSaveData(nbt);
    }

    @Inject(method = "addAdditionalSaveData",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/food/FoodData;addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
    private void injectAdditionalSaveData(CompoundTag nbt, CallbackInfo ci) {
        getThirstData().addAdditionalSaveData(nbt);
    }

    @Inject(method = "eat",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/food/FoodData;eat(Lnet/minecraft/world/item/Item;Lnet/minecraft/world/item/ItemStack;)V"))
    private void injectEat(Level level, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        this.getThirstData().drink(stack.getItem(), stack);
    }

    @Redirect(method = "checkMovementStatistics",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/entity/player/Player;causeFoodExhaustion(F)V"))
    private void injectCheckMovementStatistics(Player instance, float amount) {
        if (!this.abilities.invulnerable) {
            if (!this.level.isClientSide) {
                this.thirstDataImpl.addExhaustion(amount * 1.5f);
            }
        }
    }

    @Redirect(method = "jumpFromGround",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/entity/LivingEntity;jumpFromGround()V"))
    private void injectJumpFromGround(LivingEntity instance) {
        double d0 = (double)this.getJumpPower() + this.getJumpBoostPower();
        if (this.foodData.getFoodLevel() <= 0) {
            d0 *= 0.75;
        }
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, d0, vec3.z);
        if (this.isSprinting()) {
            float f = this.getYRot() * ((float)Math.PI / 180F);
            this.setDeltaMovement(this.getDeltaMovement().add(-Mth.sin(f) * 0.2F, 0.0D, Mth.cos(f) * 0.2F));
        }
        this.hasImpulse = true;
        net.minecraftforge.common.ForgeHooks.onLivingJump(this);
    }

    @Redirect(method = "jumpFromGround",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/entity/player/Player;causeFoodExhaustion(F)V"))
    private void injectJumpFromGround(Player instance, float amount) {
        if (!this.abilities.invulnerable) {
            if (!this.level.isClientSide) {
                this.thirstDataImpl.addExhaustion(amount * 2.0f);
            }
        }
    }
}
