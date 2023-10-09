package com.gevcorst.k_forceopenweather.ui.composables.custom

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.gevcorst.k_forceopenweather.R
import kotlinx.coroutines.flow.MutableStateFlow

object CustomSnackbar{
    val msg = SnackbarMessage.StringSnackbarMessage("")
    private val messages: MutableStateFlow<SnackbarMessage> = MutableStateFlow(msg)
    val snackbarMessages: MutableStateFlow<SnackbarMessage> get() = messages
    fun showMessage(@StringRes message: Int) {
        messages.value = SnackbarMessage.ResourceSnackbarMessage(message)
    }
    fun showMessage(message: SnackbarMessage) {
        messages.value = message
    }

}

sealed class SnackbarMessage {
    class StringSnackbarMessage(val msg: String) : SnackbarMessage()
    class ResourceSnackbarMessage(@StringRes val msg: Int) : SnackbarMessage()
    companion object{
        fun SnackbarMessage.showMessage(resources: Resources): String {
            return when (this) {
                is StringSnackbarMessage -> this.msg
                is ResourceSnackbarMessage -> resources.getString(this.msg)
                else -> ""
            }
        }

        fun Throwable.getErrorMessage(): SnackbarMessage {
            val message = this.message.orEmpty()
            return if (message.isBlank()) StringSnackbarMessage(message)
            else ResourceSnackbarMessage(R.string.generic_error)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}
