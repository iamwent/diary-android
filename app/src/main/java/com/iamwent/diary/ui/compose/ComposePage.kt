package com.iamwent.diary.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.iamwent.diary.R
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalLayoutApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ComposePage(
    navController: NavHostController,
    id: Long?,
    composeViewModel: ComposeViewModel = hiltViewModel()
) {
    val composeState by composeViewModel.composeState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val saveDiary = {
        scope.launch {
            val saved = composeViewModel.save()
            navController.popBackStack()

            if (saved != null) {
                val now = LocalDate.now()
                navController.navigate("/${now.year}/${now.monthValue}/${saved}")
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    LaunchedEffect(key1 = id) {
        composeViewModel.load(id)
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .imePadding()
            .imeNestedScroll(),
    ) {
        val textFieldColors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            unfocusedIndicatorColor = Color.White,
            focusedIndicatorColor = Color.White,
        )
        val textStyle = MaterialTheme.typography.bodyLarge.copy(
            fontSize = 18.sp,
        )
        TextField(
            value = composeState.diary.title.orEmpty(),
            onValueChange = { composeViewModel.compose(title = it) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = textStyle,
            colors = textFieldColors,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        TextField(
            value = composeState.diary.content.orEmpty(),
            onValueChange = { composeViewModel.compose(content = it) },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0F)
                .scrollable(scrollState, Orientation.Vertical)
                .focusRequester(focusRequester)
                .focusable(),
            textStyle = textStyle,
            colors = textFieldColors,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = composeState.diary.location.orEmpty(),
                onValueChange = { composeViewModel.compose(location = it) },
                modifier = Modifier.weight(1.0F),
                textStyle = textStyle,
                colors = textFieldColors,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() },
                ),
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.bg))
                    .clickable { saveDiary() }
            ) {
                Text(
                    text = stringResource(id = R.string.label_done),
                    color = Color.White,
                    style = textStyle,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}
