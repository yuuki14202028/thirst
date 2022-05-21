package com.github.yuuki14202028.minecraft.thirst.thirst

import com.github.yuuki14202028.minecraft.thirst.thirst.client.ThirstRender
import com.github.yuuki14202028.minecraft.thirst.thirst.config.ThirstConfig
import com.github.yuuki14202028.minecraft.thirst.thirst.effect.ModEffects
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.LivingEntity
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.gui.ForgeIngameGui
import net.minecraftforge.client.gui.OverlayRegistry
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext


@Mod("thirst")
class Thirst {
    init {
        val modEventBus = FMLJavaModLoadingContext.get().modEventBus

        modEventBus.addListener(this::setup)
        MinecraftForge.EVENT_BUS.register(this)

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ThirstConfig.SPEC, "thirst.toml")
        //items

        //effects
        ModEffects.registerBus(modEventBus)

        DistExecutor.safeRunWhenOn(Dist.CLIENT) {
            DistExecutor.SafeRunnable {
                MinecraftForge.EVENT_BUS.register(ThirstRender)
                OverlayRegistry.registerOverlayAbove(ForgeIngameGui.FOOD_LEVEL_ELEMENT,"Thirst Level") { gui, mStack, partialTicks, width, height ->
                    val isMounted = Minecraft.getInstance().player?.vehicle is LivingEntity
                    if (!isMounted && !Minecraft.getInstance().options.hideGui && gui.shouldDrawSurvivalElements()) {
                        gui.setupOverlayRenderState(true, false)
                        ThirstRender.renderThirst(gui, width, height, mStack)
                    }
                }
            }
        }

        PacketHandler.init()
    }

    @SubscribeEvent
    fun setup(event: FMLCommonSetupEvent) {
        ThirstConfig.register()
    }

    companion object {
        const val MOD_ID = "thirst"
    }
}