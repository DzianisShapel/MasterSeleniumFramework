package org.selenium.pom.constants;

public enum EndPoint {

    ACCOUNT("/account");

    private final String endpoint;

    EndPoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getUrl() {
        return endpoint;
    }
}
