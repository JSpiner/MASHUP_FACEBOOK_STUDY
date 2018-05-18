package net.jspiner.mashup2;

import java.io.File;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

public final class NetworkRequest {

    private static final String API_URL = "http://52.78.84.8:5000";
    private static OkHttpClient httpClient;

    public static OkHttpClient getHttpClient() {
        if (httpClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

            httpClient = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();
        }
        return httpClient;
    }

    public static void requestLogin(String facebookId, String name, String profileImage, Callback callback) {
        HttpUrl httpUrl = HttpUrl.parse(API_URL).newBuilder()
                .addEncodedPathSegment("/api/user")
                .build();

        RequestBody requestBody = new FormBody.Builder()
                .add("facebook_id", facebookId)
                .add("name", name)
                .add("profile_image", profileImage)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .post(requestBody)
                .build();

        getHttpClient().newCall(request).enqueue(callback);
    }

    public static void requestPostList(Callback callback) {
        HttpUrl httpUrl = HttpUrl.parse(API_URL).newBuilder()
                .addEncodedPathSegment("/api/post")
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .build();

        getHttpClient().newCall(request).enqueue(callback);
    }

    public static void requestWritePost(String userId, String content, File file, Callback callback) {
        HttpUrl httpUrl = HttpUrl.parse(API_URL).newBuilder()
                .addEncodedPathSegment("/api/post")
                .build();

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user_facebook_id", userId)
                .addFormDataPart("content", content)
                .addFormDataPart(
                        "image",
                        file.getName(),
                        RequestBody.create(MediaType.parse("image/png"), file)
                ).build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .post(body)
                .build();

        getHttpClient().newCall(request).enqueue(callback);

    }

}
