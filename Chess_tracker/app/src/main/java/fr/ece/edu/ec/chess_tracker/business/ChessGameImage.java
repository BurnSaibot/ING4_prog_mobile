package fr.ece.edu.ec.chess_tracker.business;

import android.graphics.Bitmap;

import java.util.Arrays;

public class ChessGameImage {
    int gameId;
    byte [] image;

    public ChessGameImage(int gameId, byte[] image) {
        this.gameId = gameId;
        this.image = image;
    }

    public int getGameId() {
        return gameId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ChessGameImage{" +
                "gameId=" + gameId +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
