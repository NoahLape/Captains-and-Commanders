
//This is the top level file.
//Purpose:
//1)Create a general stage such that all of the UI elements will have a place to be
		//stored then rendered from one place
//2)Global variables storing the pixel width of the screen
//3)



package com.hg.cnc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.hg.cnc.states.GameState;
import com.hg.cnc.states.InLevelState;

public class CandCgame extends com.badlogic.gdx.Game {

	//these are the global varaibles controlling the pixel size of the game screen
	private final int
			WIDTH = 2400,
			HEIGHT = 1200;


	//Variable controlling
		//general game state
		//view port for the camera
		//orthograpghicCamera from gdx to convert pixel coordinates to a use-able global coordinate system
	private GameState currentState;
	private FitViewport viewport;
	private OrthographicCamera camera;

	//General UI and display variable
		//stage is to store the graphics for the entire UI system
		//button style is a general style used to generate buttons
		//the font of the game is stored here.
	private Stage uiStage;
	private TextButton.TextButtonStyle buttonStyle;
	private BitmapFont font;



	//called once and is used to set up the game intials
	@Override
	public void create () {

		//creates a camera object
			//very important for creating a useable universal coordinate system
		camera = new OrthographicCamera();
		//viewport object
			//defines the window the camera will use to view the game world
		viewport = new FitViewport(WIDTH, HEIGHT, camera);
		//stage object
			//used to store the UI elements
		uiStage = new Stage();
		//input Processor is important to recieve keyboard inputs and communicate with the stage (uiStage)
		Gdx.input.setInputProcessor(uiStage);

		//loads in assets
		loadAssets();

		//this is a general in level state.
			//more will be added later on.
				//ex. Loading state, menu state ...
		currentState = new InLevelState(this);
	}

	//This is the render method that preforms the initial render calls.
	//This method is looped over continuosly until the game is called to a halt.
	@Override
	public void render () {
		//this sets the background color
		Gdx.gl.glClearColor(1, 1, 1, 1);
		//This enables the buffer to acknowlege when it is cleared for writing color TO the buffer
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//this is where are the money happens currently. Again, applying the trickle down mentality of
			//passing the responsibility of rendering down.
		currentState.render(camera);

		//this is responsible for drawing the UI
		uiStage.act(Gdx.graphics.getDeltaTime());
		uiStage.draw();
	}
	
	@Override
	public void dispose () {
		currentState.dispose();
		uiStage.dispose();
		font.dispose();
	}

	@Override
	public void resize (int width, int height) {
		viewport.update(width, height);
		uiStage.getViewport().update(width, height);
	}

	public Stage getUiStage() {
		return uiStage;
	}

	public void changeState(GameState newState) {
		currentState.dispose();
		currentState = newState;
	}

	public FitViewport getViewport() {
		return viewport;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}


	//getter for the button style
	public TextButton.TextButtonStyle getButtonStyle() {
		return buttonStyle;
	}

	private void loadAssets() {
		//this is the asset manager, it is called at the very beginning in create.
				//essentially loads and sets a buttons states.
		AssetManager assetManager = new AssetManager();
		assetManager.load("buttonImages/green_button00.png", Texture.class);
		assetManager.load("buttonImages/green_button02.png", Texture.class);
		assetManager.load("buttonImages/green_button01.png", Texture.class);
		assetManager.finishLoading();
		Texture up = assetManager.get("buttonImages/green_button00.png");
		Texture over = assetManager.get("buttonImages/green_button02.png");
		Texture down = assetManager.get("buttonImages/green_button01.png");
		font = new BitmapFont(Gdx.files.internal("fonts/Test.fnt"));

		//this defines what button style is and how it will behave
		buttonStyle = new TextButton.TextButtonStyle();
		buttonStyle.up = new TextureRegionDrawable(new TextureRegion(up));
		buttonStyle.over = new TextureRegionDrawable(new TextureRegion(over));
		buttonStyle.down = new TextureRegionDrawable(new TextureRegion(down));
		buttonStyle.pressedOffsetX = 1;
		buttonStyle.pressedOffsetY = -1;
		buttonStyle.font = font;
	}
}
