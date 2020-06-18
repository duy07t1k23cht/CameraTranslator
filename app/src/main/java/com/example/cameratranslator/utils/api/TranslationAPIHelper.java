package com.example.cameratranslator.utils.api;

import com.example.cameratranslator.model.LocalizedObjectAnnotation;
import com.example.cameratranslator.model.Translation;
import com.example.cameratranslator.utils.LanguageUtils;
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
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.example.cameratranslator.utils.LanguageUtils.languages;

/**
 * Created by Duy M. Nguyen on 5/20/2020.
 */
public class TranslationAPIHelper {

    private final static String URL_REQUEST = "https://translation.googleapis.com/language/translate/v2";
    private final static String API_KEY = "AIzaSyBFx7FJJM7Jo9Bzv2qH7ZX1_013NeGWPRQ";

    public static Single<List<Translation>> translate(
            List<LocalizedObjectAnnotation> localizedObjectAnnotations,
            String targetLanguageCode) {
        return Single.fromCallable(() -> getTranslationData(localizedObjectAnnotations, targetLanguageCode));
    }

    private static List<Translation> getTranslationData(
            List<LocalizedObjectAnnotation> localizedObjectAnnotations,
            String targetLanguageCode
    ) {
        if (targetLanguageCode == null)
            targetLanguageCode = LanguageUtils.languageCode.get(languages[0]);
        try {
            FormBody.Builder builder = new FormBody.Builder()
                    .add("key", API_KEY)
                    .add("target", targetLanguageCode);

            for (LocalizedObjectAnnotation objectAnnotation : localizedObjectAnnotations) {
                builder.add("q", objectAnnotation.getName());
            }

            RequestBody requestBody = builder.build();

            Request request = new Request.Builder()
                    .url(URL_REQUEST)
                    .post(requestBody)
                    .build();

            Response response = getLogging().newCall(request).execute();
            String result = response.body().string();

            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonTranslations = jsonObject
                    .getJSONObject("data")
                    .getJSONArray("translations");

            return new Gson()
                    .fromJson(
                            jsonTranslations.toString(),
                            new TypeToken<List<Translation>>() {
                            }.getType()
                    );

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private static OkHttpClient getLogging() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient
                .Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder().method(original.method(), original.body());
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                })
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }
}
