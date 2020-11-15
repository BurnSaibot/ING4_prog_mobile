package fr.ece.edu.ec.chess_tracker.dataAcces;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.ece.edu.ec.chess_tracker.business.Player;

public class PlayerDAO {

    public static Player connectPlayer(String email, String password) {
        PreparedStatement selectOne = null;
        ResultSet res = null;
        ArrayList<Player> result = new ArrayList<>();

        String querry = "Select * from player where `email` = ? and `password` = ?";

        try (Connection conn = JDBC_connection.getConnection()) {
            selectOne = conn.prepareStatement(querry);
            selectOne.setString(1, email);
            selectOne.setString(2, password);
            if ( selectOne.execute()) {
                res = selectOne.getResultSet();
            }
            if ( res != null) {
                while (res.next()){
                    Player tmp = new Player(
                            res.getInt("userID"),
                            res.getString("email"),
                            res.getString("password"),
                            res.getInt("elo"),
                            res.getString("name"),
                            res.getString("surname"));
                    result.add(tmp);
                }
            }
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.size() > 0 ? result.get(0) : null;
    }

    public static Player registerPlayer(String mail, String password, String name, String surname, Integer elo) {
        Player newPlayer = new Player(mail, digestPassword(password), elo, name, surname);

        PreparedStatement registerPlayer = null;
        ResultSet res = null;
        ArrayList<Player> result = new ArrayList<>();

        String querry = "insert into User(email, password, elo, name, surname) values (?, ?, ?, ?, ?)";

        try (Connection conn = JDBC_connection.getConnection()) {
            registerPlayer = conn.prepareStatement(querry);
            registerPlayer.setString(1, newPlayer.getEmail());
            registerPlayer.setString(2, newPlayer.getPwd());
            registerPlayer.setInt(3, newPlayer.getElo());
            registerPlayer.setString(4, newPlayer.getName());
            registerPlayer.setString(5, newPlayer.getSurname());
            if (registerPlayer.execute()) {
                System.out.println("Player was registered:" + newPlayer);
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        }
        return connectPlayer(newPlayer.getEmail(), newPlayer.getPwd());
    }

    public static String digestPassword(String password) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digestedPwd = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i<digestedPwd.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digestedPwd[i]));
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
