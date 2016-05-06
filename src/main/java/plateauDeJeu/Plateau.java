package plateauDeJeu;

public class Plateau {

	private int ligne;
	private int colonne;
	private int victoire;
	private int tab[][];
	private int nbTour;
	private int typePartie;
	public static int BLANC = 1;
	public static int NOIR = 2;

	public int[][] getTab() {
		return tab;
	}

	public void setTab(int[][] tab) {
		this.tab = tab;
	}

	public Plateau() {

		this.ligne = 0;
		this.colonne = 0;
		this.victoire = 0;
		this.nbTour = 0;
		this.typePartie = 0;
	}

	public Plateau(int l, int c) {

		this.ligne = l;
		this.colonne = c;
		this.victoire = 0;
		this.nbTour = 0;
		this.typePartie = 0;
		this.tab = new int[l][c];
		int i = 0;
		int j = 0;
		for (i = 0; i < l; i++) {
			for (j = 0; j < c; j++) {
				this.tab[i][j] = 0;
			}
		}
	}

	public int getLigne() {
		return ligne;
	}

	public void setLigne(int ligne) {
		this.ligne = ligne;
	}

	public int getColonne() {
		return colonne;
	}

	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

	public int getVictoire() {
		return victoire;
	}

	public void setVictoire(int victoire) {
		this.victoire = victoire;
	}

	public int getTypePartie() {
		return this.typePartie;
	}

	public void setTypePartie(int type) {
		this.typePartie = type;
	}

	public int premiereCaseVide(int col) {

		int i = 0;
		for (i = this.ligne - 1; i >= 0; i--) {
			// regarde si la premiere case da la colonne est vide sinon regarde
			// celle d'au dessus
			if (this.tab[i][col] == 0)
				return i;
		}
		return i;

	}

	public void poserPion(int col) {

		int l = premiereCaseVide(col);
		// manque une prise en compte de la couleur du pion
		// prendre en compte le nbre de coup deja jou�
		if (nbTour % 2 == 0) {
			this.tab[l][col] = BLANC;
		} else {
			this.tab[l][col] = NOIR;
		}
		nbTour++;
		// this.tab[l][col] = 1 ;
	}

	public boolean partieTerminee(int l, int c) {// j'ai chang� la fonction et
		int couleur = this.tab[l][c]; // mis un boolean pour l'instant � voir
		int win = 0;
		int i = l;// on remplace car on a besoin de variable pas de cst
		int j = c;

		while (couleur == tab[i][j]) {// test si il y 4 pions align� en
										// descendant
			win += 1;
			j--;
			if (j < 0) {
				j = 0;
				break;
			} // pour pas depasser tableau
		}
		if (win == 4)
			return true;
		else {
			win = 0;
			j++;
		}

		while (couleur == tab[i][j]) {// test s'il y a 4 pions align�s en
										// montant
			win += 1;
			j += 1;
			if (j > this.colonne) {
				j = this.colonne;
				break;
			} // pour pas depasser tableau
		}
		if (win == 4)
			return true;
		else {
			win = 0;
			i = l;
			j = c;
		} // on reset pour faire tests horizontals

		while (couleur == tab[i][j]) {// test s'il y a 4 pions align�s vers la
										// droite
			win += 1;
			i++;
			if (i > this.ligne) {
				i = this.ligne;
				break;
			} // pour pas depasser tableau
		}
		if (win == 4)
			return true;
		else {
			win = 0;
			i--;
		}

		while (couleur == tab[i][j]) {
			win += 1;
			i -= 1;
			if (i < 0) {
				i = 0;
				break;
			} // pour pas depasser tableau
		}
		if (win == 4)
			return true;
		else {
			win = 0;
			i = l;
			j = c;
		} // on rest pour faire tests diagonals

		while (couleur == this.tab[i][j]) {// on test en diagonal S.O
			win += 1;
			i--;
			j--;
			if (i < 0) {
				i = 0;
				if (j < 0)
					j = 0;
				break;
			}
			if (j < 0) {
				j = 0;
				break;
			}
		}
		if (win == 4)
			return true;
		else {
			win = 0;
			i++;
			j++;
		}

		while (couleur == this.tab[i][j]) {// on test diagonal N.E
			win += 1;
			i++;
			j++;
			if (i > this.ligne) {
				i = this.ligne;
				if (j > this.colonne)
					j = this.colonne;
				break;
			}
			if (j > this.colonne) {
				j = this.colonne;
				break;
			}
		}
		if (win == 4)
			return true;
		else {
			win = 0;
			i = l;
			j = c;
		}

		while (couleur == this.tab[i][j]) {// on test diagonal N.O
			win += 1;
			i++;
			j--;
			if (i > this.ligne) {
				i = this.ligne;
				if (j < 0)
					j = 0;
				break;
			}
			if (j < 0) {
				j = 0;
				break;
			}
		}
		if (win == 4)
			return true;
		else {
			win = 0;
			i--;
			j++;
		}

		while (couleur == this.tab[i][j]) {// on test diagonal S.E
			win += 1;
			i--;
			j++;
			if (i < 0) {
				i = 0;
				if (j > this.colonne)
					j = this.colonne;
				break;
			}
			if (j > this.colonne) {
				j = this.colonne;
				break;
			}
		}
		if (win == 4)
			return true;
		return false;
	}
}
