package com.kazanova.keyboard;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity - النشاط الرئيسي للتطبيق
 * يعرض واجهة المستخدم الأساسية والإعدادات
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ربط الأزرار
        setupButtons();
    }

    private void setupButtons() {
        Button btnSettings = findViewById(R.id.btnSettings);
        Button btnAbout = findViewById(R.id.btnAbout);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAbout();
            }
        });
    }

    private void openSettings() {
        // افتح شاشة الإعدادات
        // يمكنك إضافة كود هنا لاحقاً
    }

    private void showAbout() {
        // اعرض معلومات التطبيق
        // يمكنك إضافة كود هنا لاحقاً
    }
}
