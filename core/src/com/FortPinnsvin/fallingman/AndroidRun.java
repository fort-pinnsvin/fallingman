package com.FortPinnsvin.fallingman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AndroidRun implements ApplicationListener {
	private Menu menu;
	public static String flagView;
	private GameView game;
	private Background background;

	@Override
	public void create() {
		flagView = "Menu";
		//Create menu view
		menu = new Menu();
		menu.create();
		//Create game view
		game = new GameView();
		game.create();
		
		background = new Background();
		background.create();
		
		Gdx.input.setInputProcessor(new GameInputListener());
	}

	@Override
	public void render() {
		background.render();
		if (flagView == "Menu")
			menu.render();
		if (flagView == "Game")
			game.render();	
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}
