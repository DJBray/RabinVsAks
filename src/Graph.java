import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;


public class Graph extends JPanel{
	public static final int GAP = 30;
	public static final int Y_DASHES = 20;
	public static final int X_DASHES = 20;
	public static final int DASH_WIDTH = 10;
	public static final int POINT_RADIUS = 4;
	
	private ArrayList<Point> myPoints;
	private ArrayList<Point> systPoints;

	public Graph(){
		super();
		myPoints = new ArrayList<Point>();
		systPoints = new ArrayList<Point>();
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.drawString("Speed", GAP, GAP-5);
		g2.drawString("Time", getWidth() / 2, getHeight() - 10);

		// create x and y axes 
		g2.drawLine(GAP, getHeight() - GAP, GAP, GAP);
		g2.drawLine(GAP, getHeight() - GAP, getWidth() - GAP, getHeight() - GAP);

		// create hatch marks for y axis. 
		for (int i = 0; i < Y_DASHES; i++) {
			int x0 = GAP;
			int x1 = DASH_WIDTH + GAP;
			int y0 = getHeight() - (((i + 1) * (getHeight() - GAP * 2)) / Y_DASHES + GAP);
			int y1 = y0;
			g2.drawLine(x0, y0, x1, y1);
		}

		// and for x axis
		for (int i = 0; i < X_DASHES; i++) {
			int x0 = (i + 1) * (getWidth() - GAP * 2) / (X_DASHES) + GAP;
			int x1 = x0;
			int y0 = getHeight() - GAP;
			int y1 = y0 - DASH_WIDTH;
			g2.drawLine(x0, y0, x1, y1);
		}
		
		g2.setColor(Color.GREEN);
		
		//draw my myPoints and lines between
		if(!myPoints.isEmpty()){
			Point p = myPoints.get(0);
			g2.fillOval((int)p.getX(), (int)p.getY(), POINT_RADIUS, POINT_RADIUS);
		}
		for (int i = 1; i < myPoints.size(); i++){
			int x1 = (int)myPoints.get(i-1).getX();
			int y1 = (int)myPoints.get(i-1).getY();
			int x2 = (int)myPoints.get(i).getX();
			int y2 = (int)myPoints.get(i).getY();
			g2.fillOval(x2, y2, POINT_RADIUS, POINT_RADIUS);
			g2.drawLine(x1, y1, x2, y2);
		}
		
		g2.setColor(Color.RED);
		
		//draw system myPoints
		if(!systPoints.isEmpty()){
			Point p = systPoints.get(0);
			g2.fillOval((int)p.getX(), (int)p.getY(), POINT_RADIUS, POINT_RADIUS);
		}
		for (int i = 1; i < systPoints.size(); i++){
			int x1 = (int)systPoints.get(i-1).getX();
			int y1 = (int)systPoints.get(i-1).getY();
			int x2 = (int)systPoints.get(i).getX();
			int y2 = (int)systPoints.get(i).getY();
			g2.fillOval(x2, y2, POINT_RADIUS, POINT_RADIUS);
			g2.drawLine(x1, y1, x2, y2);
		}
	}
	
	public void addMyPoint(Point p){
		p.x = (p.x) * (getWidth() - GAP * 2) / (X_DASHES) + GAP;
		p.y += GAP*2;
		myPoints.add(p);
	}
	
	public void addSystPoint(Point p){
		p.x = (p.x) * (getWidth() - GAP * 2) / (X_DASHES) + GAP;
		p.y += GAP*2;
		systPoints.add(p);
	}
}
