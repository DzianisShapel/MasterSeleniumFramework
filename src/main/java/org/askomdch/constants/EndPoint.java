package org.askomdch.constants;

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
