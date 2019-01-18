package com.butterflybarchart

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.ImageView
import com.humingbirdgraphlib.R


import java.util.ArrayList


class GraphCanvas : ImageView {

    private var paintOrange: Paint? = null
    private var paintRed: Paint? = null
    private var paintGreen: Paint? = null
    private var paintYellow: Paint? = null
    private var paintOrangeShade: Paint? = null
    private var paintRedShade: Paint? = null
    private var paintGreenShade: Paint? = null
    private var paintYellowShade: Paint? = null

    var pathOrange = Path()
    var pathRed = Path()
    var pathGreen = Path()
    var pathYellow = Path()

    var pathOrangeShade = Path()
    var pathGreenShade = Path()
    var pathRedShade = Path()
    var pathYellowShade = Path()


    var pointsRed: MutableList<Point> = ArrayList()
    var pointsGreen: MutableList<Point> = ArrayList()
    var pointsOrange: MutableList<Point> = ArrayList()
    var pointsYellow: MutableList<Point> = ArrayList()

    var mContext: Context? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }


    fun init(context: Context) {
        this.mContext = context
        setPaint()

        for (i in 0..2) {
            val point = Point()
            point.x = (90 * i).toFloat()
            point.y = 300f
            pointsOrange.add(point)
        }

        for (i in 0..2) {
            val point = Point()
            point.x = (90 * i).toFloat()
            point.y = 450f
            pointsRed.add(point)
        }
        for (i in 0..2) {
            val point = Point()
            point.x = (90 * i).toFloat()
            point.y = 50f
            pointsGreen.add(point)
        }

        for (i in 0..2) {
            val point = Point()
            point.x = (90 * i).toFloat()
            point.y = 175f
            pointsYellow.add(point)
        }


        pathCreation()


    }

    public fun setPaint() {
        paintOrange = newPaintWithColor(getColorInString(R.color.graph_orange))
        paintOrangeShade = newPaintWithShadeColor(
            getColorInStringWithAlpha(R.color.graph_alph20_orange),
            getColorInStringWithAlpha(R.color.graph_alph10_orange),
            pointsOrange
        )

        paintGreen = newPaintWithColor(getColorInString(R.color.graph_green))
        paintGreenShade = newPaintWithShadeColor(
            getColorInStringWithAlpha(R.color.graph_alph20green),
            getColorInStringWithAlpha(R.color.graph_alph10green),
            pointsGreen
        )

        paintYellow = newPaintWithColor(getColorInString(R.color.graph_yellow))
        paintYellowShade = newPaintWithShadeColor(
            getColorInStringWithAlpha(R.color.graph_alph20_yellow),
            getColorInStringWithAlpha(R.color.graph_alph10_yellow),
            pointsYellow
        )

        paintRed = newPaintWithColor(getColorInString(R.color.graph_red))
        paintRedShade = newPaintWithShadeColor(
            getColorInStringWithAlpha(R.color.graph_alph20red),
            getColorInStringWithAlpha(R.color.graph_alph10red),
            pointsRed
        )
    }

    public fun pathCreation() {
        pathOrange = createWavePath(pointsOrange, 0)
        pathOrangeShade = createWavePath(pointsOrange, 50)

        pathRed = createWavePath(pointsRed, 0)
        pathRedShade = createWavePath(pointsRed, 50)

        pathYellow = createWavePath(pointsYellow, 0)
        pathYellowShade = createWavePath(pointsYellow, 50)


        pathGreen = createWavePath(pointsGreen, 0)
        pathGreenShade = createWavePath(pointsGreen, 50)
    }

    private fun createWavePath(points: List<Point>, yAddition: Int): Path {

        // line path
//        val path = Path()
//        var first = true
//        for (point in points) {
//            if (first) {
//                first = false
//                path.moveTo(point.x, point.y + yAddition)
//            } else {
//                path.lineTo(point.x, point.y + yAddition)
//            }
//        }
//        return path

        //================ curve path
        val pathCurve = Path()

        var first = true
        var i = 0
        while (i < points.size) {
            val point = points[i]
            if (first) {
                first = false
                pathCurve.moveTo(point.x, point.y + yAddition)
            } else if (i < points.size - 1) {
                val next = points[i + 1]
                pathCurve.quadTo(point.x, point.y + yAddition, next.x, next.y + yAddition)
            } else {
                pathCurve.lineTo(point.x, point.y + yAddition)
            }
            i += 1
        }
        return pathCurve
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawGraph(canvas)

    }

    var isShowGradiant: Boolean? = true

    private fun drawGraph(canvas: Canvas) {
        canvas.drawPath(pathOrange, paintOrange!!)

        canvas.drawPath(pathGreen, paintGreen!!)

        canvas.drawPath(pathYellow, paintYellow!!)
        canvas.drawPath(pathRed, paintRed!!)


        if (this!!.isShowGradiant!!) {
            canvas.drawPath(pathYellowShade, paintYellowShade!!)
            canvas.drawPath(pathOrangeShade, paintOrangeShade!!)
            canvas.drawPath(pathGreenShade, paintGreenShade!!)

            canvas.drawPath(pathRedShade, paintRedShade!!)
        }
    }

//    override fun dispatchDraw(canvas: Canvas?) {
//        super.dispatchDraw(canvas)
////        init(context)
//        drawGraph(canvas!!)
//
//    }

    private fun newPaintWithShadeColor(color: String, endColor: String, pointArray: MutableList<Point>): Paint {
        var point: Point? = Point()

        if (pointArray.size > 0) {
            point = pointArray.get(0);
        }
        val paintOrangeShade = Paint(Paint.ANTI_ALIAS_FLAG)
        paintOrangeShade.style = Paint.Style.STROKE
        paintOrangeShade.strokeJoin = Paint.Join.ROUND
        paintOrangeShade.strokeWidth = 100f
//        paintOrangeShade.color = Color.parseColor(color)
        paintOrangeShade.shader = LinearGradient(
            point?.x?.toFloat()!!,
            point?.y?.toFloat()!!,
            point?.x?.toFloat()!!,
            point?.y?.toFloat().plus(100f)!!,
            Color.parseColor(color),
            Color.WHITE,
            Shader.TileMode.MIRROR
        ) as Shader?
//        paintOrangeShade.shader = RadialGradient(0f, 0f, 100f, Color.parseColor(color), Color.WHITE, Shader.TileMode.MIRROR) as Shader?
        return paintOrangeShade
    }

    private fun newPaintWithColor(color: String): Paint {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeWidth = 2f

        paint.color = Color.parseColor(color)
        return paint
    }


    fun getColorInString(colorResource: Int): String {
        return "#" + Integer.toHexString(ContextCompat.getColor(mContext!!, colorResource) and 0x00ffffff)
    }

    fun getColorInStringWithAlpha(colorResource: Int): String {
        return "#" + Integer.toHexString(ContextCompat.getColor(mContext!!, colorResource))
    }
}

class Point {
    constructor(p0: Float, p1: Float) {
        this.x = p0
        this.y = p1
    }

    constructor()

    var x: Float = 0.toFloat()
    var y: Float = 0.toFloat()


    override fun toString(): String {
        return x.toString() + ", " + y
    }
}
