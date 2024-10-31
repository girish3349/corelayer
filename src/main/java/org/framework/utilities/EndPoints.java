package org.framework.utilities;

public enum EndPoints {
    GET_SERVICE("get/service");


    private final String url;

    EndPoints(String url) {this.url = url;}
    public String geturl() {return url;};
}
