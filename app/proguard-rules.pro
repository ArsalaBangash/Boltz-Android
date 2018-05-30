################################################################################

-keep public class org.matheclipse.** {
   public *;
}

-keep interface java.lang.Number.** { *; }
-keep interface org.apache.commons.math3.Field { *; }
-keep interface org.apache.commons.math3.FieldElement { *; }

##########################################
-dontwarn javax.annotation.**
-dontwarn org.apache.log4j.**
############################
-dontwarn com.x5.**
-keep class com.x5.template.** { *; }
-keep class com.x5.util.* { *; }
####################
-dontwarn java.beans.**
-dontwarn javax.swing.**
-dontwarn java.awt.**
-keep class org.** { *; }
-keep class edu.** { *; }
-keep class de.lab4inf.** { *; }
-keep class cc.redberry.** { *; }

-dontwarn android.arch.**



## Google AdMob specific rules ##
## https://developers.google.com/admob/android/quick-start ##

-keep public class com.google.ads.** {
   public *;
}

# Facebook 3.2

-keep class com.facebook.** { *; }
-keepattributes Signature



-dontwarn org.joda.convert.**
-dontwarn org.joda.time.**
-keep class org.joda.time.** { *; }
-keep interface org.joda.time.** { *; }

-dontwarn okio.**
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**


# support design
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

# Support library
-dontwarn android.support.**
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.** { *; }
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }

-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}


# rxjava
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}