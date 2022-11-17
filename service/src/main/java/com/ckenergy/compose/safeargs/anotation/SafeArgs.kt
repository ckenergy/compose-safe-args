package com.ckenergy.compose.safeargs.anotation

/**
 * Created by chengkai on 2022-11-11
 */
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
@kotlin.annotation.Target(
    AnnotationTarget.CLASS)
annotation class SafeArgs(
    /**
     * 路由的名字 为空则是
     * [routeName]
     */
    val routeName: String = "" )