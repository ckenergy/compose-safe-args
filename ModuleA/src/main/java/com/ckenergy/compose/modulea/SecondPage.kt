package com.ckenergy.compose.modulea

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by chengkai on 2022/11/8.
 */
@Composable
fun SecondPage(
    data: SampleData
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        Text(modifier = Modifier.padding(10.dp), text = "index:${data.index}")
        LazyColumn {
            items(data.sampleList) {
                Text(modifier = Modifier.padding(10.dp), text = it)
                Divider()
            }
        }
    }
}