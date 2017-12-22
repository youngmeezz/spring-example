package com.test.domain.relection;

public class Sample implements ISample {
    private String name;

    public String getName(){
        return name;
    }

    @Override
    public void setName(String name){
        this.name = name;
    }
}
