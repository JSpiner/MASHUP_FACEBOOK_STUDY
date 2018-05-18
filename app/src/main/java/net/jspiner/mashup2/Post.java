package net.jspiner.mashup2;

import com.google.gson.annotations.SerializedName;

public class Post {

    public String content;

    @SerializedName("content_image")
    public String contentImage;

    public long date;

    @SerializedName("postId")
    public long postId;

    public Writer writer;

}
