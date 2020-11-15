package fr.ece.edu.ec.chess_tracker.business;

import java.util.List;

public class ChessGame {
    private int idPartie;
    private Player player1;
    private Player player2;
    private List<Move> moves;
    private Player winner;

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
