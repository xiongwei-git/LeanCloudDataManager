package com.ted.lcmanager.app.model;

import java.util.List;

/**
 * Created by Ted on 2015/3/29.
 */
public class StealDataResult {
    private int offset;
    private int count;
    private int total;
    private List<StealDataModel> data;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<StealDataModel> getData() {
        return data;
    }

    public void setData(List<StealDataModel> data) {
        this.data = data;
    }
}
