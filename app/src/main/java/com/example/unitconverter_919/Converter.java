package com.example.unitconverter_919;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Converter {

    private BigDecimal input_num;
    private BigDecimal test_num;
    private int tenToPow = 0;

    private String indexCategory = "length";

    BigDecimal numerator_n = new BigDecimal("1");
    BigDecimal denominator_n = new BigDecimal("1");

    int counter_decimalDigit = 0;
    int counter_scientificDigit = 0;
    int counter_integerDigit = 0;

    int error_index = 0;


    boolean isDotLocked = false;
    boolean isScientificLocked = false;



    public Converter(){
        input_num = new BigDecimal("0");
        test_num = new BigDecimal("1");
        tenToPow = 0;
        indexCategory = "length";
    }
//----------------------------------------------setter & getter

    public BigDecimal getInput_num(){
        return input_num;
    }

    public BigDecimal getTest_num() {
        return test_num;
    }

    public String getIndexCategory(){
        return indexCategory;
    }

    public void setInput_num(String a){
        input_num = new BigDecimal(a);
    }

    public void setTest_num(String a) {
        test_num = new BigDecimal(a);
    }

    public void setIndexCategory(String a){
        indexCategory = a;
    }
    //----------------------------------------------take the buttons inputs
    public void pressButton(String input){
        switch(input){
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            {
                addNewNum(input);
                break;
            }
            case "clr":
            {
                clear_num();
                break;
            }
            case "back":
            {
                refreshBackNum();
                break;
            }
            case "negative":
            {
                negate();
                break;
            }
            case "dot":
            {
                setOnDecimalMode();
                break;
            }
            case "scientific":
            {
                setOnScientificMode();
                break;
            }
            default:{break;}

        }
    }

    //----------------------------------------------for number inputs
    public void addNewNum(String newNum_t){
        BigDecimal newNum = new BigDecimal(newNum_t);
        if(indexCategory.equals("temperature")){
            BigDecimal abs_hot = new BigDecimal("1.41e32");
            BigDecimal temp_num = new BigDecimal(input_num.toString()+"E"+Integer.toString(tenToPow));
            if(temp_num.compareTo(abs_hot)==1){
                error_index = 2; //absolute hot
                return;
            }
        }
        if(isScientificLocked){				//on scientific mode
            if(tenToPow < 1e4){
                counter_scientificDigit++;
                if(tenToPow >= 0){
                    tenToPow = tenToPow * 10 + newNum.intValue();
                }
                else{
                    tenToPow = tenToPow * 10 - newNum.intValue();
                }
            }
            else{
                error_index = 1; //number is too big;
                return;
            }
        }
        else if(isDotLocked){				//on dot mode
            counter_decimalDigit++;
            newNum = newNum.movePointLeft(counter_decimalDigit);
            if(input_num.compareTo(BigDecimal.ZERO) != -1){
                input_num = input_num.add(newNum);
            }
            else{
                input_num = input_num.subtract(newNum);
            }
        }
        else{								//on normal mode
            if(input_num.doubleValue() != 0.0d){
                counter_integerDigit++;
            }
            if(input_num.compareTo(BigDecimal.ZERO) != -1){
                input_num = (input_num.multiply(BigDecimal.TEN)).add(newNum);
            }
            else{
                input_num = (input_num.multiply(BigDecimal.TEN)).subtract(newNum);
            }
        }
    }
    //----------------------------------------------for back button
    public void refreshBackNum(){
        if(isScientificLocked){
            if(counter_scientificDigit <= 1){
                isScientificLocked = false;
                counter_scientificDigit = 0;
                tenToPow = 0;
                if(counter_decimalDigit == 0){
                    isDotLocked = false;
                }
            }
            else{
                counter_scientificDigit--;
                tenToPow /= 10;
            }

        }
        else if(isDotLocked){
            if(counter_decimalDigit <= 1){
                isDotLocked = false;
                counter_decimalDigit = 0;
                input_num = input_num.setScale(0, RoundingMode.DOWN);
            }
            else {
                counter_decimalDigit--;
                input_num = new BigDecimal(format_dot(input_num, counter_decimalDigit));
            }
        }
        else{
            if (input_num.abs().doubleValue() < 10d) {
                counter_integerDigit = 0;
                input_num = new BigDecimal("0");
            }
            else {
                counter_integerDigit--;
                input_num = input_num.divideToIntegralValue(BigDecimal.TEN).setScale(0, RoundingMode.DOWN);
            }
        }
    }
    //----------------------------------------------for clr button
    public void clear_num(){
        input_num = new BigDecimal("0");
        counter_decimalDigit = 0;
        counter_integerDigit = 0;
        counter_scientificDigit = 0;
        tenToPow = 0;
        isDotLocked = false;
        isScientificLocked = false;
    }
    //----------------------------------------------for negate button
    public void negate(){
        if(isScientificLocked == false){
            input_num = input_num.negate();
        }
        else{
            tenToPow = -tenToPow;
        }
    }
    //----------------------------------------------for scientific mode
    public void setOnScientificMode(){
        if(isScientificLocked == true){
            return;
        }
        else{
            isScientificLocked = true;
            isDotLocked = true;
            tenToPow = 0;
        }
    }
    //----------------------------------------------for dot mode
    public void setOnDecimalMode(){
        if(isDotLocked == true){
            return;
        }
        else{
            isDotLocked = true;
            counter_decimalDigit = 0;
            input_num = input_num.setScale(1);
        }
    }
    //----------------------------------------------display input
    public String input2txt(){
        String res_txt;
        if(isScientificLocked){
            res_txt = input_num.toPlainString() + "E" +Integer.toString(tenToPow);

        }
        else if(isDotLocked){
            res_txt = input_num.toString();
            if(counter_decimalDigit >= 7){
                res_txt = format_dot(input_num, counter_decimalDigit);
            }
        }
        else{
            res_txt = input_num.toString();
        }
        return res_txt;
    }
    //----------------------------------------------diplay output
    public String output2txt(){
        String res_str = "";
        BigDecimal res_num = new BigDecimal("0");
        BigDecimal temp_num = new BigDecimal(input_num.toString());

        if(indexCategory.equals("temperature")){
            double formula;
            BigDecimal one_eight = new BigDecimal("1.8");
            BigDecimal thirty_two = new BigDecimal("32");
            BigDecimal two_hun = new BigDecimal("273.15");
            formula = test_num.doubleValue();
            res_str = temp_num.toPlainString();
            if(isScientificLocked){
                temp_num = new BigDecimal(input_num.toString()+"E"+Integer.toString(tenToPow));
                res_str = temp_num.toEngineeringString();
            }

            if(formula == 1){
                res_num = temp_num;
            }
            else if(formula == 2){ //from Fahrenheit  to  Celsius
                res_num = (temp_num.subtract(thirty_two)).divide(one_eight, 2, RoundingMode.HALF_UP);

            }
            else if(formula == 0.2){//from Fahrenheit  to Kelvin
                res_num = (temp_num.subtract(thirty_two)).divide(one_eight, 2, RoundingMode.HALF_UP).add(two_hun);

            }
            else if(formula == 0.5){//from Celsius  to Fahrenheit
                res_num = temp_num.multiply(one_eight).add(thirty_two);

            }
            else if(formula == 0.1){//from Celsius  to Kelvin
                res_num = temp_num.add(two_hun);

            }
            else if(formula == 10){//from Kelvin to Celsius
                res_num = temp_num.subtract(two_hun);

            }
            else if(formula == 5){//from Kelvin to Fahrenheit
                res_num = temp_num.subtract(two_hun).multiply(one_eight).add(thirty_two);

            }
            if(isScientificLocked){
                res_str = format_scientific(res_num,2);
            }
            else{
                res_str = format_dot(res_num, 2);
            }
            res_str = "=" + res_str;
        }
        else {
            if (isScientificLocked) {
                res_str = input_num.toPlainString() + "E" + Integer.toString(tenToPow);
                res_num = new BigDecimal(res_str);
                res_num = res_num.multiply(test_num);
                if (res_num.toString().length() > 12) {
                    res_str = "=" + format_scientific(res_num, 7);
                    if (res_str.length() > 12) {
                        res_str = "=" + format_scientific(res_num, 5);
                    }
                } else {
                    res_str = "=" + res_num.toString();
                }
            } else {
                res_num = input_num.multiply(test_num);
                if (res_num.toString().length() > 12) {
                    res_str = "=" + format_scientific(res_num, 7);
                } else {
                    res_str = "=" + res_num.toString();
                }
            }
        }
        return res_str;
    }
    //----------------------------------------------update test_num
    public void update_test_num(String nominator_t, String denominator_t){
        numerator_n = new BigDecimal(nominator_t);
        denominator_n = new BigDecimal(denominator_t);
        test_num = numerator_n.divide(denominator_n, 20, RoundingMode.HALF_UP);
        test_num = new BigDecimal(myRounding(test_num));
    }
    //----------------------------------------------set format
    private static String format_scientific(BigDecimal x, int scale){
        NumberFormat formatter = new DecimalFormat("###################0E0");
        formatter.setRoundingMode(RoundingMode.DOWN);
        formatter.setMinimumFractionDigits(scale);
        return formatter.format(x);
    }

    private static String format_integer(BigDecimal x, int scale){
        NumberFormat formatter = new DecimalFormat("#,###,###");
        formatter.setRoundingMode(RoundingMode.DOWN);
        formatter.setMinimumFractionDigits(scale);
        return formatter.format(x);
    }

    private static String format_dot(BigDecimal x, int scale){
        NumberFormat formatter = new DecimalFormat("#,###0.0");
        formatter.setRoundingMode(RoundingMode.DOWN);
        formatter.setMinimumFractionDigits(scale);
        return formatter.format(x);
    }

    public String myRounding(BigDecimal num){
        String int_str = "";
        String decimal_str = "";
        int_str = num.toEngineeringString();
        double a = Double.parseDouble(int_str);
        int_str = Double.toString(a);
        return  int_str;

    }
}
