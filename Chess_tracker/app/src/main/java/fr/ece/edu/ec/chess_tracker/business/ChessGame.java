package fr.ece.edu.ec.chess_tracker.business;

import java.io.Serializable;
import java.util.List;

public class ChessGame implements Serializable {
    private int idPartie;
    private Player player1;
    private Player player2;
    private List<Move> moves;
    private Player winner;
    private String title;

    public ChessGame(Player player1, Player player2, List<Move> moves, Player winner ) {
        this.player1 = player1;
        this.player2 = player2;
        this.moves = moves;
        this.winner = winner;
        this.title =  player1.getName() + " vs " + player2.getName();
    }

    public ChessGame(int idPartie, Player player1, Player player2, List<Move> moves, Player winner) {
        this.idPartie = idPartie;
        this.player1 = player1;
        this.player2 = player2;
        this.moves = moves;
        this.winner = winner;
        this.title = player1.getName() + " vs " + player2.getName();
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

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
