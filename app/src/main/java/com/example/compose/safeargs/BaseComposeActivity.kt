package com.example.compose.safeargs

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.safeargs.theme.ComposeTestTheme


/**
 *
 * Compose框架activity的基类内部定义了appBar，加载条
 * 非全屏使用[setSampleContent] 全屏使用[setSampleFullContent]
 *
 * Created by chengkai on 2022/10/28.
 */
open class BaseComposeActivity : ComponentActivity() {
//    private val progressDialog by lazy {
//        CustomProgressBarDialog(this)
//    }

    protected fun showLoading() {
//        if (!progressDialog.isShowing) {
//            progressDialog.show()
//        }
    }

    protected fun dismissLoading() {
//        if (progressDialog.isShowing) {
//            progressDialog.dismiss()
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()
    }

}

fun BaseComposeActivity.setSampleContent(
    modifier: Modifier = Modifier,
    appBar: @Composable () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    setContent1 {
        SampleContent(modifier, appBar, content)
    }
}

@Composable
fun SampleContent(modifier: Modifier = Modifier,
                  appBar: @Composable () -> Unit = {},
                  content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = modifier) {
        appBar()
        content()
    }
}

fun BaseComposeActivity.setSampleFullContent(
    modifier: Modifier = Modifier,
    appBar: @Composable () -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    setContent1 {
        SampleFullContent(modifier = modifier) {
            content()
            appBar()
        }
    }
}

@Composable
fun SampleFullContent(
    modifier: Modifier = Modifier,
    appBar: @Composable () -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier) {
        content()
        appBar()
    }
}

internal fun BaseComposeActivity.setContent1(
    content: @Composable () -> Unit
) {
    setContent {
        ComposeTestTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background,
            ) {
                content()
            }
        }
    }
}

@Composable
fun SampleAppBar(
    title: String,
    rightButton: @Composable BoxScope.() -> Unit = {},
    onBack: () -> Unit = {  }
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(Color.White),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp),
            text = title,
            color = Color.Black,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
        )
        rightButton()
        IconButton(
            modifier = Modifier.size(50.dp),
            onClick = { onBack() }
        ) {
            Icon(
                bitmap = ImageBitmap.imageResource(id = R.mipmap.ic_back),
                null
            )
        }
    }
}

@Composable
fun BoxScope.AppBarRight(text: String, click: () -> Unit) {
    Text(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .align(Alignment.CenterEnd)
            .clickable {
                click()
            },
        text = text,
//        color = primaryColor,
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
    )
}