package com.github.yuuki14202028.minecraft.thirst.thirst

class ThirstProperties(
    private var hydration: Int,
    private var saturationModifier: Float,
    private var canAlwaysDrink: Boolean,
    private var fastDrink: Boolean
){

    constructor (builder: Builder) : this(
        hydration = builder.hydration,
        saturationModifier = builder.saturationModifier,
        canAlwaysDrink = builder.canAlwaysDrink,
        fastDrink = builder.fastFood
    )

    fun getHydration(): Int {
        return hydration
    }

    fun getSaturationModifier(): Float {
        return saturationModifier
    }

    fun canAlwaysDrink(): Boolean {
        return canAlwaysDrink
    }

    fun isFastFood(): Boolean {
        return fastDrink
    }

    fun isThirst():Boolean {
        return hydration <= 0
    }

    class Builder {
        var hydration = 0
        var saturationModifier = 0f
        var canAlwaysDrink = false
        var fastFood = false
        fun hydration(p_38761_: Int): Builder {
            hydration = p_38761_
            return this
        }

        fun saturationMod(p_38759_: Float): Builder {
            saturationModifier = p_38759_
            return this
        }

        fun alwaysEat(always: Boolean): Builder {
            canAlwaysDrink = always
            return this
        }

        fun fast(fast: Boolean): Builder {
            fastFood = fast
            return this
        }

        fun build(): ThirstProperties {
            return ThirstProperties(this)
        }
    }
}