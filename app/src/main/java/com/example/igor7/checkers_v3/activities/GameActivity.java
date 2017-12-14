package com.example.igor7.checkers_v3.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.igor7.checkers_v3.R;

public class GameActivity extends AppCompatActivity {

    private IGameContract.Presenter gamePresenter;
    private GameBoardFragment gameBoardFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_game);
        gameBoardFragment = (GameBoardFragment) getSupportFragmentManager()
                .findFragmentById(R.id.gameBoardFragment);
        gamePresenter = new GamePresenter(gameBoardFragment);
        gameBoardFragment.setPresenter(gamePresenter);
    }
}
