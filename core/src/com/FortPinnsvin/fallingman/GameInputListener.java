package com.FortPinnsvin.fallingman;

import com.badlogic.gdx.InputProcessor;

public class GameInputListener implements InputProcessor {
	private LogicMenu	logicMenu;

	public GameInputListener() {
		logicMenu = new LogicMenu();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (AndroidRun.flagView.equals("Menu")) logicMenu.buttonClick(screenX, screenY);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		logicMenu.draggedCloude(screenX, screenY);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
