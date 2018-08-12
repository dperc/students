package com.student.demo.model;

import java.util.ArrayList;
import java.util.List;
import com.google.common.base.Preconditions;

public class RestResponse<T> {

    private List<T> data = new ArrayList<>();

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void appendDataItem(T item) {
        Preconditions.checkNotNull(item);
        data.add(item);
    }

    public List<T> getData() {
        return data;
    }
}
