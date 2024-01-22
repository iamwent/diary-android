package com.iamwent.diary

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.iamwent.diary.theme.DiaryTheme
import com.iamwent.diary.ui.compose.ComposePage
import com.iamwent.diary.ui.day.DayPage
import com.iamwent.diary.ui.diary.DiaryPage
import com.iamwent.diary.ui.month.MonthPage
import com.iamwent.diary.ui.year.YearPage

@Composable
fun App() {
    DiaryTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "/",
            enterTransition = {
                scaleIn(
                    animationSpec = tween(220, delayMillis = 90),
                    initialScale = 0.9F
                ) + fadeIn(animationSpec = tween(320, delayMillis = 90))
            },
            exitTransition = {
                scaleOut(
                    animationSpec = tween(
                        durationMillis = 220,
                        delayMillis = 90
                    ), targetScale = 0.9F
                ) + fadeOut(tween(delayMillis = 90))
            },
        ) {
            composable("/") { YearPage(navController) }
            composable(
                route = "/{year}",
                arguments = listOf(navArgument("year") { type = NavType.IntType })
            ) { backStackEntry ->
                val year = backStackEntry.arguments?.getInt("year")
                MonthPage(navController, year)
            }
            composable(
                route = "/{year}/{month}",
                arguments = listOf(
                    navArgument("year") { type = NavType.IntType },
                    navArgument("month") { type = NavType.IntType },
                )
            ) { backStackEntry ->
                val year = backStackEntry.arguments?.getInt("year")
                val month = backStackEntry.arguments?.getInt("month")
                DayPage(navController, year, month)
            }
            composable(
                route = "/{year}/{month}/{id}",
                arguments = listOf(
                    navArgument("year") { type = NavType.IntType },
                    navArgument("month") { type = NavType.IntType },
                    navArgument("id") { type = NavType.LongType },
                )
            ) { backStackEntry ->
                val year = backStackEntry.arguments?.getInt("year")
                val month = backStackEntry.arguments?.getInt("month")
                val id = backStackEntry.arguments?.getLong("id")
                DiaryPage(navController, id)
            }
            composable(
                route = "/compose/{id}",
                arguments = listOf(navArgument("id") { type = NavType.LongType }),
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getLong("id")
                ComposePage(navController, id)
            }
        }
    }
}
