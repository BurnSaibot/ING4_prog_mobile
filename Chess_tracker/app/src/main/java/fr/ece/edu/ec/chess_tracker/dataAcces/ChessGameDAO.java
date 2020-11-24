package fr.ece.edu.ec.chess_tracker.dataAcces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import fr.ece.edu.ec.chess_tracker.business.ChessGame;
import fr.ece.edu.ec.chess_tracker.business.ChessMove;
import fr.ece.edu.ec.chess_tracker.business.Player;

import static fr.ece.edu.ec.chess_tracker.dataAcces.JDBC_connection.conn;

public class ChessGameDAO {

    public static ChessGame insertChessGame(List<ChessMove> moves, Player player1, Player player2, Player winner) {

        PreparedStatement insertChessGame = null;
        ChessGame result = null;

        PreparedStatement insertChessMove = null;
        ArrayList<ChessMove> resultChessMove = new ArrayList<>();

        String querryInsertGame = "insert into Game(idPlayer1, idPlayer2, winner, title) values(?, ?, ?, ?)";
        String querryInsertMove = "insert into Move(idGame, numberMove, fromSquare, toSquare) values(?, ?, ?, ?)";
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try (Connection conn = JDBC_connection.getConnection()) {
            insertChessGame = conn.prepareStatement(querryInsertGame, Statement.RETURN_GENERATED_KEYS);
            insertChessGame.setInt(1, player1.getIdUser());
            insertChessGame.setInt(2, player2.getIdUser());
            insertChessGame.setInt(3, (winner != null) ? winner.getIdUser() : -1);
            insertChessGame.setString(4, player1.getName() + " vs " + player2.getName() + ":" + format.format(d));

            int nbRowInserted = insertChessGame.executeUpdate();
            if (nbRowInserted == 1) {
                System.out.println("Game was registered");
            } else {
                conn.rollback();
                System.out.println("Row was not inserted");
                return null;
            }

            int gameId = -1;
            ResultSet gameIds = insertChessGame.getGeneratedKeys();
            if (gameIds.next()){
                gameId = gameIds.getInt(1);
            }

            int cpt = 1;
            for (ChessMove m : moves) {
                resultChessMove.add(m);
                insertChessMove = conn.prepareStatement(querryInsertMove);
                insertChessMove.setInt(1, gameId);
                insertChessMove.setInt(2, m.getMoveNumber());
                insertChessMove.setString(3, m.getFrom());
                insertChessMove.setString(4, m.getTo());

                nbRowInserted = insertChessMove.executeUpdate();
                if (nbRowInserted == 1) {
                    System.out.println("Move was registered");
                } else {
                    conn.rollback();
                    System.out.println("Row was not inserted");
                    return null;
                }
                cpt++;
            }
            conn.commit();
            result = new ChessGame(gameId, player1.getIdUser(), player2.getIdUser(), resultChessMove, winner.getIdUser(), player1.getName() + " vs " + player2.getName() );
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static List<ChessGame> getAllMyGames(Player me){
        int id = me.getIdUser();
        ArrayList<ChessGame> myGames = new ArrayList<>();
        PreparedStatement getMyGames = null;
        ResultSet res = null;

        String querry = "Select DISTINCT * from `Game` where `idPlayer1` = ? OR `idPlayer2` = ? ORDER BY idGame DESC";

        try (Connection conn = JDBC_connection.getConnection()) {
            getMyGames = conn.prepareStatement(querry);
            getMyGames.setInt(1, me.getIdUser());
            getMyGames.setInt(2, me.getIdUser());

            if (getMyGames.execute()) {
                res = getMyGames.getResultSet();
            }
            if (res != null) {
                while (res.next()) {
                    ChessGame tmp = new ChessGame(
                            res.getInt("idGame"),
                            res.getInt("idPlayer1"),
                            res.getInt("idPlayer2"),
                            new ArrayList<ChessMove>(),
                            res.getInt("winner"),
                            res.getString("title")
                    );
                    myGames.add(tmp);
                }
            } else {
                System.out.println("ResultSet is empty!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myGames;
    }

    public static List<ChessGame> getSomeOfMyGames(Player me, int batchSize, int batchNumber) {
        int id = me.getIdUser();
        ArrayList<ChessGame> myGames = new ArrayList<>();
        PreparedStatement getMyGames = null;
        ResultSet res = null;

        int startingRow = batchNumber * batchSize;

        String querry = "Select DISTINCT * from game where `idPlayer1` = ? OR `idPlayer2` = ? LIMIT ?,? ORDER BY DESC idGame";

        try (Connection conn = JDBC_connection.getConnection()) {
            getMyGames = conn.prepareStatement(querry);
            getMyGames.setInt(1, me.getIdUser());
            getMyGames.setInt(2, me.getIdUser());
            getMyGames.setInt(3, startingRow);
            getMyGames.setInt(4, batchSize);


            if (getMyGames.execute()) {
                res = getMyGames.getResultSet();
            }
            if (res != null) {
                while (res.next()) {
                    ChessGame tmp = new ChessGame(
                            res.getInt("idGame"),
                            res.getInt("idPlayer1"),
                            res.getInt("idPlayer2"),
                            new ArrayList<ChessMove>(),
                            res.getInt("winner"),
                            res.getString("title")
                    );
                    myGames.add(tmp);
                }
            } else {
                System.out.println("ResultSet is empty!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myGames;
    }

    public static ChessGame getGameById(int gameId) {
            PreparedStatement selectOne = null;
            ResultSet res = null;
            ChessGame result = null;

            PreparedStatement getMoves = null;
            ResultSet resMoves = null;

            String querry = "Select * from Game where `idGame` = ?";
            String querryMoves = "Select * from Move where `idGame` = ? ORDER BY numberMove ";

            try (Connection conn = JDBC_connection.getConnection()) {
                selectOne = conn.prepareStatement(querry);
                selectOne.setInt(1, gameId);

                if (selectOne.execute()) {
                    res = selectOne.getResultSet();
                }
                if (res != null) {
                    while (res.next()) {
                        ChessGame tmp = new ChessGame(
                                res.getInt("idGame"),
                                res.getInt("idPlayer1"),
                                res.getInt("idPlayer2"),
                                new ArrayList<ChessMove>(),
                                res.getInt("winner"),
                                res.getString("title")
                                );
                        System.out.println("Game found : " + tmp);
                        result = tmp;
                    }
                } else {
                    System.out.println("ResultSet is empty!");
                }

                getMoves = conn.prepareStatement(querryMoves);
                getMoves.setInt(1, gameId);

                if (getMoves.execute()) {
                    resMoves = getMoves.getResultSet();
                }
                if (resMoves != null) {
                    while (resMoves.next()) {
                        ChessMove tmp = new ChessMove(
                                resMoves.getInt("idGame"),
                                resMoves.getString("fromSquare"),
                                resMoves.getString("toSquare"),
                                resMoves.getInt("numberMove")
                        );
                        result.getChessMoves().add(tmp);
                    }
                } else {
                    System.out.println("ChessMoves resultSet is empty!");
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
    }



}
