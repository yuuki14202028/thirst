package com.github.yuuki14202028.minecraft.thirst.thirst.client

import com.github.yuuki14202028.minecraft.thirst.thirst.Thirst
import net.minecraft.client.Minecraft
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(value = [Dist.CLIENT])
object RenderHook {
    @SubscribeEvent
    fun renderGameOverlay(event:RenderGameOverlayEvent) {
        Thirst.logger.info("Thirst RenderGameOverlay Event starting.")
    }
}