package net.jspiner.mashup2;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private ArrayList<Post> dataList;

    public PostAdapter() {
        dataList = new ArrayList<>();
    }

    public void setData(ArrayList<Post> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("TAG" , "onCreateViewHolder");
        return new PostViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.card_post,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Log.i("TAG" , "onBindViewHolder " + position);
        holder.setData(
                dataList.get(position)
        );
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
