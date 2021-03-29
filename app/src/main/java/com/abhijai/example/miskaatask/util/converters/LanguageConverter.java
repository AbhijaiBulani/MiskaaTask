package com.abhijai.example.miskaatask.util.converters;

import androidx.room.TypeConverter;

import com.abhijai.example.miskaatask.models.Language;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LanguageConverter {
    @TypeConverter
    public String fromLanguageListToString(List<Language> languageList){
        if (languageList != null){
            StringBuilder temp = new StringBuilder();
            for (Language la : languageList){
                temp.append(la.getName()).append(" ");
            }
            //temp = temp.deleteCharAt(temp.length() - 1);
            return temp.toString();
        }
        else {
            return "";
        }
    }

    @TypeConverter
    public List<Language> fromStringToLanguageList(String languageString){
        if (languageString!=null){
            String[] strArr = languageString.split(" ");
            List<Language> returnedData = new ArrayList<>();
            for (String str : strArr){
                Language la = new Language();
                la.setName(str);
                returnedData.add(la);
            }
            return returnedData;
        }
        else {
            return null;
        }
    }
}
