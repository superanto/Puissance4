package interfaceGraphique;

//import java.awt.*;
//import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FenetreStatistiques extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel3;
	private JLabel statistiquesHvsM;
	private JLabel statistiquesMvsH;
	private JLabel statistiquesHvsH;
	/*
	 * private JTextArea ratioHvsM; private JTextArea ratioMvsH; private
	 * JTextArea ratioHvsH;
	 */

	public FenetreStatistiques() {
		panel3 = new JPanel();
		statistiquesHvsM = new JLabel("Homme VS Machine :");
		statistiquesMvsH = new JLabel("Machine VS Homme :");
		statistiquesHvsH = new JLabel("Homme VS Homme :");
		this.setTitle("Fenetre Statistiques");
		this.setSize(400, 250);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		panel3.setLayout(null);
		panel3.add(statistiquesHvsM);
		statistiquesHvsM.setBounds(10, 30, 150, 30);
		panel3.add(statistiquesMvsH);
		statistiquesMvsH.setBounds(10, 90, 150, 30);
		panel3.add(statistiquesHvsH);
		statistiquesHvsH.setBounds(10, 150, 150, 30);
		this.add(panel3);
		this.setVisible(true);
	}
}