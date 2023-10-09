package com.gevcorst.k_forceopenweather.ui.composables.custom

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.gevcorst.k_forceopenweather.R.drawable as AppIcon
import com.gevcorst.k_forceopenweather.R.string as AppText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    label: String,
    value:String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    placeHolderText: String, modifier: Modifier,
    keyboardType: KeyboardType,
    onTextChange:(text:String)->Unit

) {
    OutlinedTextField(
        value = value,
        modifier = modifier,
        label = { Text(text = label) },
        placeholder = { Text(text = placeHolderText) },
        visualTransformation = visualTransformation,
        onValueChange = {
            onTextChange(it)
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}


@Composable
fun CustomText(
    text: String, modifier: Modifier, onClickAction: () -> Unit,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text, modifier = modifier.clickable {
            onClickAction.invoke()
        }, style = TextStyle(
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold, fontSize = 13.sp,
        ), textAlign = textAlign
    )
}

@Composable
fun BasicButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
    Button(
        onClick = action,
        modifier = modifier,
        colors =
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(text = stringResource(text), fontSize = 16.sp)
    }
}

@Composable
fun CustomImage(
    url: String,
    contentScale: ContentScale = ContentScale.Fit,
    modifier: Modifier
) {
    val painter =
        rememberAsyncImagePainter(
            ImageRequest.Builder //Used while loading
                (LocalContext.current).data(data = url)
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true) //Crossfade animation between images
                    placeholder(AppIcon.loading_img) //Used while loading
                    fallback(AppIcon.ic_baseline_broken_image_24) //Used if data is null
                    error(AppIcon.ic_baseline_broken_image_24) //Used when loading returns with error
                }).build()
        )


    Image(
        modifier = modifier,
        //Use painter in Image composable
        painter = painter,
        contentScale = contentScale,
        contentDescription = stringResource(id = AppText.imageloader)
    )

}