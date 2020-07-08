package com.hg.cnc.models.troops;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hg.cnc.models.Tile;

//sub class of troop
public class Swordsman extends Troop {

    private static final int
            //because the swordsman is a melee fighter it has a +1 to it's base fighting stat
            SWORDSMAN_TOUGHNESS = 1,
            SWORDSMAN_POWER = 2,
            SWORDSMAN_REACH = 1,
            //general speed of 3
            SWORDSMAN_SPEED = 3,
            GRAPHIC_INSET = 10;

    //constructor defining the basic attributes of a troop
    public Swordsman() {
        super(SWORDSMAN_TOUGHNESS, SWORDSMAN_POWER, SWORDSMAN_REACH, SWORDSMAN_SPEED);
    }

    //renders the actual troop
    //because this troop is in the filled square batch, it will be a filled square
    @Override
    public void render(ShapeRenderer renderer, int positionX, int positionY) {
        renderer.setColor(new Color(0, .7f, 0, 1));
        renderer.rect(
                positionX+GRAPHIC_INSET,
                positionY + GRAPHIC_INSET,
                Tile.WIDTH - GRAPHIC_INSET*2,
                Tile.HEIGHT - GRAPHIC_INSET*2);
    }
}
