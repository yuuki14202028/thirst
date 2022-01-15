package com.github.yuuki14202028.minecraft.thirst.thirst

import com.github.yuuki14202028.minecraft.thirst.thirst.client.ThirstRender
import com.github.yuuki14202028.minecraft.thirst.thirst.effect.ModEffects
import com.github.yuuki14202028.minecraft.thirst.thirst.items.ModItems
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.LivingEntity
import net.minecraftforge.client.gui.ForgeIngameGui
import net.minecraftforge.client.gui.OverlayRegistry
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext

@Mod("thirst")
class Thirst {
    init {
        val modEventBus = FMLJavaModLoadingContext.get().modEventBus

        //items
        ModItems.registerBus(modEventBus)
        ModItems.changeVanillaItemProperties()

        //effects
        ModEffects.registerBus(modEventBus)

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this)
        MinecraftForge.EVENT_BUS.register(ThirstRender)

        OverlayRegistry.registerOverlayAbove(ForgeIngameGui.FOOD_LEVEL_ELEMENT,"Thirst Level") { gui, mStack, partialTicks, width, height ->
            val isMounted = Minecraft.getInstance().player?.vehicle is LivingEntity
            if (!isMounted && !Minecraft.getInstance().options.hideGui && gui.shouldDrawSurvivalElements()) {
                gui.setupOverlayRenderState(true, false)
                ThirstRender.renderThirst(gui, width, height, mStack)
            }
        }
    }

    companion object {
        const val MOD_ID = "thirst"
    }
}