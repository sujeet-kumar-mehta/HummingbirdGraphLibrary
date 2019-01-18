package com.butterflybarchart

import com.humingbirdgraphlib.R
import java.util.ArrayList

class VerticalBar {
    var height: Int = 0
    var width: Int = 0
    var commentAbs: String? = null
    var commentProgression: String? = null
    var paid: Boolean? = null
    var isClicable: Boolean? = true
    var tip: String? = null
    internal var xPoint: Float = 0.toFloat()
    internal var yPoint: Float = 0.toFloat()
    var percentage: Float = 0.toFloat()

    var dateString: String? = null
    var color: Int = 0

    var percentile0: Double? = null

    var percentile25: Double? = null

    var percentile50: Double? = null

    var percentile75: Double? = null

    var percentile100: Double? = null

    var pointsRed: MutableList<Point> = ArrayList()
    var pointsGreen: MutableList<Point> = ArrayList()
    var pointsOrange: MutableList<Point> = ArrayList()
    var pointsYellow: MutableList<Point> = ArrayList()

    val backGroundColor: Int
        get() {
            if (this!!.paid!!) {
                if (percentage <= 0.25) {
                    return R.color.graph_red

                } else if (percentage > 0.25 && percentage <= 0.50) {
                    return R.color.graph_orange

                } else if (percentage > 0.5 && percentage <= 0.75) {
                    return R.color.graph_yellow

                } else if (percentage > 0.75) {
                    return R.color.graph_green

                }
            } else {
                return R.color.grey
            }
            return android.R.color.white
        }

    val alphaBackGroundColor: Int
        get() {
            if (this!!.paid!!) {
                if (percentage <= 0.25) {
                    return R.color.graph_alph20red

                } else if (percentage > 0.25 && percentage <= 0.50) {
                    return R.color.graph_alph20_orange

                } else if (percentage > 0.5 && percentage <= 0.75) {
                    return R.color.graph_alph20_yellow

                } else if (percentage > 0.75) {
                    return R.color.graph_alph20green

                }
            } else {
                return R.color.grey

            }
            return android.R.color.white
        }

    fun getxPoint(): Float {
        return xPoint
    }

    fun setxPoint(xPoint: Float) {
        this.xPoint = xPoint
    }

    fun getyPoint(): Float {
        return yPoint
    }

    fun setyPoint(yPoint: Float) {
        this.yPoint = yPoint
    }
}
