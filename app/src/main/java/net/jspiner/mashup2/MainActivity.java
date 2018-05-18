package net.jspiner.mashup2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

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
}
