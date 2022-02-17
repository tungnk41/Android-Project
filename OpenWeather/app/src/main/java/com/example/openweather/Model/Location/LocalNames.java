package com.example.openweather.Model.Location;

public class LocalNames {
    private String vi;

    public LocalNames(String vi) {
        this.vi = vi;
    }

    public String getVi() {
        return vi;
    }

    public void setVi(String vi) {
        this.vi = vi;
    }

    @Override
    public String toString() {
        return "LocalNames{" +
                "vi='" + vi + '\'' +
                '}';
    }
}
