package com.humingbirdgraphlib.spiralgraph


import android.graphics.Color

class PieHelper {

    var startDegree: Float = 0.toFloat()
        private set
    var endDegree: Float = 0.toFloat()
        private set
    private var targetStartDegree: Float = 0.toFloat()
    private var targetEndDegree: Float = 0.toFloat()
    var title: String? = null
        private set
    var color: Int = 0
        private set
    var sweep: Float = 0.toFloat()
        private set

    internal var velocity = 5

    internal val isColorSetted: Boolean
        get() = color != 0

    internal val isAtRest: Boolean
        get() = startDegree == targetStartDegree && endDegree == targetEndDegree

    internal val percentStr: String
        get() {
            val percent = (sweep / 360 * 100).toInt()
            return "$percent%"
        }

    constructor(percent: Float, color: Int) : this(percent, null, color) {}

    /**
     * @param percent from 0 to 100
     * @param title
     */
//    @JvmOverloads
//     constructor(percent: Float, title: String) : this(percent, title, 0) {}

    /**
     * @param percent from 0 to 100
     * @param title
     * @param color
     */
    @JvmOverloads
    constructor(percent: Float, title: String? = null, color: Int = 0) {
        this.sweep = percent * 360 / 100
        this.title = title
        this.color = color
    }

    internal constructor(startDegree: Float, endDegree: Float, targetPie: PieHelper) {
        this.startDegree = startDegree
        this.endDegree = endDegree
        targetStartDegree = targetPie.startDegree
        targetEndDegree = targetPie.endDegree
        this.sweep = targetPie.sweep
        this.title = targetPie.title
        this.color = targetPie.color
    }

    internal fun setTarget(targetPie: PieHelper): PieHelper {
        this.targetStartDegree = targetPie.startDegree
        this.targetEndDegree = targetPie.endDegree
        this.title = targetPie.title
        this.color = targetPie.color
        this.sweep = targetPie.sweep
        return this
    }

    internal fun setDegree(startDegree: Float, endDegree: Float) {
        this.startDegree = startDegree
        this.endDegree = endDegree
    }

    internal fun update() {
        this.startDegree = updateSelf(startDegree, targetStartDegree, velocity)
        this.endDegree = updateSelf(endDegree, targetEndDegree, velocity)
        this.sweep = endDegree - startDegree
    }

    private fun updateSelf(origin: Float, target: Float, velocity: Int): Float {
        var origin = origin
        if (origin < target) {
            origin += velocity.toFloat()
        } else if (origin > target) {
            origin -= velocity.toFloat()
        }
        if (Math.abs(target - origin) < velocity) {
            origin = target
        }
        return origin
    }

    companion object {

        var PIE_CHART_COLOR_LIST = arrayOf(

            "#050F2C",
            "#003666",
            "#00AEFF",
            "#3369E7",
            "#8E43E7",
            "#B84592",
            "#FF4F81",
            "#FF6C5F",
            "#FFC168",
            "#2DDE98",
            "#1CC7D0"
        )

        val randomColor: Int
            get() {
                val r = (0xff * Math.random()).toInt()
                val g = (0xff * Math.random()).toInt()
                val b = (0xff * Math.random()).toInt()
                return Color.rgb(r, g, b)
            }
    }
}
/**
 * @param percent from 0 to 100
 */