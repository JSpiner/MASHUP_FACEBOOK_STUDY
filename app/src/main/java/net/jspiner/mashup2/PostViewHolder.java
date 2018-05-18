package net.jspiner.mashup2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PostViewHolder extends RecyclerView.ViewHolder {

    private ImageView profileImage;
    private TextView name;
    private TextView date;
    private TextView content;
    private ImageView contentImage;

    public PostViewHolder(View itemView) {
        super(itemView);

        profileImage = itemView.findViewById(R.id.profile_image);
        name = itemView.findViewById(R.id.name);
        date = itemView.findViewById(R.id.date);
        content = itemView.findViewById(R.id.content);
        contentImage = itemView.findViewById(R.id.content_image);
    }

    public void setData(Post post) {
        name.setText(post.writer.name);
        content.setText(post.content);
    }

}
