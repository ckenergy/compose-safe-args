package com.example.compose.safeargs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.compose.safeargs.destination.MainData

/**
 * Created by chengkai on 2022/11/8.
 */
@Composable
fun FourPage(
    data: MainData,
    onBack: () -> Unit
) {
    SampleContent(
        modifier = Modifier.background(Color.White),
        appBar = {
            SampleAppBar(
                title = data.name,
                onBack = onBack
            )
        },
    )  {
        Text(modifier = Modifier.padding(10.dp), text = "index:${data.index}")
    }
}