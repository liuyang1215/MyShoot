package cn.tedu.shoot;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Airplane extends AirplaneObject implements Enemy{
	private int speed = 3;

	public int getScore() {
		return 5;
	}
	public Airplane() {
		image = ShootGame.airplane;
		width = image.getWidth();
		height = image.getHeight();
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH - this.width);
		y = this.height;
	}
	@Override
	public void step() {
		y += speed;		
	}
	@Override
	public boolean outOfBounds() {
		return this.y >= ShootGame.HEIGHT; 
	}
}
