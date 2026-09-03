# Kazanova Keyboard - Build & Deployment Workflow 🚀

مستودع شامل لبناء واختبار ونشر تطبيق **Kazanova Keyboard** على Android مع إشعارات تلقائية وتكامل كامل مع Google Play Store.

---

## 📋 المميزات الرئيسية

### ✅ البناء والاختبار
- ✓ Lint Checks (فحص جودة الكود)
- ✓ Unit Tests (اختبارات الوحدة)
- ✓ Debug APK Building (بناء APK للاختبار)
- ✓ Release APK Building (بناء APK للإصدار)

### 🔐 التوقيع الرقمي
- ✓ توقيع آمن للـ APK (Debug و Release)
- ✓ إدارة آمنة لمفاتيح التوقيع عبر GitHub Secrets
- ✓ توليد Checksums للتحقق من سلامة الملفات

### 📱 النشر التلقائي
- ✓ نشر تلقائي على Google Play Store
- ✓ رفع على مسار Internal Testing أولاً
- ✓ إنشاء GitHub Releases تلقائياً
- ✓ رفع الـ APK والـ Checksums

### 📢 الإشعارات
- ✓ إشعارات Discord عند النجاح والفشل
- ✓ إشعارات Slack اختيارية
- ✓ تفاصيل كاملة عن البناء والمطور والـ Commit

### ⚡ تحسينات الأداء
- ✓ Gradle Caching لتسريع البناء
- ✓ تشغيل المهام بالتوازي
- ✓ استخدام الإصدار الأحدث من Actions

---

## 🔧 الإعداد المطلوب

### 1️⃣ GitHub Secrets المطلوبة

اذهب إلى: **Settings → Secrets and variables → Actions**

أضف الـ Secrets التالية:

```
# للتوقيع Debug
DEBUG_KEYSTORE              # ملف Keystore بصيغة Base64
DEBUG_KEYSTORE_PASSWORD     # كلمة مرور Keystore
DEBUG_KEY_ALIAS             # اسم المفتاح
DEBUG_KEY_PASSWORD          # كلمة مرور المفتاح

# للتوقيع Release
RELEASE_KEYSTORE            # ملف Keystore بصيغة Base64
RELEASE_KEYSTORE_PASSWORD   # كلمة مرور Keystore
RELEASE_KEY_ALIAS           # اسم المفتاح
RELEASE_KEY_PASSWORD        # كلمة مرور المفتاح

# Google Play Store
PLAY_STORE_SERVICE_ACCOUNT  # ملف JSON لخدمة Google Play

# الإشعارات
DISCORD_WEBHOOK             # رابط Discord Webhook (اختياري)
SLACK_WEBHOOK               # رابط Slack Webhook (اختياري)
```

### 2️⃣ تحويل Keystore إلى Base64

```bash
# لنظام Linux/Mac
base64 your-keystore.keystore | tr -d '\n' | xclip -selection clipboard

# لنظام Windows PowerShell
[Convert]::ToBase64String([IO.File]::ReadAllBytes("your-keystore.keystore"))
```

### 3️⃣ إعداد Google Play Developer Account

1. اذهب إلى [Google Play Console](https://play.google.com/console)
2. انشئ Service Account مع الأذونات المطلوبة
3. حمّل ملف JSON الخاص به
4. حوّله إلى Base64 وضعه في GitHub Secrets

---

## 🚀 كيفية الاستخدام

### تشغيل Workflow تلقائياً
- ✅ عند الـ Push على branch `main`
- ✅ عند الـ Push على branch `develop`
- ✅ عند فتح Pull Request

### تشغيل يدوي
اذهب إلى: **Actions → Build, Test & Deploy APK → Run workflow**

---

## 📊 مراحل البناء

```
1. Lint & Unit Tests
   ├─ فحص جودة الكود
   └─ تشغيل الاختبارات

2. Build Debug APK (يعمل بالتوازي)
   ├─ بناء Debug APK
   ├─ توقيع الـ APK
   └─ رفع الـ Artifacts

3. Build Release APK (يعمل بالتوازي)
   ├─ بناء Release APK
   ├─ توقيع الـ APK
   └─ رفع الـ Artifacts

4. Publish to Play Store (عند main فقط)
   ├─ بناء App Bundle
   └─ رفع على Google Play

5. Notifications
   ├─ إشعار Discord
   └─ إشعار Slack

6. Create Release (عند main فقط)
   ├─ تحميل الـ APK
   └─ إنشاء GitHub Release
```

---

## 📥 تحميل الـ Artifacts

بعد انتهاء البناء بنجاح:

1. اذهب إلى **Actions → أحدث Run**
2. اضغط على **Artifacts** في الأسفل
3. حمّل:
   - `kazanova-debug-apk` (للاختبار)
   - `kazanova-release-apk` (للإصدار)
   - `kazanova-apk-checksum` (للتحقق)

---

## 🔍 مراقبة البناء

اذهب إلى **Actions** لرؤية:
- ✅ سجل البناء الكامل
- ✅ رسائل الأخطاء (إن وجدت)
- ✅ مدة كل خطوة
- ✅ استخدام الموارد

---

## 🛠️ استكشاف الأخطاء

### ❌ خطأ: "Keystore file not found"
**الحل:** تأكد من تحويل ملف Keystore إلى Base64 صحيحاً

### ❌ خطأ: "Build failed"
**الحل:** 
1. تحقق من `build.gradle` وصحة الإعدادات
2. تأكد من وجود `gradlew` في المستودع
3. راجع سجلات البناء المفصلة

### ❌ خطأ: "Lint or Test failures"
**الحل:**
1. اضغط على **lint-reports** أو **unit-test-reports**
2. صحح الأخطاء في الكود
3. أعد محاولة البناء

### ❌ خطأ: "Google Play upload failed"
**الحل:**
1. تأكد من صحة بيانات Service Account
2. تحقق من أن App Bundle صحيح
3. تأكد من أن الإصدار لم يُرفع بنفس الرقم من قبل

---

## 📞 المساعدة والدعم

للمزيد من المعلومات:
- 📖 [GitHub Actions Documentation](https://docs.github.com/en/actions)
- 📖 [Gradle Android Plugin](https://developer.android.com/studio/build)
- 📖 [Google Play Console Help](https://support.google.com/googleplay/android-developer)

---

## 📝 الترخيص

هذا المستودع مفتوح المصدر وخاضع لـ MIT License

---

## 👨‍💻 المطور

**عمار منصور ريدي** (Ammar Al-Raidi)
- GitHub: [@ammaralraidi-pixel](https://github.com/ammaralraidi-pixel)
- Project: Kazanova Keyboard v1.0

---

## 🎯 خارطة الطريق

- [ ] إضافة اختبارات UI تلقائية
- [ ] دعم Beta Testing على Google Play
- [ ] إضافة قياس الأداء والتحليلات
- [ ] دعم النشر على متاجر أخرى (F-Droid, APKPure)
- [ ] تحسينات الأمان والتشفير

---

**تم الإنشاء بـ ❤️ باستخدام GitHub Actions**
