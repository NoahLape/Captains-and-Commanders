package com.hg.cnc.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hg.cnc.CandCgame;
import com.hg.cnc.models.Map;
import com.hg.cnc.models.Renderable;
import com.hg.cnc.models.Tile;

public class InLevelState implements GameState, Renderable {
    //Super useful global variables that allow us to alter the size of the map in one place
    private int MAP_WIDTH = 5000, MAP_HEIGHT = 5000;

    //because the game is played in the state, we will create a new game here
    private CandCgame candCgame;
    //again, because the game is made here, we implement the creation of a map here
    private Map map;

    //this is the constructor for creating a new "InLavelState", once called it will generate
        //all nessessary top level requirements of the game state
    public InLevelState(CandCgame candCgame) {
        //this block basically takes the new game, MAKES SURE the size of the map is going to be
            //divisible by the size of a tile
        this.candCgame = candCgame;
        assert(MAP_WIDTH % Tile.WIDTH == 0);
        assert(MAP_HEIGHT % Tile.HEIGHT == 0);
        //creates the map object here
        this.map = new Map(candCgame, MAP_WIDTH, MAP_HEIGHT, MAP_WIDTH / Tile.WIDTH, MAP_HEIGHT / Tile.HEIGHT);
    }

    @Override
    public void render(OrthographicCamera camera) {
       //the camera can move in this state
        moveCamera(camera);
        //this is now going to render the map itself
        map.render(camera);
    }

    //must dispose of the map created object
    @Override
    public void dispose() {
        map.dispose();
    }


    //method to move the camera, with a shift speed boost added
    private void moveCamera(OrthographicCamera camera) {
        float speed = 1.0f;
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            speed = 2.0f;

        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            camera.translate(0, 10 * speed);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            camera.translate(10 * speed, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            camera.translate(-10 * speed, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            camera.translate(0, -10 * speed);
        }

        //important to update the camera after changing it's position
        camera.update();
    }
}
