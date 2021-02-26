package com.github.michalszynkiewicz.digitteacher

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class DotsView(context: Context, attributes: AttributeSet) : View(context, attributes) {

    val dotColor = Paint()
    val background = Paint()

    var _count: Int = 0


    init {
        dotColor.color = Color.parseColor("#ff0000")
        dotColor.isAntiAlias = true // mstodo test impact

        background.color = Color.parseColor("#ffffff")
        background.isAntiAlias = true // mstodo test impact
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.parseColor("#ffffff"))

        val radius = min((width - 50f) / 10f, (height - 50f) / 4f)

        var startX = 10 + radius
        var startY = 10 + radius
        for (i in 0 until _count) {
            if (i == 4) {
                startX = 10 + radius
                startY = 20 + 3 * radius;
            }
            canvas?.drawCircle(startX, startY, radius, dotColor)
            startX += 2*radius + 10
        }
    }

    fun setCount(no: Int) {
        _count = no;
        invalidate()
    }
}