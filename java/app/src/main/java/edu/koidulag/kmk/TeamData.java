package edu.koidulag.kmk;

import java.util.List;

public class TeamData {

    int teamId;
    String teamName;
    List<String> members;   //TODO

    public TeamData(int id) {
        teamId = id;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamName(String s) {
        teamName = s;
    }

    public String getTeamName() {
        return teamName;
    }

}
