package com.hg.cnc.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.hg.cnc.CandCgame;
import com.hg.cnc.models.troops.Pikeman;
import com.hg.cnc.models.troops.Shieldman;
import com.hg.cnc.models.troops.Swordsman;

public class Map implements Renderable {

    //game variable
    private CandCgame game;
    //the board will consist of a "grid" of Tile's, a tile is important because it will contain data nessissary for a
        //troop
    private Tile[][] grid;
    //the shapeRenderer will be respossible for generating in most of our cases, rectangles
    private ShapeRenderer renderer;

    //general requirements for the grid, as well as some "last-Tile-Selected" variables
    private int
            width, height,
            currentlySelectedI, currentlySelectedJ,
            lastGridSelectI = -1, lastGridSelectJ = -1;

    //will be used to generate a menu for placing troops
    private Table contextMenu;

    //constructor for a map
    public Map(CandCgame game, int width, int height, int tilesX, int tilesY) {
        //takes the current game
        this.game = game;
        this.width = width;
        this.height = height;
        //makes a new grid object that will contain tiles
        grid = new Tile[tilesX][tilesY];
        //filling the grid with tiles
        for (int i = 0; i < tilesX; i ++) {
            for (int j = 0; j < tilesY; j ++) {
                grid[i][j] = new Tile();
            }
        }
        //grid[5][5].put(new Swordsman());
        //grid[5][6].put(new Pikeman());
        //grid[5][7].put(new Shieldman());
        this.renderer = new ShapeRenderer();

        //this is important because instead of generating a new menu every time we want to place a troop,
            //it's always there, just hidden
        //creates the new table object
        contextMenu = new Table();

        contextMenu.setWidth(300);
        contextMenu.setHeight(300);
        //helpful for seeing how big the menu actually is
        contextMenu.setDebug(true);
        //this is to add our new table to the stage, such that once the stage is called to draw, it will have the
            //table loaded
        game.getUiStage().addActor(contextMenu);
        //here the table is set to invisible, it will be set to visible once a player clicks to place a troop.
        contextMenu.setVisible(false);

        //This creates a button
            //"getButtonStyle" is the preloaded button style created to handle different button states
                // (in "CandCgame.java")
        Button swordsmanButton = new TextButton("Place Swordsman", game.getButtonStyle());
        //this listener will take in a click listener to form asynchronus code that is essentially listening for when
            //the button is clicked. The important part is that it is asynchronus.
        swordsmanButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //this will place a unit, in this case a swordsman on this specific highlighted tile
                grid[lastGridSelectI][lastGridSelectJ].put(new Swordsman());
                //once clicked turns the table off
                contextMenu.setVisible(false);
                //resets the flag variables to -1
                lastGridSelectI = lastGridSelectJ = -1;
            }
        });
        Button shieldmanButton = new TextButton("Place Shieldman", game.getButtonStyle());
        shieldmanButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                grid[lastGridSelectI][lastGridSelectJ].put(new Shieldman());
                contextMenu.setVisible(false);
                lastGridSelectI = lastGridSelectJ = -1;
            }
        });
        Button pikemanButton = new TextButton("Place Pikeman", game.getButtonStyle());
        pikemanButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                grid[lastGridSelectI][lastGridSelectJ].put(new Pikeman());
                contextMenu.setVisible(false);
                lastGridSelectI = lastGridSelectJ = -1;
            }
        });
        contextMenu.add(swordsmanButton);
        contextMenu.row();
        contextMenu.add(shieldmanButton);
        contextMenu.row();
        contextMenu.add(pikemanButton);
    }

    //This is the point where:
        //1.)The map is rendered
        //2.)The the curser "highlights" a box red
        //3.)Troops are rendered
    @Override
    public void render(OrthographicCamera camera) {
        //calculates the current highlighted spot. It does so by:
            //taking the cameras position, adding or subtracting  the current pixel location of the mouse,
            //and then adding or subtracting the view port divided 2. Finally dividing it the total amount
            //of tiles width and height wise
            //^ equation will give the integer value in grid coordinates instead of just raw pixel coordinates
        this.currentlySelectedI = (int) (camera.position.x + Gdx.input.getX() - game.getViewport().getScreenWidth() / 2) / Tile.WIDTH;
        this.currentlySelectedJ = (int) (camera.position.y - Gdx.input.getY() + game.getViewport().getScreenHeight() / 2) / Tile.HEIGHT;


        //This will develop the renderer.
        renderer.setProjectionMatrix(camera.combined);
        //specifies the type of shape, in this one, it's a line
        renderer.begin(ShapeRenderer.ShapeType.Line);

        //This loop is responsible for rendering the entire grid, NOT the troops inside of it
        for (int i = 0; i < grid.length; i ++) {
            for (int j = 0; j < grid[i].length; j ++) {
                //sets the color and determines the position in grid coordinates instead of pixel coordinates
                renderer.setColor(Color.BLACK);
                renderer.rect(i * Tile.WIDTH, j * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);

                //This is for displaying the "selected tile" grey square.
                    //This works by storing the clicked coordinates and storing them
                    //Therefore, when this renders the new grid, it will see that the "lastGridSelect" variables will
                    //have valid coordinates. Otherwise, the "lastGridSelect" variables will be negative 1
                if (i == lastGridSelectI && j == lastGridSelectJ) {
                    renderer.setColor(new Color(0, .5f, 0, 1));
                    renderer.rect(i * Tile.WIDTH + 5, j * Tile.HEIGHT + 5, Tile.WIDTH - 10, Tile.HEIGHT - 10);
                }

                //This uses the math described above to render a red rectangle showing the currently highlighted square
                if (i == currentlySelectedI && j == currentlySelectedJ) {
                    renderer.setColor(Color.RED);
                    renderer.rect(i * Tile.WIDTH + 5 ,j * Tile.HEIGHT + 5,Tile.WIDTH - 10,Tile.HEIGHT - 10);
                    //if the mouse is used here AND the menu is currently not being displayed, store the coordinates
                        //so that a troop may be rendered there
                    if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !contextMenu.isVisible()) {
                        this.lastGridSelectI = i;
                        this.lastGridSelectJ = j;
                        contextMenu.setVisible(true);
                    }
                }
            }
        }
        renderer.end();

        //This is responsible for rendering all of the troops into their respective grids
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        //this will go through each grid space and find the spaces with troops in them and then render them
        for (int i = 0; i < grid.length; i ++) {
            for (int j = 0; j < grid[i].length; j++) {
                //call the render method on each individual tile
                grid[i][j].render(renderer, i * Tile.WIDTH, j * Tile.HEIGHT);
            }
        }
        renderer.end();
        //if the menu is active, it will stay on the tile it was selected for
        //basically makes the table stay in one place
             //Ex. As the camera moves right, the menu will move left to compensate for it
        if (lastGridSelectI != -1 && lastGridSelectJ != -1) {
            contextMenu.addAction(Actions.moveTo(
                    lastGridSelectI*Tile.WIDTH - camera.position.x + game.getViewport().getScreenWidth()/2,
                    lastGridSelectJ*Tile.HEIGHT - camera.position.y + game.getViewport().getScreenHeight()/2));
        }
    }

    public void dispose() {
        renderer.dispose();
    }
}
