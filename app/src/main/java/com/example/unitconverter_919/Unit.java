package com.example.unitconverter_919;

/**
 * Created by Tyan Ou on 2017-9-20.
 */

public class Unit {
    private String unitName;
    private String unitValue;

    public Unit(){
    }

    public Unit(String uName, String uValue){
        unitName = uName;
        unitValue = uValue;
    }

    public String getUnitName(){
        return unitName;
    }

    public String getUnitValue() {
        return unitValue;
    }

    public void  setUnitName(String uName){
        this.unitName = uName;
    }

    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }
}

