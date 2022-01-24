package com.tsu.itindr.user_profile_activity

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserProfileModelFactory(val app: Application, val userId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Application::class.java, String::class.java).newInstance(app, userId)
    }
}