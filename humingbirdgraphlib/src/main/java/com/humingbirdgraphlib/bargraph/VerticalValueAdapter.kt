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

class VerticalValueAdapter : RecyclerView.Adapter<VerticalValueAdapter.ViewHolder> {

    private var verticalBarList: List<String>? = null
    private var mActivity: Activity? = null


    constructor(mActivity: Activity, mAwardList: List<String>) {
        this.verticalBarList = mAwardList
        this.mActivity = mActivity
    }

    constructor(mActivity: Activity) {
        this.verticalBarList = verticalBarList
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): VerticalValueAdapter.ViewHolder {

        var viewHolder: VerticalValueAdapter.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        val viewItem = inflater.inflate(R.layout.item_text_view, parent, false)
        viewHolder = ViewHolder(viewItem)

        return viewHolder

    }


    override fun onBindViewHolder(holder: VerticalValueAdapter.ViewHolder, i: Int) {
       // holder.textView.text = verticalBarList!![i] + "  "

    }


    override fun getItemCount(): Int {
        return verticalBarList!!.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            textView = view.findViewById(R.id.textview)

        }
    }


}