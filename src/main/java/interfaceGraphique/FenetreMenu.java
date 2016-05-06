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
import plateauDeJeu.Plateau;

public class FenetreMenu extends JFrame implements ActionListener {
	/**
	 * 
	 */
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

	public void actionPerformed(ActionEvent e) {
		int i, j;
		Object source = e.getSource();
		if (source == lancerPartie) {
			if (HvsM.isSelected()) {
				this.dispose();
				i = Integer.parseInt(lignePlateau.getText());
				j = Integer.parseInt(colonnePlateau.getText());
				Plateau temp = new Plateau(i, j);
				temp.setTypePartie(1);
				new FenetreJeu(temp);
			}
			if (MvsH.isSelected()) {
				this.dispose();
				i = Integer.parseInt(lignePlateau.getText());
				j = Integer.parseInt(colonnePlateau.getText());
				Plateau temp = new Plateau(i, j);
				temp.setTypePartie(2);
				new FenetreJeu(temp);
			}
			if (HvsH.isSelected()) {
				this.dispose();
				i = Integer.parseInt(lignePlateau.getText());
				j = Integer.parseInt(colonnePlateau.getText());
				Plateau temp = new Plateau(i, j);
				temp.setTypePartie(3);
				new FenetreJeu(temp);
			}
		}
		if (source == chargerPartie) {
			Plateau temp = new Plateau();
			GslPlateau aCharger = new GslPlateau(temp);
			aCharger.charge();
			new FenetreJeu(aCharger.getPlateau());
		}
		if (source == voirStatistiques) {
			new FenetreStatistiques();
		}
	}

	/*
	 * public static void main(String[] args) { new FenetreMenu(); }
	 */
}
