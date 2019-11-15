package edu.koidulag.kmk;

public enum Role {

    //TODO; Lisada puuduvad rollid

    LEADER(0, "Liider"),
    MEMBER(1, "Default");

    int id;
    String title;

    Role(int _id, String _title) {
        id = _id;
        title = _title;
    }

    public String getTitle() {
        return title;
    }

}
