package net.jspiner.mashup2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

}
