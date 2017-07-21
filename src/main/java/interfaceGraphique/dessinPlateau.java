package interfaceGraphique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import algoApproximation.Solution;
import plateauDeJeu.Plateau;

public class dessinPlateau extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private int nbLignes;
	private int nbColonnes;
	private Plateau monPlateau;

	public dessinPlateau(Plateau monPlateau2) {
		this.nbLignes = monPlateau2.getLigne();
		this.nbColonnes = monPlateau2.getColonne();
		this.monPlateau = monPlateau2;

		int leTour;
		int k = 0;
		int max = -10;
		int valmax = -10;
		double h = 0;
		if (monPlateau.getVictoire() == 0 && monPlateau.getTypePartie() == 2 && monPlateau.getNbTour() % 2 == 0) {
			Solution maSoluce = null;
			maSoluce = new Solution(monPlateau);
			if (maSoluce.CoupDuDebut() >= 0)
				monPlateau.poserPion(maSoluce.CoupDuDebut());
			else if (maSoluce.CaseGagnanteDirect() >= 0)
				monPlateau.poserPion(maSoluce.CaseGagnanteDirect() - 1);
			else if (maSoluce.CaseBloquanteDirect() >= 0)
				monPlateau.poserPion(maSoluce.CaseBloquanteDirect() - 1);
			else {
				while(k == 0)
				{
				h = Math.random() * monPlateau.getColonne();
				if(monPlateau.premiereCaseVide((int)Math.round(h))!= monPlateau.getLigne() )k++;
				}
				valmax = (((int) h) % (maSoluce.get_listeSol().size() - 1)) + 1;
				max = maSoluce.get_listeSol().get(valmax).get_poids();
				for (leTour = 1; leTour < maSoluce.get_listeSol().size(); leTour++) {
					maSoluce.get_listeSol().get(leTour).AlgoMinimax(2, 1);
					if ((max < maSoluce.get_listeSol().get(leTour).get_poids()) && (maSoluce.get_listeSol().get(0)
							.get_plateau().premiereCaseVide(leTour - 1) < monPlateau.getLigne())) {
						max = maSoluce.get_listeSol().get(leTour).get_poids();
						valmax = leTour;
					}
				}
				for (leTour = 1; leTour < maSoluce.get_listeSol().size(); leTour++) {

					maSoluce.get_listeSol().get(leTour).SupprimeElementListe();

				}
				monPlateau.poserPion(valmax - 1);
			}
			maSoluce = null;
		}

		this.addMouseListener(new MouseAdapter() {

			/*Clique servant à l'ajout de pion sur le plateau de jeu*/
			public void mouseClicked(MouseEvent e) {
				int x, i;
				int leTour;
				int max = -10;
				int valmax = -10;
				double h = 0;
				int k = 0;
				x = e.getX();
				Solution maSoluce = null;
				for (i = 0; i < nbColonnes; i++) {
					if ((x > (i * 70)) && (x < (i + 1) * 70)) {
						if (monPlateau.getVictoire() == 0) {
							if (monPlateau.getTypePartie() == 3)
								monPlateau.poserPion(i);
							else {
								monPlateau.poserPion(i);

								if (monPlateau.getVictoire() == 0 && monPlateau.getNbTour() % 2 == 0
										&& monPlateau.getTypePartie() == 2) {
									maSoluce = new Solution(monPlateau);
									if (maSoluce.CoupDuDebut() >= 0)
										monPlateau.poserPion(maSoluce.CoupDuDebut());
									else if (maSoluce.CaseGagnanteDirect() >= 0)
										monPlateau.poserPion(maSoluce.CaseGagnanteDirect() - 1);
									else if (maSoluce.CaseBloquanteDirect() >= 0)
										monPlateau.poserPion(maSoluce.CaseBloquanteDirect() - 1);
									else {
										while(k == 0)
										{
										h = Math.random() * monPlateau.getColonne();
										if(monPlateau.premiereCaseVide((int)h)!= monPlateau.getLigne() )k++;
										}
										valmax = (((int) h) % (maSoluce.get_listeSol().size() - 1)) + 1;
										max = maSoluce.get_listeSol().get(valmax).get_poids();
										for (leTour = 1; leTour < maSoluce.get_listeSol().size(); leTour++) {

											maSoluce.get_listeSol().get(leTour).AlgoMinimax(2, 1);
											if ((max < maSoluce.get_listeSol().get(leTour).get_poids())
													&& (maSoluce.get_listeSol().get(0).get_plateau()
															.premiereCaseVide(leTour - 1) < monPlateau.getLigne())) {
												max = maSoluce.get_listeSol().get(leTour).get_poids();
												valmax = leTour;
											}
										}
										for (leTour = 1; leTour < maSoluce.get_listeSol().size(); leTour++) {

											maSoluce.get_listeSol().get(leTour).SupprimeElementListe();
										}
										monPlateau.poserPion(valmax - 1);
									}
									maSoluce = null;
								}

								if (monPlateau.getVictoire() == 0 && monPlateau.getNbTour() % 2 == 1
										&& monPlateau.getTypePartie() == 1) {
									maSoluce = new Solution(monPlateau);
									if (maSoluce.CoupDuDebut() >= 0)
										monPlateau.poserPion(maSoluce.CoupDuDebut());
									else if (maSoluce.CaseGagnanteDirect() >= 0)
										monPlateau.poserPion(maSoluce.CaseGagnanteDirect() - 1);
									else if (maSoluce.CaseBloquanteDirect() >= 0)
										monPlateau.poserPion(maSoluce.CaseBloquanteDirect() - 1);
									else {
										while(k == 0)
										{
										h = Math.random() * monPlateau.getColonne();
										if(monPlateau.premiereCaseVide((int)h)!= monPlateau.getLigne() )k++;
										}
										valmax = (((int) h) % (maSoluce.get_listeSol().size() - 1)) + 1;
										max = maSoluce.get_listeSol().get(valmax).get_poids();
										for (leTour = 1; leTour < maSoluce.get_listeSol().size(); leTour++) {

											maSoluce.get_listeSol().get(leTour).AlgoMinimax(2, 1);
											if ((max < maSoluce.get_listeSol().get(leTour).get_poids())
													&& (maSoluce.get_listeSol().get(0).get_plateau()
															.premiereCaseVide(leTour - 1) < monPlateau.getLigne())) {
												max = maSoluce.get_listeSol().get(leTour).get_poids();
												valmax = leTour;
											}
										}
										for (leTour = 1; leTour < maSoluce.get_listeSol().size(); leTour++) {

											maSoluce.get_listeSol().get(leTour).SupprimeElementListe();
										}
										monPlateau.poserPion(valmax - 1);
									}
									maSoluce = null;
								}
							}
							/* Dès lors où l'on a une victoire d'un des deux joueurs, 
							 * on réactualise la fenetreJeu afin de pouvoir ajouter un
							 *  JLabel indiquant qui à remporter la partie*/

							if (monPlateau.getVictoire() != 0) {
								JFrame fenetre = (JFrame) SwingUtilities.windowForComponent(getParent());
								fenetre.dispose();
								new FenetreJeu(monPlateau);
							}
							/* Actualise le plateau à chaque fois que l'on pose un pion
							 *  pour l'afficher dans la méthode paintComponent*/
							repaint();
						}
					}
				}
			}
		});
	}

	public Plateau get_Plateau() {
		return this.monPlateau;
	}

	/* Méthode qui dessine le plateau de jeu en fonction du nombre
	 *  de lignes et de colonnes et affiche les pions au fur et Ã 
	 *   mesure qu'il sont ajoutés sur le plateau de jeu*/
	public void paintComponent(Graphics g) {
		/*Dessin des cases du plateau de jeu*/
		int i, j;
		super.paintComponent(g);
		for (i = 0; i < nbLignes + 1; i++) {
			g.drawLine(0, i * 70, nbColonnes * 70, i * 70);
		}
		for (j = 0; j < nbColonnes + 1; j++) {
			g.drawLine(j * 70, 0, j * 70, nbLignes * 70);
		}

		/*Dessin des pions et de leur couleurs*/
		int l, c;
		int tab[][] = new int[nbLignes][nbColonnes];
		tab = monPlateau.getTab();
		for (l = 0; l < nbLignes; l++) {
			for (c = 0; c < nbColonnes; c++) {
				if (tab[l][c] == 2) {
					g.setColor(Color.RED);
					g.fillOval((c * 70) + 5, (l * 70) + 5, 60, 60);
					g.setColor(Color.BLACK);
					g.drawOval((c * 70) + 5, (l * 70) + 5, 60, 60);
				}
				if (tab[l][c] == 1) {
					g.setColor(Color.YELLOW);
					g.fillOval((c * 70) + 5, (l * 70) + 5, 60, 60);
					g.setColor(Color.BLACK);
					g.drawOval((c * 70) + 5, (l * 70) + 5, 60, 60);
				}
			}
		}
	}
}
