package com.iamwent.diary.ui.diary

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.net.Uri
import android.view.View
import android.webkit.WebView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.allViews
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.iamwent.diary.R
import com.iamwent.diary.widget.DiaryWebView
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun DiaryPage(
    navController: NavHostController,
    id: Long?,
    diaryViewModel: DiaryViewModel = hiltViewModel(),
) {
    val diaryState by diaryViewModel.diaryState.collectAsStateWithLifecycle()
    var showOperator by remember { mutableStateOf(true) }
    val showDeleteDialog = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val view = LocalView.current
    val diary = diaryState.diary

    LaunchedEffect(Unit) {
        diaryViewModel.queryDiary(id)
    }

    val share = {
        coroutineScope.launch {
            shareDiary(view)
        }
    }

    Box {
        if (diary == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "空",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        } else {
            DiaryWebView(
                modifier = Modifier
                    .fillMaxSize()
                    .heightIn(min = 1.dp),
                diary = diary,
                onClick = { showOperator = !showOperator }
            )
        }

        AnimatedVisibility(
            visible = showOperator,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .align(Alignment.BottomCenter),
            enter = slideInVertically { height -> height + height / 2 } + fadeIn(),
            exit = slideOutVertically { height -> height + height / 2 } + fadeOut(),
        ) {
            Operator(
                compose = {
                    diary?.let {
                        navController.popBackStack()
                        navController.navigate("/compose/${diary.id}")
                    }
                },
                share = { share() },
                delete = { showDeleteDialog.value = true },
            )
        }

        if (showDeleteDialog.value) {
            DeleteDialog(
                confirm = {
                    diaryViewModel.delete(diary)
                    navController.popBackStack()
                },
                cancel = {
                    showDeleteDialog.value = false
                },
            )
        }
    }
}

@Composable
private fun Operator(
    modifier: Modifier = Modifier,
    compose: () -> Unit,
    share: () -> Unit,
    delete: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(colorResource(id = R.color.bg))
                .clickable { compose() }
        ) {
            Text(
                text = stringResource(id = R.string.label_edit),
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
            )
        }

        Spacer(modifier = Modifier.width(24.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(colorResource(id = R.color.bg))
                .clickable { share() }
        ) {
            Text(
                text = stringResource(id = R.string.label_share),
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
            )
        }

        Spacer(modifier = Modifier.width(24.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(colorResource(id = R.color.bg))
                .clickable { delete() }
        ) {
            Text(
                text = stringResource(id = R.string.label_delete),
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
            )
        }
    }

}

@Composable
private fun DeleteDialog(
    confirm: () -> Unit,
    cancel: () -> Unit,
) {
    AlertDialog(
        icon = {
            Icon(
                Icons.Default.Warning,
                contentDescription = "Warning",
                tint = colorResource(id = R.color.red),
            )
        },
        title = {
            Text(
                text = "确认删除吗？",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 26.sp,
            )
        },
        text = {
            Text(
                text = "删除后将无法恢复",
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        onDismissRequest = cancel,
        confirmButton = {
            TextButton(onClick = cancel) {
                Text(
                    text = "取消",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = confirm
            ) {
                Text(
                    text = "确认",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorResource(id = R.color.red),
                )
            }
        }
    )
}

fun shareDiary(root: View) {
    val webView = root.allViews.filterIsInstance<WebView>().firstOrNull() ?: return
    val bitmap = screenshot(webView)
    val path = saveBitmap(root.context, bitmap) ?: return

    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "image/jpeg"
        putExtra(Intent.EXTRA_STREAM, Uri.parse("file://$path"))
    }
    root.context.startActivity(Intent.createChooser(shareIntent, "小记"))
}

private fun screenshot(webView: WebView): Bitmap {
    webView.measure(
        View.MeasureSpec.makeMeasureSpec(
            View.MeasureSpec.UNSPECIFIED,
            View.MeasureSpec.UNSPECIFIED
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

private fun saveBitmap(context: Context, bitmap: Bitmap): String? {
    val parent = context.getExternalFilesDir(null) ?: return null
    val path = File(parent, "${System.currentTimeMillis()}.jpg")
    try {
        FileOutputStream(path).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            return path.absolutePath
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}
