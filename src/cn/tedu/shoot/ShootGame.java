package cn.tedu.shoot;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JFrame;


public class ShootGame extends JPanel{
	public static final int WIDTH = 400;
	public static final int HEIGHT = 654;
	
	public static BufferedImage background;
	public static BufferedImage airplane;
	public static BufferedImage bee;
	public static BufferedImage bullet;
	public static BufferedImage gameover;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	public static BufferedImage pause;
	public static BufferedImage start;
	
	static {
		try {
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	private Hero hero = new Hero();
	private Bullet[] bullets = {};
	private AirplaneObject[] flyings = {};
	
	public AirplaneObject nextOne() {	
		Random rand = new Random();
		int type = rand.nextInt(25);
		if(type < 6) {
			return new Bee();
		} else {
			return new Airplane();
		}
	}
	
	int flyEnterIndex = 0;
	public void enterAction() {
		flyEnterIndex++;
		if(flyEnterIndex % 40 == 0) {
			AirplaneObject obj = nextOne();
			flyings = Arrays.copyOf(flyings, flyings.length + 1);
			flyings[flyings.length - 1] = obj;
		}
	}
	public void stepAction() {
		hero.step();
		for(int i=0;i<bullets.length;i++) {
			bullets[i].step();
		}
		for(int i=0;i<flyings.length;i++) {
			flyings[i].step();
		}
	}
	int shootIndex = 0;
	public void shootAction() {
		shootIndex++;
		if(shootIndex % 30 == 0) {
			Bullet[] bs = hero.shoot();
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);
		}
	}
	public void outOfBoundsAction() {
		int index = 0;
		AirplaneObject[] flyingLives = new AirplaneObject[flyings.length];
		for(int i=0;i<flyings.length;i++) {
			AirplaneObject f = flyings[i];
			if(!f.outOfBounds()) {
				flyingLives[index] = f;
				index++;
			}
		}
		flyings = Arrays.copyOf(flyingLives, index);
		index = 0;
		Bullet[] bulletsLives = new Bullet[bullets.length];
		for(int i=0;i<bullets.length;i++) {
			Bullet b = bullets[i];
			if(!b.outOfBounds()) {
				bulletsLives[index] = b;
				index++;
			}
		}
		bullets = Arrays.copyOf(bulletsLives, index);
	}
	
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);
		paintHero(g);
		paintBullet(g);
		paintAirplaneObject(g);
	}
	public void paintHero(Graphics g) {
		g.drawImage(hero.image, hero.x, hero.y, null);
	}
	public void paintBullet(Graphics g) {
		for(int i=0;i<bullets.length;i++) {
			Bullet b = bullets[i];
			g.drawImage(b.image, b.x,b.y, null); 
		}
	}
	public void paintAirplaneObject(Graphics g) {
		for(int i=0;i<flyings.length;i++) {
			AirplaneObject f = flyings[i];
			g.drawImage(f.image,f.x, f.y, null);
		}
	}
	public void action() {
		MouseAdapter l = new MouseAdapter() {
			public void moveTo(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				hero.moveTo(x, y);
			}
		};
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		Timer timer = new Timer();
		int intervel = 10;
		timer.schedule(new TimerTask() {
			public void run() {
			enterAction();
			stepAction();
			shootAction();
			outOfBoundsAction();
			repaint();	
			}
		},intervel,intervel);	
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("PLAY AIRPLANE");
		ShootGame game = new ShootGame();
		frame.add(game);
		frame.setSize(WIDTH, HEIGHT);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.action();

	}

}
