package com.dozze

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.dozze.navigation.TimersGraph
import com.dozze.theme.DozzeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
fun App() {
    val navHostController = rememberNavController()
    KoinContext {
        DozzeTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                TimersGraph(navController = navHostController)
            }
        }
    }
}

@Composable
@Preview
private fun AppPreview() {
    App()
}