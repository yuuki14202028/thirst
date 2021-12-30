package com.github.yuuki14202028.minecraft.thirst.thirst.interfaces;

import com.github.yuuki14202028.minecraft.thirst.thirst.ThirstProperties;
import net.minecraft.world.food.FoodProperties;

import javax.annotation.Nullable;

public interface ItemAdditionalData {

    @Nullable
    public ThirstProperties getThirstProperties();
    public void setThirstProperties(ThirstProperties properties);

    public default boolean isDrinkable() {
        return this.getThirstProperties() != null;
    }
}
