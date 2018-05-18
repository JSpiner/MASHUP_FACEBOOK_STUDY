package net.jspiner.mashup2;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.Profile;
import com.google.gson.Gson;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WriteActivity extends AppCompatActivity {

    private final int STORAGE_PERMISSION_CODE = 8225;
    private final int GALLERY_INTENT_CODE = 1070;

    private EditText content;
    private File image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        init();
    }

    private void init() {
        initViews();
    }

    private void initViews() {
        initToolbar();
        content = findViewById(R.id.content);
        findViewById(R.id.write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
        findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGalleryActivity();
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("글 작성하기");
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private void checkValidation() {
        String contentText = content.getText().toString();
        if (TextUtils.isEmpty(contentText) || image == null) {
            Toast.makeText(getBaseContext(), "값을 입력해주세요", Toast.LENGTH_SHORT).show();
        } else {
            requestWritePost();
        }
    }

    private void requestWritePost() {
        Profile profile = Profile.getCurrentProfile();

        NetworkRequest.requestWritePost(
                profile.getId(),
                content.getText().toString(),
                image,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onWriteError();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onWriteSuccess();
                            }
                        });
                    }
                }
        );
    }

    private void onWriteError() {
        Toast.makeText(getBaseContext(), "서버 에러가 발생하였습니다.", Toast.LENGTH_SHORT).show();
    }

    private void onWriteSuccess() {
        Toast.makeText(getBaseContext(), "게시글이 작성되었습니다.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void startGalleryActivity() {
        if (isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Matisse.from(this)
                    .choose(MimeType.allOf())
                    .countable(true)
                    .maxSelectable(1)
                    .thumbnailScale(0.85f)
                    .imageEngine(new GlideEngine())
                    .forResult(GALLERY_INTENT_CODE);
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },
                    STORAGE_PERMISSION_CODE

            );
        }
    }

    private boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(
                this,
                permission
        ) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_INTENT_CODE) {
            List<Uri> uriList = Matisse.obtainResult(data);
            onGalleryImageReceived(uriList.get(0));
        }
    }

    private void onGalleryImageReceived(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            ImageView imageView = findViewById(R.id.image);
            imageView.setImageBitmap(bitmap);

            image = new File(getRealPath(uri));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "이미지를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public String getRealPath(Uri uri) {
        String resourcePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            resourcePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return resourcePath;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
