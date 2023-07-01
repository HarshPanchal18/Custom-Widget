package com.example.widgetapp

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.widget.RemoteViews
import java.time.LocalDate
import java.time.LocalTime

/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget2 : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    //val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.new_app_widget2)
    // Instruct the widget manager to update the widget
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        views.setImageViewBitmap(R.id.imageTime, buildUpdate(LocalTime.now().withNano(0).toString(), 100, context))
        views.setImageViewBitmap(R.id.imageDate, buildUpdate(LocalDate.now().toString(), 25, context))
    }
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

fun buildUpdate(txtTime: String, size: Int, context: Context): Bitmap {
    val customTypeface = Typeface.createFromAsset(context.assets, "fonts/montserrat.ttf")
    val paint = Paint()
    paint.apply {
        textSize = size.toFloat()
        typeface = customTypeface
        color = Color.WHITE
        textAlign = Paint.Align.LEFT
        isSubpixelText = true
        isAntiAlias = true
    }
    val baseline: Float = -paint.ascent()
    val width = (paint.measureText(txtTime) + 0.5f).toInt()
    val height = (baseline + paint.descent() + 0.5f).toInt()
    val image: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444)
    val canvas = Canvas(image)
    canvas.drawText(txtTime, 0F, baseline, paint)
    return image
}
