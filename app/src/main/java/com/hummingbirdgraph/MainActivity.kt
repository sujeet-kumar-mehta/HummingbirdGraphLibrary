package com.hummingbirdgraph

import android.content.res.Resources
import android.os.Bundle
import android.support.design.card.MaterialCardView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.butterflybarchart.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    var maxYPoint = 188
    var maxXPoint = 24

    var verticalBarArray: FloatArray =
        floatArrayOf(0.2f, 0.4f, 0.6f, 0.1f, 1f, 0.5f, 0.3f, 0.8f, 0.2f, 0.4f, 0.6f, 0.1f, 1f, 0.5f, 0.3f, 0.8f)


    lateinit var triangleView: TriangleView
    lateinit var triangleViewShadow: TriangleView
    lateinit var suggestionCardView: MaterialCardView
    lateinit var graphTipCardView: MaterialCardView
    lateinit var overall_summary_card_view: CardView
    lateinit var suggestionCircularMatericalCard: MaterialCardView
    lateinit var horizontalRecyclerView: RecyclerView
    lateinit var verticalValueRecyclerView: RecyclerView
    lateinit var verticalValueIndicatorRecylerView: RecyclerView
    lateinit var titleTV: TextView
    lateinit var llTitle: LinearLayout
    lateinit var dateRangeTV: TextView
    lateinit var morethanTV: TextView

    lateinit var morethanDataTV: TextView
    lateinit var suggestionTextView: TextView
    lateinit var reportInfoTextView: TextView

    lateinit var overallDataTV: TextView
    lateinit var overallTV: TextView
    lateinit var textviewYInfo: TextView

    lateinit var minutesAnalysedTV: TextView
    lateinit var tipDateRangeTextView: TextView

    lateinit var activatePlanTV: TextView
    lateinit var tip2DateRangeTextView: TextView

    lateinit var tipLayout: LinearLayout
    lateinit var unpaidLayout: LinearLayout
    lateinit var yAxisValue1: TextView
    lateinit var yAxisValue2: TextView
    lateinit var yAxisValue3: TextView
    lateinit var yAxisValue4: TextView
    lateinit var yAxisValue5: TextView
    var yAxisTextViewArray: Array<TextView>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        maxYPoint =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 188.toFloat(), resources?.displayMetrics).toInt()
        maxXPoint =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24.toFloat(), resources?.displayMetrics).toInt()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        setGraphUi()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun setGraphUi() {

        llTitle = findViewById(R.id.ll_title)

        titleTV = findViewById(R.id.reportTitleTV)
        dateRangeTV = findViewById(R.id.dateRangeTV)

        overallDataTV = findViewById(R.id.overall_data_tv)
        overallTV = findViewById(R.id.overall_data_title_tv)

        morethanDataTV = findViewById(R.id.more_than_data_tv)
        morethanTV = findViewById(R.id.more_than_title_tv)

        minutesAnalysedTV = findViewById(R.id.minutes_analyzed_data_tv)
        tipDateRangeTextView = findViewById(R.id.tip_date_text_view)

        overall_summary_card_view = findViewById(R.id.overall_summary_card_view)
        activatePlanTV = findViewById(R.id.active_plan_tv)
        tip2DateRangeTextView = findViewById(R.id.tip_date2_text_view)

        tipLayout = findViewById(R.id.tool_tip_info_layout)
        unpaidLayout = findViewById(R.id.not_paid_layout)
        textviewYInfo = findViewById(R.id.textviewYInfo)

        triangleView = findViewById(R.id.trangleView)
        triangleViewShadow = findViewById(R.id.trangleViewShadow)
        suggestionCardView = findViewById(R.id.suggestion_materical_card)
        suggestionTextView = findViewById(R.id.suggestion_tv)
        reportInfoTextView = findViewById(R.id.report_info_text_view)
        graphTipCardView = findViewById(R.id.graph_tip_card_view)
        suggestionCircularMatericalCard = findViewById(R.id.suggestion_circular_materical_card)
        horizontalRecyclerView = findViewById(R.id.vertical_bar_recyler_view)
        verticalValueRecyclerView = findViewById(R.id.horizontal_bar_recyler_view)
        verticalValueIndicatorRecylerView = findViewById(R.id.vertical_bar_info_recyler_view)

        yAxisValue1 = findViewById(R.id.pertextview1)
        yAxisValue2 = findViewById(R.id.pertextview2)
        yAxisValue3 = findViewById(R.id.pertextview3)
        yAxisValue4 = findViewById(R.id.pertextview4)
        yAxisValue5 = findViewById(R.id.pertextview5)

        yAxisTextViewArray = arrayOf(yAxisValue5, yAxisValue4, yAxisValue3, yAxisValue2, yAxisValue1)

        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.HORIZONTAL, false)
        var vertical_bar_recyler_view: RecyclerView = findViewById(R.id.vertical_bar_recyler_view)
        vertical_bar_recyler_view.layoutManager = linearLayoutManager

        var verticalBarList: ArrayList<VerticalBar>? = arrayListOf()

        if (verticalBarArray != null) {
            for (i in verticalBarArray?.indices!!) {

                var verticalBar = VerticalBar()
                verticalBar.percentage = verticalBarArray[i]
                verticalBar.isClicable = true
                verticalBar.paid = true
                verticalBar?.tip = "This is a very useful app to improve your driving speed"
                verticalBar?.commentAbs = "This is a very useful app to improve your driving speed"
                verticalBar?.commentProgression = "Put this information in progress and keep improving"
                verticalBar?.dateString = "8 jan -14 Nov 2018"
                verticalBarList?.add(verticalBar)

            }

        } else {
        }

        val verticalBarAdapter = VerticalBarAdapter(this, verticalBarList!!)
        verticalBarAdapter.sizeOfClickableNumber = verticalBarList.size
        vertical_bar_recyler_view.adapter = verticalBarAdapter
        verticalBarAdapter.notifyDataSetChanged()
        vertical_bar_recyler_view.smoothScrollToPosition(verticalBarList!!.size)
        verticalBarAdapter.onVerticalBarClick = { verticalBar ->
            onItemClickEvent(verticalBar)

        }

        if (verticalBarList?.size!! < 5 && verticalBarList?.size > 0) {
            var lastverticalBar: VerticalBar? = VerticalBar()
            var xArray: FloatArray = floatArrayOf(0f, (maxXPoint / 2).toFloat(), maxXPoint.toFloat())
            for (i in xArray.indices) {
                lastverticalBar?.pointsOrange?.add(Point(xArray[i], verticalBarList.last().pointsOrange[2].y))
                lastverticalBar?.pointsGreen?.add(Point(xArray[i], verticalBarList.last().pointsGreen[2].y))
                lastverticalBar?.pointsYellow?.add(Point(xArray[i], verticalBarList.last().pointsYellow[2].y))
                lastverticalBar?.pointsRed?.add(Point(xArray[i], verticalBarList.last().pointsRed[2].y))
//                Log.i("extraPoint", "x: " + verticalBarList.last().pointsRed[2].x + " y: " + verticalBarList.last().pointsRed[2].y)

            }

            for (i in 0..4 - verticalBarList.size) {
                lastverticalBar?.isClicable = false
                lastverticalBar?.tip = ""
                lastverticalBar?.commentAbs = ""
                lastverticalBar?.commentProgression = ""
                lastverticalBar?.paid = true
                verticalBarList?.add(lastverticalBar!!)
            }

        }


        val mValueVerticalList = ArrayList<String>()

        val reportValueInterval = 100?.div(5)
        var percentage = "%"
        for (i in 5 downTo 1) {
            var interval = DecimalFormat("###.#").format(reportValueInterval?.times(i)!!)

            mValueVerticalList.add("$interval$percentage")
            yAxisTextViewArray!![i - 1].text = "$interval$percentage"
        }
//        mValueVerticalList.add("")
        textviewYInfo.text = "Speed"
        val verticalValueAdapter = VerticalValueAdapter(this, mValueVerticalList)

        verticalValueRecyclerView.adapter = verticalValueAdapter
        verticalBarAdapter.notifyDataSetChanged()

//        // onItemClickEvent(mBarArrayList.get(position), holder);
//        if (verticalBarList.size <= 5) {
//             verticalValueIndicatorRecylerView.visibility = View.VISIBLE
//             verticalValueIndicatorRecylerView.layoutManager =
//                    LinearLayoutManager( itemView.context, LinearLayoutManager.VERTICAL, false)
//             verticalValueIndicatorRecylerView.adapter =
//                    VerticalValueIndicatorAdapter( itemView.context as Activity)
//        } else
//             verticalValueIndicatorRecylerView.visibility = View.GONE
    }


    fun onItemClickEvent(verticalBar: VerticalBar) {
        triangleView.setColor(resources.getColor(verticalBar.backGroundColor))
        graphTipCardView.strokeColor = resources.getColor(verticalBar.backGroundColor)
        suggestionCardView.setCardBackgroundColor(resources.getColor(verticalBar.alphaBackGroundColor))
        suggestionCircularMatericalCard.setCardBackgroundColor(
            resources.getColor(
                verticalBar.backGroundColor
            )
        )
        tipDateRangeTextView.text = verticalBar.dateString
        tip2DateRangeTextView.text = verticalBar.dateString

        suggestionTextView.setText(verticalBar.tip)
        var progression: String =
            if (!TextUtils.isEmpty(verticalBar?.commentProgression)) "\n" + verticalBar?.commentProgression else ""
        reportInfoTextView.setText(verticalBar.commentAbs + progression)
//         tipDateRangeTextView.setText() - Util.convertDpToPixel(64f)
        triangleView.animate()
            .x(verticalBar.getxPoint() - convertDpToPixel(64f))
            .setDuration(200)
            .start()

        triangleViewShadow.animate()
            .x(verticalBar.getxPoint() - convertDpToPixel(64f))
            .setDuration(200)
            .start()

        // overall_summary_card_view.setCardBackgroundColor(resources.getColor(if (verticalBar?.paid!!) R.color.violent else R.color.grey))
        unpaidLayout.visibility = if (verticalBar?.paid!!) View.GONE else View.VISIBLE
        tipLayout.visibility = if (verticalBar?.paid!!) View.VISIBLE else View.GONE


//         activatePlanTV.text = Html.fromHtml("To check your analysis <u><font color='#23b9f8'>Activate your plan</font></u>")
//         activatePlanTV.setOnClickListener {
//            activatePaidPlan?.invoke()
//        }

        horizontalRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }

            private var overallXScroll = 0
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                overallXScroll = overallXScroll.plus(dx)
                val changeX = verticalBar.getxPoint() - convertDpToPixel(64f) - overallXScroll

                triangleView.animate()
                    .x(changeX)
                    .setDuration(0)
                    .start()
                Log.i("scroll_x", "x: " + -recyclerView.computeHorizontalScrollOffset() + " setX: $changeX")

                triangleViewShadow.animate()
                    .x(changeX)
                    .setDuration(0)
                    .start()
            }
        })

    }

    fun convertDpToPixel(dp: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return Math.round(px).toFloat()
    }
}
