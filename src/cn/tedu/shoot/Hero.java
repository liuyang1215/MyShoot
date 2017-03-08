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
		life = 3;
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
	public void subtractLife() {
		life--;
	}
	public void addDoubleFire() {
		doubleFire += 40;
	}
	public void clearDoubleFire() {
		doubleFire = 0;
	}
	public int getLife() {
		return life;
	}
	public boolean hit(AirplaneObject other) {
		int x1 = other.x - this.width/2;
		int x2 = other.x + this.width/2 + other.width;
		int y1 = other.y - this.height/2;
		int y2 = other.y + this.height/2 + other.height;
		int x = this.x - this.width/2;
		int y = this.y - this.height/2;
		return x <= x2 && x >=x1 && y <= y2 && y >= y1;
	}
}
