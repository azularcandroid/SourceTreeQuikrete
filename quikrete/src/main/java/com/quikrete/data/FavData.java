package com.quikrete.data;

public class FavData {

    private String calc_id;
    private String value;
    private String option;

    public FavData(String calc_id, String value, String option) {
        this.calc_id = calc_id;
        this.value = value;
        this.option = option;
    }

    public String getCalc_id() {
        return calc_id;
    }

    public void setCalc_id(String calc_id) {
        this.calc_id = calc_id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return "FavData{" +
                "calc_id='" + calc_id + '\'' +
                ", value='" + value + '\'' +
                ", option='" + option + '\'' +
                '}';
    }
}
