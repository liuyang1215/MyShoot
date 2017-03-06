package cn.tedu.shoot;

import java.awt.image.BufferedImage;

public abstract class AirplaneObject {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected BufferedImage image;
	
	public abstract void step();
	
	public abstract boolean outOfBounds();

}
