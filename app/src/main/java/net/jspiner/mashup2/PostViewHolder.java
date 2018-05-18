package net.jspiner.mashup2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class PostViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private ImageView profileImage;
    private TextView name;
    private TextView date;
    private TextView content;
    private ImageView contentImage;

    public PostViewHolder(View itemView) {
        super(itemView);

        context = itemView.getContext();
        profileImage = itemView.findViewById(R.id.profile_image);
        name = itemView.findViewById(R.id.name);
        date = itemView.findViewById(R.id.date);
        content = itemView.findViewById(R.id.content);
        contentImage = itemView.findViewById(R.id.content_image);
    }

    public void setData(Post post) {
        Log.i("TAG", "post : " + new Gson().toJson(post));
        Glide.with(context)
                .load(post.writer.profileImage)
                .into(profileImage);

        name.setText(post.writer.name);
        content.setText(post.content);

        Glide.with(context)
                .load(NetworkRequest.API_URL + post.contentImage)
                .into(contentImage);
    }

}
