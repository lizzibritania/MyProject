package com.hellogwt.shared.domain;

import java.io.Serializable;

public class Greeting implements Serializable {

    private Integer id;
    private String name;
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String author) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String text) {
        this.address = address;
    }
}