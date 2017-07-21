package interfaceGraphique;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gDC.GslPlateau;
import gDC.GslStatistiques;
import plateauDeJeu.Plateau;

public class FenetreMenu extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel panel1;
	private JButton lancerPartie;
	private JButton chargerPartie;
	private JButton voirStatistiques;
	private JCheckBox HvsM;
	private JCheckBox MvsH;
	private JCheckBox HvsH;
	private JLabel taillePlateau;
	private JTextField lignePlateau;
	private JTextField colonnePlateau;

	public FenetreMenu() {
		panel1 = new JPanel();
		lancerPartie = new JButton("Lancer la partie");
		lancerPartie.setPreferredSize(new Dimension(150, 120));
		chargerPartie = new JButton("Charger la partie");
		voirStatistiques = new JButton("Statistiques");
		HvsM = new JCheckBox("Homme VS Machine");
		MvsH = new JCheckBox("Machine VS Homme");
		HvsH = new JCheckBox("Homme VS Homme");
		taillePlateau = new JLabel("Taille du plateau (Lignes,Colonnes)");
		lignePlateau = new JTextField();
		colonnePlateau = new JTextField();

		/* Affichage de la fenètre (JButton, JCheckBox, JTextField)*/
		this.setTitle("Puissance 4");
		this.setSize(520, 260);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		panel1.setLayout(null);
		panel1.add(taillePlateau);
		taillePlateau.setBounds(100, 50, 260, 30);
		lignePlateau.setColumns(2);
		panel1.add(lignePlateau);
		lignePlateau.setBounds(370, 50, 30, 30);
		panel1.add(colonnePlateau);
		colonnePlateau.setBounds(410, 50, 30, 30);
		panel1.add(HvsM);
		HvsM.setBounds(10, 90, 170, 30);
		panel1.add(MvsH);
		MvsH.setBounds(180, 90, 170, 30);
		panel1.add(HvsH);
		HvsH.setBounds(350, 90, 170, 30);
		panel1.add(lancerPartie);
		lancerPartie.setBounds(10, 130, 150, 30);
		lancerPartie.addActionListener(this);
		panel1.add(chargerPartie);
		chargerPartie.setBounds(180, 130, 160, 30);
		chargerPartie.addActionListener(this);
		panel1.add(voirStatistiques);
		voirStatistiques.addActionListener(this);
		voirStatistiques.setBounds(360, 130, 150, 30);
		this.add(panel1);
		this.setVisible(true);
	}

	/*Méthode qui détermine quoi faire lors du clique de chaque boutons*/
	public void actionPerformed(ActionEvent e) {
		int i = 0, j = 0;
		Object source = e.getSource();
		/*On lance la partie avec le type de partie que l'on a choisi à  l'aide des JCheckBox*/
		if (source == lancerPartie) {
			if(!lignePlateau.getText().isEmpty() || !colonnePlateau.getText().isEmpty())
			{
			i = Integer.parseInt(lignePlateau.getText());
			j = Integer.parseInt(colonnePlateau.getText());
			}
			/* 3 conditions dans le if pour éviter de lancer la partie en ayant séléctionner 
			 * 2 ou 3 types de parties (ce qui est cens être impossible) et un plateau
			 * minimum de 4x4 et un plateau maximum de 8x8*/
			if (HvsM.isSelected() && !MvsH.isSelected() && !HvsH.isSelected()
				&& i > 3 && j > 3 && i < 9 && j < 9) { 
				this.dispose();
				Plateau temp = new Plateau(i, j);
				temp.setTypePartie(1);
				new FenetreJeu(temp);
			}
			if (MvsH.isSelected() && !HvsM.isSelected() && !HvsH.isSelected()
				&& i > 3 && j > 3 && i < 9 && j < 9) {
				this.dispose();
				Plateau temp = new Plateau(i, j);
				temp.setTypePartie(2);
				new FenetreJeu(temp);
			}
			if (HvsH.isSelected() && !HvsM.isSelected() && !MvsH.isSelected()
				&& i > 3 && j > 3 && i < 9 && j < 9) {
				this.dispose();
				Plateau temp = new Plateau(i, j);
				temp.setTypePartie(3);
				new FenetreJeu(temp);
			}
		}
		/*Charge une partie qui à  été sauvegardé dans un fichier texte et lance la partie*/
		if (source == chargerPartie) {
			this.dispose();
			Plateau temp = new Plateau();
			GslPlateau aCharger = new GslPlateau(temp);
			aCharger.charge();
			new FenetreJeu(aCharger.getPlateau());
			System.out.println("Partie Chargée !");
		}
		/*Charge le fichier texte afin de pouvoir lire et afficher les statistiques dans la fenetre de Statistiques*/
		if (source == voirStatistiques) {
			GslStatistiques aLire = new GslStatistiques();
			aLire.charge();
			new FenetreStatistiques(aLire.get_HM(), aLire.get_MH(), aLire.get_HH());
		}
	}
}
