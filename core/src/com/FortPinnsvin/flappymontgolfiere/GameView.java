package com.FortPinnsvin.flappymontgolfiere;

import java.util.Arrays;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Disposable;

public class GameView implements Disposable {
	public final int	HEIGHT_BALLOON	= 10000;
	public float		W				= Gdx.graphics.getWidth();
	public float		H				= Gdx.graphics.getHeight();
	private Texture		balloon;
	public Sprite		spriteBalloon;
	public Texture		bg;
	public Sprite		spriteBg;
	public SpriteBatch	batch;
	public long			clickCount, meters, step;
	public BitmapFont	font;
	public Texture		shkala;
	public Sprite		spriteShkala;
	public Sprite		spriteMiniBalloon;
	public Sprite[]		cloud;
	public Random		rand			= new Random();
	public Texture		sattelite;
	public Sprite		spriteSattelite;
	public Texture		finish;
	public Sprite		spriteFinish;
	public boolean		flagFinish;
	public long			time;
	public boolean		isStart;

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("data/8bit.fnt"), Gdx.files.internal("data/8bit.png"), false);
		balloon = new Texture("balloon.png");
		shkala = new Texture("Shkala.png");
		sattelite = new Texture("Sattlite.png");
		finish = new Texture("Finish.png");
		spriteBalloon = new Sprite(balloon);
		spriteBalloon.setSize(W / 3, H / 4);
		spriteBalloon.setPosition(W / 2 - W / 6, H / 20);
		bg = new Texture("bg-1-mini.png");
		spriteBg = new Sprite(bg);
		spriteBg.setPosition(0, 0);
		spriteBg.setSize(W, H);
		spriteShkala = new Sprite(shkala);
		spriteShkala.setPosition((float) (W / 1.05), 0);
		spriteShkala.setSize(W - (float) (W / 1.05), H);
		spriteMiniBalloon = new Sprite(balloon);
		spriteMiniBalloon.setPosition((float) (W / 1.05), 5);
		spriteMiniBalloon.setSize(W - (float) (W / 1.05), H / 27);
		spriteSattelite = new Sprite(sattelite);
		spriteSattelite.setSize(W / 3, H / 10);
		spriteSattelite.setPosition(spriteSattelite.getX() - (W / 3), spriteBalloon.getY() + (H / 3));
		spriteFinish = new Sprite(finish);
		spriteFinish.setSize(W, H / 5);
		spriteFinish.setPosition(0, 10000);
		flagFinish = false;
		clickCount = 0;
		meters = 0;
		step = 10;
		time = System.currentTimeMillis();
		isStart = false;
		cloud = new Sprite[10];
		for (int i = 0; i < cloud.length; i++) {
			int num = (rand.nextInt(3) + 1);
			Texture texture = new Texture("cloude" + num + ".png");
			cloud[i] = new Sprite(texture);
			switch (num) {
				case 1 :
					cloud[i].setSize((float) (W / 2.2), H / 7);
					break;
				case 2 :
					cloud[i].setSize((float) (W / 3), H / 9);
					break;
				case 3 :
					cloud[i].setSize((float) (W / 2), H / 12);
					break;
			}
			cloud[i].setPosition(-W / 2 + W * rand.nextFloat(), H + 5000 * rand.nextFloat());
		}
	}

	public void render() {
		renderGradient();
		batch.begin();
		spriteBg.draw(batch);
		spriteSattelite.draw(batch);
		spriteFinish.draw(batch);
		spriteBalloon.draw(batch);
		for (int i = 0; i < cloud.length; i++)
			cloud[i].draw(batch);
		if (!flagFinish) {
			font.draw(batch, clickCount + " clicks", font.getSpaceWidth(), H - font.getSpaceWidth());
			font.draw(batch, meters + " meters", font.getSpaceWidth(), H - 2 * font.getSpaceWidth() - font.getLineHeight());
			font.draw(batch, step + " m. per click", font.getSpaceWidth(), H - 3 * font.getSpaceWidth() - 2 * font.getLineHeight());
		}
		spriteShkala.draw(batch);
		spriteMiniBalloon.draw(batch);
		spriteMiniBalloon.setY((H * meters) / HEIGHT_BALLOON);
		if (flagFinish) {
			renderFinishResult();
		}
		batch.end();
		if (meters > 8000 && spriteSattelite.getX() <= W) {
			spriteSattelite.setPosition(spriteSattelite.getX() + 10, spriteSattelite.getY() - 3);
		}
		if (meters - (step / 10) >= 0 && isStart) {
			meters -= (step / 10);
			step = 10 + (clickCount * 10) / ((System.currentTimeMillis() - time) / 1000);
		}
		spriteBg.setPosition(0, -meters);
		spriteFinish.translateY(step / 10);
		for (int i = 0; i < cloud.length; i++)
			cloud[i].translateY(step / 10);
		if (meters >= HEIGHT_BALLOON && !flagFinish) {
			flagFinish = true;
			int[] results = LocalStorage.loadResults();
			int len = results.length;
			int[] new_arr = new int[len + 1];
			System.arraycopy(results, 0, new_arr, 0, len);
			new_arr[len] = (int) clickCount;
			Arrays.sort(new_arr);
			LocalStorage.saveResults(new_arr);
		}
	}

	public void renderGradient() {
		float red, green, blue;
		if (meters > H) {
			blue = 255 - ((float) meters / HEIGHT_BALLOON) * 255;
			red = 88 - ((float) meters / HEIGHT_BALLOON) * 88;
			green = 190 - ((float) meters / HEIGHT_BALLOON) * 190;
		} else {
			blue = 255;
			green = 190;
			red = 88;
		}
		ShapeRenderer shape = new ShapeRenderer();
		shape.begin(ShapeType.Filled);
		shape.setColor(red / 255f, green / 255f, blue / 255f, 1);
		shape.rect(0, 0, W, H);
		shape.end();
		shape.dispose();
	}

	public void processClick(int x, int y) {
		if (spriteBalloon.getBoundingRectangle().contains(x, y) && !flagFinish) {
			if (!isStart) {
				time = System.currentTimeMillis() - 1001;
				isStart = true;
			} else {
				meters += step;
				clickCount++;
				step = 10 + (clickCount * 10) / ((System.currentTimeMillis() - time) / 1000);
				spriteBg.setPosition(0, -meters);
				spriteFinish.translateY(-step);
				for (int i = 0; i < cloud.length; i++)
					cloud[i].translateY(-step);
			}
		}
	}

	public void renderFinishResult() {
		font.drawWrapped(batch, "Cooooooool\nYou make " + clickCount + " clicks", 0, H / 2, W, HAlignment.CENTER);
	}

	public void dispose() {
		balloon.dispose();
		bg.dispose();
		batch.dispose();
		font.dispose();
		shkala.dispose();
		sattelite.dispose();
		finish.dispose();
	}
}
