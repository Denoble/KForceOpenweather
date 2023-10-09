package com.gevcorst.k_forceopenweather.ui.weatherScreen


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension


import androidx.hilt.navigation.compose.hiltViewModel
import com.gevcorst.k_forceopenweather.ui.composables.custom.BasicButton
import com.gevcorst.k_forceopenweather.ui.composables.custom.CustomAlertDialog
import com.gevcorst.k_forceopenweather.ui.composables.custom.CustomImage
import com.gevcorst.k_forceopenweather.ui.composables.custom.CustomOutlinedTextField
import com.gevcorst.k_forceopenweather.ui.composables.custom.CustomText
import com.gevcorst.k_forceopenweather.ui.composables.custom.DropdownContextMenu
import com.gevcorst.k_forceopenweather.ui.composables.custom.DropdownSelector
import com.gevcorst.k_forceopenweather.ui.theme.MilkyWhite
import com.gevcorst.k_forceopenweather.R.string as AppText

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {

    val cityState = viewModel.uiCityState
    val countryCode = stringResource(id = AppText.countryCodeUS)
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
            CustomText(text = viewModel.fahrenheitValue.value.toString() +
                   stringResource(id =  AppText.fahrenheit_symbol),
                modifier = Modifier
                    .constrainAs(tempText){
                top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent

            },
                textStyle = TextStyle(
                    fontSize = 40.sp,fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif),
                onClickAction = { /*TODO*/ })
            CustomImage(url = viewModel.weatherIconPath.value,
                contentScale = ContentScale.Crop, modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .constrainAs(tempImage) {
                        top.linkTo(tempText.top)
                        start.linkTo(tempText.end)
                        width = Dimension.value(100.dp)
                        height = Dimension.value(100.dp)
                    })
            CustomText(text = viewModel.uiCityState.value.name +" "
                    + viewModel.uiCityState.value.name,
                 modifier =   Modifier.constrainAs(cityText){
                     top.linkTo(tempImage.bottom, margin = 16.dp)
                     start.linkTo(tempText.start)
                     end.linkTo(parent.end, margin = 16.dp)
                     width = Dimension.fillToConstraints
                     height = Dimension.wrapContent
                 }, onClickAction = { /*TODO*/ })
            CustomOutlinedTextField(
                label = stringResource(id = AppText.enter_city),
                value = cityState.value.name,
               placeHolderText = stringResource(id = AppText.enter_city),
                keyboardType = KeyboardType.Text,
                onTextChange = {
                               viewModel.updateCityName(it)
                },
               modifier = Modifier.constrainAs(cityName){
                   start.linkTo(cityText.start)
                   top.linkTo(cityText.top)
                   width = Dimension.wrapContent
                   height = Dimension.wrapContent
               })
            BasicButton(text = AppText.refresh, modifier = Modifier
                .constrainAs(refreshButton){
                    top.linkTo(cityName.bottom, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }) {
                viewModel.fetchLatitudeLongitude(
                    viewModel.uiCityState.value.name, countryCode)
            }
            if(viewModel.wrongCity.value){
                val openAlertDialog = remember { mutableStateOf(false) }
                CustomAlertDialog(
                    onDismissRequest = { openAlertDialog.value = false
                        viewModel.upDateWrongCity(false)},
                    onConfirmation = {
                        openAlertDialog.value = false
                        viewModel.upDateWrongCity(false)
                    },
                    dialogTitle = stringResource(id = AppText.dialog_title),
                    dialogText = stringResource(id = AppText.dialog_message),
                    icon = Icons.Default.Warning
                )
            }
        }
    }
}