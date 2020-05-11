import java.awt.Color;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class GamePlay extends JPanel implements KeyListener,ActionListener {

	private boolean play = false;
	private int score=0;
	private int totalBricks=60;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerx = 310;
	
	private int ballposX = 200;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir=-2;
	
	private Generatoor map;
	
	public GamePlay()
	{
		map = new Generatoor(6,10);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
		timer.start();		
		
	}
    public void paint(Graphics g) 
    {
    	//background
    	g.setColor(Color.DARK_GRAY);
    	g.fillRect(1, 1, 692, 592);
    	
    	//Generator
    	map.draw((Graphics2D) g);
    	
    	//the borders
    	g.setColor(Color.red);
    	g.fillRect(playerx, 550, 100, 83);
        g.fillRect(0,0,692,3);
        g.fillRect(691, 0,3,592);
        
        
        //Scoring
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score, 590, 30);
        
    	//the paddle
        g.setColor(Color.red);
        g.fillRect(playerx, 550, 100,8);
        
        //the ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);
        
        if(totalBricks <=0) {
        	
        	play = false;
        	ballXdir =0;
        	ballYdir = 0;
        	g.setColor(Color.red);
        	g.setFont(new Font("serif",Font.BOLD,50));
        	g.drawString("You WON !!!" , 190, 300);
        	
        	g.setFont(new Font("serif",Font.BOLD,20));
        	g.drawString("x Press Enter to Restart", 230, 350);
        	
        }
        
        if(ballposY > 570) {
        	play = false;
        	ballXdir =0;
        	ballYdir = 0;
        	
        	
        	g.setColor(Color.red);
        	g.setFont(new Font("serif",Font.BOLD,30));
        	g.drawString(" Game Over, Your Score was " + score, 190, 300);
        	
        	g.setFont(new Font("serif",Font.BOLD,20));
        	g.drawString(" Press Enter to Restart", 230, 350);
        	
        }
        
        g.dispose();
    }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		timer.start();
		if(play) {
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerx,550,100,8))) {
				ballYdir = -ballYdir;
			}
			A:for(int i=0; i < map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j] > 0) {
						int brickX = j*map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
						Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickvalue(0,i,j);
							totalBricks--;
							score+=5;
							
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
								ballXdir= - ballYdir;
							}else {
								ballYdir = -ballYdir;
							}
							break A;
						}
						
					}
				}
			}
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX < 0) {
				ballXdir = - ballXdir;
			}
			if(ballposY < 0) {
				ballYdir = -ballYdir;
			}
			if(ballposX > 670) {
				ballXdir = -ballXdir;
			}
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerx >= 600) {
			 playerx = 600;
			}else {
				moveRight();
			}
		}
		if(arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerx < 10) {
				playerx = 10;
			}else {
				moveLeft();
			}
		}
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballposX = 200;
				ballposY=350;
				ballXdir = -1;
				ballYdir =-2;
				playerx=310;
				score=0;
				totalBricks=60;
				
                map = new Generatoor(6,10);
                
                repaint();
			}
		}
		
	}
	public void moveRight() {
		play= true;
		playerx+=20;
		
	}
    public void moveLeft() {
    	play=true;
    	playerx-=20;
    }
	@Override
	public void keyReleased(KeyEvent arg0) {
		
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
		
	}
}
