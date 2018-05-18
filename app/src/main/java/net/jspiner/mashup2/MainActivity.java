package net.jspiner.mashup2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
        initRecyclerView();

        requestPostList();
    }

    private void initRecyclerView() {
        postAdapter = new PostAdapter();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(postAdapter);

        ArrayList<Post> test = new ArrayList<>();
        test.add(new Post());
        test.add(new Post());
        test.add(new Post());

        postAdapter.setData(test);
    }

    private void requestPostList() {
        NetworkRequest.requestPostList(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onLoadError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
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

    }

    private void onLoaded(ArrayList<Post> postList) {
        postAdapter.setData(postList);
    }
}
