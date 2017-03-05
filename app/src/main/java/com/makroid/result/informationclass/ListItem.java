package com.makroid.result.informationclass;

public class ListItem {
    private String exam,marks,internal,external;
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
}
