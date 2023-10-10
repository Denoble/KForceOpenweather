package com.gevcorst.k_forceopenweather.ui.composables

import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gevcorst.k_forceopenweather.ui.WeatherScreen
import com.gevcorst.k_forceopenweather.ui.composables.custom.CustomSnackbar
import com.gevcorst.k_forceopenweather.ui.composables.custom.SnackbarMessage.Companion.showMessage
import com.gevcorst.k_forceopenweather.ui.theme.KForceOpenWeatherTheme
import com.gevcorst.k_forceopenweather.ui.weatherScreen.MainScreen
import com.gevcorst.k_forceopenweather.ui.weatherScreen.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(){
  KForceOpenWeatherTheme {
      Surface(color = MaterialTheme.colorScheme.secondary){
          val snackbarHostState = remember {SnackbarHostState()}
          val appState = rememberAppState(snackbarHostState = snackbarHostState)
          Scaffold(
              snackbarHost = {
                  SnackbarHost(
                      hostState = snackbarHostState,
                      modifier = Modifier.padding(8.dp),
                      snackbar = { snackbarData ->
                          Snackbar(
                              snackbarData, contentColor =
                              MaterialTheme.colorScheme.surface)
                      }
                  )
              }) { paddingValue ->
              NavHost(
                  navController = appState.navController,
                  startDestination = WeatherScreen.route,
                  modifier = Modifier.padding(paddingValue)
              ) {
                  mainNavgraph(appState)
              }
          }
      }
  }
}

@Composable
fun rememberAppState(
    snackbarHostState: SnackbarHostState,
    navController: NavHostController = rememberNavController(),
    customSnackbar: CustomSnackbar = CustomSnackbar,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(snackbarHostState, navController, customSnackbar, resources, coroutineScope) {
    AppScreenState(
        snackbarHostState,
        navController,
        customSnackbar,
        resources,
        coroutineScope
    )
}
@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

class AppScreenState(
    val snackbarHostState: SnackbarHostState,
    val navController: NavHostController,
    private val customSnackbar: CustomSnackbar,
    private val resources: Resources,
    coroutineScope: CoroutineScope
) {
    init {
        /*coroutineScope.launch {
            customSnackbar.snackbarMessages.filterNotNull().collect { snackbarMessage ->
                val text = snackbarMessage.showMessage (resources)
                snackbarHostState.showSnackbar("HELLO WELCOME !")
            }
        }*/
    }
}
fun NavGraphBuilder.mainNavgraph(appState: AppScreenState) {
    composable(route = WeatherScreen.route) {
        val context = LocalContext.current
        MainScreen(
           /* navigate = { to, popUp -> appState.navigateAndPopUp(to, popUp) },
            toLogin = { route -> appState.navigate(route) }*/)
    }
}