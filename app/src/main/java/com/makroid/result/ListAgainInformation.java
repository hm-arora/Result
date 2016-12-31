package com.makroid.result;

import java.util.List;

/**
 * Created by himanshu on 12/31/16.
 */

public class ListAgainInformation {
    List<ListInformation> listInformation;
    int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public void setList(List<ListInformation> list) {
        this.listInformation = list;
    }

    public int getPosition() {
        return position;
    }

    public List<ListInformation> getList() {
        return listInformation;
    }
}
