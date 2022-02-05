package com.nizamisadykhov.messenger.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.preference.PreferenceFragment
import android.view.MenuItem
import com.nizamisadykhov.messenger.R

class GeneralPreferenceFragment: PreferenceFragment() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_general)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            startActivity(Intent(activity, SettingsActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}