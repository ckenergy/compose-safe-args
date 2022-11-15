package com.example.compose.safeargs

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

/**
 * Created by chengkai on 2022/11/8.
 */
@Composable
fun MainPage(next: () -> Unit, four:() -> Unit, onBack: () -> Unit) {
    SampleContent(
        appBar = {
            SampleAppBar(
                title = "main",
                rightButton = {
                    AppBarRight("four") {
                        four()
                    }
                },
                onBack = onBack
            )
        }) {
        Button(onClick = { next() }) {
            Text(text = "next")
        }

    }

}