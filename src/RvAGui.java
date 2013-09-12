import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


public class RvAGui extends JFrame {

	private JPanel contentPane;
	private JButton btnConfirm;
	private JTextArea txtpnResults;
	private Graph graphPanel;

	/**
	 * Create the frame.
	 */
	public RvAGui() {
		super("Miller-Rabin vs. Java Library");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 652, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		btnConfirm = new JButton("Start");
		btnConfirm.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				startTest();
			}
		});
		contentPane.add(btnConfirm, BorderLayout.SOUTH);
		
		txtpnResults = new JTextArea();
		txtpnResults.setSize(300, this.getHeight());
		txtpnResults.setEditable(false);
		contentPane.add(txtpnResults, BorderLayout.WEST);
		
		graphPanel = new Graph();
		contentPane.add(graphPanel, BorderLayout.CENTER);
	}

	public void startTest(){
		MillerPrimalityTest myTest = new MillerPrimalityTest(this);
	}
	
	public void setText(String text){
		txtpnResults.setText(text);
	}
	
	public void appendText(String text){
		txtpnResults.setText(txtpnResults.getText() + "\n" + text);
	}
	
	public void addMyPoint(Point p){
		graphPanel.addMyPoint(p);
	}
	
	public void addSystPoint(Point p){
		graphPanel.addSystPoint(p);
	}
}

