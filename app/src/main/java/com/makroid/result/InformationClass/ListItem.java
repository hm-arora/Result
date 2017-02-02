package com.makroid.result.InformationClass;

/**
 * Created by himanshu on 12/25/16.
 */

public class ListItem {
    String exam,marks,internal,external;
    public void setexam(String exam){
        this.exam = exam;
    }
    public void setmarks(String marks){
        this.marks = marks;
    }
    public void setexternal(String external) {
        this.external = external;
    }

    public void setinternal(String internal) {
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
}
