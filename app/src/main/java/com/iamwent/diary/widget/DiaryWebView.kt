package com.iamwent.diary.widget

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iamwent.diary.data.bean.Diary
import com.iamwent.diary.utils.LunarUtil


private const val HOLDER_CONTENT_MARGIN = "#contentMargin#"
private const val HOLDER_MIN_WIDTH = "#minWidth#"
private const val HOLDER_TITLE_MARGIN_RIGHT = "#titleMarginRight#"
private const val HOLDER_TITLE = "#title#"
private const val HOLDER_NEW_DIARY_STRING = "#newDiaryString#"
private const val HOLDER_LOCATION = "#location#"
private const val HOLDER_TIME_STRING = "#timeString#"
private const val MIME = "text/html"
private const val contentMargin = 10
private const val titleMarginRight = 15

@Composable
fun DiaryWebView(
    modifier: Modifier = Modifier,
    diary: Diary,
    onClick: () -> Unit,
) {
    val html = readingHtml()
        .replace(HOLDER_CONTENT_MARGIN, contentMargin.toString())
        .replace(HOLDER_MIN_WIDTH, 160.toString())
        .replace(HOLDER_TITLE_MARGIN_RIGHT, titleMarginRight.toString())
        .replace(HOLDER_TITLE, String.format("<div class='title'>%s</div>", diary.title))
        .replace(HOLDER_NEW_DIARY_STRING, diary.content?.replace("\n", "<br/>").orEmpty())
        .replace(HOLDER_LOCATION, diary.location.orEmpty())
        .replace(HOLDER_TIME_STRING, LunarUtil.time2Chinese(diary.createdDateTime))

    val webViewState = rememberWebViewStateWithHTMLData(
        data = html,
        baseUrl = "file:///android_res/",
        mimeType = MIME,
    )
    WebView(
        state = webViewState,
        modifier = modifier,
        captureBackPresses = false,
        onCreated = {
            init(it, onClick)
        }
    )
}

private fun readingHtml(): String {
    return """
    <!DOCTYPE html>
    <html lang="zh-Hant">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0;">
        <title></title>
        <style>
            @font-face {
                font-family: 'GutiFangsong';
                src: url('font/fz_longzhao_fw.ttf');
            }
            body {
                padding: 0px;
                background-color: white;
            }
            * {
                margin: 0;
                font-family: 'GutiFangsong';
                -webkit-text-size-adjust: 100%;
                -webkit-writing-mode: vertical-rl;
                -webkit-text-orientation: mixed;
                writing-mode: vertical-rl;
                text-orientation: mixed;
                letter-spacing: 3px;
            }
            .content {
                min-width: 160px;
                margin-right: 10px;
            }
            .content p {
                font-size: 13pt;
                line-height: 24pt;
            }
            .title {
                font-size: 13pt;
                font-weight: bold;
                line-height: 24pt;
                margin-right: 15px;
                padding-left: 20px;
            }
            .extra {
                font-size: 13pt;
                line-height: 24pt;
                margin-right: 30px;
            }
            .container {
                padding: 25px 10px 25px 25px;
            }
            .stamp {
                width: 24px;
                height: auto;
                position: fixed;
                bottom: 20px;
            }
        </style>
    </head>
    <body>
    <div class="container">
        #title#
        <div class="content">
            <p>#newDiaryString#</p>
        </div>
        <div class="extra">
            #location#<br>#timeString#
        </div>
    </div>
    </body>
    </html>
    """.trimIndent()
}

@SuppressLint("ClickableViewAccessibility")
private fun init(webView: WebView, onClick: () -> Unit) {
    WebView.enableSlowWholeDocumentDraw()
    webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
    webView.isLongClickable = false
    webView.isVerticalScrollBarEnabled = false
    webView.isHorizontalScrollBarEnabled = false

    val detector = GestureDetector(webView.context, object : SimpleOnGestureListener() {
        override fun onLongPress(e: MotionEvent) {}
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            onClick()
            return true
        }
    })
    webView.setOnTouchListener { _, event -> detector.onTouchEvent(event) }
}
