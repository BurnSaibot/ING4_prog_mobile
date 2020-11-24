package fr.ece.edu.ec.chess_tracker.dataAcces;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.ece.edu.ec.chess_tracker.business.ChessGameImage;

public class ChessGameImageDAO {

    public static List<ChessGameImage> getImageFromMyGame(int gameId) {
        PreparedStatement selectOne = null;
        ResultSet res = null;
        List<ChessGameImage> result = new ArrayList<>();

        PreparedStatement getMoves = null;
        ResultSet resMoves = null;

        String querry = "Select * from gameImages where `idGame` = ?";

        try (Connection conn = JDBC_connection.getConnection()) {
            selectOne = conn.prepareStatement(querry);
            selectOne.setInt(1, gameId);

            if (selectOne.execute()) {
                res = selectOne.getResultSet();
            }
            if (res != null) {
                while (res.next()) {
                    Blob tmpBlob;
                    tmpBlob = res.getBlob("image");

                    ChessGameImage tmp = new ChessGameImage(
                            res.getInt("idGame"),
                            tmpBlob.getBytes(1,(int)tmpBlob.length())
                    );
                    System.out.println("Image found : " + tmp);
                    result.add(tmp);
                }
            } else {
                System.out.println("ResultSet is empty!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ChessGameImage insertImage(ChessGameImage image) {

        PreparedStatement insertImage = null;
        ResultSet res = null;

        String querry = "insert into gameImages(idGame, image) values (?, ?)";
        InputStream inputStream = new ByteArrayInputStream(image.getImage());

        try (Connection conn = JDBC_connection.getConnection()) {
            insertImage = conn.prepareStatement(querry);
            insertImage.setInt(1, image.getGameId());
            insertImage.setBinaryStream(2, inputStream, image.getImage().length);
            int nbRowInserted = insertImage.executeUpdate();
            if (nbRowInserted == 1) {
                System.out.println("Image was registered:" + image);
                conn.commit();
            } else {
                conn.rollback();
                System.out.println("Row was not inserted");
            }
        } catch (SQLException e) {
        }

        return image;
    }
}
