package com.FortPinnsvin.fallingman;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class AndroidRun implements ApplicationListener {
	private Menu			menu;
	public static String	flagView;
	public static GameView			game;
	private AboutView		about;
	private Background		background;
	private TimerAnimation	timerAnimation;

	@Override
	public void create() {
		flagView = "Menu";
		timerAnimation = new TimerAnimation();
		// MenuView
		menu = new Menu();
		menu.create();
		// GameView
		game = new GameView();
		// BackgroundView
		background = new Background();
		background.create();
		// AboutView
		about = new AboutView();
		about.create();
		Gdx.input.setInputProcessor(new GameInputListener(this));
		Gdx.input.setCatchBackKey(true);
	}

	@Override
	public void render() {
		background.render();
		if (flagView == "Menu" || !timerAnimation.timer()) menu.render();
		if (flagView == "Game" && timerAnimation.timer()) game.render();
		if (flagView == "About" && timerAnimation.timer()) about.render();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}
}
