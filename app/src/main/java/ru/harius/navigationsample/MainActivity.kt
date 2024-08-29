package ru.harius.navigationsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import ru.harius.navigationsample.ui.theme.NavigationSampleTheme

sealed interface Route {
    @Serializable
    object ScreenA: Route
    @Serializable
    object ScreenB: Route
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavigationSampleTheme {
                NavigationHost(navController = navController)
            }
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.ScreenA
    ) {
        composable<Route.ScreenA> {
            val viewModel: MyViewModel = viewModel(
                factory = MyViewModelFactory(
                    openScreenB = {
                        navController.navigate(Route.ScreenB) {
                            launchSingleTop = true
                        }
                    }
                )
            )
            Screen(
                title = "Screen A",
                buttonLabel = "Navigate to Screen B",
                onClick = {
                    viewModel.openScreenB()
                }
            )
        }
        composable<Route.ScreenB> {
            Screen(
                title = "Screen B",
                buttonLabel = "Navigate back to Screen A",
                onClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}

@Composable
fun Screen(
    title: String,
    buttonLabel: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BackButton(text = buttonLabel, onClick = onClick)
        Text(title)
    }
}

@Composable
fun BackButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text)
    }
}