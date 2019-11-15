package edu.koidulag.kmkhelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
*   Seda koodi kasutame mängijate tiimidesse asetamiseks
*/

public class Main {

    public static void main(String[] args) {
        String s1 = "localhost";
        int i = 3306;
        String s2 = "andmebaas";
        String s3 = "kasutaja";
        String s4 = "parool";
        String url = "jdbc:mysql://" + s1 + ":" + i + "/" + s2;

        Connection c = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");     // mysql-connector-java-5.1.32-bin.jar
            c = DriverManager.getConnection(url, s3, s4);
            System.out.println("Database established");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = c.prepareStatement("SELECT value FROM settings WHERE type='register'");
            rs = st.executeQuery(); rs.next();
            if (rs.getInt("value") == 0) {
                System.out.println("Registration disabled, cancelling team shuffle");
                rs.close(); st.close(); c.close();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Integer> teamMap = new ArrayList<>();
        Map<Integer, Integer> userMap = new HashMap<>();

        try {
            st = c.prepareStatement("SELECT * FROM users ORDER BY RAND()");
            rs = st.executeQuery();

            int ii = 5;
            int iii = 0;
            while (rs.next()) {
                if (ii == 5) {
                    iii++;
                    teamMap.add(iii);
                    ii = 0;
                }
                userMap.put(rs.getInt("id"), iii);
                System.out.println(rs.getInt("id") + " - " + iii);
                ii++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int _i : teamMap) {
            try {
                st = c.prepareStatement("INSERT INTO teams (n) values ('Team " + _i + "')");
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        int _ii = 5;
        for (int _i : userMap.keySet()) {
            int role = 1;
            if (_ii == 5) {
                role = 0;
                _ii = 0;
            }
            try {
                st = c.prepareStatement("INSERT INTO teams_members (team,user,role) values (" + userMap.get(_i) +"," + _i +"," + role +")");
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            _ii++;
        }

        try {
            st = c.prepareStatement("UPDATE settings SET value=0 WHERE type='register'");
            st.executeUpdate();
            rs.close(); st.close(); c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
