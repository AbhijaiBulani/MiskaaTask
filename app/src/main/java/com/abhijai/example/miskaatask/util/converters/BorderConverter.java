package com.abhijai.example.miskaatask.util.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BorderConverter {
    @TypeConverter
    public String fromBorderListToString(List<String> borderValues) {
        if (borderValues == null) {
            return (null);
        }
        StringBuilder temp = new StringBuilder();
        for (String str : borderValues){
            temp.append(str).append(" ");
        }
        //temp = temp.deleteCharAt(temp.length() - 1);
        return temp.toString();
    }

    @TypeConverter // note this annotation
    public List<String> fromBorderStringToList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        String[] str = optionValuesString.split(" ");
        return new ArrayList<>(Arrays.asList(str));
    }

}
