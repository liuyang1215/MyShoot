package cn.tedu.shoot;

import java.awt.image.BufferedImage;

public class Hero extends AirplaneObject{
	private int doubleFire;
	private int life;
	private BufferedImage[] images = {};
	int index = 0;
	
	public Hero() {
		doubleFire = 0;
		life = 1;
		images = new BufferedImage[]{ShootGame.hero0,ShootGame.hero1};
		image = ShootGame.hero0;
		width = image.getWidth();
		height = image.getHeight();
		x = 150;
		y = 400;
	}

}
