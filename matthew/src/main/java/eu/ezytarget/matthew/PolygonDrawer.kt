package eu.ezytarget.matthew

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.Log
import android.R.attr.radius
import android.R.attr.centerY
import android.R.attr.centerX
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


class PolygonDrawer(private val paint: Paint = Paint()) {

    var color: Color
        get() {
            return paint.color
        }
        set(color) {
            paint.color = color
        }

    fun draw(x: Float, y: Float, radius: Float, numOfEdges: Int, canvas: Canvas) {
        if (numOfEdges < MIN_NUM_OF_VERTICES) {
            Log.e(tag, "Minimum number of vertices: $MIN_NUM_OF_VERTICES, given: $numOfEdges")
            return
        }

        val cappedNumOfEdges = if (numOfEdges > MAX_NUM_OF_VERTICES) {
            Log.w(tag, "Maximum number of vertices: $MAX_NUM_OF_VERTICES, given: $numOfEdges")
            MAX_NUM_OF_VERTICES
        } else {
            numOfEdges
        }

        val polygonPath = Path()

        for (edge in 1..cappedNumOfEdges) {
            val angle = TWO_PI * edge / cappedNumOfEdges
            val pointX = (x + radius * cos(angle))
            val pointY = (y + radius * sin(angle))

            if (edge == 1) {
                polygonPath.moveTo(pointX, pointY)
            } else {
                polygonPath.lineTo(pointX, pointY)
            }
        }

        polygonPath.close()

        draw(polygonPath, canvas)
    }

    fun draw(path: Path, canvas: Canvas) {
        canvas.drawPath(path, paint)
    }

    companion object {
        const val MIN_NUM_OF_VERTICES = 3
        const val MAX_NUM_OF_VERTICES = 16
        private const val TWO_PI = 2f * PI.toFloat()
        val tag = PolygonDrawer::class.java.simpleName
    }
}