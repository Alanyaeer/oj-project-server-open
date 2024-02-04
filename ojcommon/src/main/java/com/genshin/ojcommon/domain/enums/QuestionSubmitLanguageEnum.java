package com.genshin.ojcommon.domain.enums;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 吴嘉豪
 * @date 2024/1/11 9:39
 */
public enum QuestionSubmitLanguageEnum {
    CPP(0, "CPP"),
    JAVA(1, "JAVA"),
    GO(2, "GO"),
    C(3, "C"),
    PYTHON(4, "PYTHON"),
    RUST(5, "RUST");
    private final Integer type;
    private final String language;

    QuestionSubmitLanguageEnum(Integer type, String language){
        this.type = type;
        this.language = language;
    }
    public static List<String> getLanguageList(){
        return Arrays.stream(values()).map(e->e.language).collect(Collectors.toList());
    }
    public static Map<Integer, String> getLanguageConfig(){
        return Arrays.stream(values()).collect(Collectors.toMap(QuestionSubmitLanguageEnum::getType, QuestionSubmitLanguageEnum::getLanguage));
    }

    public static Integer getCount() {
        return values().length;
    }

    public static String getEnumByKey(Integer languageId) {
        for (QuestionSubmitLanguageEnum value : QuestionSubmitLanguageEnum.values()) {
            if(value.getType() == languageId){
                return value.getLanguage();
            }
        }
        return null;
    }

    public static Integer getKeyToValue(String language) {
        for (QuestionSubmitLanguageEnum value : QuestionSubmitLanguageEnum.values()) {
            if(value.getLanguage().equals(language) ){
                return value.getType();
            }
        }
        return null;
    }

    public Integer getType(){
        return type;
    }
    public String getLanguage(){
        return language;
    }

}
