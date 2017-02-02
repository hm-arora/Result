package com.makroid.result.InformationClass;

import java.util.Comparator;

/**
 * Created by himanshu on 12/28/16.
 */
public class ListInformation implements Comparator<ListInformation>{
    String roll;
    String name;
    int total_marks;
    int creadit_marks;
    double percentage;
    double credit_percentage;
    int rank;


    public void setName(String name){
        this.name = name;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public void setTotalMarks(int totalMarks) {
        this.total_marks = totalMarks;
    }

    public void setCreditMarks(int creditMarks) {
        this.creadit_marks = creditMarks;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void setCreditPercentage(double creditPercentage) {
        this.credit_percentage = creditPercentage;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    public String getRank(){
        return "#"+rank;
    }
    public String getRoll(){
        return roll;
    }
    public int getTotalMarks(){
        return total_marks;
    }
    public int getCreaditMarks(){
        return  creadit_marks;
    }
    public double getPercentage(){
        return percentage;
    }
    public double getCreditPercentage(){
        return credit_percentage;
    }
    public String getName(){
        return name;
    }

    @Override
    public int compare(ListInformation listInformation, ListInformation t1) {
        if(listInformation.getCreditPercentage() > t1.getCreditPercentage()) return -1;
        else if(listInformation.getCreditPercentage() < t1.getCreditPercentage()) return 1;
        return 0;
    }

}
