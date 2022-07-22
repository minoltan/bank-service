package com.example.bankservice.response;

import java.util.Date;

public class MyResponse {

    private String language = "en";
    private String statusInfo;
    private String entityType;
    private Date lastModified;
    private Object data;
    private boolean success = true;
    private String errorMessage;
    private String requestedURI;

    public MyResponse() {
        this.lastModified = new Date();
    }

    public MyResponse(Object data) {
        this.data = data;
        this.lastModified = new Date();
        this.entityType = data.getClass().toString();
    }

    public MyResponse(Object data, String statusInfo) {
        this.data = data;
        this.statusInfo = statusInfo;
        this.lastModified = new Date();
        this.entityType = data.getClass().toString();
    }

    public MyResponse(String language, String statusInfo, String entityType, Date lastModified, Object data) {
        this.language = language;
        this.statusInfo = statusInfo;
        this.entityType = entityType;
        this.lastModified = lastModified;
        this.data = data;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStatusInfo() {
        return this.statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public String getEntityType() {
        return this.entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Date getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getRequestedURI() {
        return this.requestedURI;
    }

    public void setRequestedURI(String requestedURI) {
        this.requestedURI = requestedURI;
    }

    public String toString() {
        return "Response{data=" + this.data + "}";
    }
}
