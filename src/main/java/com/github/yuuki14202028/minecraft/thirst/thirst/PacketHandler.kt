package com.github.yuuki14202028.minecraft.thirst.thirst

import com.github.yuuki14202028.minecraft.thirst.thirst.packets.ClientboundSetThirstPacket
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.network.NetworkDirection
import net.minecraftforge.network.NetworkRegistry


object PacketHandler {
    private const val PROTOCOL_VERSION = "1"
    val Connection = NetworkRegistry.newSimpleChannel(
        ResourceLocation(Thirst.MOD_ID, "main"),
        { PROTOCOL_VERSION },
        { anObject: String? -> PROTOCOL_VERSION == anObject }
    ) { anObject: String? -> PROTOCOL_VERSION == anObject }
    fun init() {
        var index = 0
        Connection.messageBuilder(ClientboundSetThirstPacket::class.java, index++, NetworkDirection.PLAY_TO_CLIENT)
            .encoder(ClientboundSetThirstPacket::encode)
            .decoder(ClientboundSetThirstPacket::decode)
            .consumer(ClientboundSetThirstPacket::handle)
            .add()
    }
}