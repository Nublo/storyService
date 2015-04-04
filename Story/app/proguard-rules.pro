-dontwarn com.google.appengine.api.urlfetch.**
-dontwarn com.fasterxml.jackson.databind.**
-dontwarn com.squareup.okhttp.**
-dontwarn rx.**
-dontwarn retrofit.converter.JacksonConverter

# OrmLite uses reflection
-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }
-keepclassmembers class * {
  public <init>(android.content.Context);
}

# Robospice Configuration

-keepattributes Signature
-keepattributes *Annotation*
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

-keep class by.anatoldeveloper.stories.model.**
-keepclassmembers class by.anatoldeveloper.stories.model.** {
      *;
}