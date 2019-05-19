# HummingbirdGraphLibrary

This is an android library for displaying graph such as bar chart, spiral, pie chart etc written in kotlin

## Screenshots of the project

1. 
![Bar Chart](https://github.com/sujeet-kumar-mehta/HummingbirdGraphLibrary/tree/master/screenshots/screenshot1.png)
2.
![Bar Chart](https://github.com/sujeet-kumar-mehta/HummingbirdGraphLibrary/tree/master/screenshots/screeenshot_2.png)
3.
![Bar Chart](https://github.com/sujeet-kumar-mehta/HummingbirdGraphLibrary/tree/master/screenshots/screenshots_3.png)
4.
![Bar Chart](https://github.com/sujeet-kumar-mehta/HummingbirdGraphLibrary/tree/master/screenshots/screen_shots_4.png)



## Integration

Add below line in project build.gradle

```bash
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```
Implement below in app build.gradle

```bash
    implementation 'com.github.sujeet-kumar-mehta:HummingbirdGraphLibrary:v0.0.1'

```

## Integrate in your project

include this in layout

```kotlin
<include layout="@layout/view_horizontal_graph"
         android:layout_width="match_parent"
         android:layout_height="200dp"
         android:layout_marginTop="16dp"></include>


```
include this in activity or fragment 

```kotlin
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




```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)

[![](https://jitpack.io/v/sujeet-kumar-mehta/HummingbirdGraphLibrary.svg)](https://jitpack.io/#sujeet-kumar-mehta/HummingbirdGraphLibrary)
