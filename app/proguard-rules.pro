# ProGuard rules for Kazanova Keyboard
# يتم استخدام هذا الملف لتقليل حجم التطبيق

# Keep all public classes and their methods
-keep public class * {
    public *;
}

# Keep Android classes
-keep class android.** { *; }
-keep interface android.** { *; }

# Keep support library
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# Keep custom application classes
-keep class com.kazanova.keyboard.** { *; }
-keep interface com.kazanova.keyboard.** { *; }

# Keep enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Remove logging
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# Optimization
-optimizationpasses 5
-dontusemixedcaseclassnames
