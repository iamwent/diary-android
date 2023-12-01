package com.iamwent.diary.widget

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener

/**
 * Created by iamwent on 9/19/16.
 *
 * @author iamwent
 * @since 9/19/16
 */
abstract class OnRecyclerItemClickedListener protected constructor(context: Context?) :
    OnItemTouchListener {
    protected abstract fun onItemClick(view: View?, position: Int)
    private val detector: GestureDetector

    init {
        detector = GestureDetector(context, object : SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }
        })
    }

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
        val childView = view.findChildViewUnder(e.x, e.y)
        if (childView != null && detector.onTouchEvent(e)) {
            onItemClick(childView, view.getChildAdapterPosition(childView))
            return true
        }
        return false
    }

    override fun onTouchEvent(view: RecyclerView, motionEvent: MotionEvent) {}
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
}
