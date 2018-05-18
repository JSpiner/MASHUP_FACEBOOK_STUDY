package net.jspiner.mashup2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        initViews();

        requestPostList();
    }

    private void initViews() {
        initToolbar();
        initRecyclerView();

        findViewById(R.id.write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWriteActivity();
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private void initRecyclerView() {

    }

    private void requestPostList() {
        onLoadError();
    }

    private void onLoadError() {
        findViewById(R.id.network_error).setVisibility(View.VISIBLE);
    }

    private void onLoaded(ArrayList<Object> postList) {
        findViewById(R.id.network_error).setVisibility(View.GONE);
    }

    private void startWriteActivity() {
        Intent intent = new Intent(this, WriteActivity.class);
        startActivity(intent);
    }
}
