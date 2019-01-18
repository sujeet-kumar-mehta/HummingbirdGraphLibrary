package com.butterflybarchart

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.humingbirdgraphlib.R


/**
 * Created by aurigait on 17/1/17.
 */

class VerticalValueIndicatorAdapter : RecyclerView.Adapter<VerticalValueIndicatorAdapter.ViewHolder> {

    private var mActivity: Activity? = null

    private var statesList = listOf<String>("More than 75% users", "More than 50% users", "Less than 50% users", "Less than 25% users")
    private var colorList = listOf<Int>(R.color.graph_green, R.color.graph_yellow, R.color.graph_orange, R.color.graph_red)

    constructor(mActivity: Activity) {
        this.mActivity = mActivity
    }


    override fun onCreateViewHolder(parent: ViewGroup, i: Int): VerticalValueIndicatorAdapter.ViewHolder {

        var viewHolder: VerticalValueIndicatorAdapter.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)


        val viewItem = inflater.inflate(R.layout.item_text_view_bar_color, parent, false)
        viewHolder = ViewHolder(viewItem)

        return viewHolder

    }


    override fun onBindViewHolder(holder: VerticalValueIndicatorAdapter.ViewHolder, i: Int) {
        holder.textView.text = statesList[i]
        mActivity?.resources?.getColor(colorList[i])?.let { holder.verticalBarIndicator.setBackgroundColor(it) }


    }


    override fun getItemCount(): Int {
        return statesList!!.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textView: TextView
        val verticalBarIndicator: View

        init {

            textView = view.findViewById(R.id.tv)
            verticalBarIndicator = view.findViewById(R.id.vertical_bar_color_indicator)

        }
    }


}