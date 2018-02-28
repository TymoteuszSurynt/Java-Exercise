package Zadanie7;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
/**
 * 
 * @author Tymoteusz Surynt
 * @version 1.0
 */
public class OknoWlasciwe extends JFrame implements ComponentListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**los-jedyny generator losowy */
	private Random los=new Random();
	/**x-wspolrzedna x, przesunieta o 20 w celu otrzymania lepszego efektu graficznego */
	private double x=20;
	/**y-wspolrzedna y, przesunieta o 40 w celu otrzymania lepszego efektu graficznego */
	private double y=40;
	/**w-szerokosc naszego boksu */
	private double w;
	/**h-wysokosc naszego boksu */
	private double h;
	/**szerokosc planszy liczona w boksach 
	 * @see Zadanie7.OknoPierwsze#m szerokosc*/
	private int m;
	/**wysokosc planszy liczona w boksach 
	 * @see Zadanie7.OknoPierwsze#n wysokosc*/
	private int n;
	/**t- tablica prostokatow ktore beda robic za boksy */
	private Rectangle2D[][] t;
	/**kolor- tablica kolorow */
	private Color[][] kolor;
	/**a-tablica watkow*/
	private WatkiWlasciwe[][] a;
	private Image obraz;
	private Graphics g22;
	/** Generowanie planszy z symulacja
	 * @param m szerokosc planszy liczona w boksach (int)
	 * @param n wysokosc planszy liczona w boksach (int)
	 * @param k szybkosc dzialania programu, im wiecej tym wolniej (int)
	 * @param p prawdopodobienstwo w procentach (int)
	 * */
	OknoWlasciwe(int m, int n, int k, int p)
	{
		this.m=m;
		this.n=n;
		addComponentListener(this);
		setLayout(null);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Symulacja");
		if(m*10<150||n*10<150)
			setBounds(100,100,150,150);
		else if(m*10>1000||n*10>1000)
			setBounds(100,100,1000,1000);
		else
			setBounds(100,100,m*10,n*10);
		w=(this.getWidth()-40)*1.0/m;
		h=(this.getHeight()-60)*1.0/n;
		t=new Rectangle2D[n][m];
		kolor=new Color[n][m];
		a= new WatkiWlasciwe[n][m];
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<m;j++)
			{
				t[i][j]=new Rectangle2D.Double(x,y,w,h);
				a[i][j]=new WatkiWlasciwe(i,j,kolor,t,m,n,p,k);
				kolor[i][j]=new Color(los.nextInt(256), los.nextInt(256), los.nextInt(256));
				x+=w;
				
				
			}
			x=20;
			y+=h;
		}
		repaint();
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<m;j++)
			{
				a[i][j].start();
			}
		}
		
		
	}
	/**podwojne  buforowanie */
	public void paint(Graphics g)
	{
		
		obraz=createImage(this.getWidth(),this.getHeight());
		g22=obraz.getGraphics();
		paintComponents(g22);
		g.drawImage(obraz,0,0,this);
	}
	/**rysowanie boksow*/
	public void paintComponents(Graphics g) 
	{
        Graphics2D g2 = (Graphics2D)g;
        for(int i=0;i<n;i++)
		{
			for(int j=0;j<m;j++)
			{

				g2.setColor(kolor[i][j]);
				g2.fill(t[i][j]);
				g2.setColor(Color.BLACK);
				g2.draw(t[i][j]);
				
				
			}
		}
        g2.dispose();

	}
	public class WatkiWlasciwe extends Thread
	{
		private int i1,j1,p,k,m,n;
		private Color[][] kolor;
		private Rectangle2D[][] t;
		private Lock lock= new ReentrantLock();
		/** Tworzenie watku
		 * @param i1,j1 "wsporzedne" naszego watku w tablicy (int)
		 * @param kolor tablica kolorow na ktorej watek bedzie pracowac (Color[][])
		 * @param t tablica "boksow" ktorzy kolory beda zmieniane w czasie prezentacji (Rectangle2D[][])
		 * @param m szerokosc planszy liczona w boksach (int)
		 * @param n wysokosc planszy liczona w boksach (int)
		 * @param k szybkosc dzialania programu, im wiecej tym wolniej (int)
		 * @param p prawdopodobienstwo w procentach (int)
		 * */
		public WatkiWlasciwe(int i1, int j1,Color[][] kolor, Rectangle2D[][] t, int m, int n, int p, int k)
		{
			this.i1=i1;
			this.j1=j1;
			this.m=m;
			this.n=n;
			this.p=p;
			this.k=k;
			this.kolor=kolor;
			this.t=t;
		}
		public void run()
		{
			
			while(!Thread.interrupted())
			{
				try 
				{
					int a=(int) Math.round((los.nextDouble()+0.5)*k);
					Thread.sleep(a);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				lock.lock();
				int a=los.nextInt(100);
				if(a<p)
				{
					kolor[i1][j1]=new Color(los.nextInt(256), los.nextInt(256), los.nextInt(256));
					int x1,y1,w1,h1;
					x1=(int) t[i1][j1].getX()+1;
					y1=(int) t[i1][j1].getY()+1;
					w1=(int) t[i1][j1].getWidth()+1;
					h1=(int) t[i1][j1].getHeight()+1;
					repaint(x1,y1,w1,h1);
				}
				a=los.nextInt(100);
				if(a>=p)
				{
					int[] tab=new int [12];
					if(i1!=0)
					{
						tab[0]=kolor[i1-1][j1].getRed();
						tab[1]=kolor[i1-1][j1].getBlue();
						tab[2]=kolor[i1-1][j1].getGreen();
					}
					else
					{
						tab[0]=kolor[n-1][j1].getRed();
						tab[1]=kolor[n-1][j1].getBlue();
						tab[2]=kolor[n-1][j1].getGreen();
					}
					if(i1+1!=n)
					{
						tab[3]=kolor[i1+1][j1].getRed();
						tab[4]=kolor[i1+1][j1].getBlue();
						tab[5]=kolor[i1+1][j1].getGreen();
					}
					else
					{
						tab[3]=kolor[0][j1].getRed();
						tab[4]=kolor[0][j1].getBlue();
						tab[5]=kolor[0][j1].getGreen();
					}
					if(j1!=0)
					{
						tab[6]=kolor[i1][j1-1].getRed();
						tab[7]=kolor[i1][j1-1].getBlue();
						tab[8]=kolor[i1][j1-1].getGreen();
					}
					else
					{
						tab[6]=kolor[i1][m-1].getRed();
						tab[7]=kolor[i1][m-1].getBlue();
						tab[8]=kolor[i1][m-1].getGreen();
					}
					if(j1+1!=m)
					{
						tab[9]=kolor[i1][j1+1].getRed();
						tab[10]=kolor[i1][j1+1].getBlue();
						tab[11]=kolor[i1][j1+1].getGreen();
					}
					else
					{
						tab[9]=kolor[i1][0].getRed();
						tab[10]=kolor[i1][0].getBlue();
						tab[11]=kolor[i1][0].getGreen();
					}
					kolor[i1][j1]=new Color((tab[0]+tab[3]+tab[6]+tab[9])/4,(tab[1]+tab[4]+tab[7]+tab[10])/4,(tab[2]+tab[5]+tab[8]+tab[11])/4);
					int x1,y1,w1,h1;
					x1=(int) t[i1][j1].getX()+1;
					y1=(int) t[i1][j1].getY()+1;
					w1=(int) t[i1][j1].getWidth()+1;
					h1=(int) t[i1][j1].getHeight()+1;
					repaint(x1,y1,w1,h1);
					
				}
				lock.unlock();
				
			}
		}
	}
	/**skalowanie, zaleznie od wielkosci nowego okna */
	public void componentResized(ComponentEvent e) 
	{
		w=(this.getWidth()-40)*1.0/m;
		h=(this.getHeight()-60)*1.0/n;
		x=20;
		y=40;
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<m;j++)
			{
				t[i][j]=new Rectangle2D.Double(x,y,w,h);
				x+=w;
				
			}
			x=20;
			y+=h;
		}
		repaint();
		
	}
	public void componentHidden(ComponentEvent arg0){}
	public void componentMoved(ComponentEvent arg0){}
	public void componentShown(ComponentEvent arg0){}
}
