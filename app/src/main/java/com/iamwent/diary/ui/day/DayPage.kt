package com.iamwent.diary.ui.day

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.iamwent.diary.R
import com.iamwent.diary.data.bean.Diary
import com.iamwent.diary.model.Month
import com.iamwent.diary.model.Year
import com.iamwent.diary.widget.VerticalText

@Composable
fun DayPage(
    navController: NavHostController,
    year: Int?,
    month: Int?,
    dayViewModel: DayViewModel = hiltViewModel(),
) {
    val dayState by dayViewModel.dayState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        dayViewModel.queryDiaries(year, month)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        navController.popBackStack()
                    }
                )
            },
    ) {
        Sidebar(
            year = dayState.year,
            month = dayState.month,
            modifier = Modifier
                .padding(top = 16.dp, end = 16.dp)
                .align(Alignment.TopEnd),
            gotoHomePage = { navController.navigate("/") },
            gotoComposePage = { navController.navigate("/compose/-1") },
        )

        DayList(
            diaries = dayState.diaries,
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 48.dp)
                .align(Alignment.Center),
        ) { diary ->
            navController.navigate("/${year}/${month}/${diary.id}")
        }
    }
}

@Composable
fun DayList(
    diaries: List<Diary>,
    modifier: Modifier = Modifier,
    openPreviewPage: (diary: Diary) -> Unit = {},
) {
    val state = rememberLazyListState()

    LazyRow(
        state = state,
        modifier = modifier,
        reverseLayout = true,
    ) {
        items(diaries) { diary ->
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center,
            ) {
                VerticalText(
                    text = diary.title ?: "无题",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .clickable { openPreviewPage(diary) }
                )
            }
        }
    }
}

@Preview(
    name = "Day page",
    device = Devices.PIXEL_4,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
)
@Composable
fun DayListPreview() {
    val mock = Diary(
        id = 1,
        year = 2024,
        month = 10,
        title = "临江仙",
        content = "滚滚长江东逝水\n浪花淘尽英雄\n是非成败转头空\n青山依旧在\n几度夕阳红\n\n白发渔樵江渚上\n惯看秋月春风\n一壶浊酒喜相逢\n古今多少事\n都付笑谈中",
        location = "Wuhan",
        createdAt = System.currentTimeMillis()
    )
    val diaries = listOf(mock, mock.copy(id = 2), mock.copy(id = 3))
    DayList(diaries = diaries)
}


@Composable
private fun Sidebar(
    year: Year,
    month: Month,
    modifier: Modifier = Modifier,
    gotoHomePage: () -> Unit,
    gotoComposePage: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        VerticalText(
            text = year.text,
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .clickable { gotoHomePage() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(colorResource(id = R.color.bg))
                .clickable { gotoComposePage() }
        ) {
            Text(
                text = stringResource(id = R.string.label_compose),
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        VerticalText(
            text = month.text,
            color = colorResource(id = R.color.red),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
