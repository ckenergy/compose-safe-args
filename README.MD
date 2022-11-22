# Safe Args 
### Safe Args is for compose navigation to transport data easily </p>

[文档还是中文的好](README_CN.MD)

## example

add in dependencies
```groovy
implementation 'io.github.ckenergy.compose.safeargs:service:1.0.0'
ksp 'io.github.ckenergy.compose.safeargs:compiler:1.0.0'
```

add **SafeArgs** annotation in the class where you went transport data 
```kotlin 
@SafeArgs("route_name") //route_name default use class package name when is empty
class SampleData(
    val index: Int,
    val name: String = "",
    val sampleList: List<String>
)
```
in **NavHost** register route_name
```kotlin 
composableSafeArgs<SampleData> { entry, it ->
    if (it != null)
        SecondPage(it)
}
```
launch SecondPage with route_name
```kotlin
val toSecond: (NavBackStackEntry, SampleData) -> Unit = { from, bean->
        navController.navigateWithSafeArgs(from, bean)
    }
```
#### Safe Args use Gson format data， so class SampleData can transform to class OtherModuleSampleData when their field name and type are the same, and route_name need equally
#### although two module are dependencies SampleData class is sample way
```kotlin
@SafeArgs("route_name")
class OtherModuleSampleData(
    val sampleList: List<String>,
    val name: String = "",
    val index: Int
)
```

### don't use **SafeArgs** annotation

in **NavHost** 
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
launch ThirdPage
```kotlin
navController.navigateWithSafeArgs(MainDestinations.ROUTE_THIRD) {
    addParam("index", 1)
    addParam("list", listOf("1", "2", "3"))
}
```
