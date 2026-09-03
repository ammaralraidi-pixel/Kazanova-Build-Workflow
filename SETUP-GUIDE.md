# 🔧 دليل الإعداد الكامل - Kazanova Keyboard Workflow

دليل خطوة بخطوة لإعداد سير العمل بشكل صحيح

---

## 📋 الخطوة 1: إنشاء Keystore للتوقيع

### أ) إنشاء Keystore جديد (إذا لم تكن لديك واحد)

```bash
# لنظام Windows/Mac/Linux
keytool -genkey -v -keystore my-app-key.keystore -keyalg RSA -keysize 2048 -validity 10000 -alias my-key-alias

# سيطلب منك:
# 1. كلمة مرور Keystore
# 2. معلومات عامة (الاسم، الشركة، إلخ)
# 3. كلمة مرور المفتاح
```

### ب) تحويل Keystore إلى Base64

**لنظام Mac/Linux:**
```bash
base64 my-app-key.keystore | tr -d '\n' > keystore.txt
cat keystore.txt  # انسخ المحتوى
```

**لنظام Windows (PowerShell):**
```powershell
$keystore = [Convert]::ToBase64String([IO.File]::ReadAllBytes("my-app-key.keystore"))
Set-Clipboard -Value $keystore
```

**أو استخدم هذا الموقع:**
https://www.base64encode.org/ (اختر ملف واضغط Encode)

---

## 🔐 الخطوة 2: إضافة GitHub Secrets

### الطريقة:
1. اذهب إلى مستودعك على GitHub
2. **Settings** → **Secrets and variables** → **Actions**
3. اضغط **New repository secret**

### أضف هذه Secrets:

#### للتوقيع Debug:
```
Name: DEBUG_KEYSTORE
Value: [ملصق Base64 الكامل للـ Keystore]
```

```
Name: DEBUG_KEYSTORE_PASSWORD
Value: [كلمة المرور التي استخدمتها عند الإنشاء]
```

```
Name: DEBUG_KEY_ALIAS
Value: [اسم المفتاح، مثال: my-key-alias]
```

```
Name: DEBUG_KEY_PASSWORD
Value: [كلمة مرور المفتاح]
```

#### للتوقيع Release:
نفس الخطوات السابقة لكن استبدل `DEBUG_` بـ `RELEASE_`

```
Name: RELEASE_KEYSTORE
Value: [نفس قيمة Keystore أو واحد مختلف]

Name: RELEASE_KEYSTORE_PASSWORD
Value: [كلمة المرور]

Name: RELEASE_KEY_ALIAS
Value: [اسم المفتاح]

Name: RELEASE_KEY_PASSWORD
Value: [كلمة مرور المفتاح]
```

---

## 🎮 الخطوة 3: إعداد Google Play Console

### أ) إنشاء Service Account

1. اذهب إلى: https://play.google.com/console
2. اختر تطبيقك أو أنشئ واحد جديد
3. **Settings** → **API Access**
4. اضغط **Create new service account**
5. سيأخذك إلى Google Cloud Console

### ب) في Google Cloud Console:

1. **Navigation Menu** → **APIs & Services** → **Credentials**
2. اضغط **Create Credentials** → **Service Account**
3. أكمل البيانات:
   - Service account name: `kazanova-play-uploader`
   - Description: `Service account for uploading Kazanova Keyboard`
4. اضغط **Create and Continue**

### ج) إضافة الأدوار:

1. في صفحة Permissions:
   - اختر Role: `Editor`
2. اضغط **Continue** ثم **Done**

### د) إنشاء مفتاح JSON:

1. اضغط على Service Account المُنشأ
2. اختر tab: **Keys**
3. اضغط **Add Key** → **Create new key**
4. اختر **JSON**
5. سيتم تحميل ملف JSON تلقائياً - احفظه

### هـ) تحويل JSON إلى Base64:

**Mac/Linux:**
```bash
base64 downloaded-key.json | tr -d '\n' > play-store-key.txt
cat play-store-key.txt
```

**Windows:**
```powershell
$key = [Convert]::ToBase64String([IO.File]::ReadAllBytes("downloaded-key.json"))
Set-Clipboard -Value $key
```

### و) إضافة Secret في GitHub:

```
Name: PLAY_STORE_SERVICE_ACCOUNT
Value: [ملصق Base64 كامل للـ JSON]
```

---

## 💬 الخطوة 4: إضافة Webhooks (اختياري)

### Discord Webhook:

1. اذهب إلى خادمك على Discord
2. **Server Settings** → **Integrations** → **Webhooks**
3. اضغط **Create Webhook**
4. انسخ الـ URL
5. أضف Secret في GitHub:

```
Name: DISCORD_WEBHOOK
Value: https://discord.com/api/webhooks/...
```

### Slack Webhook:

1. اذهب إلى: https://api.slack.com/apps
2. اضغط **Create New App** → **From scratch**
3. أدخل الاسم واختر workspace
4. **Incoming Webhooks** → Enable
5. اضغط **Add New Webhook to Workspace**
6. اختر Channel وأكّد
7. انسخ الـ Webhook URL
8. أضف Secret في GitHub:

```
Name: SLACK_WEBHOOK
Value: https://hooks.slack.com/services/...
```

---

## 📱 الخطوة 5: إعداد ملف build.gradle

تأكد من أن `build.gradle` يحتوي على:

```gradle
android {
    compileSdkVersion 33
    
    defaultConfig {
        applicationId "com.kazanova.keyboard"
        minSdkVersion 21
        targetSdkVersion 33
        versionCode 1
        versionName "1.0.0"
    }
    
    buildTypes {
        debug {
            debuggable true
        }
        release {
            minifyEnabled true
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}
```

---

## 🧪 الخطوة 6: تجربة البناء

### محلياً أولاً:

```bash
# فحص Lint
./gradlew lint

# تشغيل الاختبارات
./gradlew test

# بناء Debug APK
./gradlew assembleDebug

# بناء Release APK
./gradlew assembleRelease
```

### ثم على GitHub:

1. اضغط زر **Code** → اختر branch
2. أضف أو عدّل ملف
3. اضغط **Commit**
4. اذهب إلى **Actions** ورقب البناء

---

## ✅ قائمة التحقق النهائية

قبل البدء، تأكد من:

- [ ] Keystore مُعد بشكل صحيح
- [ ] جميع Secrets مُضافة في GitHub
- [ ] Google Play Service Account مُعد
- [ ] build.gradle يعمل محلياً
- [ ] Repository يحتوي على كود Android صحيح
- [ ] gradlew موجود في المستودع
- [ ] Discord/Slack Webhooks (اختياري) مُضافة

---

## 🚀 الخطوة 7: تشغيل البناء الأول

1. اضغط **Actions** في مستودعك
2. اختر **Build, Test & Deploy APK**
3. اضغط **Run workflow**
4. اختر Branch واضغط **Run workflow**

---

## 📊 متابعة البناء

اذهب إلى **Actions** وشاهد:
- ✅ Lint & Unit Tests
- ✅ Build Debug APK
- ✅ Build Release APK
- ✅ Publish to Play Store
- ✅ Create GitHub Release

---

## 🆘 حل المشاكل الشائعة

### ❌ "Permission denied" للـ gradlew
```bash
git update-index --chmod=+x gradlew
git commit -m "Make gradlew executable"
git push
```

### ❌ "Invalid keystore"
- تأكد من تحويل Base64 صحيح
- تحقق من كلمات المرور

### ❌ "Build cache issues"
```bash
./gradlew clean
```

### ❌ "Secrets not found"
- تأكد من الأسماء الصحيحة (حساسة لحالة الأحرف)
- أضفها على branch الصحيح

---

**تم! الآن أنت جاهز للبدء 🎉**

لأي مشاكل: راجع سجلات البناء في **Actions** بالتفصيل
