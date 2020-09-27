#-dontwarn cn.cloudwalk.**
#-keep class cn.cloudwalk.**{*;}
-dontwarn cn.cloudwalk**
-keep class cn.cloudwalk**{*;}


-dontwarn cn.cloudwalk.libface**
-keep class cn.cloudwalk.libface**{*;}


-dontwarn com.icbc.bas.face**
-keep class com.icbc.bas.face**{*;}

# 不混淆资源类
-keepclassmembers class **.R$* { *; }