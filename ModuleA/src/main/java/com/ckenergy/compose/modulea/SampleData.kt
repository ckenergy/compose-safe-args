package com.ckenergy.compose.modulea

import com.ckenergy.compose.safeargs.anotation.SafeArgs

/**
 * Created by chengkai on 2022/11/10.
 */
@SafeArgs("route_second")
class SampleData(
    val index: Int,
    val name: String = "",
    val sampleList: List<String>
)
