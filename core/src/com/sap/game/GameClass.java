package com.sap.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import java.util.Random;


public class GameClass extends ApplicationAdapter {

	SpriteBatch batch,batch1;
	Texture ball;
	Texture play;

	int xmax,ymax;
	float velocity;
	float xVelocity,yVelocity;
	float setVelocity,resetVelocity;

	float cX,cY;

	int n1,n2,n3;

	int xDir,yDir;

	float xPosition,yPosition;

	BitmapFont scoreDisplay,life,gameOver,levelDisplay;

	Vector3 tmp = new Vector3();
	Rectangle textureBounds = new Rectangle();

	GlyphLayout layout;
	String item = "Gave Over";
	float w,h;

	int level,lives,score;

	int gameState;

	boolean batchFlag;

	public int random2(){
		Random rand = new Random();

		int  n = rand.nextInt(2) + 0;

		return n;
	}

	public int random3(){
		Random rand = new Random();

		int  n = rand.nextInt(3) + 0;

		return n;
	}

	public int random4(){
		Random rand = new Random();

		int  n = rand.nextInt(4) + 0;

		return n;
	}

	public int xAxisRandom(){
		Random rand = new Random();

		int  n = rand.nextInt(xmax-200) + 10;

		return n;
	}
	public int yAxisRandom(){
		Random rand = new Random();

		int  n = rand.nextInt(ymax-200) + 10;

		return n;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		batch1=new SpriteBatch();
		ball = new Texture("ball.jpg");
		play = new Texture("play.png");

		xmax= Gdx.graphics.getWidth();
		ymax=Gdx.graphics.getHeight();

		xPosition=Gdx.graphics.getWidth()/2 - 50;
		yPosition=Gdx.graphics.getHeight()/2 - 50;

		cX=xPosition;
		cY=yPosition;

		scoreDisplay = new BitmapFont();
		scoreDisplay.setColor(Color.BLACK);
		scoreDisplay.getData().scale(5);

		life = new BitmapFont();
		life.setColor(Color.BLACK);
		life.getData().scale(5);

		gameOver = new BitmapFont();
		gameOver.setColor(Color.BLACK);
		gameOver.getData().scale(5);

		levelDisplay = new BitmapFont();
		levelDisplay.setColor(Color.BLACK);
		levelDisplay.getData().scale(5);

		layout= new GlyphLayout();
		layout.setText(gameOver,item);
		w = layout.width;
		h = layout.height;

		xVelocity   = 1;
		yVelocity   = 1;
		velocity    = 1;
		resetVelocity=velocity;
		setVelocity = 0.4f;

		gameState = 1;

		level = 1;
		lives = 5;
		score = 0;

		batchFlag = false;

	}


	@Override
	public void render () {

		if(!batchFlag) {

			batch.begin();

			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.draw(play, xmax / 2 - play.getWidth() / 2, ymax / 2 - play.getHeight() / 2);

			if (Gdx.input.justTouched()) {

				tmp.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 0);
				textureBounds.set(xmax / 2 - play.getWidth() / 2, ymax / 2 - play.getHeight() / 2, play.getWidth(), play.getHeight());

				if (textureBounds.contains(tmp.x, tmp.y)) {

					batchFlag=true;
				}
			}

			batch.end();
		}

		if(batchFlag) {

			batch1.begin();

			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


			if (gameState == 1) {

				batch1.draw(ball, xPosition, yPosition);
				scoreDisplay.draw(batch1, String.valueOf(score), 140, 140);
				life.draw(batch1, String.valueOf(lives), xmax - 140, 140);

				if (Gdx.input.justTouched()) {

					tmp.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 0);
					textureBounds.set(xPosition, yPosition, 140, 140);

					if (textureBounds.contains(tmp.x, tmp.y)) {

						score++;
						level++;

						if (xVelocity > 0 && yVelocity > 0) {
							xVelocity += setVelocity;
							yVelocity += setVelocity;
						} else if (xVelocity > 0 && yVelocity < 0) {
							xVelocity += setVelocity;
							yVelocity -= setVelocity;
						} else if (xVelocity < 0 && yVelocity > 0) {
							xVelocity -= setVelocity;
							yVelocity += setVelocity;
						} else if (xVelocity < 0 && yVelocity < 0) {
							xVelocity -= setVelocity;
							yVelocity -= setVelocity;
						}

						if (velocity < 0) {
							velocity -= setVelocity;
						} else {
							velocity += setVelocity;
						}

						xDir = random2();
						yDir = random2();
						n1 = random2();

						if (level > 10) {
							n2 = random3();
							n3 = random4();
						}

						if(level > 30){
							xPosition = xAxisRandom();
							yPosition = yAxisRandom();
						}

					} else {

						if (lives > 0) {
							lives--;
						}
						if (lives == 0)
							gameState = 0;
					}
				}

				if (level <= 2) {

					xPosition += xVelocity;

				} else if (level <= 7) {

					if (n1 == 0) {

						if (xDir == 1) {
							xPosition += xVelocity;
						} else {
							xPosition -= xVelocity;
						}
					} else {
						if (yDir == 1) {
							yPosition += yVelocity;
						} else {
							yPosition -= yVelocity;
						}
					}

				} else if (level > 7) {

					if (n2 == 0 || n2 == 1) {

						if (xDir == 1) {
							xPosition += xVelocity;
						} else {
							xPosition -= xVelocity;
						}

						if (yDir == 1) {
							yPosition += yVelocity;
						} else {
							yPosition -= yVelocity;
						}
					} else {

						if (n1 == 0) {

							if (xDir == 1) {
								xPosition += xVelocity;
							} else {
								xPosition -= xVelocity;
							}
						} else {

							if (yDir == 1) {
								yPosition += yVelocity;
							} else {
								yPosition -= yVelocity;
							}
						}
					}
				}

				if (xPosition >= xmax - 140 || xPosition <= 0) {
					xVelocity = -xVelocity;
				}
				if (yPosition >= ymax - 140 || yPosition <= 0) {
					yVelocity = -yVelocity;
				}

			}

			if (gameState == 0) {

				gameOver.draw(batch1, "Game Over", xmax / 2 - w / 2, ymax / 2);

				if (Gdx.input.justTouched()) {

					tmp.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 0);
					textureBounds.set(xmax / 2 - w / 2, ymax / 2, w, h);

					if (textureBounds.contains(tmp.x, tmp.y)) {

						lives = 4;
						score = -1;
						gameState = 1;
						level = 1;

						xVelocity = resetVelocity;
						yVelocity = resetVelocity;
						velocity = resetVelocity;

						xPosition = Gdx.graphics.getWidth() / 2 - 50;
						yPosition = Gdx.graphics.getHeight() / 2 - 50;

						batchFlag=false;

					}
				}
			}

			batch1.end();
		}
	}

	@Override
	public void dispose () {

	}
}