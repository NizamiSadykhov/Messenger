package com.nizamisadykhov.messenger.ui.settings

import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.NavUtils
import androidx.preference.PreferenceFragmentCompat
import com.nizamisadykhov.messenger.R

class SettingsActivity : AppCompatPreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onMenuItemSelected(featureId: Int, item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            if (!super.onMenuItemSelected(featureId, item)) {
                NavUtils.navigateUpFromSameTask(this)
            }
            return true
        }
        return super.onMenuItemSelected(featureId, item)
    }

    override fun onBuildHeaders(target: MutableList<Header>?) {
        loadHeadersFromResource(R.xml.pref_headers, target)
    }

    override fun isValidFragment(fragmentName: String?): Boolean {
        return PreferenceFragmentCompat::class.java.name == fragmentName
                || GeneralPreferenceFragment::class.java.name == fragmentName
    }
}