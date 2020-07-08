package com.hg.cnc.models;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hg.cnc.models.troops.Troop;

public class Tile {

    //this is the pixel size of a tile, if changed here, it will change everywhere
    public static int WIDTH = 100, HEIGHT = 100;

    private Troop troop;

    //if there is a troop on the tile it will call render
    public void render(ShapeRenderer renderer, int positionX, int positionY) {
        if (troop != null) {
            //call render on the troop
            troop.render(renderer, positionX, positionY);
        }
    }
    //this method is called when placing troop, essentially jsut assigns a troop to this tile
    public void put(Troop troop) {
        this.troop = troop;
    }

    public Troop get() {
        return troop;
    }
}
