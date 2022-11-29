# Safe Args 
### Safe Args 是为了简化 compose navigation 数据传输的插件 </p>

## example

依赖中加入
```groovy
implementation 'io.github.ckenergy.compose.safeargs:service:1.0.2'
ksp 'io.github.ckenergy.compose.safeargs:compiler:1.0.2'
```

在你要传递的实体类上加上SafeArgs注解
```kotlin 
@SafeArgs("route_name") 
class SampleData(
    val index: Int,
    val name: String = "",
    val sampleList: List<String>
)
```
在 **NavHost** 注册路由
```kotlin 
composableSafeArgs<SampleData> { entry, it ->
    if (it != null)
        SecondPage(it)
}
```
启动 SecondPage 
```kotlin
val toSecond: (NavBackStackEntry, SampleData) -> Unit = { from, bean->
        navController.navigateWithSafeArgs(from, bean)
    }
```
#### 因为使用的是Gson来序列化，所以SampleData和OtherModuleSampleData名字和类型对应上，注解上的route_name一致就能获取到数据,模块间通信使用。当然也可以把SampleData下沉到基础模块
```kotlin
@SafeArgs("route_name")
class OtherModuleSampleData(
    val sampleList: List<String>,
    val name: String = "",
    val index: Int
)
```
### 如果你的数据结构太简单，不想使用 **SafeArgs**

在 **NavHost** 
```kotlin 
composableSafeArgs(MainDestinations.ROUTE_THIRD) { entry, it ->
    val list: List<String> = it.getParam("list") ?: listOf()
    ThirdPage(it.getParam<Int>("index") ?: 0, list) {
        val data = OtherModuleSampleData(
            listOf("1", "2", "3"),
            "OtherModule",
            1
        )
        actions.toSecond(entry, data)
    }
}
```
启动 ThirdPage
```kotlin
navController.navigateWithSafeArgs(MainDestinations.ROUTE_THIRD) {
    addParam("index", 1)
    addParam("list", listOf("1", "2", "3"))
}
```
