package cn.tedu.shoot;

import java.awt.Color;
import java.awt.Font;
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

public class ShootGame extends JPanel {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private Hero hero = new Hero();
	private Bullet[] bullets = {};
	private AirplaneObject[] flyings = {};
	
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	
	int state = START;

	public AirplaneObject nextOne() {
		Random rand = new Random();
		int type = rand.nextInt(25);
		if (type < 6) {
			return new Bee();
		} else {
			return new Airplane();
		}
	}

	int flyEnterIndex = 0;

	public void enterAction() {
		flyEnterIndex++;
		if (flyEnterIndex % 40 == 0) {
			AirplaneObject obj = nextOne();
			flyings = Arrays.copyOf(flyings, flyings.length + 1);
			flyings[flyings.length - 1] = obj;
		}
	}

	public void stepAction() {
		hero.step();
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].step();
		}
		for (int i = 0; i < flyings.length; i++) {
			flyings[i].step();
		}
	}

	int shootIndex = 0;

	public void shootAction() {
		shootIndex++;
		if (shootIndex % 30 == 0) {
			Bullet[] bs = hero.shoot();
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);
		}
	}

	public void outOfBoundsAction() {
		int index = 0;
		AirplaneObject[] flyingLives = new AirplaneObject[flyings.length];
		for (int i = 0; i < flyings.length; i++) {
			AirplaneObject f = flyings[i];
			if (!f.outOfBounds()) {
				flyingLives[index] = f;
				index++;
			}
		}
		flyings = Arrays.copyOf(flyingLives, index);
		index = 0;
		Bullet[] bulletsLives = new Bullet[bullets.length];
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			if (!b.outOfBounds()) {
				bulletsLives[index] = b;
				index++;
			}
		}
		bullets = Arrays.copyOf(bulletsLives, index);
	}

	public void bangAction() {
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			if (bang(b)) {
				bullets[i] = bullets[bullets.length - 1];
				bullets[bullets.length - 1] = b;
				bullets = Arrays.copyOf(bullets, bullets.length - 1);
			}
		}
	}

	int score = 0;

	public boolean bang(Bullet b) {
		int index = -1;
		boolean bangbang = false;

		for (int i = 0; i < flyings.length; i++) {
			AirplaneObject obj = flyings[i];
			if (obj.shootBy(b)) {
				index = i;
				bangbang = true;
				break;
			}
		}

		if (index != -1) {
			AirplaneObject one = flyings[index];
			AirplaneObject t = flyings[index];
			flyings[index] = flyings[flyings.length - 1];
			flyings[flyings.length - 1] = t;
			flyings = Arrays.copyOf(flyings, flyings.length - 1);
			if (one instanceof Enemy) {
				Enemy e = (Enemy) one;
				score += e.getScore();
			} else if (one instanceof Award) {
				Award a = (Award) one;
				int type = a.getType();
				switch (type) {
				case Award.DOUBLE_FIRE:
					hero.addDoubleFire();
					break;

				case Award.LIFE:
					hero.addLife();
					break;
				}
			}
		}

		return bangbang;
	}
	

	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);
		paintHero(g);
		paintBullet(g);
		paintAirplaneObject(g);
		paintScoreAndLife(g);
		paintState(g);
	}

	public void paintHero(Graphics g) {
		g.drawImage(hero.image, hero.x, hero.y, null);
	}

	public void paintBullet(Graphics g) {
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			g.drawImage(b.image, b.x, b.y, null);
		}
	}

	public void paintAirplaneObject(Graphics g) {
		for (int i = 0; i < flyings.length; i++) {
			AirplaneObject f = flyings[i];
			g.drawImage(f.image, f.x, f.y, null);
		}
	}
	
	public void paintScoreAndLife(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		g.drawString("SCORE:"+score,10,25);
		g.drawString("LIFE:"+hero.getLife(), 10, 45);
	}
	public void paintState(Graphics g) {
		switch (state) {
		case START:
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE:
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER:
			g.drawImage(gameover, 0, 0, null);
			break;
		}
	}
	public void checkGameOverAction() {
		if(isGameOver()) {
			state = GAME_OVER;
		}
	}
	public boolean isGameOver() {
		int index = -1;
		for(int i=0;i<flyings.length;i++) {
			AirplaneObject object = flyings[i];
			if(hero.hit(object)) {
				hero.subtractLife();
				hero.clearDoubleFire();
				index = i;
			}
			if(index != -1) {
				AirplaneObject  t = flyings[index];
				flyings[index] = flyings[flyings.length - 1];
				flyings[flyings.length - 1] = t;
				
				flyings = Arrays.copyOf(flyings, flyings.length -1);
 			}
		}
		return (hero.getLife() <= 0);
	}

	public void action() {
		MouseAdapter l = new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				if(state == RUNNING) {
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}
			public void mouseClicked(MouseEvent e) {
				switch (state) {
				case START:
					state = RUNNING;
					break;

				case GAME_OVER:
					bullets = new Bullet[0];
					flyings = new AirplaneObject[0];
					hero = new Hero();
					score = 0;
					state = START;
					break;
				}
			}
			public void mouseExited(MouseEvent e) {
				if(state == RUNNING) {
					state = PAUSE;
				}
			}
			public void mouseEntered(MouseEvent e) {
				if(state == PAUSE) {
					state = RUNNING;
				}
			}
		};
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		Timer timer = new Timer();
		int intervel = 10;
		timer.schedule(new TimerTask() {
			public void run() {
				if(state == RUNNING) {
					enterAction();
					stepAction();
					shootAction();
					outOfBoundsAction();
					bangAction();
					checkGameOverAction();
				}
				repaint();
			}
		}, intervel, intervel);
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
