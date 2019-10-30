package edu.koidulag.kmk;

import java.util.UUID;

public class UserData {

    UUID uuid;
    String firstname;
    String lastname;
    String username;
    String _class;

    public void setUuid(UUID _uuid) {
        uuid = _uuid;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setFirstname(String s) {
        firstname = s;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(String s) {
        lastname = s;
    }

    public String getLastname() {
        return lastname;
    }

    public void setUsername(String s) {
        username = s;
    }

    public String getUsername() {
        return username;
    }

    public void set_class(String s) {
        _class = s;
    }

    public String get_class() {
        return _class;
    }

}
