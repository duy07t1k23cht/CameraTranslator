package com.example.cameratranslator.utils.api;

import com.example.cameratranslator.database.flashcard.FlashCard;
import com.example.cameratranslator.model.LocalizedObjectAnnotation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Duy M. Nguyen on 5/25/2020.
 */
public class TextToSpeechAPIHelper {
    private final static String API_KEY = "AIzaSyBFx7FJJM7Jo9Bzv2qH7ZX1_013NeGWPRQ";
    private final static String URL_REQUEST = "https://texttospeech.googleapis.com/v1/text:synthesize?key=" + API_KEY;

    public static Single<String> getAudio(String text, String languageCode) {
        return Single.fromCallable(() -> getAudioData(text, languageCode));
    }

    public static Single<String> getAudio(LocalizedObjectAnnotation localizedObjectAnnotation, String languageCode) {
        return Single.fromCallable(() -> {
            if (localizedObjectAnnotation.getAudioContent() == null ||
                    localizedObjectAnnotation.getAudioContent().isEmpty() ||
                    !localizedObjectAnnotation.getCurrentLanguageCode().equals(languageCode)) {
                String text = localizedObjectAnnotation.getTranslation();
                return getAudioData(text, languageCode);
            } else {
                return localizedObjectAnnotation.getAudioContent();
            }
        });
    }

    public static Single<String> getAudio(FlashCard flashCard, String languageCode) {
        return Single.fromCallable(() -> {
            if (flashCard.getAudioContent() == null ||
                    flashCard.getAudioContent().isEmpty()) {
                String text = flashCard.getWord();
                return getAudioData(text, languageCode);
            } else {
                return flashCard.getAudioContent();
            }
        });
    }

    private static String getAudioData(
            String text,
            String languageCode
    ) {
        try {
            String jsonBody = "{\"audioConfig\": {\"audioEncoding\": \"LINEAR16\",\"pitch\": 0,\"speakingRate\": 1},\"input\": {\"text\": \"" + text + "\"},\"voice\": {\"languageCode\": \"" + languageCode + "\"}}";
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonBody);
            Request request = new Request.Builder()
                    .url(URL_REQUEST)
                    .post(body)
                    .build();

            Response response = getLogging().newCall(request).execute();
            String result = response.body().string();

            JSONObject jsonObject = new JSONObject(result);

            return jsonObject.getString("audioContent");

        } catch (IOException | JSONException | NullPointerException e) {
            e.printStackTrace();
        }

        return "";
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
