package Zadanie7;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
/**
 * 
 * @author Tymoteusz Surynt
 * @version 1.0
 */
public class OknoPierwsze extends JFrame implements ActionListener
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**m-szerokosc */
	private int m;
	/**n-wysokosc */
	private int n;
	/**p-prawdopodobienstwo */
	private int p;
	/**k-szybkosc */
	private int k;
	JButton b1, b2;
	JLabel l1,l2,l3,l4;
	JTextField t1,t2,t3,t4;
	public OknoPierwsze()
	{
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		b1=new JButton("Dalej");
		b2=new JButton("Wyjscie");
		l1=new JLabel("Szerokosc:");
		l2=new JLabel("Wysokosc:");
		l3=new JLabel("Szybkosc:");
		l4=new JLabel("Prawdopodobienstwo:");
		t1=new JTextField("");
		t2=new JTextField("");
		t3=new JTextField("");
		t4=new JTextField("");
		setTitle("Podawanie danych");
		setBounds(100,100,500,200);
		b1.setBounds(160,130,80,30);
		b2.setBounds(260,130,80,30);
		l1.setBounds(15,20,100,30);
		l2.setBounds(125,20,100,30);
		l3.setBounds(235,20,100,30);
		l4.setBounds(335,20,150,30);
		t1.setBounds(15,55,70,30);
		t2.setBounds(125,55,70,30);
		t3.setBounds(235,55,70,30);
		t4.setBounds(335,55,70,30);
		b1.addActionListener(this);
		b2.addActionListener(this);
		add(b1);
		add(b2);
		add(l1);
		add(l2);
		add(l3);
		add(l4);
		add(t1);
		add(t2);
		add(t3);
		add(t4);
	}

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("Wyjscie"))
		{
			System.exit(0);
		}
		else if(e.getActionCommand().equals("Dalej"))
		{
			try
			{
				if(t1.getText().equals(""))
					m=50;
				else
					m=Integer.parseInt(t1.getText());
			}
			catch(NumberFormatException w)
			{
				m=50;
			}
			try
			{
				if(t2.getText().equals(""))
					n=50;
				else
					n=Integer.parseInt(t2.getText());
			}
			catch(NumberFormatException w1)
			{
				n=50;
			}
			try
			{
				if(t3.getText().equals(""))
					k=1000;
				else
					k=Integer.parseInt(t3.getText());
			}
			catch(NumberFormatException w2)
			{
				k=1000;
			}
			try
			{
				if(t4.getText().equals(""))
					p=5;
				else
					p=Integer.parseInt(t4.getText());
			}
			catch(NumberFormatException w3)
			{
				p=5;
			}
			if(m>140)
				m=140;
			if(n>140)
				n=140;
			if(k<100)
				k=100;
			if(p<0)
				p=0;
			OknoWlasciwe a= new OknoWlasciwe(m,n,k,p);
			a.setVisible(true);
			this.dispose();
		}
		
	}
	
}
