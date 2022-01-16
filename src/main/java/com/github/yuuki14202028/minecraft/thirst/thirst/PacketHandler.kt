package com.github.yuuki14202028.minecraft.thirst.thirst

import com.github.yuuki14202028.minecraft.thirst.thirst.packets.ClientboundSetThirstPacket
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.network.NetworkDirection
import net.minecraftforge.network.NetworkRegistry
import net.minecraftforge.network.NetworkRegistry.newSimpleChannel
import net.minecraftforge.network.simple.SimpleChannel


object PacketHandler {
    private const val PROTOCOL_VERSION = "1"
    val connection: SimpleChannel = NetworkRegistry.ChannelBuilder.named(ResourceLocation(Thirst.MOD_ID,"main"))
        .networkProtocolVersion { PROTOCOL_VERSION }
        .clientAcceptedVersions { anObject: String -> PROTOCOL_VERSION == anObject }
        .serverAcceptedVersions { anObject: String -> PROTOCOL_VERSION == anObject }
        .simpleChannel()

    fun init() {
        var index = 0
        connection.messageBuilder(ClientboundSetThirstPacket::class.java, index++, NetworkDirection.PLAY_TO_CLIENT)
            .encoder(ClientboundSetThirstPacket::encode)
            .decoder(ClientboundSetThirstPacket::decode)
            .consumer(ClientboundSetThirstPacket::handle)
            .add()
    }
}