# 保持不被混淆
-keep class * implements com.ckenergy.compose.safeargs.service.IDestinationProvider { *; }
-keep class com.ckenergy.compose.safeargs.service.IDestinationProvider { *; }

-keep,allowobfuscation @interface com.ckenergy.compose.safeargs.anotation.SafeArgs
-keep @com.ckenergy.compose.safeargs.anotation.SafeArgs class * {*;}
-keepclassmembers @com.ckenergy.compose.safeargs.anotation.SafeArgs class * {*;}