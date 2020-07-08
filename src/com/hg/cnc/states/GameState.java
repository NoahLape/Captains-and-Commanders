package com.hg.cnc.states;

import com.badlogic.gdx.graphics.OrthographicCamera;

//lays out generally what the in game state will have
public interface GameState {

    public void render(OrthographicCamera camera);
    public void dispose();

}
