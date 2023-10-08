package com.gevcorst.k_forceopenweather.ui.weatherScreen

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension


import androidx.hilt.navigation.compose.hiltViewModel
import com.gevcorst.k_forceopenweather.ui.composables.custom.BasicButton
import com.gevcorst.k_forceopenweather.ui.composables.custom.CustomImage
import com.gevcorst.k_forceopenweather.ui.composables.custom.CustomText
import com.gevcorst.k_forceopenweather.ui.composables.custom.CustomTextField
import com.gevcorst.k_forceopenweather.ui.composables.custom.DropdownContextMenu
import com.gevcorst.k_forceopenweather.ui.composables.custom.DropdownSelector
import com.gevcorst.k_forceopenweather.ui.theme.MilkyWhite
import com.gevcorst.k_forceopenweather.R.string as AppText

@Composable
fun MainScreen(viewModel: MainViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(3.dp,  MaterialTheme.colorScheme.secondary),
        colors = CardDefaults.cardColors(containerColor = MilkyWhite)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (tempText,tempImage,cityText,countryDropDown,refreshButton,cityName) = createRefs()
            CustomText(text = viewModel.currentWeather.value.temp.toString() ,
                modifier = Modifier.constrainAs(tempText){
                top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent

            }, onClickAction = { /*TODO*/ })
            CustomImage(url = "", contentScale = ContentScale.Crop, modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .constrainAs(tempImage) {
                    top.linkTo(tempText.top)
                    start.linkTo(tempText.end)
                })
            CustomText(text = viewModel.uiCityState.value.name +" " + viewModel.uiCityState.value.name,
                 modifier =   Modifier.constrainAs(cityText){
                     top.linkTo(tempText.bottom, margin = 16.dp)
                     start.linkTo(tempText.start)
                     end.linkTo(parent.end, margin = 16.dp)
                     width = Dimension.fillToConstraints
                     height = Dimension.wrapContent
                 }, onClickAction = { /*TODO*/ })
           CustomTextField(label = "Enter city name",
               placeHolderText = "Enter city name",
               modifier = Modifier.constrainAs(cityName){
                   start.linkTo(cityText.start)
                   top.linkTo(cityText.top)
                   width = Dimension.wrapContent
                   height = Dimension.wrapContent
               })
            DropdownSelector( options = viewModel.countries.value,
               selection = stringResource( AppText.selectCountry),
                modifier =  Modifier.constrainAs(countryDropDown){
                    top.linkTo(cityName.bottom, margin = 16.dp)
                    start.linkTo(cityName.start)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent

                }
                , onNewValue   = {
                    viewModel.updateCountryCode(it)
                    Log.i("COUNTRYCODE",viewModel.countryCode.value)
                })
            BasicButton(text = AppText.refresh, modifier = Modifier
                .constrainAs(refreshButton){
                    top.linkTo(countryDropDown.bottom, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }) {

            }
        }
    }
}