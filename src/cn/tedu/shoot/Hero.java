package cn.tedu.shoot;

import java.awt.image.BufferedImage;

import javax.swing.text.html.HTMLEditorKit.InsertHTMLTextAction;

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

	@Override
	public void step() {
		image = images[index++/10%images.length];
	}
	public Bullet[] shoot() {
		int xStep = this.width/4;
		int yStep = 20;
		if(doubleFire > 0) {
			Bullet[] bs = new Bullet[2];
			bs[0] = new Bullet(this.x +1*xStep, this.y - yStep);
			bs[1] = new Bullet(this.x +3*xStep, this.y - yStep);
			doubleFire -= 2;
			return bs;
		} else {
			Bullet[] bs = new Bullet[1];
			bs[0] = new Bullet(this.x +2*xStep, this.y - yStep);
			return bs;
		}
	}
	public void moveTo(int x,int y) {
		this.x = x - width/2;
		this.y = y - height/2;
	}

	@Override
	public boolean outOfBounds() {
		return false;
	}
	public void addLife() {
		life++;
	}
	public void addDoubleFire() {
		doubleFire = 40;
	}
}
