package com.iamwent.diary.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.iamwent.diary.R


@Composable
fun DiaryTheme(
    content: @Composable () -> Unit
) {
    val bodyFontFamily = FontFamily(
        Font(R.font.fz_longzhao_fw, FontWeight.Normal),
    )
    val titleFontFamily = FontFamily(
        Font(R.font.tpld_khangxidicttrial, FontWeight.Normal),
    )
    val typography = Typography(
        bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontFamily = bodyFontFamily),
        bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontFamily = bodyFontFamily),
        bodySmall = MaterialTheme.typography.bodySmall.copy(fontFamily = bodyFontFamily),
        titleLarge = MaterialTheme.typography.titleLarge.copy(fontFamily = titleFontFamily),
        titleMedium = MaterialTheme.typography.titleMedium.copy(fontFamily = titleFontFamily),
        titleSmall = MaterialTheme.typography.titleSmall.copy(fontFamily = titleFontFamily),
    )
    MaterialTheme(
        typography = typography,
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = colorResource(id = R.color.red),
            surface = Color.White,
        ),
        content = content,
    )
}
