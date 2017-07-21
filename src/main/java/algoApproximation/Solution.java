package algoApproximation;

import java.util.ArrayList;

import plateauDeJeu.Plateau;

public class Solution {
	private ArrayList<Noeud> listeSolutions;

	public Solution() {
		this.listeSolutions = null;
	}

	public Solution(Plateau P) {
		/* 1 er Noeud = Plateau courant, les autres étant les fils
		 *  c.a.d les plateau possible contenant un pion de plus*/
		int i, k, m;
		int pt = -10;
		this.listeSolutions = new ArrayList<Noeud>();
		this.listeSolutions.add(new Noeud(P, 0));
		for (i = 0; i < P.getColonne(); i++) {
			int l = P.premiereCaseVide(i);
			pt = l;
			if (pt + 1 >= P.getLigne())
				pt = P.getLigne() - 1;
			this.listeSolutions.add(new Noeud(new Plateau(P.getLigne(), P.getColonne()), -1));

			for (k = 0; k < P.getLigne(); k++) {
				for (m = 0; m < P.getColonne(); m++) {
					this.listeSolutions.get(i + 1).get_plateau().set_pion(k, m,

							this.listeSolutions.get(0).get_plateau().get_pion(k, m));
				}
			}
			if ((l < P.getLigne()) && (P.partieTerminee(pt, i) >= 0)) {
				this.listeSolutions.get(i + 1).set_colonneJouee(i);
				if (P.getNbTour() % 2 == 0) {
					this.listeSolutions.get(i + 1).get_plateau().set_pion(l, i, 1);
				} else {
					this.listeSolutions.get(i + 1).get_plateau().set_pion(l, i, 2);
				}
				this.listeSolutions.get(i + 1).get_plateau().setNbTour(P.getNbTour() + 1);
				this.listeSolutions.get(i + 1).get_plateau().setTypePartie(P.getTypePartie());
			}
		}
	}

	public ArrayList<Noeud> get_listeSol() {
		return this.listeSolutions;
	}

	public Plateau get_Plat(int i) {
		return this.listeSolutions.get(i).get_plateau();
	}

	public void set_listeSol(ArrayList<Noeud> NewListeSol) {
		if (this.listeSolutions.isEmpty() != true)
			this.listeSolutions.clear();
		this.listeSolutions = null;
		this.listeSolutions = NewListeSol;
	}

	public int ColonneAJouer() {
		/*
		 * Valeur regardant tous les poids des noeuds de la liste et renvoie la
		 * colonne Ã  jouer du noeud comprenant le poids le plus fort
		 */
		int i;
		int max = this.listeSolutions.get(1).get_poids();
		int valmax = 1;
		for (i = 2; i < this.listeSolutions.get(0).get_plateau().getColonne(); i++) {
			if (max < this.listeSolutions.get(i).get_poids()) {
				valmax = i;
				max = this.listeSolutions.get(i).get_poids();
			}
		}
		return this.listeSolutions.get(valmax).get_colonne();
	}
	public void TriListe() {
		/*
		 * Organise la liste des noeuds comme ceci: Le premier noeud Ã©tant
		 * l'état du Plateau en temps réel, cette méthode va placer le noeudfils
		 * avec le poids le plus fort en deuxième position
		 */
		int i, k;
		Noeud temp = null;
		int j = this.listeSolutions.size();
		int valmin = 0;
		int min = this.listeSolutions.get(1).get_poids();

		for (k = 1; k < this.listeSolutions.size(); k++) {
			for (i = 1; i < j; i++) {
				if (min < this.listeSolutions.get(i).get_poids()) {
					min = this.listeSolutions.get(i).get_poids();
					valmin = i;
				}
			}
			temp = this.listeSolutions.get(valmin);
			this.listeSolutions.remove(valmin);
			this.listeSolutions.add(temp);
			j--;
		}
	}

	public int CoupDuDebut() {

		/*
		 * Méthode qui joue les 2 premiers coups de la partie car temps de
		 * calcul trop long et de plus, les deux meilleurs coups pour les deux
		 * premiers coups seront toujours ceux jouer le plus au milieu possible
		 */

		if (this.listeSolutions.get(0).get_plateau().getNbTour() <= 2) {
			return this.listeSolutions.get(0).get_plateau().getColonne() / 2;
		} else
			return -1;
	}

	public int CaseGagnanteDirect() {
		/*
		 * Cette méthode va regarder en fonction de chaque noeud de la liste, si
		 * le dernier pion posÃ© donne une victoire, alors on renvoie la case
		 * correspondant Ã  ce pion posÃ© directement
		 */
		int a = -1;
		int i;
		int pt = -10;
		for (i = 1; i <= this.listeSolutions.get(0).get_plateau().getColonne(); i++) {
			if (this.listeSolutions.get(i).get_colonne() != -1) {
				a = this.listeSolutions.get(i).get_colonne();
				pt = this.listeSolutions.get(i).get_plateau().premiereCaseVide(a)+1;
				if (pt >= this.listeSolutions.get(0).get_plateau().getLigne())
					pt = 0;
				if (this.listeSolutions.get(i).get_plateau().partieTerminee(pt, a) > 0) {
					return i;
				}
			}
		}
		return -1;
	}

	public int CaseBloquanteDirect() {
		/*
		 * Méthode qui va changer valeur de la case à poser, test si le joueur
		 * adversaire gagne, si c'est le cas, alors on renvoie la colonne car
		 * c'est cette case qu'il faut jouer
		 */
		int temp = -1;
		int laValeurCour = -2;
		int CS = -10;
		int i;
		for (i = 1; i < this.listeSolutions.get(0).get_plateau().getColonne() + 1; i++) {
			if (this.listeSolutions.get(i).get_colonne() != -1) {
				CS = this.listeSolutions.get(i).get_plateau().premiereCaseVide(i - 1);
				if (CS >= this.listeSolutions.get(i).get_plateau().getLigne())
					CS = -1;
				temp = this.listeSolutions.get(i).get_plateau().get_pion(CS + 1, i - 1);

				if (temp == 1) {
					temp = 2;
				} else if (temp == 2) {
					temp = 1;
				}
				this.listeSolutions.get(i).get_plateau().set_pion(CS + 1, i - 1, temp);

				if (this.listeSolutions.get(i).get_plateau().partieTerminee(CS + 1, i - 1) > 0) {
					laValeurCour = i;
				}

				if (temp == 1)
					temp = 2;
				else if (temp == 2)
					temp = 1;
				this.listeSolutions.get(i).get_plateau().set_pion(CS + 1, i - 1, temp);

				if (laValeurCour > 0)
					return laValeurCour;
			}
		}
		return -1;
	}
}
