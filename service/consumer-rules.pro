# 保持不被混淆
-keep class * implements com.ckenergy.compose.safeargs.service.IDestinationProvider { *; }
-keep class com.ckenergy.compose.safeargs.service.IDestinationProvider { *; }