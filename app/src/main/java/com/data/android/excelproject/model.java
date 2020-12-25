package com.data.android.excelproject;

public class model {

    private Object number;
    private Object present;

    public model(Object number, Object present) {
        this.number = number;
        this.present = present;
    }

    public Object getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Object getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }
}
