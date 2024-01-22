package com.iamwent.diary.ui.month

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.iamwent.diary.R
import com.iamwent.diary.model.Month
import com.iamwent.diary.model.Year
import com.iamwent.diary.utils.LunarUtil
import com.iamwent.diary.widget.VerticalText

@Composable
fun MonthPage(
    navController: NavHostController,
    year: Int?,
    monthViewModel: MonthViewModel = hiltViewModel(),
) {
    val monthState by monthViewModel.monthState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        monthViewModel.queryMonths(year)
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
            year = monthState.year,
            modifier = Modifier
                .padding(top = 16.dp, end = 16.dp)
                .align(Alignment.TopEnd),
            gotoHomePage = { navController.navigate("/") },
            gotoComposePage = { navController.navigate("/compose/-1") },
        )

        MonthList(
            months = monthState.months,
            modifier = Modifier
                .padding(horizontal = 48.dp)
                .align(Alignment.Center),
        ) { month ->
            navController.navigate("/${year}/${month}")
        }
    }
}

@Composable
fun MonthList(
    months: List<Month>,
    modifier: Modifier = Modifier,
    openDayPage: (month: Int) -> Unit = {},
) {
    val state = rememberLazyListState()

    LazyRow(
        state = state,
        modifier = modifier,
        reverseLayout = true,
    ) {
        items(months) { month ->
            VerticalText(
                text = month.text,
                fontSize = 22.sp,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable { openDayPage(month.value) }
            )
        }
    }
}

@Preview(
    name = "Month page",
    device = Devices.PIXEL_4,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
)
@Composable
fun MonthListPreview() {
    val months = (1..9).toList().map {
        Month(value = it, text = LunarUtil.month2Chinese(it))
    }
    MonthList(months = months)
}


@Composable
private fun Sidebar(
    year: Year,
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
    }
}

