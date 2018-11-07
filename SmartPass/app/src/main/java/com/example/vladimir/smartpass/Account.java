package com.example.vladimir.smartpass;

public class Account {
    private String SiteAdress;
    private String SiteName;
    private String Description;
    private String Login;
    private String Password;

    public Account(String siteAdress, String siteName, String description, String login, String password) {
        SiteAdress = siteAdress;
        SiteName = siteName;
        Description = description;
        Login = login;
        Password = password;
    }

    public String getSiteAdress() {
        return SiteAdress;
    }

    public void setSiteAdress(String siteAdress) {
        SiteAdress = siteAdress;
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
