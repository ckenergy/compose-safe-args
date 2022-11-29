package com.example.compose.safeargs.destination

import com.ckenergy.compose.safeargs.anotation.SafeArgs
import com.google.gson.annotations.Expose

/**
 * Created by chengkai on 2022/11/10.
 */
@SafeArgs
class MainData(
    @Expose
    val index: Int,
    val name: String? = "",
)
