package net.jspiner.mashup2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private PostAdapter postAdapter;
    private RecyclerView recyclerView;

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
        postAdapter = new PostAdapter();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(postAdapter);

    }

    private void requestPostList() {
        NetworkRequest.requestPostList(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onLoadError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() != 200) {
                    onLoadError();
                    return;
                }
                ArrayList<Post> postList = new Gson().fromJson(
                        response.body().string(),
                        new TypeToken<ArrayList<Post>>(){}.getType()
                );

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onLoaded(postList);
                    }
                });
            }
        });
    }

    private void onLoadError() {
        findViewById(R.id.network_error).setVisibility(View.VISIBLE);
    }

    private void onLoaded(ArrayList<Post> postList) {
        findViewById(R.id.network_error).setVisibility(View.GONE);
        postAdapter.setData(postList);
    }

    private void startWriteActivity() {
        Intent intent = new Intent(this, WriteActivity.class);
        startActivity(intent);
    }
}
