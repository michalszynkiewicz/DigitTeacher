package com.github.michalszynkiewicz.digitteacher

import android.content.Context
import android.content.res.Configuration
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ContextMenu
import android.view.View
import android.view.WindowInsets
import kotlin.math.min

class DotsView(context: Context, attributes: AttributeSet) : View(context, attributes) {

    private val dotColor = Paint().apply {
        color = Color.parseColor("#ff0000")
        isAntiAlias = true // mstodo test impact
    }

    private val backgroundColor = Color.parseColor("#ffffff")

    private var _count: Int = 0

    override fun onApplyWindowInsets(insets: WindowInsets?): WindowInsets {
        return super.onApplyWindowInsets(insets)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onCreateContextMenu(menu: ContextMenu?) {
        super.onCreateContextMenu(menu)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    override fun onDrawForeground(canvas: Canvas?) {
        super.onDrawForeground(canvas)
    }

    override fun onDisplayHint(hint: Int) {
        super.onDisplayHint(hint)
    }

    override fun onFocusChanged(gainFocus: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)
    }

    override fun onRtlPropertiesChanged(layoutDirection: Int) {
        super.onRtlPropertiesChanged(layoutDirection)
    }

    override fun onScreenStateChanged(screenState: Int) {
        super.onScreenStateChanged(screenState)
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        super.setBackgroundColor(backgroundColor)

        canvas?.drawColor(Color.parseColor("#ffffff"))

        if (canvas != null) {
            val radius = min((canvas.width - 50f) / 10f, (canvas.height - 50f) / 4f)

            var startX = 10 + radius
            var startY = 10 + radius
            for (i in 0 until _count) {
                if (i == 4) {
                    startX = 10 + radius
                    startY = 20 + 3 * radius;
                }
                canvas.drawCircle(startX, startY, radius, dotColor)
                startX += 2 * radius + 10
            }
        }
    }

    fun setCount(no: Int) {
        _count = no;
        invalidate()
    }
}