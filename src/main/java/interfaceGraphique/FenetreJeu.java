package interfaceGraphique;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gDC.GslPlateau;
import plateauDeJeu.Plateau;

public class FenetreJeu extends JFrame implements ActionListener {
	
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

		/* Affichage de la fenètre de Jeu (Boutons + Plateau de Jeu)*/
		if (monPlateau.getTypePartie() == 1) {
			this.setTitle("Puissance 4 - Homme VS Machine");
		}
		if (monPlateau.getTypePartie() == 2) {
			this.setTitle("Puissance 4 - Machine VS Homme");
		}
		if (monPlateau.getTypePartie() == 3) {
			this.setTitle("Puissance 4 - Homme VS Homme");
		}

		this.setSize(100 + (70 * monPlateau.getColonne()), 120 + (70 * monPlateau.getLigne()));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		panel2.add(accesMenu);
		accesMenu.addActionListener(this);
		panel2.add(sauvegardePartie);
		sauvegardePartie.addActionListener(this);
		this.setLayout(null);
		panel2.setBounds(0, 0, 100 + (70 * monPlateau.getColonne()), 30);
		this.add(panel2);
		affichePlateau.setBounds(50, 40, 1 + (70 * monPlateau.getColonne()), 1 + (70 * monPlateau.getLigne()));
		this.add(affichePlateau);

		/*Affichage de quel joueur a gagné si l'un deux à  reussi à  aligner 4 pions de leur couleurs*/
		monPlateau = affichePlateau.get_Plateau();
		if (monPlateau.getVictoire() == 1) {
			JLabel lab = new JLabel("Joueur 1 a gagné !");
			lab.setHorizontalAlignment(JLabel.CENTER);
			lab.setBounds(0, 51 + (70 * monPlateau.getLigne()), 100 + (70 * monPlateau.getColonne()), 30);
			this.add(lab);
		}
		if (monPlateau.getVictoire() == 2) {
			JLabel lab = new JLabel("Joueur 2 a gagné !");
			lab.setHorizontalAlignment(JLabel.CENTER);
			lab.setBounds(0, 51 + (70 * monPlateau.getLigne()), 100 + (70 * monPlateau.getColonne()), 30);
			this.add(lab);
		}
		if (monPlateau.getVictoire() == 3) {
			JLabel lab = new JLabel("Le Plateau est rempli !");
			lab.setHorizontalAlignment(JLabel.CENTER);
			lab.setBounds(0, 51 + (70 * monPlateau.getLigne()), 100 + (70 * monPlateau.getColonne()), 30);
			this.add(lab);
		}
		this.setVisible(true);
	}

	/*Méthode qui détermine quoi faire lors du clique de chaque boutons*/
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		/*Retourne au menu dès lors du clique du bouton Menu*/
		if (source == accesMenu) {
			this.dispose();
			new FenetreMenu();
		}
		/*Sauvegarde la partie en cours dans un fichier texte 
		 * que l'on pourra reprendre lorsque l'on aura charger (à partir du Menu)*/
		if (source == sauvegardePartie) {
			GslPlateau aSauvegarder = new GslPlateau(this.affichePlateau.get_Plateau());
			aSauvegarder.sauvegarde();
			System.out.println("Partie sauvegardée !");
		}
	}

	public void set_aficheP(Plateau PL) {
		dessinPlateau d = new dessinPlateau(PL);
		this.affichePlateau = d;
	}
}
