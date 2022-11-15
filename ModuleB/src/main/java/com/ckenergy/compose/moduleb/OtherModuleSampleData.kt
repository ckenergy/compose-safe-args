package com.ckenergy.compose.moduleb

import com.ckenergy.compose.safeargs.anotation.SafeArgs

/**
 * Created by ckenergy on 2022/11/10.
 *
 */
@SafeArgs("route_second")
class OtherModuleSampleData(
    val sampleList: List<String>,
    val name: String = "",
    val index: Int
)
