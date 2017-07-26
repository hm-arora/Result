package com.makroid.result.model;

import java.util.Comparator;


// Use to Store information along with their ranks to show in Rank Section
public class RankModel implements Comparator<RankModel> {
    // Use Comparator to sort
    // RankModel object according to creditPercentage
    private String college, name, percentage, creditPercentage, totalMarks, rank, roll, crank;
    // rank -> university rank
    // crank -> college rank

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getCreditPercentage() {
        return creditPercentage;
    }

    public void setCreditPercentage(String creditPercentage) {
        this.creditPercentage = creditPercentage;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getCrank() {
        return crank;
    }

    public void setCrank(String crank) {
        this.crank = crank;
    }

    // Comparator
    @Override
    public int compare(RankModel rankModel, RankModel t1) {
        if (Integer.parseInt(rankModel.getRank()) > Integer.parseInt(t1.getRank()))
            return 1;
        else if (Integer.parseInt(rankModel.getRank()) < Integer.parseInt(t1.getRank()))
            return -1;
        return 0;
    }

}
