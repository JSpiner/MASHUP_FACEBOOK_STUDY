package net.jspiner.mashup2;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;
import java.security.MessageDigest;

public class LoginActivity extends AppCompatActivity {

    private static final int ACTIVITY_START_DELAY_MILLIS = 1000 * 3;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        initStatusBar();
        initFacebookLogin();

        printAppHashKey();
    }

    private void initStatusBar() {
        setWindowLightStatusBarEnable();
        setStatusBarColor(Color.WHITE);
    }

    protected void setStatusBarColor(@ColorInt int color) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(color);
    }

    protected void setWindowLightStatusBarEnable() {
        View view = findViewById(R.id.root);

        int flags = view.getSystemUiVisibility();
        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        view.setSystemUiVisibility(flags);
    }

    public void printAppHashKey() {
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo(
                    "net.jspiner.mashup2",
                    PackageManager.GET_SIGNATURES
            );
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.i("TAG", "MY KEY HASH:" + hashKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "NameNotFoundException");
        }
    }

    private void initFacebookLogin() {

    }

    private void requestLogin() {
        onLoginError();
    }

    private void onLoginSuccess() {
        Toast.makeText(this, "로그인되셨습니다.", Toast.LENGTH_SHORT).show();
        startMainActivity();
    }

    private void onLoginError() {
        Toast.makeText(this, "에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
    }

    private void startMainActivityWithDelay() {
        handler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        startMainActivity();
                    }
                },
                ACTIVITY_START_DELAY_MILLIS
        );
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
