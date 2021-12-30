package com.github.yuuki14202028.minecraft.thirst.thirst.mixins;

import com.github.yuuki14202028.minecraft.thirst.thirst.PacketHandler;
import com.github.yuuki14202028.minecraft.thirst.thirst.packets.ClientboundSetThirstPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraftforge.network.PacketDistributor;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends PlayerMixin {

    @Shadow public ServerGamePacketListenerImpl connection;

    @Shadow protected abstract void updateScoreForCriteria(ObjectiveCriteria p_9105_, int p_9106_);

    protected ServerPlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) { super(entityType, level); }

    int lastSentThirst = -99999999;
    boolean lastFoodSaturationZero = true;

    int lastRecordedThirstLevel = Integer.MIN_VALUE;;

    @Inject(method = "restoreFrom",
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/server/level/ServerPlayer;foodData:Lnet/minecraft/world/food/FoodData;",
                    opcode = Opcodes.GETFIELD
            ))
    private void injectRestoreFromWithThirstData(ServerPlayer player, boolean bool, CallbackInfo ci) {
        setThirstData(((PlayerMixin)(Object)player).getThirstData());
    }

    @Inject(method = "restoreFrom",
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/server/level/ServerPlayer;lastSentFood:I",
                    opcode = Opcodes.PUTFIELD
            ))
    private void injectRestoreFromWithLastSentThirst(ServerPlayer player, boolean bool, CallbackInfo ci) {
        lastSentThirst = -1;
    }


    @Inject(method = "doTick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayer;getHealth()F",
                    ordinal = 0
            ))
    private void injectDoTickWithLastSentThirst(CallbackInfo ci) {
        if (lastSentThirst != getThirstData().getThirstLevel() || getThirstData().getSaturationLevel() == 0.0F != lastFoodSaturationZero) {
            PacketHandler.INSTANCE.getConnection().send(
                    PacketDistributor.PLAYER.with(()->(ServerPlayer)(Object)this),
                    new ClientboundSetThirstPacket(getThirstData().getThirstLevel(),getThirstData().getSaturationLevel())
            );
            lastSentThirst = getThirstData().getThirstLevel();
            lastFoodSaturationZero = getThirstData().getSaturationLevel() == 0.0F;
        }
    }

    @Inject(method = "doTick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/food/FoodData;getFoodLevel()I",
                    ordinal = 3
            ))
    private void injectDoTickWith(CallbackInfo ci) {
        if (this.getThirstData().getThirstLevel() != lastRecordedThirstLevel) {
            lastRecordedThirstLevel = getThirstData().getThirstLevel();
            //TODO
        }
    }

}
