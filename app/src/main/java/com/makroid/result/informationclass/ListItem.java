package com.makroid.result.informationclass;

public class ListItem {
    private String exam,marks,internal,external,percentage,cPercentage,cRank,uRank,total,credit;
    void setexam(String exam){
        this.exam = exam;
    }
    void setmarks(String marks){
        this.marks = marks;
    }
    void setexternal(String external) {
        this.external = external;
    }

    void setinternal(String internal) {
        this.internal = internal;
    }
    public String getexam(){
        return exam;
    }
    public String getmarks(){
        return marks;
    }
    public String getexternal() {
        return external;
    }
    public String getinternal() {
        return internal;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getcPercentage() {
        return cPercentage;
    }

    public void setcPercentage(String cPercentage) {
        this.cPercentage = cPercentage;
    }

    public String getcRank() {
        return cRank;
    }

    public void setcRank(String cRank) {
        this.cRank = cRank;
    }

    public String getuRank() {
        return uRank;
    }

    public void setuRank(String uRank) {
        this.uRank = uRank;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
}
