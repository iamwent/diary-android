package com.iamwent.diary.modules.preview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.iamwent.diary.R;
import com.iamwent.diary.data.DiaryRepository;
import com.iamwent.diary.data.bean.Diary;
import com.iamwent.diary.utils.LunarUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PreviewActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "PreviewActivity";

    private static final String EXTRA_DIARY = "diary";

    private static final String HOLDER_CONTENT_MARGIN = "#contentMargin#";
    private static final String HOLDER_MIN_WIDTH = "#minWidth#";
    private static final String HOLDER_TITLE_MARGIN_RIGHT = "#titleMarginRight#";

    private static final String HOLDER_TITLE = "#title#";
    private static final String HOLDER_NEW_DIARY_STRING = "#newDiaryString#";
    private static final String HOLDER_LOCATION = "#location#";
    private static final String HOLDER_TIME_STRING = "#timeString#";

    private static final String MIME = "text/html";
    private static final String ENCODING = "utf-8";

    private WebView webView;
    private LinearLayout layoutContainer;

    private int contentWidthOffset = 205;
    private static int contentMargin = 10;
    private static int titleMarginRight = 15;

    private long id;
    private Diary diary;
    private boolean toVisiable = true;

    public static void start(Context context, long id) {
        Intent starter = new Intent(context, PreviewActivity.class);
        starter.putExtra(EXTRA_DIARY, id);
        context.startActivity(starter);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView.enableSlowWholeDocumentDraw();
        setContentView(R.layout.activity_preview);

        id = getIntent().getLongExtra(EXTRA_DIARY, 0L);

        layoutContainer = (LinearLayout) findViewById(R.id.layout_container);

        webView = (WebView) findViewById(R.id.web);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        final GestureDetector detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                toVisiable = !toVisiable;
                int animId = toVisiable ? R.anim.slide_in_bottom : R.anim.slide_out_bottom;
                Animation animation = AnimationUtils.loadAnimation(PreviewActivity.this, animId);
                layoutContainer.startAnimation(animation);
                return true;
            }
        });
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });
        webView.setLongClickable(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);

    }

    @Override
    protected void onResume() {
        super.onResume();

        diary = DiaryRepository.getInstance(this).queryDiary(id);
        String html = readingHtml()
                .replace(HOLDER_CONTENT_MARGIN, "10")
                .replace(HOLDER_MIN_WIDTH, String.valueOf(160))
                .replace(HOLDER_TITLE_MARGIN_RIGHT, "15")
                .replace(HOLDER_TITLE, String.format("<div class='title'>%s</div>", diary.title))
                .replace(HOLDER_NEW_DIARY_STRING, diary.content.replace("\n", "<br/>"))
                .replace(HOLDER_LOCATION, diary.location)
                .replace(HOLDER_TIME_STRING, LunarUtil.time2Chinese(diary.createdAt));

        webView.loadDataWithBaseURL("file:///android_asset/", html, MIME, ENCODING, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_compose:
//                EditorActivity.start(PreviewActivity.this, id);
//                break;
//            case R.id.btn_share:
//                share();
//                break;
//            case R.id.btn_delete:
//                delete();
//                break;
//            default:
//                break;
        }
    }

    private void share() {
        // todo
        String path = save2sdcard(screenshot(webView));

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + path));
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, diary.content));
    }

    private String save2sdcard(Bitmap bitmap) {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()
                + File.separator + id + ".jpg";

        try (FileOutputStream out = new FileOutputStream(path)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            return path;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Bitmap screenshot(WebView webView) {
        webView.measure(View.MeasureSpec.makeMeasureSpec(
                        View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        webView.layout(0, 0, webView.getMeasuredWidth(), webView.getMeasuredHeight());
        webView.setDrawingCacheEnabled(true);
        webView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(webView.getMeasuredWidth(),
                webView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        int iHeight = bitmap.getHeight();
        canvas.drawBitmap(bitmap, 0, iHeight, paint);
        webView.draw(canvas);

        return bitmap;
    }

    private void updateGallery(String path) {
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
    }

    private void delete() {
        DiaryRepository.getInstance(this).delete(id);
        onBackPressed();
    }

    private String readingHtml() {
        String html = null;
        try (InputStream in = getAssets().open("DiaryTemplate.html")) {
            byte[] buffer = new byte[1024];
            int read;
            StringBuilder builder = new StringBuilder(1024 * 10);
            while ((read = in.read(buffer)) != -1) {
                builder.append(new String(buffer, 0, read));
            }
            html = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return html;
    }
}
