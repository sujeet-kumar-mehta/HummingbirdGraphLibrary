package com.humingbirdgraphlib.spiralgraph

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.util.*

class SpiralPieChartView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {

    private val cirPaint: Paint
    private val whiteLinePaint: Paint
    private val pieCenterPoint: Point
    private val textPaint: Paint
    private val cirRect: RectF
    private val cirSelectedRect: RectF

    private var mViewWidth: Int = 0
    private var mViewHeight: Int = 0
    private var margin: Int = 0
    private var pieRadius: Int = 0
    private var increment: Float = 0.toFloat()
    private var onPieClickListener: OnPieClickListener? = null

    private var pieHelperList: ArrayList<PieHelper> = arrayListOf()
    private var selectedIndex = NO_SELECTED_INDEX

    private var mStartDistance = 0.0f

    private var mIncrementDistance = 0.0f

    private var ONE_CHAR_LENGTH = 10f

    private var maxCharPossibleToWrite = 15

    private var showPercentLabel = true
    private val DEFAULT_COLOR_LIST = intArrayOf(
        Color.parseColor("#33B5E5"),
        Color.parseColor("#AA66CC"),
        Color.parseColor("#99CC00"),
        Color.parseColor("#FFBB33"),
        Color.parseColor("#FF4444")
    )
    private val animator = object : Runnable {
        override fun run() {
            var needNewFrame = false
            for (pie in pieHelperList) {
                pie.update()
                if (!pie.isAtRest) {
                    needNewFrame = true
                }
            }
            if (needNewFrame) {
                postDelayed(this, 10)
            }
            invalidate()
        }
    }

    interface OnPieClickListener {
        fun onPieClick(index: Int)
    }

    init {

        pieHelperList = ArrayList()
        cirPaint = Paint()
        cirPaint.isAntiAlias = true
        cirPaint.color = Color.GRAY
        whiteLinePaint = Paint(cirPaint)
        whiteLinePaint.color = Color.WHITE
        whiteLinePaint.strokeWidth = 4f
        textPaint = Paint()
        textPaint.isAntiAlias = true
        textPaint.color = Color.WHITE
        textPaint.textSize = sp2px(getContext(), 10f).toFloat()
        textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        textPaint.strokeWidth = 5f
        textPaint.textAlign = Paint.Align.CENTER
        pieCenterPoint = Point()
        cirRect = RectF()
        cirSelectedRect = RectF()
        mStartDistance = convertDpToPixel(18f)
        mIncrementDistance = convertDpToPixel(5f)
        ONE_CHAR_LENGTH = sp2px(getContext(), 10f).toFloat()
        increment = mStartDistance

    }

    fun showPercentLabel(show: Boolean) {
        showPercentLabel = show
        postInvalidate()
    }

    fun setOnPieClickListener(listener: OnPieClickListener) {
        onPieClickListener = listener
    }

    fun setDate(helperList: ArrayList<PieHelper>?) {
        initPies(helperList!!)
        pieHelperList.clear()
        removeSelectedPie()

        if (helperList != null && !helperList.isEmpty()) {
            for (pieHelper in helperList) {
                pieHelperList.add(PieHelper(pieHelper.startDegree, pieHelper.startDegree, pieHelper))
            }
        } else {
            pieHelperList.clear()
        }

        removeCallbacks(animator)
        post(animator)

        // pieHelperList = helperList;
        // postInvalidate();
    }

    /**
     * Set startDegree and endDegree for each PieHelper
     *
     * @param helperList
     */
    private fun initPies(helperList: ArrayList<PieHelper>) {
        var totalAngel = 270f
        for (pie in helperList) {
            pie.setDegree(totalAngel, totalAngel + pie.sweep)
            totalAngel += pie.sweep
        }
    }

    fun selectedPie(index: Int) {
        selectedIndex = index
        if (onPieClickListener != null) onPieClickListener!!.onPieClick(index)
        postInvalidate()
    }

    fun removeSelectedPie() {
        selectedIndex = NO_SELECTED_INDEX
        if (onPieClickListener != null) onPieClickListener!!.onPieClick(NO_SELECTED_INDEX)
        postInvalidate()
    }


    override fun onDraw(canvas: Canvas) {
        if (pieHelperList.isEmpty()) {
            return
        }
        increment = mStartDistance
        var index = 0
        for (pieHelper in pieHelperList) {
            val selected = selectedIndex == index
            val rect = if (selected) cirSelectedRect else cirRect
            if (pieHelper.isColorSetted) {
                cirPaint.color = pieHelper.color
            } else {
                cirPaint.color = DEFAULT_COLOR_LIST[index % 5]
            }

            val rectF = RectF()

            val mChangingRadius = pieRadius - pieRadius / 8 * index
            rectF.set(
                (pieCenterPoint.x - mChangingRadius).toFloat(),
                (pieCenterPoint.y - mChangingRadius).toFloat(),
                (pieCenterPoint.x + mChangingRadius).toFloat(),
                (pieCenterPoint.y + mChangingRadius).toFloat()
            )
            canvas.drawArc(rectF, pieHelper.startDegree, pieHelper.sweep, true, cirPaint)


            textPaint.color = pieHelper.color
            whiteLinePaint.color = pieHelper.color

            if (pieHelper.sweep > 3.6 * 2)
                drawPercentText(canvas, pieHelper, mChangingRadius)


            // drawText(canvas, pieHelper);
            //  drawLineBesideCir(canvas, pieHelper.getStartDegree(), selected);
            //            drawLineBesideCir(canvas, pieHelper.getEndDegree(), selected);
            index++


        }


    }

    private fun drawLineBesideCir(canvas: Canvas, angel: Float, selectedCir: Boolean) {
        val sth2 =
            if (selectedCir) mViewHeight / 2 else pieRadius // Sorry I'm really don't know how to name the variable..
        var sth = 1 // And it's
        if (angel % 360 > 180 && angel % 360 < 360) {
            sth = -1
        }
        val lineToX = (mViewHeight / 2 + Math.cos(Math.toRadians((-angel).toDouble())) * sth2).toFloat()
        val lineToY =
            (mViewHeight / 2 + sth.toDouble() * Math.abs(Math.sin(Math.toRadians((-angel).toDouble()))) * sth2.toDouble()).toFloat()
        canvas.drawLine(pieCenterPoint.x.toFloat(), pieCenterPoint.y.toFloat(), lineToX, lineToY, whiteLinePaint)
    }


    private fun drawPercentText(canvas: Canvas, pieHelper: PieHelper, mChangingRadius: Int) {
        if (!showPercentLabel) return
        val angel = (pieHelper.startDegree + pieHelper.endDegree) / 2
        var sth = 1
        if (angel % 360 > 180 && angel % 360 < 360) {
            sth = -1
        }
        val changingTextRadius = (this.pieRadius + increment).toInt()
        //int pieRadius= (int) (this.pieRadius + increment);
        val x = (mViewHeight / 2 + Math.cos(Math.toRadians((-angel).toDouble())) * changingTextRadius).toFloat()
        val y =
            (mViewHeight / 2 + sth.toDouble() * Math.abs(Math.sin(Math.toRadians((-angel).toDouble()))) * changingTextRadius.toDouble()).toFloat()

        var text = pieHelper.title
        if (text!!.length > maxCharPossibleToWrite) {
            text = pieHelper.title!!.subSequence(0, maxCharPossibleToWrite) as String
        }
        canvas.drawText(text, x, y, textPaint)


        val lineRadius = this.pieRadius - 10f
        val xL = (mViewHeight / 2 + Math.cos(Math.toRadians((-angel).toDouble())) * lineRadius).toFloat()
        val yL =
            (mViewHeight / 2 + sth.toDouble() * Math.abs(Math.sin(Math.toRadians((-angel).toDouble()))) * lineRadius.toDouble()).toFloat()
        canvas.drawLine(pieCenterPoint.x.toFloat(), pieCenterPoint.y.toFloat(), xL, yL, whiteLinePaint)
        canvas.drawCircle(xL, yL, 5f, whiteLinePaint)
        increment = increment + mIncrementDistance

    }


    private fun checkLengthOfTextToWrite(title: String?, x: Float, y: Float): String? {
        var title = title


        if (title != null) {
            val lengthOfText = ONE_CHAR_LENGTH * title.length

            if (lengthOfText >= margin - mIncrementDistance) {
                title = title.subSequence(0, maxCharPossibleToWrite) as String
            }
        }

        return title
    }

    private fun drawText(canvas: Canvas, pieHelper: PieHelper) {
        if (pieHelper.title == null) return
        val angel = (pieHelper.startDegree + pieHelper.endDegree) / 2
        var sth = 1
        if (angel % 360 > 180 && angel % 360 < 360) {
            sth = -1
        }
        val x = ((mViewHeight + 40) / 2 + Math.cos(Math.toRadians((-angel).toDouble())) * pieRadius / 2).toFloat()
        val y =
            ((mViewHeight + 40) / 2 + sth.toDouble() * Math.abs(Math.sin(Math.toRadians((-angel).toDouble()))) * pieRadius.toDouble() / 2).toFloat()
        canvas.drawText(pieHelper.percentStr, x, y, textPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
        //            selectedIndex = findPointAt((int) event.getX(), (int) event.getY());
        //            if (onPieClickListener != null) {
        //                onPieClickListener.onPieClick(selectedIndex);
        //            }
        //            postInvalidate();
        //        }

        return true
    }

    /**
     * find pie index where point is
     *
     * @param x
     * @param y
     * @return
     */
    private fun findPointAt(x: Int, y: Int): Int {
        var degree = Math.atan2((x - pieCenterPoint.x).toDouble(), (y - pieCenterPoint.y).toDouble()) * 180 / Math.PI
        degree = -(degree - 180) + 270
        var index = 0
        for (pieHelper in pieHelperList) {
            if (degree >= pieHelper.startDegree && degree <= pieHelper.endDegree) {
                return index
            }
            index++
        }
        return NO_SELECTED_INDEX
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mViewWidth = measureWidth(widthMeasureSpec)
        mViewHeight = measureHeight(heightMeasureSpec)
        margin = mViewWidth / 5

        maxCharPossibleToWrite = (margin / ONE_CHAR_LENGTH).toInt()
        pieRadius = mViewWidth / 2 - margin
        pieCenterPoint.set(pieRadius + margin, pieRadius + margin)
        cirRect.set(
            (pieCenterPoint.x - pieRadius).toFloat(),
            (pieCenterPoint.y - pieRadius).toFloat(),
            (pieCenterPoint.x + pieRadius).toFloat(),
            (pieCenterPoint.y + pieRadius).toFloat()
        )
        cirSelectedRect.set(
            2f, //minor margin for bigger circle
            2f,
            (mViewWidth - 2).toFloat(),
            (mViewHeight - 2).toFloat()
        )
        setMeasuredDimension(mViewWidth, mViewHeight)
    }

    private fun measureWidth(measureSpec: Int): Int {
        val preferred = 3
        return getMeasurement(measureSpec, preferred)
    }

    private fun measureHeight(measureSpec: Int): Int {
        val preferred = mViewWidth
        return getMeasurement(measureSpec, preferred)
    }

    private fun getMeasurement(measureSpec: Int, preferred: Int): Int {
        val specSize = View.MeasureSpec.getSize(measureSpec)
        val measurement: Int

        when (View.MeasureSpec.getMode(measureSpec)) {
            View.MeasureSpec.EXACTLY -> measurement = specSize
            View.MeasureSpec.AT_MOST -> measurement = Math.min(preferred, specSize)
            else -> measurement = preferred
        }
        return measurement
    }

    companion object {
        val NO_SELECTED_INDEX = -999

        fun sp2px(context: Context, spValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        fun convertDpToPixel(dp: Float): Float {
            val metrics = Resources.getSystem().displayMetrics
            val px = dp * (metrics.densityDpi / 160f)
            return Math.round(px).toFloat()
        }
    }
}
