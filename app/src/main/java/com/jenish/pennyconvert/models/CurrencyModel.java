package com.jenish.pennyconvert.models;

import java.util.HashMap;

public class CurrencyModel {
    public String result;
    public String documentation;
    public String terms_of_use;
    public String time_last_update_unix;
    public String time_last_update_utc;
    public String time_next_update_unix;
    public String time_next_update_utc;
    public String base_code;
    public HashMap<String, Double> conversion_rates;

    public CurrencyModel(){
        conversion_rates = new HashMap<>();
    }
}
