package com.example.jainam_vacation.models;
import com.google.gson.annotations.SerializedName;

public class Currency {

    @SerializedName("code")

    private String code;
    @SerializedName("name")

    private String name;
    @SerializedName("symbol")

    private String symbol;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}