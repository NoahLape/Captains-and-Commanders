package com.hg.cnc.models.troops;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hg.cnc.models.Tile;

public class Pikeman extends Troop {

    private static final int
            TOUGHNESS = 1,
            POWER = 1,
            //because the pikeman is a ranged fighter it has a +1 to it's base reach stat
            REACH = 2,
            //this troop cost more so, it has also a added speed bonus
            SPEED = 4,
            GRAPHIC_INSET = 10;
    //constructor defining the basic attributes of a troop
    public Pikeman() {
        super(TOUGHNESS, POWER, REACH, SPEED);
    }

    //renders the actual troop
    //because this troop is in the filled square batch, it will be a filled square
    @Override
    public void render(ShapeRenderer renderer, int positionX, int positionY) {
        renderer.setColor(new Color(128/255.0f, 110/255.0f, 42/255.0f, 1));
        renderer.rect(
                positionX+GRAPHIC_INSET,
                positionY + GRAPHIC_INSET,
                Tile.WIDTH - GRAPHIC_INSET*2,
                Tile.HEIGHT - GRAPHIC_INSET*2);
    }
}
