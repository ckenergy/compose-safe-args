package com.example.compose.safeargs

import android.os.Bundle
import androidx.activity.compose.setContent

class MainActivity : BaseComposeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavGraph(finishActivity = {
                finish()
            })
        }
    }
}