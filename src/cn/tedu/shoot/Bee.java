package cn.tedu.shoot;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Bee extends AirplaneObject implements Award{
	private int xSpeed = 1;
	private int ySpeed = 2;
	private int awardType;
	
	public int getType() {
		return awardType;
	}
	public Bee() {
		image = ShootGame.bee;
		width = image.getWidth();
		height = image.getHeight();
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH - this.width);
		y = -this.height;
		awardType = rand.nextInt(2);
		
	}

}
