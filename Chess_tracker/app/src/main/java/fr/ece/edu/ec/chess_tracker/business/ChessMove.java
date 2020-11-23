package fr.ece.edu.ec.chess_tracker.business;

import java.io.Serializable;

public class Move implements Serializable {
    private int gameId;
    private String from;
    private String to;
    private int moveNumber;

    public Move(int gameId, String from, String to, int moveNumber) {
        this.gameId = gameId;
        this.from = from;
        this.to = to;
        this.moveNumber = moveNumber;
    }

    public Move(String from, String to, int moveNumber) {
        this.from = from;
        this.to = to;
        this.moveNumber = moveNumber;
    }

    public Move(com.github.bhlangonijr.chesslib.move.Move move, int moveNumber) {
        this.from = move.getFrom().toString();
        this.to = move.getTo().toString();
        this.moveNumber = moveNumber;
    }

    public int getGameId() {
        return gameId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    @Override
    public String toString() {
        return "Move{" +
                "gameId=" + gameId +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", moveNumber=" + moveNumber +
                '}';
    }

}
