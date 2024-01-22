package com.iamwent.diary.ui.year

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.iamwent.diary.model.Year
import com.iamwent.diary.utils.LunarUtil
import com.iamwent.diary.widget.VerticalText


@Composable
fun YearPage(
    navController: NavHostController,
    yearViewModel: YearViewModel = hiltViewModel(),
) {
    val yearState by yearViewModel.yearState.collectAsStateWithLifecycle()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 48.dp),
    ) {
        YearList(years = yearState.years) { year ->
            navController.navigate("/${year.value}")
        }
    }
}

@Composable
fun YearList(years: List<Year>, openMonthPage: (year: Year) -> Unit = {}) {
    val state = rememberLazyListState()

    LazyRow(state = state, reverseLayout = true) {
        items(years) { year ->
            VerticalText(
                text = year.text,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable { openMonthPage(year) }
            )
        }
    }
}

@Preview(
    name = "Year page",
    device = Devices.PIXEL_4,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
)
@Composable
fun YearListPreview() {
    YearList(years = (2002..2024).toList().map {
        Year(it, LunarUtil.year2Chinese(it))
    })
}
