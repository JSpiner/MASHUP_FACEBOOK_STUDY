package net.jspiner.mashup2;

import com.google.gson.annotations.SerializedName;

public class Writer {

    @SerializedName("facebook_id")
    public long facebookId;

    public String name;

    @SerializedName("profileImage")
    public String profileImage;
}
