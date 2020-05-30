package com.example.cameratranslator.utils;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.cameratranslator.model.LocalizedObjectAnnotation;
import com.example.cameratranslator.model.TextAnnotations;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.example.cameratranslator.utils.BitmapUtils.getStringBase64;

/**
 * Created by Duy M. Nguyen on 5/14/2020.
 */
public class ImageDetectionHelper {

    private final static String URL_REQUEST = "https://content-vision.googleapis.com/v1/images:annotate?.xgafv=1&alt=json&prettyPrint=true&key=AIzaSyCghTC9CkWTzu7NbFIb8lWW5f-aAWaPfO0&%24.xgafv=1";

    /// Text detection
    public static Single<List<TextAnnotations>> getTextData(Bitmap bitmap) {
        return Single.fromCallable(() -> {
            String base64 = getStringBase64(bitmap);
            String jsonBody = "{\"requests\": [{\n" +
                    "                \"image\": {\n" +
                    "                    \"content\": \"" + base64 + "\"\n" +
                    "                },\n" +
                    "                \"features\": [{\n" +
                    "                    \"type\": \"TEXT_DETECTION\",\n" +
                    "                    \"maxResults\": 50\n" +
                    "                }]\n" +
                    "            }]}";
            return getTextData(jsonBody);
        });
    }

    private static List<TextAnnotations> getTextData(String jsonBody) {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonBody);
            Request request = new Request.Builder()
                    .url(ImageDetectionHelper.URL_REQUEST)
//                    .addHeader("Content-Type", "application/json")
//                    .addHeader(
//                            "Referer",
//                            "https://content-vision.googleapis.com/static/proxy.html?usegapi=1&jsh=m%3B%2F_%2Fscs%2Fapps-static%2F_%2Fjs%2Fk%3Doz.gapi.vi.S2ZrayFw1_o.O%2Fam%3DwQE%2Fd%3D1%2Frs%3DAGLTcCNciJPc_PoDS84WYnHa1tuOu3o3eg%2Fm%3D__features__"
//                    )
//                    .addHeader("Sec-Fetch-Mode", "cors")
//                    .addHeader(
//                            "User-Agent",
//                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36"
//                    )
//                    .addHeader("authority", "content-vision.googleapis.com")
//                    .addHeader("origin", "https://content-vision.googleapis.com")
//                    .addHeader("sec-fetch-site", "same-origin")
//                    .addHeader(
//                            "x-client-data",
//                            "CKK1yQEIjrbJAQiktskBCMS2yQEI0LfJAQipncoBCKijygEIzqXKAQjiqMoBCJetygEIza3KAQjyrcoBCMuuygEIyq/KAQjIsMoB"
//                    )
//                    .addHeader(
//                            "x-clientdetails",
//                            "appVersion=5.0%20(Macintosh%3B%20Intel%20Mac%20OS%20X%2010_14_5)%20AppleWebKit%2F537.36%20(KHTML%2C%20like%20Gecko)%20Chrome%2F76.0.3809.100%20Safari%2F537.36&platform=MacIntel&userAgent=Mozilla%2F5.0%20(Macintosh%3B%20Intel%20Mac%20OS%20X%2010_14_5)%20AppleWebKit%2F537.36%20(KHTML%2C%20like%20Gecko)%20Chrome%2F76.0.3809.100%20Safari%2F537.36"
//                    )
//                    .addHeader("X-Goog-Encode-Response-If-Executable", "base64")
//                    .addHeader(
//                            "X-JavaScript-User-Agent",
//                            "apix/3.0.0 google-api-javascript-client/1.1.0"
//                    )
//                    .addHeader("x-origin", "https://explorer.apis.google.com")
//                    .addHeader("x-referer", "https://explorer.apis.google.com")
//                    .addHeader("Accept", "*/*")
                    .post(body)
                    .build();

            Response response = getLogging().newCall(request).execute();
            String result = response.body().string();

            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonObjectTextAnnotations = jsonObject
                    .getJSONArray("responses")
                    .getJSONObject(0)
                    .getJSONArray("textAnnotations");
            List<TextAnnotations> arrTextAnnotations = new Gson().fromJson(
                    jsonObjectTextAnnotations.toString(),
                    new TypeToken<List<TextAnnotations>>() {
                    }.getType());

            return arrTextAnnotations;

        } catch (IOException | NullPointerException | JSONException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    // Object detection
    public static Single<List<LocalizedObjectAnnotation>> getObjectData(Bitmap bitmap) {
        return Single.fromCallable(() -> {
            String base64 = getStringBase64(bitmap);
            String jsonBody = "{\"requests\": [{\n" +
                    "                \"image\": {\n" +
                    "                    \"content\": \"" + base64 + "\"\n" +
                    "                },\n" +
                    "                \"features\": [{\n" +
                    "                    \"type\": \"OBJECT_LOCALIZATION\",\n" +
                    "                    \"maxResults\": 50\n" +
                    "                }]\n" +
                    "            }]}";
            return getLabelData(jsonBody);
        });
    }

    private static List<LocalizedObjectAnnotation> getLabelData(String jsonBody) {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonBody);
            Request request = new Request.Builder()
                    .url(ImageDetectionHelper.URL_REQUEST)
//                    .addHeader("Content-Type", "application/json")
//                    .addHeader(
//                            "Referer",
//                            "https://content-vision.googleapis.com/static/proxy.html?usegapi=1&jsh=m%3B%2F_%2Fscs%2Fapps-static%2F_%2Fjs%2Fk%3Doz.gapi.vi.S2ZrayFw1_o.O%2Fam%3DwQE%2Fd%3D1%2Frs%3DAGLTcCNciJPc_PoDS84WYnHa1tuOu3o3eg%2Fm%3D__features__"
//                    )
//                    .addHeader("Sec-Fetch-Mode", "cors")
//                    .addHeader(
//                            "User-Agent",
//                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36"
//                    )
//                    .addHeader("authority", "content-vision.googleapis.com")
//                    .addHeader("origin", "https://content-vision.googleapis.com")
//                    .addHeader("sec-fetch-site", "same-origin")
//                    .addHeader(
//                            "x-client-data",
//                            "CKK1yQEIjrbJAQiktskBCMS2yQEI0LfJAQipncoBCKijygEIzqXKAQjiqMoBCJetygEIza3KAQjyrcoBCMuuygEIyq/KAQjIsMoB"
//                    )
//                    .addHeader(
//                            "x-clientdetails",
//                            "appVersion=5.0%20(Macintosh%3B%20Intel%20Mac%20OS%20X%2010_14_5)%20AppleWebKit%2F537.36%20(KHTML%2C%20like%20Gecko)%20Chrome%2F76.0.3809.100%20Safari%2F537.36&platform=MacIntel&userAgent=Mozilla%2F5.0%20(Macintosh%3B%20Intel%20Mac%20OS%20X%2010_14_5)%20AppleWebKit%2F537.36%20(KHTML%2C%20like%20Gecko)%20Chrome%2F76.0.3809.100%20Safari%2F537.36"
//                    )
//                    .addHeader("X-Goog-Encode-Response-If-Executable", "base64")
//                    .addHeader(
//                            "X-JavaScript-User-Agent",
//                            "apix/3.0.0 google-api-javascript-client/1.1.0"
//                    )
//                    .addHeader("x-origin", "https://explorer.apis.google.com")
//                    .addHeader("x-referer", "https://explorer.apis.google.com")
//                    .addHeader("Accept", "*/*")
                    .post(body)
                    .build();

            Response response = getLogging().newCall(request).execute();
            String result = response.body().string();

            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonObjectLabelAnnotations = jsonObject
                    .getJSONArray("responses")
                    .getJSONObject(0)
                    .getJSONArray("localizedObjectAnnotations");
            List<LocalizedObjectAnnotation> arrLocalizedObjectAnnotation = new Gson().fromJson(
                    jsonObjectLabelAnnotations.toString(),
                    new TypeToken<List<LocalizedObjectAnnotation>>() {
                    }.getType());

            return arrLocalizedObjectAnnotation;

        } catch (IOException | NullPointerException | JSONException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private static OkHttpClient getLogging() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient
                .Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        // Request customization: add request headers
                        Request.Builder requestBuilder = original.newBuilder().method(original.method(), original.body());
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }
}
