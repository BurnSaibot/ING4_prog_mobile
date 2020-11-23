package fr.ece.edu.ec.chess_tracker.business;

import java.io.Serializable;
import java.util.List;

import fr.ece.edu.ec.chess_tracker.dataAcces.PlayerDAO;

public class ChessGame implements Serializable {
    private int idPartie;
    private int player1;
    private int player2;
    private int winner;
    private String title;
    private List<ChessMove> chessMoves;

    public ChessGame(int player1, int player2, List<ChessMove> chessMoves, int winner, String title) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
        this.chessMoves = chessMoves;
        this.title = title;
    }

    public ChessGame(int idPartie, int player1, int player2, List<ChessMove> chessMoves, int winner, String title) {
        this.idPartie = idPartie;
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
        this.chessMoves = chessMoves;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIdPartie() {
        return idPartie;
    }

    public int getPlayer1() {
        return player1;
    }

    public void setPlayer1(int player1) {
        this.player1 = player1;
    }

    public int getPlayer2() {
        return player2;
    }

    public void setPlayer2(int player2) {
        this.player2 = player2;
    }


    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public List<ChessMove> getChessMoves() {
        return chessMoves;
    }

    public void setChessMoves(List<ChessMove> chessMoves) {
        this.chessMoves = chessMoves;
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "idPartie=" + idPartie +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", winner=" + winner +
                ", title='" + title + '\'' +
                ", chessMoves=" + chessMoves +
                '}';
    }
}
