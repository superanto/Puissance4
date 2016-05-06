package interfaceGraphique;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gDC.GslPlateau;
import plateauDeJeu.Plateau;

public class FenetreJeu extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel2;
	private JButton accesMenu;
	private JButton sauvegardePartie;
	private dessinPlateau affichePlateau;

	public FenetreJeu(Plateau monPlateau) {
		panel2 = new JPanel();
		affichePlateau = new dessinPlateau(monPlateau);
		accesMenu = new JButton("Menu");
		accesMenu.setPreferredSize(new Dimension(125, 25));
		sauvegardePartie = new JButton("Sauvegarder");
		this.setTitle("DeuxiemeFenetre");
		this.setSize(70 * monPlateau.getColonne(), 67 + (70 * monPlateau.getLigne()));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		panel2.add(accesMenu);
		accesMenu.addActionListener(this);
		panel2.add(sauvegardePartie);
		sauvegardePartie.addActionListener(this);
		this.setLayout(new BorderLayout());
		this.add(panel2, BorderLayout.NORTH);
		this.add(affichePlateau, BorderLayout.CENTER);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == accesMenu) {
			this.dispose();
			new FenetreMenu();
		}
		if (source == sauvegardePartie) {
			GslPlateau aSauvegarder = new GslPlateau(this.affichePlateau.get_Plateau());
			aSauvegarder.sauvegarde();
			System.out.println("Partie sauvegardée !");

		}
	}
}
