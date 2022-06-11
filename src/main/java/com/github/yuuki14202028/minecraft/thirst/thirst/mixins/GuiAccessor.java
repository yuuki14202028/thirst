package com.github.yuuki14202028.minecraft.thirst.thirst.mixins;

import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import java.util.*;


@Mixin(Gui.class)
public interface GuiAccessor {
    @Invoker("getCameraPlayer")
    Player invokeCameraPlayer();


    @Accessor("random")
    RandomSource random();
}