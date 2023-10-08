package com.gevcorst.k_forceopenweather.ui.composables.custom

import android.content.res.Resources
import androidx.annotation.StringRes
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