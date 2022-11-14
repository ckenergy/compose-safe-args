package com.example.compose.safeargs

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Created by chengkai on 2022/11/8.
 */
@Composable
fun MainPage(filter: () -> Unit, onBack: () -> Unit) {
    SampleContent(modifier = Modifier.background(Color(0xFFF5F1F0)),
        appBar = {
            SampleAppBar(
                title = "main",
                rightButton = {
                    AppBarRight("second") {
                        filter()
                    }
                },
                onBack = onBack
            )
        }) {
    }

}