package com.nizamisadykhov.messenger.ui.settings

import android.annotation.SuppressLint
import android.content.Context
import android.preference.EditTextPreference
import android.util.AttributeSet
import android.widget.Toast
import com.nizamisadykhov.messenger.data.local.AppPreferences
import com.nizamisadykhov.messenger.data.remote.request.StatusUpdateRequestObject
import com.nizamisadykhov.messenger.service.MessengerApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProfileStatusPreference(
    context: Context,
    attributeSet: AttributeSet
) : EditTextPreference(context, attributeSet) {

    private val service: MessengerApiService = MessengerApiService.getInstance()

    private val preference: AppPreferences = AppPreferences.create(context)

    @SuppressLint("CheckResult")
    override fun onDialogClosed(positiveResult: Boolean) {
        if (positiveResult) {
            val etStatus = editText

            if (etStatus.text.isEmpty()) {
                Toast.makeText(context, "Status cannot be empty.", Toast.LENGTH_LONG).show()
            } else {
                val requestObject = StatusUpdateRequestObject(etStatus.text.toString())

                service.updateUserStatus(requestObject, preference.accessToken as String)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ res ->
                        preference.storeUserDetails(res)
                    }, { error ->
                        Toast.makeText(
                            context,
                            "Unable tu update status at the moment, Try again later",
                            Toast.LENGTH_LONG
                        ).show()
                        error.printStackTrace()
                    })
            }
        }
        super.onDialogClosed(positiveResult)
    }
}