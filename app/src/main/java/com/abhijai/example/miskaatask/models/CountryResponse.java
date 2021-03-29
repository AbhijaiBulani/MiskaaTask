package com.abhijai.example.miskaatask.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.abhijai.example.miskaatask.util.converters.BorderConverter;
import com.abhijai.example.miskaatask.util.converters.LanguageConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "country_table")
public class CountryResponse{

    @NonNull
    @PrimaryKey
    @ColumnInfo
    @SerializedName("name")
    @Expose
    private String name;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Ignore
    private boolean status = true;
    @Ignore
    private String message = "";

    @Ignore
    @SerializedName("topLevelDomain")
    @Expose
    private List<String> topLevelDomain = null;

    @SerializedName("alpha2Code")
    @Expose
    private String alpha2Code;

    @SerializedName("alpha3Code")
    @Expose
    private String alpha3Code;

    @Ignore
    @SerializedName("callingCodes")
    @Expose
    private List<String> callingCodes = null;

    @ColumnInfo
    @SerializedName("capital")
    @Expose
    private String capital;

    @Ignore
    @SerializedName("altSpellings")
    @Expose
    private List<String> altSpellings = null;

    @ColumnInfo
    @SerializedName("region")
    @Expose
    private String region;

    @ColumnInfo
    @SerializedName("subregion")
    @Expose
    private String subregion;

    @ColumnInfo
    @SerializedName("population")
    @Expose
    private String population;


    @Ignore
    @SerializedName("latlng")
    @Expose
    private List<Double> latlng = null;


    @Ignore
    @SerializedName("demonym")
    @Expose
    private String demonym;

    @Ignore
    @SerializedName("area")
    @Expose
    private Double area;

    @Ignore
    @SerializedName("gini")
    @Expose
    private Double gini;

    @Ignore
    @SerializedName("timezones")
    @Expose
    private List<String> timezones = null;

    @TypeConverters(BorderConverter.class)
    @ColumnInfo
    @SerializedName("borders")
    @Expose
    private List<String> borders = null;

    @Ignore
    @SerializedName("nativeName")
    @Expose
    private String nativeName;

    @Ignore
    @SerializedName("numericCode")
    @Expose
    private String numericCode;

    @Ignore
    @SerializedName("currencies")
    @Expose
    private List<Currency> currencies = null;

    @TypeConverters(LanguageConverter.class)
    @ColumnInfo
    @SerializedName("languages")
    @Expose
    private List<Language> languages = null;

    @Ignore
    @SerializedName("translations")
    @Expose
    private Translations translations;

    @ColumnInfo
    @SerializedName("flag")
    @Expose
    private String flag;

    @Ignore
    @SerializedName("regionalBlocs")
    @Expose
    private List<RegionalBloc> regionalBlocs = null;

    @Ignore
    @SerializedName("cioc")
    @Expose
    private String cioc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTopLevelDomain() {
        return topLevelDomain;
    }

    public void setTopLevelDomain(List<String> topLevelDomain) {
        this.topLevelDomain = topLevelDomain;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public List<String> getCallingCodes() {
        return callingCodes;
    }

    public void setCallingCodes(List<String> callingCodes) {
        this.callingCodes = callingCodes;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public List<String> getAltSpellings() {
        return altSpellings;
    }

    public void setAltSpellings(List<String> altSpellings) {
        this.altSpellings = altSpellings;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public List<Double> getLatlng() {
        return latlng;
    }

    public void setLatlng(List<Double> latlng) {
        this.latlng = latlng;
    }

    public String getDemonym() {
        return demonym;
    }

    public void setDemonym(String demonym) {
        this.demonym = demonym;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getGini() {
        return gini;
    }

    public void setGini(Double gini) {
        this.gini = gini;
    }

    public List<String> getTimezones() {
        return timezones;
    }

    public void setTimezones(List<String> timezones) {
        this.timezones = timezones;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public Translations getTranslations() {
        return translations;
    }

    public void setTranslations(Translations translations) {
        this.translations = translations;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<RegionalBloc> getRegionalBlocs() {
        return regionalBlocs;
    }

    public void setRegionalBlocs(List<RegionalBloc> regionalBlocs) {
        this.regionalBlocs = regionalBlocs;
    }

    public String getCioc() {
        return cioc;
    }

    public void setCioc(String cioc) {
        this.cioc = cioc;
    }
}
