package uk.co.polat.ergun.wikipedia.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import uk.co.polat.ergun.wikipedia.R
import uk.co.polat.ergun.wikipedia.fragments.SettingsFragment

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportFragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment()).commit()

    }
}
