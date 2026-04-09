package com.yourapp.vocalize.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.yourapp.vocalize.ui.screens.BackupRestoreScreen
import com.yourapp.vocalize.ui.screens.CalendarScreen
import com.yourapp.vocalize.ui.screens.CategoryManageScreen
import com.yourapp.vocalize.ui.screens.HomeScreen
import com.yourapp.vocalize.ui.screens.MemoDetailScreen
import com.yourapp.vocalize.ui.screens.PlaylistScreen
import com.yourapp.vocalize.ui.screens.RecorderScreen
import com.yourapp.vocalize.ui.screens.SearchScreen
import com.yourapp.vocalize.data.model.Memo

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Recorder : Screen("recorder")
    object MemoDetail : Screen("memo_detail")
    object Calendar : Screen("calendar")
    object Search : Screen("search")
    object Settings : Screen("settings")
    object Playlist : Screen("playlist")
    object CategoryManage : Screen("category_manage")
    object BackupRestore : Screen("backup_restore")
}

@Composable
fun VocalizeNavHost(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.Splash.route, modifier = modifier) {
        composable(Screen.Splash.route) {
            SplashScreen(onTimeout = { navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            } })
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onRecordTap = { navController.navigate(Screen.Recorder.route) },
                onMemoSelected = { navController.navigate(Screen.MemoDetail.route) },
                onCalendarTap = { navController.navigate(Screen.Calendar.route) },
                onSearchTap = { navController.navigate(Screen.Search.route) },
                onSettingsTap = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(Screen.Recorder.route) {
            RecorderScreen(onCancel = { navController.popBackStack() }, onSaved = { navController.popBackStack() })
        }
        composable(Screen.MemoDetail.route) {
            MemoDetailScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Calendar.route) {
            CalendarScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Search.route) {
            SearchScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
                onCategoryManage = { navController.navigate(Screen.CategoryManage.route) },
                onBackupRestore = { navController.navigate(Screen.BackupRestore.route) }
            )
        }
        composable(Screen.Playlist.route) {
            PlaylistScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.CategoryManage.route) {
            CategoryManageScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.BackupRestore.route) {
            BackupRestoreScreen(onBack = { navController.popBackStack() })
        }
    }
}
