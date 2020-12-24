package com.udacity.jwdnd.course1.cloudstorage.model;

/**
 * create File class to model SQL table "CREDENTIALS"
 */

public class Credential {

    private Integer credentialid;
    private String url;
    private String username;
    private String password;
    private Integer userid;
    private String key;

    public Credential() {
    }

    public Credential(String url, String username, String password, Integer userid, String key) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.userid = userid;
        this.key = key;
    }

    public Credential(Integer credentialid, String url, String username, String password, Integer userid, String key) {
        this.credentialid = credentialid;
        this.url = url;
        this.username = username;
        this.password = password;
        this.userid = userid;
        this.key = key;
    }

    public Integer getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(Integer credentialid) {
        this.credentialid = credentialid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
