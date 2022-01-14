package com.github.yuuki14202028.minecraft.thirst.thirst

import com.github.yuuki14202028.minecraft.thirst.thirst.client.ThirstRender
import com.github.yuuki14202028.minecraft.thirst.thirst.effect.ModEffects
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.block.Blocks
import net.minecraftforge.client.gui.ForgeIngameGui
import net.minecraftforge.client.gui.OverlayRegistry
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.server.ServerStartingEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.InterModComms
import net.minecraftforge.fml.InterModComms.IMCMessage
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.LogManager
import java.util.stream.Collectors


// The value here should match an entry in the META-INF/mods.toml file
@Mod("thirst")
class Thirst {
    init {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().modEventBus.addListener { event: FMLCommonSetupEvent -> setup(event) }
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().modEventBus.addListener { event: InterModEnqueueEvent -> enqueueIMC(event) }
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().modEventBus.addListener { event: InterModProcessEvent -> processIMC(event) }

        val modEventBus = FMLJavaModLoadingContext.get().modEventBus
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

    private fun setup(event: FMLCommonSetupEvent) {
        // some preinit code
        logger.info("HELLO FROM PREINIT")
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.registryName)

        event.enqueueWork(PacketHandler::init)
    }

    private fun enqueueIMC(event: InterModEnqueueEvent) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("thirst", "helloworld") {
            logger.info("Hello world from the MDK")
            "Hello world"
        }
    }

    private fun processIMC(event: InterModProcessEvent) {
        // some example code to receive and process InterModComms from other mods
        logger.info("Got IMC {}", event.imcStream.map { m: IMCMessage -> m.messageSupplier().get() }.collect(Collectors.toList()))
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    fun onServerStarting(event: ServerStartingEvent?) {
        // do something when the server starts
        logger.info("HELLO from server starting")
    }

    companion object {
        // Directly reference a log4j logger.
        val logger = LogManager.getLogger()

        const val MOD_ID = "thirst"
    }
}