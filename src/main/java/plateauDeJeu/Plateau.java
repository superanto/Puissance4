package plateauDeJeu;

import gDC.GslStatistiques;

public class Plateau {

	private int ligne;
	private int colonne;
	private int victoire;
	private int tab[][];
	private int nbTour;
	private int typePartie;
	public static int BLANC = 1;
	public static int NOIR = 2;
	public static int VIDE = 0;

	public int[][] getTab() {
		return tab;
	}

	public int get_pion(int l, int c) {
		int a = this.tab[l][c];
		return a;
	}

	public void set_pion(int l, int c, int lacase) {
		this.tab[l][c] = lacase;
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

	public int getNbTour() {
		return this.nbTour;
	}

	public void setNbTour(int nbTour) {
		this.nbTour = nbTour;
	}

	public int getTypePartie() {
		return this.typePartie;
	}

	public void setTypePartie(int type) {
		this.typePartie = type;
	}

	/*renvoie la premierecasevide d'une colonne donnée*/
	public int premiereCaseVide(int col) {
		int i = 0;
		for (i = this.ligne - 1; i >= 0; i--) {
			if (this.tab[i][col] == 0)
				return i;
		}
		return this.ligne;
	}

	/*Pose un pion dans une colonne donnée et si une partie est gagnée,
	 * les statistiques sont incrémentés*/
	public void poserPion(int col) {
		/* Détermine la premiere case vide de la colonne où l'on joue
		 * et place le pion avec sa couleur en fonction du nbTour (donc du
		 * joueur)*/
		int l = premiereCaseVide(col);
		if (l != this.ligne) {
			if (nbTour % 2 == 0) {
				this.tab[l][col] = BLANC;
			} else {
				this.tab[l][col] = NOIR;
			}
			nbTour++;
			this.victoire = partieTerminee(l, col);
			if (this.victoire != 0) {
				/*Si la partie a detecté la victoire de l'un des deux joueurs*/
				System.out.println("Victoire du joueur: " + this.victoire + "\n");
				GslStatistiques aLire = new GslStatistiques();
				aLire.charge();
				if (this.typePartie == 1) {
					/*
					 * Si le type de partie à  modifier est une partie Homme
					 * contre Machine. On va alors incrémenter les statistiques des
					 * victoires ou des défaites et le nombre de parties
					 */
					if (this.victoire == 1) {
						/* Si le premier joueurs à gagné*/
						aLire.get_HM().set_total(aLire.get_HM().get_victoire() + 1, aLire.get_HM().get_defaite(),
								aLire.get_HM().get_nb_parties() + 1);
					} else if (this.victoire == 2) {
						/* Si le deuxime joueur à gagné*/
						aLire.get_HM().set_total(aLire.get_HM().get_victoire(), aLire.get_HM().get_defaite() + 1,
								aLire.get_HM().get_nb_parties() + 1);
					} else {
						/*Si la partie est nulle*/
						aLire.get_HM().set_total(aLire.get_HM().get_victoire(), aLire.get_HM().get_defaite(),
								aLire.get_HM().get_nb_parties() + 1);
					}
				} else if (this.typePartie == 2) {

					/* Si le type de partie à modifier est une partie Machine 
					 * contre Homme.*/

					if (this.victoire == 1) {
						/*Si le premier joueurs à gagné*/
						aLire.get_MH().set_total(aLire.get_MH().get_victoire() + 1, aLire.get_MH().get_defaite(),
								aLire.get_MH().get_nb_parties() + 1);
					} else if (this.victoire == 2) {
						/*Si le deuxième joueur à gagné*/
						aLire.get_MH().set_total(aLire.get_MH().get_victoire(), aLire.get_MH().get_defaite() + 1,
								aLire.get_MH().get_nb_parties() + 1);
					} else {
						/* Si la partie est nulle*/
						aLire.get_MH().set_total(aLire.get_MH().get_victoire(), aLire.get_MH().get_defaite(),
								aLire.get_MH().get_nb_parties() + 1);
					}
				} else if (this.typePartie == 3) {

					/* Si le type de partie à modifier est une partie Homme
					 * contre Homme*/
					if (this.victoire == 1) {
						/*Si le premier joueurs à gagné*/
						aLire.get_HH().set_total(aLire.get_HH().get_victoire() + 1, aLire.get_HH().get_defaite(),
								aLire.get_HH().get_nb_parties() + 1);
					} else if (this.victoire == 2) {
						/*Si le deuxième joueur à gagné*/
						aLire.get_HH().set_total(aLire.get_HH().get_victoire(), aLire.get_HH().get_defaite() + 1,
								aLire.get_HH().get_nb_parties() + 1);
					} else {
						/* Si la partie est nulle*/
						aLire.get_HH().set_total(aLire.get_HH().get_victoire(), aLire.get_HH().get_defaite(),
								aLire.get_HH().get_nb_parties() + 1);
					}
				} else {
					System.out.println("Problème durant l'initialisation des Stats");
				}
				aLire.sauvegarde();
			}
		}
	}

	public int partieTerminee(int l, int c) {
		if ((l < this.ligne) && (c < this.colonne)) {
			int couleur = this.tab[l][c];
			int win = 0;
			int i = l;/* on remplace car on a besoin de variable pas de cst*/
			int j = c;

			while (couleur == tab[i][j]) {
				/* test si il y 4 pions alignés en horizontal vers la gauche */
				win += 1;
				j--;
				if (j < 0) {
					j = 0;
					break;
				} /* pour ne pas depasser le tableau*/
			}
			if (win >= 4)
				return couleur;
			else {
				win = 0;
				if (this.tab[i][j] != couleur)
					j++;/* pour revenir sur le dernier pion de même couleur*/
			}

			while (couleur == tab[i][j]) {
				/* test s'il y a 4 pions alignés en horizontal vers la droite*/
				win += 1;
				j += 1;
				if (j > this.colonne - 1) {
					j = this.colonne - 1;
					break;
				} 
			}
			if (win >= 4)
				return couleur;
			else {
				win = 0;
				j = c;
			} /* on reset la variable j pour faire les tests verticaux*/

			while (couleur == tab[i][j]) {
				/* test s'il y a 4 pions alignés en verticals vers le bas*/

				win += 1;
				i++;
				if (i > this.ligne - 1) {
					i = this.ligne - 1;
					break;
				} 
			}
			if (win >= 4)
				return couleur;
			else {
				win = 0;
				if (this.tab[i][j] != couleur)
					i--;
			}

			while (couleur == tab[i][j]) {
				/* test s'il y a 4 pions alignées en verticale vers le haut*/
				win += 1;
				i -= 1;
				if (i < 0) {
					i = 0;
					break;
				}
			}
			if (win >= 4)
				return couleur;
			else {
				win = 0;
				i = l;
			} /*on reset la variable i pour faire les tests en diagonals*/

			while (couleur == this.tab[i][j]) {
				/* test s'il y a 4 pions alignées en diagonal vers le Nord-Ouest*/
				win += 1;
				i--;
				j--;
				if (i < 0)
					break;
				if (j < 0)
					break;
			}
			if (win >= 4)
				return couleur;
			else {
				win = 0;
				if (i == -1 || j == -1 || i == this.ligne || j == this.colonne) {
					i++;
					j++;
				} else if (this.tab[i][j] != couleur) {
					i++;
					j++;
				} 
			}

			while (couleur == this.tab[i][j]) {
				/* test s'il y a 4 pions alignées en diagonal vers le Sud-Est*/
				win += 1;
				i++;
				j++;
				if (i > this.ligne - 1) {
					i = this.ligne - 1;
					if (j > this.colonne - 1)
						j = this.colonne - 1;
					break;
				}
				if (j > this.colonne - 1) {
					j = this.colonne - 1;
					break;
				}
			}
			if (win >= 4)
				return couleur;
			else {
				win = 0;
				i = l;
				j = c;/* on reset pour faire les tests de l'autre diagonals*/
			}

			while (couleur == this.tab[i][j]) {
				/* test s'il y a 4 pions alignées en diagonal vers le Sud-Ouest*/
				win += 1;
				i++;
				j--;
				if (i > this.ligne - 1)
					break;
				if (j < 0)
					break;
			}
			if (win >= 4)
				return couleur;
			else {
				win = 0;
				/*test pour voir si l'on ne sort pas du tableau*/
				if (i == -1 || j == -1 || i == this.ligne || j == this.colonne) {
					i--;
					j++;
				} else if (this.tab[i][j] != couleur) {
					i--;
					j++;
				}
			}

			while (couleur == this.tab[i][j]) {
				/* test s'il y a 4 pions alignées en diagonal vers le Nord-Est*/
				win += 1;
				i--;
				j++;
				if (i < 0) {
					i = 0;
					if (j > this.colonne - 1)
						j = this.colonne - 1;
					break;
				}
				if (j > this.colonne - 1) {
					j = this.colonne - 1;
					break;
				}
			}
			if (win >= 4)
				return couleur;
		
			if (this.nbTour < this.ligne * this.colonne)
				return 0;
			return 3;
		}
		return -1;
	}
}