package by.epam.webtask.util;

public final class LanguageUtil {
    private static final String ENGLISH = "en_US";
    private static final String RUSSIAN = "ru_RU";
    private LanguageUtil(){}

    public static boolean isCorrectLanguage(String language){
        return language!=null && (language.equals(ENGLISH) || language.equals(RUSSIAN));
    }
}