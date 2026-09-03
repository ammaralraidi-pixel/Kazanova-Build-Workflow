📚 # شرح مفصل لملف build.gradle

---

## 🎯 ما هو build.gradle؟

ملف `build.gradle` هو **ملف التكوين الأساسي** للمشروع Android. يتحكم في:
- ✅ إصدارات SDK المستخدمة
- ✅ المكتبات والمتطلبات
- ✅ إعدادات البناء (Debug و Release)
- ✅ التوقيع الرقمي
- ✅ ProGuard و Minification

---

## 📁 هناك ملفا build.gradle:

### 1️⃣ **build.gradle (Project Level)**
📍 المسار: `build.gradle` (في جذر المشروع)

```gradle
// هذا على مستوى المشروع كله
buildscript {
    repositories {
        google()        // مستودع Google
        mavenCentral()  // مستودع Maven المركزي
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:8.0.2'  // إصدار Android Gradle Plugin
    }
}

plugins {
    id 'com.android.application' version '8.0.2' apply false
    id 'com.android.library' version '8.0.2' apply false
}

task clean(type: Delete) {
    delete rootProject.buildDir  // تنظيف البناء القديم
}
```

**الشرح:**
- `buildscript` - إعدادات البناء الأساسية
- `repositories` - أين نحصل على المكتبات
- `classpath` - إصدار أداة البناء (Android Gradle)
- `task clean` - أمر لحذف ملفات البناء القديمة

---

### 2️⃣ **app/build.gradle (App Level)**
📍 المسار: `app/build.gradle` (داخل مجلد app)

هذا هو الملف **الأكثر أهمية**:

```gradle
plugins {
    id 'com.android.application'  // تحديد أن هذا تطبيق Android
}

android {
    namespace 'com.kazanova.keyboard'  // معرّف فريد للتطبيق (ID)
    compileSdk 34                      // إصدار SDK للبناء
    
    defaultConfig {
        applicationId "com.kazanova.keyboard"  // رقم تعريف التطبيق (يجب أن يكون فريداً)
        minSdk 21                              // أقل إصدار Android مدعوم
        targetSdk 34                           // أحدث إصدار Android
        versionCode 1                          // رقم الإصدار (للنشر)
        versionName "1.0.0"                   // اسم الإصدار (يراه المستخدم)
    }
    
    buildTypes {
        debug {
            debuggable true           // السماح بتصحيح الأخطاء
            minifyEnabled false       // عدم تقليل حجم الكود
        }
        release {
            debuggable false          // عدم السماح بالتصحيح
            minifyEnabled true        // تقليل حجم الكود
            shrinkResources true      // حذف الموارد غير المستخدمة
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
    
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_11  // إصدار Java
        targetCompatibility JavaVersion.VERSION_11
    }
}

dependencies {
    // المكتبات المطلوبة
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'com.google.android.material:material:1.9.0'
}
```

---

## 🔑 معاني الكلمات المهمة:

### 📌 **namespace** و **applicationId**
```gradle
namespace 'com.kazanova.keyboard'
applicationId "com.kazanova.keyboard"
```
- هذا هو **معرّف فريد** للتطبيق
- يجب أن يكون فريداً على Google Play
- لا يمكن نشر تطبيقين بنفس الـ ID

**أمثلة:**
- ✅ `com.facebook.android`
- ✅ `com.whatsapp`
- ✅ `com.kazanova.keyboard` (اختيارك)

---

### 📌 **minSdk** و **targetSdk**

```gradle
minSdk 21        // Android 5.0 (2014)
targetSdk 34     // Android 14 (2023)
```

**الجدول:**
```
API Level  الإصدار        السنة
21         Android 5.0     2014
24         Android 7.0     2016
28         Android 9.0     2018
30         Android 11      2020
34         Android 14      2023
```

**القاعدة:**
- `minSdk` - الأجهزة الأقدم المدعومة
- `targetSdk` - أحدث Android تم اختباره عليه
- كلما ارتفع `targetSdk`، كلما كان التطبيق أفضل

---

### 📌 **versionCode** و **versionName**

```gradle
versionCode 1      // رقم داخلي (1, 2, 3, ...)
versionName "1.0.0"  // رقم يراه المستخدم
```

**مثال لعدة إصدارات:**
```
الإصدار 1:
  versionCode = 1
  versionName = "1.0.0"

الإصدار 2:
  versionCode = 2
  versionName = "1.0.1" (تحديث صغير)

الإصدار 3:
  versionCode = 3
  versionName = "1.1.0" (ميزات جديدة)

الإصدار 4:
  versionCode = 4
  versionName = "2.0.0" (تحديث كبير)
```

---

### 📌 **buildTypes** (Debug و Release)

#### **Debug Build:**
```gradle
debug {
    debuggable true       // السماح باختبار الكود
    minifyEnabled false   // لا تقلل الكود (أسهل للتطوير)
}
```
- ✅ للاختبار المحلي
- ✅ الملف أكبر حجماً
- ✅ الكود سهل القراءة

#### **Release Build:**
```gradle
release {
    debuggable false           // لا تسمح باختبار
    minifyEnabled true         // قلل الكود (حجم أصغر)
    shrinkResources true       // احذف الموارد غير المستخدمة
    proguardFiles ... 'proguard-rules.pro'  // قوانين تقليل الحجم
}
```
- ✅ للنشر على Google Play
- ✅ الملف أصغر حجماً (~5 MB بدلاً من 20 MB)
- ✅ الأداء أفضل

---

## 🛠️ كيفية تعديل الملف:

### 1️⃣ غير معرّف التطبيق (ID):
```gradle
// من هنا:
applicationId "com.kazanova.keyboard"

// إلى:
applicationId "com.example.myapp"  // ID فريد لك
```

### 2️⃣ غير رقم الإصدار (للتحديثات):
```gradle
// النسخة الأولى:
versionCode 1
versionName "1.0.0"

// بعد إضافة ميزات:
versionCode 2
versionName "1.0.1"

// تحديث كبير:
versionCode 3
versionName "1.1.0"
```

### 3️⃣ أضف مكتبات جديدة:
```gradle
dependencies {
    // المكتبات الموجودة...
    
    // أضف هنا:
    implementation 'com.squareup.okhttp3:okhttp:4.11.0'  // مثال
}
```

### 4️⃣ أضف التوقيع الرقمي (للـ Release):
```gradle
signingConfigs {
    release {
        storeFile file("../my-release-key.keystore")
        storePassword System.getenv("KEYSTORE_PASSWORD")
        keyAlias System.getenv("KEY_ALIAS")
        keyPassword System.getenv("KEY_PASSWORD")
    }
}

buildTypes {
    release {
        signingConfig signingConfigs.release
        // باقي الإعدادات...
    }
}
```

---

## 📊 مثال: بناء التطبيق محلياً

```bash
# 1. بناء Debug (للاختبار)
./gradlew assembleDebug
# الملف النتيجة: app/build/outputs/apk/debug/app-debug.apk

# 2. بناء Release (للنشر)
./gradlew assembleRelease
# الملف النتيجة: app/build/outputs/apk/release/app-release.apk

# 3. تنظيف البناء القديم
./gradlew clean

# 4. فحص الأخطاء
./gradlew lint

# 5. تشغيل الاختبارات
./gradlew test
```

---

## ⚠️ أخطاء شائعة:

### ❌ خطأ 1: "Manifest merger failed"
```
السبب: minSdk أو targetSdk غير متوافق
الحل: غير القيمة إلى رقم أعلى
```

### ❌ خطأ 2: "Could not find ..."
```
السبب: مكتبة غير موجودة
الحل: تحقق من اسم المكتبة والإصدار
```

### ❌ خطأ 3: "Compilation failed"
```
السبب: كود Java خاطئ
الحل: تحقق من رسالة الخطأ وصحح الكود
```

---

## 🎓 مثال عملي كامل:

```gradle
// app/build.gradle - نسخة كاملة مستخدمة

plugins {
    id 'com.android.application'
}

android {
    namespace 'com.kazanova.keyboard'
    compileSdk 34

    defaultConfig {
        applicationId "com.kazanova.keyboard"
        minSdk 21
        targetSdk 34
        versionCode 1
        versionName "1.0.0"
        
        // إضافات مفيدة:
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            debuggable true
            minifyEnabled false
        }
        release {
            debuggable false
            minifyEnabled true
            shrinkResources true
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_11
        targetCompatibility JavaVersion.VERSION_11
    }
}

dependencies {
    // Core Android
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'com.google.android.material:material:1.9.0'

    // IME Library
    implementation 'androidx.inputmethod:inputmethod:1.1.0-alpha01'

    // Testing
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
}
```

---

## ✅ قائمة التحقق:

- [ ] هل `applicationId` فريد؟
- [ ] هل `minSdk` و `targetSdk` صحيحة؟
- [ ] هل جميع المكتبات موجودة؟
- [ ] هل تم تعديل `versionCode` قبل النشر؟
- [ ] هل التوقيع الرقمي معد؟
- [ ] هل `proguard-rules.pro` موجود؟

---

## 📞 المراجع:

- 📖 [Android Gradle Plugin Guide](https://developer.android.com/studio/build)
- 📖 [Dependencies في Gradle](https://developer.android.com/studio/build/dependencies)
- 📖 [Build Configuration](https://developer.android.com/studio/build/gradle-tips)

---

**تم الإنشاء بـ ❤️ - Kazanova Keyboard Development Guide**
