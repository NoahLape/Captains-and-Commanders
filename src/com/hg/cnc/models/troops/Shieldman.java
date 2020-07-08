package com.hg.cnc.models.troops;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hg.cnc.models.Tile;
//sub class of troop
public class Shieldman extends Troop {

    private static final int

            //because the shieldman is a defensive fighter it has a +1 to it's base toughness stat
            TOUGHNESS = 2,
            POWER = 1,
            REACH = 1,
            SPEED = 3,
            GRAPHIC_INSET = 10;
    //constructor defining the basic attributes of a troop
    public Shieldman() {
        super(TOUGHNESS, POWER, REACH, SPEED);
    }

    //renders the actual troop
    //because this troop is in the filled square batch, it will be a filled square
    @Override
    public void render(ShapeRenderer renderer, int positionX, int positionY) {
        renderer.setColor(new Color(0, 0, .7f, 1));
        renderer.rect(
                positionX+GRAPHIC_INSET,
                positionY + GRAPHIC_INSET,
                Tile.WIDTH - GRAPHIC_INSET*2,
                Tile.HEIGHT - GRAPHIC_INSET*2);
    }
}
