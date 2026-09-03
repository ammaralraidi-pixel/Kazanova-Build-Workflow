# Troubleshooting Guide - دليل استكشاف الأخطاء

## 🔴 أخطاء شائعة والحلول

---

## 1. ❌ "Permission denied" للـ gradlew

### المشكلة:
```
bash: ./gradlew: Permission denied
```

### الحل:
```bash
# تأكد من أن gradlew قابل للتنفيذ
git update-index --chmod=+x gradlew
git commit -m "Make gradlew executable"
git push
```

---

## 2. ❌ "Keystore file not found" أو "Invalid keystore"

### المشكلة:
```
Keystore file: /home/runner/debug.keystore (No such file or directory)
Invalid keystore format, or corrupted keystore data
```

### الحل:
- ✅ تأكد من تحويل Keystore إلى Base64 **بشكل صحيح**
- ✅ تحقق من أن Secret اسمه **بالضبط**: `DEBUG_KEYSTORE` أو `RELEASE_KEYSTORE`
- ✅ تأكد من أن كلمات المرور **صحيحة تماماً**
- ✅ جرب هذا الأمر لتحويل Keystore:

```bash
# Linux/Mac
base64 my-app-key.keystore > keystore.txt
cat keystore.txt

# Windows PowerShell
$bytes = [System.IO.File]::ReadAllBytes('my-app-key.keystore')
$base64 = [System.Convert]::ToBase64String($bytes)
Set-Clipboard -Value $base64
```

---

## 3. ❌ "Build failed: Gradle build error"

### المشكلة:
```
Build failed with an exception.
What went wrong: Execution failed for task ':app:compileDebugJava'.
```

### الحل:
1. **تحقق من `build.gradle`:**
```gradle
android {
    compileSdkVersion 33  // تأكد من الإصدار
    
    defaultConfig {
        minSdkVersion 21
        targetSdkVersion 33
    }
}

dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    // أضف كل المكتبات المطلوبة
}
```

2. **تشغيل Gradle محلياً:**
```bash
./gradlew clean build
```

3. **مسح الكاش:**
```bash
./gradlew clean
rm -rf .gradle
./gradlew build
```

---

## 4. ❌ "Lint check failed"

### المشكلة:
```
Lint found errors in your code:
- AndroidLintTypographyDashes
- AndroidLintMissingTranslation
```

### الحل:

**أ) إصلاح الأخطاء في الكود:**
- اذهب إلى الملف المذكور
- صحح المشاكل

**ب) أو تعطيل Lint مؤقتاً في `build.gradle`:**
```gradle
android {
    lintOptions {
        abortOnError false
        disable 'MissingTranslation', 'ExtraTranslation'
    }
}
```

---

## 5. ❌ "Unit tests failed"

### المشكلة:
```
com.example.MyTest > testSomething FAILED
    AssertionError: expected <true> but was <false>
```

### الحل:

1. **تشغيل الاختبارات محلياً:**
```bash
./gradlew test
```

2. **مراجعة سجلات الاختبارات:**
- اذهب إلى: `Actions → Run → Artifacts → unit-test-reports`
- افتح `index.html` لرؤية التفاصيل الكاملة

3. **إصلاح الاختبارات:**
```java
@Test
public void testExample() {
    // تأكد من أن الاختبار صحيح
    assertTrue(true);
}
```

---

## 6. ❌ "Google Play upload failed"

### المشكلة:
```
Error: The following error occurred during execution:
Service account key file is invalid or expired.
```

### الحل:

**أ) تحقق من صحة Service Account JSON:**
1. اذهب إلى: https://play.google.com/console
2. **Settings → API Access**
3. حذف Service Account القديم
4. إنشاء واحد جديد
5. تحميل ملف JSON جديد
6. تحويله إلى Base64 وإضافته كـ Secret

**ب) تأكد من الأذونات:**
- اذهب إلى Google Cloud Console
- تحقق من أن Service Account له Role: `Editor`

**ج) تحقق من بيانات التطبيق:**
```yaml
# في workflow، تأكد من:
packageName: com.kazanova.keyboard  # نفس ID التطبيق في Google Play
releaseFiles: app/build/outputs/bundle/release/app-release.aab
track: internal  # أو: alpha, beta, production
```

---

## 7. ❌ "Discord/Slack notification not sent"

### المشكلة:
لا تصل الإشعارات رغم نجاح البناء

### الحل:

**أ) تحقق من Webhook URL:**
```bash
# اختبر الـ Discord Webhook
curl -X POST -H 'Content-type: application/json' \
  --data '{"text":"Test"}' \
  YOUR_WEBHOOK_URL
```

**ب) تأكد من أن Secret اسمه صحيح:**
- `DISCORD_WEBHOOK` (للـ Discord)
- `SLACK_WEBHOOK` (للـ Slack)

**ج) تحقق من أن Webhook نشط:**
- Discord: **Server Settings → Integrations → Webhooks** (تأكد من عدم حذفه)
- Slack: https://api.slack.com/apps (تحقق من أن التطبيق فعّال)

---

## 8. ❌ "Artifact not found"

### المشكلة:
```
Unable to find artifact file: app/build/outputs/apk/debug/app-debug.apk
```

### الحل:

1. **تأكد من path الصحيح:**
```bash
# محلياً، بناء Debug APK وتحقق من المسار
./gradlew assembleDebug
find . -name "*.apk" -type f
```

2. **قد يكون البناء فشل:**
- اذهب إلى logs وشاهد رسالة الخطأ الفعلية
- صحح الخطأ وأعد المحاولة

3. **تأكد من اسم المشروع في workflow:**
```yaml
# في build-and-deploy.yml، تحقق من:
path: app/build/outputs/apk/debug/app-debug.apk
# قد يكون المجلد اسمه مختلف (مثل: myapp)
```

---

## 9. ❌ "secrets.RELEASE_KEYSTORE is not defined"

### المشكلة:
```
Error: Secrets not available. Please ensure all required secrets are defined.
```

### الحل:

1. **اذهب إلى**: **Settings → Secrets and variables → Actions**
2. **تأكد من أن جميع الـ Secrets موجودة:**
   - DEBUG_KEYSTORE
   - DEBUG_KEYSTORE_PASSWORD
   - DEBUG_KEY_ALIAS
   - DEBUG_KEY_PASSWORD
   - RELEASE_KEYSTORE
   - RELEASE_KEYSTORE_PASSWORD
   - RELEASE_KEY_ALIAS
   - RELEASE_KEY_PASSWORD
   - PLAY_STORE_SERVICE_ACCOUNT
   - DISCORD_WEBHOOK (اختياري)
   - SLACK_WEBHOOK (اختياري)

3. **تأكد من الأسماء**: يجب أن تكون **بالضبط** كما هي مكتوبة (حساسة لحالة الأحرف)

---

## 10. ❌ "Rate limit exceeded"

### المشكلة:
```
API rate limit exceeded. Please retry later.
```

### الحل:
- انتظر 1 ساعة قبل محاولة البناء مرة أخرى
- استخدم `workflow_dispatch` بدلاً من الـ Auto-trigger

---

## 11. ❌ "Out of memory" أو "Gradle daemon error"

### المشكلة:
```
FAILURE: Build failed with an exception.
OutOfMemoryError: Java heap space
```

### الحل:

1. **في `.github/workflows/build-and-deploy.yml`:**
```yaml
env:
  GRADLE_OPTS: "-Xmx4096m -Xms512m"
```

2. **أو في `gradle.properties`:**
```properties
org.gradle.jvmargs=-Xmx2048m -XX:MaxPermSize=512m
```

---

## 📊 كيفية قراءة سجلات الخطأ

### الخطوة 1: اذهب إلى Actions
```
GitHub Repository → Actions → أحدث Run
```

### الخطوة 2: اضغط على Job الفاشل
```
عادة يكون أحمر ❌
```

### الخطوة 3: ابحث عن الخطأ الفعلي
```
ابحث عن كلمات مثل:
- ERROR
- FAILURE
- Exception
- Invalid
```

### الخطوة 4: انسخ رسالة ا��خطأ الكاملة
```
استخدمها للبحث أو اطلب المساعدة
```

---

## 🆘 إذا لم تجد الحل

### 1. تحقق من:
- [ ] هل جميع الملفات المطلوبة موجودة؟
- [ ] هل all Secrets مضافة بشكل صحيح؟
- [ ] هل المشروع يعمل محلياً؟
- [ ] هل استخدمت الإصدارات الصحيحة من المكتبات؟

### 2. جرب هذا:
```bash
# تنظيف شامل
./gradlew clean

# بناء من جديد
./gradlew build

# مع verbose output
./gradlew build --info
```

### 3. اطلب المساعدة:
- 📖 [GitHub Actions Docs](https://docs.github.com/actions)
- 📖 [Android Gradle Docs](https://developer.android.com/studio/build)
- 📖 [Stack Overflow](https://stackoverflow.com/questions/tagged/android+gradle)

---

**تذكر:** معظم الأخطاء تحدث بسبب:
1. ❌ Secrets غير صحيحة
2. ❌ build.gradle خاطئ
3. ❌ مسارات ملفات غير صحيحة
4. ❌ كود Android يحتوي على أخطاء

تحقق من هذه الأشياء أولاً! ✅
