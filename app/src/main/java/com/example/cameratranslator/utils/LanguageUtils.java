package com.example.cameratranslator.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Duy M. Nguyen on 5/26/2020.
 */
public class LanguageUtils {

    private static final String ENGLISH = "English (Tiếng Anh)";
    private static final String VIETNAMESE = "Tiếng Việt";
    private static final String CHINESE_SIMPLIFIED = "中文 (简体) (Tiếng Trung giản thể)";
    private static final String JAPANESE = "日本語 (Tiếng Nhật)";
    private static final String KOREAN = "한국어 (Tiếng Hàn)";

    public static String[] languages = {ENGLISH, VIETNAMESE, CHINESE_SIMPLIFIED, JAPANESE, KOREAN};

    public static Map<String, String> languageCode = new HashMap<String, String>() {
        {
            put(ENGLISH, "en");
            put(VIETNAMESE, "vi");
            put(CHINESE_SIMPLIFIED, "zh");
            put(JAPANESE, "ja");
            put(KOREAN, "ko");
        }
    };
}
