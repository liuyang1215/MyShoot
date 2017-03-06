package cn.tedu.shoot;

public class Bullet extends AirplaneObject{
	private int speed = 3;
	
	public Bullet(int x,int y) {
		image = ShootGame.bullet;
		width = image.getWidth();
		height = image.getHeight();
		this.x = x;
		this.y = y;
	}

	@Override
	public void step() {
		y -= speed;		
	}

	@Override
	public boolean outOfBounds() {
		return this.y <= ShootGame.HEIGHT;
	}
}
