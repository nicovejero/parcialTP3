package com.kubernights.tp3.parcialnw.ui.view

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.kubernights.tp3.parcialnw.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}