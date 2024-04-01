package com.example.androidremark.ui3.photocropper

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * @author: cb
 * @date: 2024/3/29
 * @desc: 立定跳远标定view
 */
class StandJumpRangeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : View(context, attrs, defStyle) {
    /**
     * 默认边距
     */
    private var defaultMargin = 100

    /**
     * 标记当前按下的圆点
     */
    @DotsPosition
    private var mCurrPressDots: Int = DotsPosition.UNKNOWN

    /**
     * 圆点的半径
     */
    private val dotsRadius = 30
    private val topLeftPoint = Point()
    private val topRightPoint = Point()
    private val bottomLeftPoint = Point()
    private val bottomRightPoint = Point()
    private val topCenterPoint = Point()
    private val bottomCenterPoint = Point()
    private var touchDownX = 0f
    private var touchDownY = 0f

    private var minX = 0
    private var maxX = 0
    private var minY = 0
    private var maxY = 0
    private var centerX = 0
    private var centerY = 0
    private var mEdgePaint: Paint = Paint()
    private var mDotsPaint: Paint = Paint()

    init {
        mEdgePaint.color = Color.WHITE
        mEdgePaint.strokeWidth = 3f
        mEdgePaint.isAntiAlias = true

        mDotsPaint.color = Color.WHITE
        mDotsPaint.style = Paint.Style.FILL
    }

    fun setData() {

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawEdge(canvas)
        drawDots(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        minX = 0
        minY = 0
        maxX = measuredWidth
        maxY = measuredHeight
        centerY = (maxY - minY) / 2
        centerX = (maxX - minX) / 2

        defaultMargin = if (maxX - minX < defaultMargin || maxY - minY < defaultMargin) 0 else 100
        Log.e("stk", "maxX - minX=" + (maxX - minX))
        Log.e("stk", "maxY - minY=" + (maxY - minY))


        topLeftPoint.set(minX + defaultMargin, minY + defaultMargin)
        topRightPoint.set(maxX - defaultMargin, minY + defaultMargin)
        bottomLeftPoint.set(minX + defaultMargin, maxY - defaultMargin)
        bottomRightPoint.set(maxX - defaultMargin, maxY - defaultMargin)
        topCenterPoint.set(centerX, minY + defaultMargin)
        bottomCenterPoint.set(centerX, maxY - defaultMargin)
    }

    /**
     * 四个点
     *
     * @param canvas
     */
    private fun drawDots(canvas: Canvas) {
        canvas.drawCircle(
            topLeftPoint.x.toFloat(), topLeftPoint.y.toFloat(), dotsRadius.toFloat(), mDotsPaint
        )
        mDotsPaint.color = Color.RED
        canvas.drawCircle(
            topRightPoint.x.toFloat(), topRightPoint.y.toFloat(), dotsRadius.toFloat(), mDotsPaint
        )
        mDotsPaint.color = Color.GREEN
        canvas.drawCircle(
            bottomLeftPoint.x.toFloat(),
            bottomLeftPoint.y.toFloat(),
            dotsRadius.toFloat(),
            mDotsPaint
        )
        mDotsPaint.color = Color.YELLOW
        canvas.drawCircle(
            bottomRightPoint.x.toFloat(),
            bottomRightPoint.y.toFloat(),
            dotsRadius.toFloat(),
            mDotsPaint
        )
        mDotsPaint.color = Color.BLUE
        canvas.drawCircle(
            topCenterPoint.x.toFloat(), topCenterPoint.y.toFloat(), dotsRadius.toFloat(), mDotsPaint
        )
        mDotsPaint.color = Color.DKGRAY
        canvas.drawCircle(
            bottomCenterPoint.x.toFloat(),
            bottomCenterPoint.y.toFloat(),
            dotsRadius.toFloat(),
            mDotsPaint
        )
    }

    /**
     * 画四周的线
     * @param canvas
     */
    private fun drawEdge(canvas: Canvas) {
        canvas.drawLine(
            topLeftPoint.x.toFloat(),
            topLeftPoint.y.toFloat(),
            topRightPoint.x.toFloat(),
            topRightPoint.y.toFloat(),
            mEdgePaint
        )
        canvas.drawLine(
            topLeftPoint.x.toFloat(),
            topLeftPoint.y.toFloat(),
            bottomLeftPoint.x.toFloat(),
            bottomLeftPoint.y.toFloat(),
            mEdgePaint
        )
        canvas.drawLine(
            bottomRightPoint.x.toFloat(),
            bottomRightPoint.y.toFloat(),
            topRightPoint.x.toFloat(),
            topRightPoint.y.toFloat(),
            mEdgePaint
        )
        canvas.drawLine(
            bottomRightPoint.x.toFloat(),
            bottomRightPoint.y.toFloat(),
            bottomLeftPoint.x.toFloat(),
            bottomLeftPoint.y.toFloat(),
            mEdgePaint
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> parent.requestDisallowInterceptTouchEvent(false)
            MotionEvent.ACTION_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(false)
                return onActionDown(event)
            }

            MotionEvent.ACTION_MOVE -> {
                parent.requestDisallowInterceptTouchEvent(true)
                return onActionMove(event)
            }
        }
        return false
    }

    private fun onActionDown(event: MotionEvent): Boolean {
        touchDownX = event.x
        touchDownY = event.y

        mCurrPressDots = DotsPosition.UNKNOWN
        if (insidePoint(topLeftPoint)) {
            mCurrPressDots = DotsPosition.TOP_LEFT
        }
        if (insidePoint(topRightPoint)) {
            mCurrPressDots = DotsPosition.TOP_RIGHT
        }
        if (insidePoint(bottomLeftPoint)) {
            mCurrPressDots = DotsPosition.BOTTOM_LEFT
        }
        if (insidePoint(bottomRightPoint)) {
            mCurrPressDots = DotsPosition.BOTTOM_RIGHT
        }
        if (insidePoint(topCenterPoint)) {
            mCurrPressDots = DotsPosition.TOP_CENTER
        }
        if (insidePoint(bottomCenterPoint)) {
            mCurrPressDots = DotsPosition.BOTTOM_CENTER
        }
        return true
    }

    /**
     * 当前点击的位置是否在圆点内
     * @param point Point?
     * @return Boolean
     */
    private fun insidePoint(point: Point?): Boolean {
        if (point == null) return false
        //增大触摸范围，要不然容易点不到
        val doubleDotsRadius = dotsRadius * 2
        val bx = touchDownX > point.x - doubleDotsRadius && touchDownX < point.x + doubleDotsRadius
        val by = touchDownY > point.y - doubleDotsRadius && touchDownY < point.y + doubleDotsRadius
        return bx && by
    }

    /**
     * 移动事件
     * @param event MotionEvent
     * @return Boolean
     */
    private fun onActionMove(event: MotionEvent): Boolean {
        if (mCurrPressDots == DotsPosition.UNKNOWN) return true

        val intX = event.x.toInt()
        val intY = event.y.toInt()
        when (mCurrPressDots) {
            DotsPosition.TOP_LEFT -> {
                adjustTopLeft(intX, intY)
                invalidate()
            }

            DotsPosition.TOP_RIGHT -> {
                adjustTopRight(intX, intY)
                invalidate()
            }

            DotsPosition.BOTTOM_LEFT -> {
                adjustBottomLeft(intX, intY)
                invalidate()
            }

            DotsPosition.BOTTOM_RIGHT -> {
                adjustBottomRight(intX, intY)
                invalidate()
            }

            DotsPosition.BOTTOM_CENTER -> {
                updateCenterPoint(
                    event.x.toInt(),
                    bottomLeftPoint,
                    bottomRightPoint,
                    bottomCenterPoint
                )
                invalidate()
            }

            DotsPosition.TOP_CENTER -> {
                updateCenterPoint(
                    event.x.toInt(),
                    topLeftPoint,
                    topRightPoint,
                    topCenterPoint
                )
                invalidate()
            }
        }
        touchDownX = event.x
        touchDownY = event.y
        return true
    }

    /**
     * 更新中间点的坐标,切只能在线上移动
     * 利用斜率计算公式 (y1-y2)=m(x1-x2)  可以计算出斜率m的值
     * @param touchX Int
     * @param leftPoint Point
     * @param rightPoint Point
     * @param centerPoint Point
     */
    private fun updateCenterPoint(
        touchX: Int,
        leftPoint: Point,
        rightPoint: Point,
        centerPoint: Point
    ) {
        //x轴确保在leftPoint,rightPoint两点之间
        if (touchX < leftPoint.x + dotsRadius * 2) {
            return
        }
        if (touchX > rightPoint.x - dotsRadius * 2) {
            return
        }

        // 检查直线是否垂直
        if (leftPoint.y == rightPoint.y) {
            centerPoint.set(touchX, leftPoint.y)
            return
        }

        // 计算斜率
        val m = (rightPoint.y - leftPoint.y) / (rightPoint.x - leftPoint.x * 1.0f)
        val newY = (m * (touchX - leftPoint.x) + leftPoint.y).toInt()
        centerPoint.set(touchX, newY)
    }

    /**
     * 左上点
     * @param touchX Int
     * @param touchY Int
     */
    private fun adjustTopLeft(touchX: Int, touchY: Int) {
        //右边界x坐标要小于中间的点
        if (touchX > topCenterPoint.x - dotsRadius * 2) {
            return
        }
        //下边界,y坐标要小于左侧顶点
        if (touchY > bottomLeftPoint.y - dotsRadius * 2) {
            return
        }
        //左边界
        val newX = touchX.coerceAtLeast(minX + defaultMargin)
        //上边界
        val newY = touchY.coerceAtLeast(minY + defaultMargin)
        topLeftPoint.set(newX, newY)
        updateCenterPoint(topCenterPoint.x, topLeftPoint, topRightPoint, topCenterPoint)
    }

    /**
     * 右上点
     * @param touchX Int
     * @param touchY Int
     */
    private fun adjustTopRight(touchX: Int, touchY: Int) {
        //左边界
        if (touchX < topCenterPoint.x + dotsRadius * 2) {
            return
        }
        //下边界,y坐标要小于左侧顶点
        if (touchY > bottomRightPoint.y - dotsRadius * 2) {
            return
        }
        //右边界
        val newX = touchX.coerceAtMost(maxX - defaultMargin)
        //上边界
        val newY = touchY.coerceAtLeast(minY + defaultMargin)
        topRightPoint.set(newX, newY)
        updateCenterPoint(topCenterPoint.x, topLeftPoint, topRightPoint, topCenterPoint)
    }

    /**
     * 左下点
     * @param touchX Int
     * @param touchY Int
     */
    private fun adjustBottomLeft(touchX: Int, touchY: Int) {
        //右边界x坐标要小于中间的点
        if (touchX > bottomCenterPoint.x - dotsRadius * 2) {
            return
        }
        //上边界
        if (touchY < topLeftPoint.y + dotsRadius * 2) {
            return
        }
        //左边界
        val newX = touchX.coerceAtLeast(minX + defaultMargin)
        //下边界
        val newY = touchY.coerceAtMost(maxY - defaultMargin)
        bottomLeftPoint.set(newX, newY)
        updateCenterPoint(bottomCenterPoint.x, bottomLeftPoint, bottomRightPoint, bottomCenterPoint)
    }

    /**
     * 右下点
     * @param touchX Int
     * @param touchY Int
     */
    private fun adjustBottomRight(touchX: Int, touchY: Int) {
        //左边界
        if (touchX < bottomCenterPoint.x + dotsRadius * 2) {
            return
        }
        //上边界
        if (touchY < topRightPoint.y + dotsRadius * 2) {
            return
        }
        //右边界
        val newX = touchX.coerceAtMost(maxX - defaultMargin)
        //下边界
        val newY = touchY.coerceAtMost(maxY - defaultMargin)
        bottomRightPoint.set(newX, newY)
        updateCenterPoint(bottomCenterPoint.x, bottomLeftPoint, bottomRightPoint, bottomCenterPoint)
    }
}
