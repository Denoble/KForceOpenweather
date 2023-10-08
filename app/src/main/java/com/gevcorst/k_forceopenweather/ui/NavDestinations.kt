package com.gevcorst.k_forceopenweather.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

interface NavDestinations {
    val icon: ImageVector
    val route: String
}
object WeatherScreen :NavDestinations{
    override val icon: ImageVector = Icons.Filled.Info
    override val route:String =  "WeatherScreen"
}