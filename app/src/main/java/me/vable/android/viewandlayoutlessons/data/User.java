package me.vable.android.viewandlayoutlessons.data;

import java.io.Serializable;

/**
 * Created by Varavut on 8/21/2014.
 */
public class User implements Serializable, Comparable<User> {
    private String username;
    private String password;
    private String email;
    private String profileImage;
    private Gender gender;
    private boolean newsletterSubscribed;
    private boolean allowedOtherEmail;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public int compareTo(User user) {
        return this.getUsername().compareTo(user.getUsername());
    }

    @Override
    public boolean equals(Object o) {
        return this.getUsername().equals(((User)o).getUsername());
    }

    public enum Gender implements Serializable {
        MALE,FEMALE
    }

    public boolean isNewsletterSubscribed() {
        return newsletterSubscribed;
    }

    public void setNewsletterSubscribed(boolean newsletterSubscribed) {
        this.newsletterSubscribed = newsletterSubscribed;
    }

    public boolean isAllowedOtherEmail() {
        return allowedOtherEmail;
    }

    public void setAllowedOtherEmail(boolean allowedOtherEmail) {
        this.allowedOtherEmail = allowedOtherEmail;
    }
}
