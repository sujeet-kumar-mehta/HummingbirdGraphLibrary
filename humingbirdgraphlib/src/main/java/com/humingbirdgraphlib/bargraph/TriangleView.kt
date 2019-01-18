package com.butterflybarchart

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.util.AttributeSet
import android.view.View
import com.humingbirdgraphlib.R


/**
 * The `TriangleView` allows for simple drawing of the triangle shape. The actual size and
 * shape if the triangle is determined by the size of this view. The direction and color can be set
 * in code using the public methods or via the XML attributes: `tr_direction` and
 * `tr_color`
 *
 * @author Itai Hanski
 */
class TriangleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var mPaint: Paint? = null
    private var mTrianglePath: Path? = null
    private var mDirection: Direction? = null
    private var mColor: Int = 0

    //
    // Utility Methods
    //

    private val trianglePath: Path
        get() {
            if (mTrianglePath == null) {
                mTrianglePath = Path()
                val width = width
                val height = height
                val p1: Point
                val p2: Point
                val p3: Point
                when (mDirection) {
                    TriangleView.Direction.LEFT -> {
                        p1 = Point(width, 0)
                        p2 = Point(width, height)
                        p3 = Point(0, height / 2)
                    }
                    TriangleView.Direction.UP -> {
                        p1 = Point(0, height)
                        p2 = Point(width, height)
                        p3 = Point(width / 2, 0)
                    }
                    TriangleView.Direction.RIGHT -> {
                        p1 = Point(0, 0)
                        p2 = Point(0, height)
                        p3 = Point(width, height / 2)
                    }
                    TriangleView.Direction.DOWN -> {
                        p1 = Point(0, 0)
                        p2 = Point(width, 0)
                        p3 = Point(width / 2, height)
                    }
                    else -> {
                        p1 = Point(0, 0)
                        p2 = Point(width, 0)
                        p3 = Point(width / 2, height)
                    }
                }
                mTrianglePath!!.moveTo(p1.x.toFloat(), p1.y.toFloat())
                mTrianglePath!!.lineTo(p2.x.toFloat(), p2.y.toFloat())
                mTrianglePath!!.lineTo(p3.x.toFloat(), p3.y.toFloat())
            }
            return mTrianglePath as Path
        }

    init {
        init(attrs)
    }

    //
    // Initialization
    //

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.TriangleView)
            when (a.getInt(R.styleable.TriangleView_tr_direction, 0)) {
                0 -> mDirection = Direction.LEFT
                1 -> mDirection = Direction.UP
                2 -> mDirection = Direction.RIGHT
                3 -> mDirection = Direction.DOWN
                else -> mDirection = Direction.DOWN
            }
            mColor = a.getColor(R.styleable.TriangleView_tr_color, DEFAULT_COLOR)
            a.recycle()
        } else {
            mDirection = DEFAULT_DIRECTION
            mColor = DEFAULT_COLOR
        }

        mPaint = Paint()
        mPaint!!.style = Paint.Style.FILL
        mPaint!!.strokeWidth = 2f
        mPaint!!.color = mColor
        mPaint!!.isAntiAlias = true
    }

    //
    // Public API
    //

    /**
     * Set the color of the triangle.
     *
     * @param color the color of the triangle.
     */
    fun setColor(color: Int) {
        if (mColor != color) {
            mColor = color
            if (mPaint != null) {
                mPaint!!.color = color
            }
            mTrianglePath = null
            invalidate()
        }
    }

    /**
     * Set the direction of the triangle.
     *
     * @param direction the direction of the triangle.
     */
    fun setDirection(direction: Direction) {
        if (direction != mDirection) {
            mDirection = direction
            mTrianglePath = null
        }
        invalidate()
    }

    //
    // View Overrides
    //

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(trianglePath, mPaint!!)
    }

    //
    // Direction
    //

    enum class Direction {
        LEFT,
        UP,
        RIGHT,
        DOWN
    }

    companion object {

        private val DEFAULT_DIRECTION = Direction.LEFT
        private val DEFAULT_COLOR = -0x8a8a8b
    }
}

