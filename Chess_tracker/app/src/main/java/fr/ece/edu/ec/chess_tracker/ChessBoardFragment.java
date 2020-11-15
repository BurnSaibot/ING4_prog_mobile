package fr.ece.edu.ec.chess_tracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChessBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChessBoardFragment extends Fragment{
    public ChessBoardFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance
     * this fragment using the provided parameters.
     *
     */
    public static ChessBoardFragment newInstance() {
        ChessBoardFragment fragment = new ChessBoardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            /*mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chess_board, container, false);
    }
}