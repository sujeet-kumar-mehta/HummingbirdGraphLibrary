package com.butterflybarchart

import android.app.Activity
import android.graphics.Point
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.humingbirdgraphlib.R


/**
 * Created by aurigait on 17/1/17.
 */

class VerticalBarAdapter : RecyclerView.Adapter<VerticalBarViewHolder> {

    private var verticalBarList: ArrayList<VerticalBar>? = arrayListOf()
    private var mActivity: Activity? = null

    internal var maxHeight = 188

    internal var barWidth = 12

    public var sizeOfClickableNumber: Int? = 0
    public var onVerticalBarClick: ((verticalBar: VerticalBar) -> Unit)? = null

    constructor(mActivity: Activity, mAwardList: ArrayList<VerticalBar>) {
        this.verticalBarList = mAwardList
        this.mActivity = mActivity

        // this.onVerticalBarClick=pnVerticalbarClick;
    }

    constructor(mActivity: Activity) {
        this.verticalBarList = verticalBarList
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): VerticalBarViewHolder {

        var viewHolder: VerticalBarViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        val viewItem = inflater.inflate(R.layout.item_vertical_bar, parent, false)
        viewHolder = VerticalBarViewHolder(viewItem)

        return viewHolder

    }


    var lastClickedBarView: View? = null

    override fun onBindViewHolder(holder: VerticalBarViewHolder, i: Int) {

        try {
            val verticalBar = verticalBarList!![i]
            setBarView(holder, verticalBar)

            holder.itemView.setOnClickListener { v ->


                if (verticalBar?.isClicable!!) {
                    sizeOfClickableNumber = i
                    actionOnClick(v, verticalBar, holder)
                }

            }

            if (i == sizeOfClickableNumber?.minus(1)) {
                lastClickedBarView = holder.barView

                holder.itemView.postDelayed({
                    actionOnClick(holder.itemView, verticalBar, holder)

                }, 600)
            }
        } catch (e: Exception) {
        }

    }

    private fun actionOnClick(v: View, verticalBar: VerticalBar, holder: VerticalBarViewHolder) {
        lastClickedBarView!!.animate()
                .scaleX(1.0f)
                //                                .y(event.getRawY() + dY)
                .setDuration(200)
                .start()

        val location = IntArray(2)
        v.getLocationOnScreen(location)
        verticalBar.setxPoint(location[0].toFloat())
        verticalBar.setyPoint(location[1].toFloat())
        onVerticalBarClick?.invoke(verticalBar)
        Log.i("location", "x" + location[0].toFloat().toString())
        holder.barView.postDelayed({
            lastClickedBarView = holder.barView
            holder.barView.animate()
                    .scaleX(1.4f)
                    //                                .y(event.getRawY() + dY)
                    .setDuration(200)
                    .start()
        }, 150)
    }


    override fun onViewRecycled(holder: VerticalBarViewHolder) {
        super.onViewRecycled(holder)
        //  holder.barView.clearAnimation()
    }


    private fun setBarView(holder: VerticalBarViewHolder, verticalBar: VerticalBar) {
//        holder.graphCanvas.pointsRed.addAll(verticalBar.pointsRed)
//        holder.graphCanvas.pointsOrange.addAll(verticalBar.pointsOrange)
//        holder.graphCanvas.pointsYellow = verticalBar.pointsYellow
//        holder.graphCanvas.pointsGreen = verticalBar.pointsGreen
//        holder.graphCanvas.pathCreation()
//        holder.graphCanvas.setPaint()
//        holder.graphCanvas.isShowGradiant = sizeOfClickableNumber == 1
//        holder.graphCanvas.postInvalidate()


        //holder.graphCanvas.visibility= if(sizeOfClickableNumber==1) VISIBLE else GONE


        val widthDP = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, barWidth.toFloat(), mActivity?.resources?.displayMetrics).toInt()
        val hightDP = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, maxHeight * verticalBar.percentage, mActivity?.resources?.displayMetrics).toInt()

        val layoutParams = FrameLayout.LayoutParams(widthDP, hightDP)
        layoutParams.gravity = Gravity.CENTER or Gravity.BOTTOM

        holder.barView.setBackgroundColor(mActivity?.resources?.getColor(verticalBar.backGroundColor)!!)
        holder.barView.layoutParams = layoutParams

        if (verticalBar.paid!!) {
            holder.barView.visibility = VISIBLE
            holder.lockImageView.visibility = GONE
            holder.itemView.setBackgroundColor(mActivity?.resources?.getColor(android.R.color.transparent)!!)

        } else {
            holder.barView.visibility = GONE
            holder.lockImageView.visibility = VISIBLE
            holder.itemView.setBackgroundColor(mActivity?.resources?.getColor(R.color.graph_alph20gray)!!)
        }
//        holder.barView.clearAnimation()
//        holder.barView.animate()
//                .scaleX(1.0f)
//                //                                .y(event.getRawY() + dY)
//                .setDuration(300)
//                .start()


    }


    override fun getItemCount(): Int {
        return verticalBarList!!.size
    }


}

class VerticalBarViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var barView: View
    var lockImageView: ImageView
    var graphCanvas: GraphCanvas
//    var frame_layout:FrameLayout

    init {

        barView = view.findViewById(R.id.bar_view) as View
        lockImageView = view.findViewById(R.id.lock_ImageView) as ImageView
        graphCanvas = view.findViewById(R.id.graphCanvas) as GraphCanvas
//        frame_layout = view.findViewById(R.id.frame_layout)  as FrameLayout

    }
}
