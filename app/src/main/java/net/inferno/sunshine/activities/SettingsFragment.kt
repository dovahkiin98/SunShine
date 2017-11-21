package net.inferno.sunshine.activities

import android.os.Bundle
import android.preference.PreferenceFragment
import net.inferno.sunshine.R

class SettingsFragment : PreferenceFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
    }
}