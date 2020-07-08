package com.hg.cnc.models.troops;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hg.cnc.models.Renderable;
import com.hg.cnc.util.Dice;

//super class with the abstract method render that will allow the specific troop the ability to define render
public abstract class Troop  {

    private int toughness, power, reach, speed;

    //basic constructor for a troop
    public Troop(int toughness, int power, int reach, int speed) {
        this.toughness = toughness;
        this.power = power;
        this.reach = reach;
        this.speed = speed;
    }

    abstract public void render(ShapeRenderer renderer, int positionX, int positionY);

    //TODO
    //method designed to allow troop to attack
    public void attack(Troop other) {
        int result = Dice.roll(2).d(6);
        
    }
}
