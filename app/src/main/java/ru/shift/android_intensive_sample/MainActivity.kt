package ru.shift.android_intensive_sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.shift.android_intensive_sample.ui.theme.AndoridintensivesampleTheme
import ru.shift.android_intensive_sample.ui.home.HomeScreen
import androidx.navigation.compose.rememberNavController
import ru.shift.android_intensive_sample.ui.navigation.AppNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndoridintensivesampleTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}