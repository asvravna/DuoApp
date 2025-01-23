package no.uio.ifi.in2000.asravna.oblig1


import androidx.compose.runtime.Composable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.asravna.oblig1.ui.palindrome.PalindromeScreen
import androidx.compose.material3.*

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme{
                Surface{
                    AppNavigation()
                }
            }
        }
    }

    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "palindrome_checker") {
            composable("palindrome_checker") {
                PalindromeScreen(navController)
            }
            composable("unit_converter") {
                UnitConverterScreen(navController) // Another screen if needed, placeholder for now
            }
        }
    }
    @Preview
    @Composable
    fun AppNavigationPreview() {
        MaterialTheme{
            Surface{
                AppNavigation()
                PalindromeScreen(navController = rememberNavController())

            }
        }
    }
}
