package com.github.yuuki14202028.minecraft.thirst.thirst.packets

import com.github.yuuki14202028.minecraft.thirst.thirst.PlayerAdditionalData
import net.minecraft.client.Minecraft
import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.network.NetworkEvent
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.Supplier


class ClientboundSetThirstPacket(private val thirst: Int, private val saturation: Float){

    public fun getThirst():Int = thirst
    public fun getSaturation():Float = saturation

    fun encode(buffer: FriendlyByteBuf) {
        buffer.writeInt(thirst)
        buffer.writeFloat(saturation)
    }

    companion object {
        @JvmStatic
        fun decode(buffer: FriendlyByteBuf): ClientboundSetThirstPacket {
            return ClientboundSetThirstPacket(
                buffer.readInt(),
                buffer.readFloat()
            )
        }
    }



    private val mc by lazy { Minecraft.getInstance() }


    fun handle(ctx: Supplier<NetworkEvent.Context>): Boolean {
        val success = AtomicBoolean(false)
        ctx.get().apply { enqueueWork {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT) {  Runnable {
                (mc.player as? PlayerAdditionalData)?.let { player ->
                    player.thirstData.thirstLevel = this@ClientboundSetThirstPacket.thirst
                    player.thirstData.setSaturation(this@ClientboundSetThirstPacket.saturation)
                    success.set(true)
                }}
            }
        } }
        ctx.get().packetHandled = true
        return success.get()
    }
}