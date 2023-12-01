package com.iamwent.diary.modules.preview

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import com.iamwent.diary.R
import com.iamwent.diary.data.DiaryRepository
import com.iamwent.diary.data.bean.Diary
import com.iamwent.diary.utils.LunarUtil
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class PreviewActivity : ComponentActivity(), View.OnClickListener {
    private var webView: WebView? = null
    private var layoutContainer: LinearLayout? = null
    private val contentWidthOffset = 205
    private var diary: Diary? = null
    private var toVisiable = true

    @Inject
    lateinit var diaryRepository: DiaryRepository

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WebView.enableSlowWholeDocumentDraw()
        setContentView(R.layout.activity_preview)
        diary = intent.getParcelableExtra(EXTRA_DIARY)
        layoutContainer = findViewById<View>(R.id.layout_container) as LinearLayout
        webView = findViewById<View>(R.id.web) as WebView
        webView!!.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        val detector = GestureDetector(this, object : SimpleOnGestureListener() {
            override fun onLongPress(e: MotionEvent) {}
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                toVisiable = !toVisiable
                val animId = if (toVisiable) R.anim.slide_in_bottom else R.anim.slide_out_bottom
                val animation = AnimationUtils.loadAnimation(this@PreviewActivity, animId)
                layoutContainer!!.startAnimation(animation)
                return true
            }
        })
        webView!!.setOnTouchListener { v, event -> detector.onTouchEvent(event) }
        webView!!.isLongClickable = false
        webView!!.isVerticalScrollBarEnabled = false
        webView!!.isHorizontalScrollBarEnabled = false
    }

    override fun onResume() {
        super.onResume()
        val html = readingHtml()
            ?.replace(HOLDER_CONTENT_MARGIN, "10")
            ?.replace(HOLDER_MIN_WIDTH, 160.toString())
            ?.replace(HOLDER_TITLE_MARGIN_RIGHT, "15")
            ?.replace(HOLDER_TITLE, String.format("<div class='title'>%s</div>", diary!!.title))
            ?.replace(HOLDER_NEW_DIARY_STRING, diary!!.content!!.replace("\n", "<br/>"))
            ?.replace(HOLDER_LOCATION, diary!!.location!!)
            ?.replace(HOLDER_TIME_STRING, LunarUtil.time2Chinese(diary!!.createdAt))
            ?: return
        webView!!.loadDataWithBaseURL("file:///android_asset/", html, MIME, ENCODING, null)
    }

    override fun onClick(v: View) {
        when (v.id) {
        }
    }

    private fun share() {
        // todo
        val path = save2sdcard(screenshot(webView))
        val shareIntent = Intent()
        shareIntent.setAction(Intent.ACTION_SEND)
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://$path"))
        shareIntent.setType("image/jpeg")
        startActivity(Intent.createChooser(shareIntent, diary!!.content))
    }

    private fun save2sdcard(bitmap: Bitmap): String? {
        val path =
            (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath
                    + File.separator + diary?.id + ".jpg")
        try {
            FileOutputStream(path).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                return path
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun screenshot(webView: WebView?): Bitmap {
        webView!!.measure(
            View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED
            ),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        webView.layout(0, 0, webView.measuredWidth, webView.measuredHeight)
        webView.isDrawingCacheEnabled = true
        webView.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(
            webView.measuredWidth,
            webView.measuredHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        val paint = Paint()
        val iHeight = bitmap.height
        canvas.drawBitmap(bitmap, 0f, iHeight.toFloat(), paint)
        webView.draw(canvas)
        return bitmap
    }

    private fun updateGallery(path: String) {
        sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://$path")))
    }

    private fun delete() {
        diary?.let { diaryRepository.delete(it) }
        onBackPressed()
    }

    private fun readingHtml(): String? {
        var html: String? = null
        try {
            assets.open("DiaryTemplate.html").use { `in` ->
                val buffer = ByteArray(1024)
                var read: Int
                val builder = StringBuilder(1024 * 10)
                while (`in`.read(buffer).also { read = it } != -1) {
                    builder.append(String(buffer, 0, read))
                }
                html = builder.toString()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return html
    }

    companion object {
        private const val TAG = "PreviewActivity"
        private const val EXTRA_DIARY = "diary"
        private const val HOLDER_CONTENT_MARGIN = "#contentMargin#"
        private const val HOLDER_MIN_WIDTH = "#minWidth#"
        private const val HOLDER_TITLE_MARGIN_RIGHT = "#titleMarginRight#"
        private const val HOLDER_TITLE = "#title#"
        private const val HOLDER_NEW_DIARY_STRING = "#newDiaryString#"
        private const val HOLDER_LOCATION = "#location#"
        private const val HOLDER_TIME_STRING = "#timeString#"
        private const val MIME = "text/html"
        private const val ENCODING = "utf-8"
        private const val contentMargin = 10
        private const val titleMarginRight = 15
        fun start(context: Context, diary: Diary) {
            val starter = Intent(context, PreviewActivity::class.java)
            starter.putExtra(EXTRA_DIARY, diary)
            context.startActivity(starter)
        }
    }
}
