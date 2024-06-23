package com.joaoxstone.stoneroyale.core.preferences

import android.content.Context

class OnboardingManager(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("onboarding_prefs", Context.MODE_PRIVATE)

    fun isFirstTimeLaunch(): Boolean {
        return !sharedPreferences.getBoolean("is_onboarding_complete", true)
    }

    fun setOnboardingComplete() {
        sharedPreferences.edit().putBoolean("is_onboarding_complete", true).apply()
    }
}
