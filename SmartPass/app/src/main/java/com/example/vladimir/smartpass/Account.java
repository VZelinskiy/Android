package com.example.vladimir.smartpass;

public class Account {
    private int Id;
    private String SiteAddress;
    private String SiteName;
    private String Description;
    private String Login;
    private String Password;

    public Account(int id, String siteAddress, String siteName, String description, String login, String password) {
        Id = id;
        SiteAddress = siteAddress;
        SiteName = siteName;
        Description = description;
        Login = login;
        Password = password;
    }

    public int getId() {
        return Id;
    }

    public String getSiteAdress() {
        return SiteAddress;
    }

    public void setSiteAdress(String siteAddress) {
        SiteAddress = siteAddress;
    }

    public String getSiteName() {
        return SiteName;
    }

    public void setSiteName(String siteName) {
        SiteName = siteName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
