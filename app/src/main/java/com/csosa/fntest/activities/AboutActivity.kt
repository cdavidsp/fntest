package com.csosa.fntest.activities

import android.os.Bundle
import com.csosa.fntest.base.BaseActivity
import com.csosa.fntest.databinding.ActivityAboutBinding

internal class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.settingsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
