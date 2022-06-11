package com.github.yuuki14202028.minecraft.thirst.thirst.client

import com.github.yuuki14202028.minecraft.thirst.thirst.Thirst
import com.github.yuuki14202028.minecraft.thirst.thirst.effect.ModEffects
import com.github.yuuki14202028.minecraft.thirst.thirst.interfaces.PlayerAdditionalData
import com.github.yuuki14202028.minecraft.thirst.thirst.mixins.GuiAccessor
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiComponent
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.client.gui.ForgeIngameGui


@OnlyIn(Dist.CLIENT)
object ThirstRender {
    private val mc by lazy { Minecraft.getInstance() }

    val GUI_ICONS = ResourceLocation(Thirst.MOD_ID, "textures/gui/icons.png")

    fun renderThirst(gui: ForgeIngameGui,width:Int,height:Int,matrixStack:PoseStack) {
        val player = (gui as GuiAccessor).invokeCameraPlayer()
        mc.profiler.push("thirst")
        RenderSystem.enableBlend()
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderTexture(0, GUI_ICONS)
        val xBase: Int = width / 2 + 91
        val yBase: Int = height - gui.right_height
        gui.right_height += 10
        val thirstAmount = (player as PlayerAdditionalData).thirstData.thirstLevel
        for (k3 in 0..9) {
            val xLoc = xBase - k3 * 8 - 9
            var yLoc = yBase
            if ((player as PlayerAdditionalData).thirstData.saturationLevel <= 0.0f && gui.guiTicks % (thirstAmount * 3 + 1) == 0) {
                yLoc = yBase + ((gui as GuiAccessor).random().m_188503_(3) - 1)
            }
            var shift = 0
            if (player.hasEffect(ModEffects.THIRST.get())) {
                shift += 18
            }
            gui.blit(matrixStack,xLoc,yLoc,1,0,7,9)
            if (k3 * 2 + 1 < thirstAmount) {
                gui.blit(matrixStack, xLoc, yLoc, 18 + 1 + shift, 0, 7, 9)
            }

            if (k3 * 2 + 1 == thirstAmount) {
                gui.blit(matrixStack, xLoc, yLoc, 9+1 + shift, 0, 7, 9)
            }
        }
        RenderSystem.disableBlend()
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        RenderSystem.setShaderTexture(0,GuiComponent.GUI_ICONS_LOCATION)
        mc.profiler.pop()
    }
}