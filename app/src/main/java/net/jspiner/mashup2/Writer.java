package net.jspiner.mashup2;

import com.google.gson.annotations.SerializedName;

public class Writer {

    @SerializedName("facebook_id")
    public long facebookId;

    public String name;

    @SerializedName("profile_image")
    public String profileImage;
}
