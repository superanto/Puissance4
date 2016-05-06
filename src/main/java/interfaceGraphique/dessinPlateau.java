package interfaceGraphique;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import plateauDeJeu.Plateau;

public class dessinPlateau extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int nbLignes;
	private int nbColonnes;
	// private int joueur;
	private Plateau monPlateau;

	public dessinPlateau(Plateau monPlateau2) {
		this.nbLignes = monPlateau2.getLigne();
		this.nbColonnes = monPlateau2.getColonne();
		// this.joueur = 1;
		this.monPlateau = monPlateau2;
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x, i;
				x = e.getX();
				for (i = 0; i < nbColonnes; i++) {
					if ((x > (i * 70)) && (x < (i + 1) * 70)) {
						monPlateau.poserPion(i);
						repaint();
					}
				}
			}
		});
	}

	public Plateau get_Plateau() {
		return this.monPlateau;
	}

	public void paintComponent(Graphics g) {
		int i, j;
		super.paintComponent(g);
		for (i = 0; i < nbLignes + 1; i++) {
			g.drawLine(0, i * 70, nbColonnes * 70, i * 70);
		}
		for (j = 1; j < nbColonnes + 1; j++) {
			g.drawLine(j * 70, 0, j * 70, nbLignes * 70);
		}
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
