package com.test.domain.gson;

import com.google.gson.annotations.SerializedName;

public class SerializedDomain {
    @SerializedName("dashes-field")
    private String dashesField;    
    private String underScores;    
    private String nullValue;
    private transient String transientField;

    public String getUnderScores() {
        return underScores;
    }

    public void setUnderScores(String underScores) {
        this.underScores = underScores;
    }

    public String getDashesField() {
        return dashesField;
    }

    public void setDashesField(String dashesField) {
        this.dashesField = dashesField;
    }
    
    public String getNullValue() {
        return nullValue;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
    }

    public String getTransientField() {
        return transientField;
    }

    public void setTransientField(String transientField) {
        this.transientField = transientField;
    }
}
