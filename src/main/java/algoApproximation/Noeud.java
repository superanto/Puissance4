package algoApproximation;

import java.util.ArrayList;
import plateauDeJeu.Plateau;

public class Noeud {

	private int colonneJouee;
	private int poids;
	private Plateau plateauDeCeNoeud;
	private ArrayList<Noeud> listeArbreFils;

	public Noeud() {
		this.colonneJouee = 0;
		this.poids = 0;
		this.plateauDeCeNoeud = null;
		this.listeArbreFils = null;
	}

	public Noeud(Plateau P, int colonneAJouee) {
		this.colonneJouee = colonneAJouee;
		this.poids = 0;
		this.plateauDeCeNoeud = P;
		this.listeArbreFils = null;
	}

	public int get_colonne() {
		return this.colonneJouee;
	}

	public void set_colonneJouee(int c) {
		this.colonneJouee = c;
	}

	public int get_poids() {
		return this.poids;
	}

	public Plateau get_plateau() {
		return this.plateauDeCeNoeud;
	}

	public ArrayList<Noeud> get_ArbFils() {
		return this.listeArbreFils;
	}

	public void AjoutElementListe(int i) {
		/*
		 * Permet de rajouter un Noeud à l'indice i dans la Liste des fils
		 */
		int k, m;
		int l = this.plateauDeCeNoeud.premiereCaseVide(i);
		int pt = l;
		if (pt + 1 >= this.plateauDeCeNoeud.getLigne())
			pt = this.plateauDeCeNoeud.getLigne() - 1;
		this.listeArbreFils
				.add(new Noeud(new Plateau(this.plateauDeCeNoeud.getLigne(), this.plateauDeCeNoeud.getColonne()), -1));
		for (k = 0; k < this.plateauDeCeNoeud.getLigne(); k++) {
			for (m = 0; m < this.plateauDeCeNoeud.getColonne(); m++) {
				this.listeArbreFils.get(i).get_plateau().set_pion(k, m, this.plateauDeCeNoeud.get_pion(k, m));
			}
		}
		if ((l < this.plateauDeCeNoeud.getLigne()) && (this.plateauDeCeNoeud.partieTerminee(pt, i) >= 0)) {
			this.listeArbreFils.get(i).set_colonneJouee(i);
			if (this.plateauDeCeNoeud.getNbTour() % 2 == 0) {
				this.listeArbreFils.get(i).get_plateau().set_pion(l, i, 1);
			} else {
				this.listeArbreFils.get(i).get_plateau().set_pion(l, i, 2);
			}
		}
	}

	public void SupprimeElementListe() {
		/*
		 * Descends dans l'arbre en profondeur, supprime la liste de noeud ,
		 * remonte et décale au noeud voisin
		 */
		int i;
		while (this.listeArbreFils != null) {
			if (this.listeArbreFils.get(0).get_ArbFils() != null) {
				for (i = 0; i < this.listeArbreFils.size(); i++) {
					this.listeArbreFils.get(i).SupprimeElementListe();
				}
			} else {
				this.listeArbreFils.clear();
				this.listeArbreFils = null;
			}
		}
	}

	public int DetermineMeilleurCoup() {
		/*
		 * Parcours les poids
		 */
		int i;
		if (this.listeArbreFils != null) {
			int max = this.listeArbreFils.get(0).get_poids();
			/*int valmax = 0;*/
			for (i = 1; i < this.listeArbreFils.size(); i++) {
				max += this.listeArbreFils.get(i).get_poids();
			}
			return max;
		}
		return 1;
	}

	public void AlgoMinimax(int profondeur, int testDeCalcul) {
		/*
		 * En fonction d'une profondeur donnée, cette méthode va remplir pour
		 * chaque Noeud de la liste, une autre liste noeud avec encore un pion
		 * de plus, et il repete ca pour tous les plateaux à la profondeur
		 * donnée
		 */
		if (profondeur > 0) {
			int i;
			this.listeArbreFils = new ArrayList<Noeud>();
			for (i = 0; i < this.plateauDeCeNoeud.getColonne(); i++) {
				this.AjoutElementListe(i);

				this.listeArbreFils.get(i).get_plateau().setNbTour(this.plateauDeCeNoeud.getNbTour() + 1);
				this.listeArbreFils.get(i).get_plateau().setTypePartie(this.plateauDeCeNoeud.getTypePartie());
				this.listeArbreFils.get(i).AlgoMinimax(profondeur - 1, testDeCalcul ^ 1);
			}
			if ((testDeCalcul == 1) && (this.colonneJouee >= 0)) {
				this.AlgoRegleDeBase();
			}
		}
	}

	public void AlphaBeta() {
		/*
		 * Première partie de l'algorithme AlphaBeta: va supprimer un noeud fils
		 * dont le plateau sera le même que le plateau du Noeud courant
		 */
		int i, k, l;
		boolean plateauPareil = true;
		for (i = 0; i < this.listeArbreFils.size(); i++) {
			for (k = 0; k < this.plateauDeCeNoeud.getColonne(); k++) {
				for (l = 0; l < this.plateauDeCeNoeud.getLigne(); l++) {
					if (this.plateauDeCeNoeud.get_pion(k, l) != this.listeArbreFils.get(i).get_plateau().get_pion(k,
							l)) {
						plateauPareil = false;
					}
				}
			}
			if (plateauPareil == true)
				this.listeArbreFils.get(i).SupprimeElementListe();
		}
	}

	public void AlgoRegleDeBase() {
		/*
		 * Fonction faisant appel aux méthodes des règles pour obtenir un poids
		 * représentatif du noeud
		 */
		if (this.colonneJouee != -1) {
			int rob = this.plateauDeCeNoeud.premiereCaseVide(this.colonneJouee);
			if (rob >= this.plateauDeCeNoeud.getLigne())
				rob = -1;
			int valeur = this.plateauDeCeNoeud.partieTerminee(rob + 1, this.colonneJouee);

			if (this.listeArbreFils != null) {
				this.poids += this.DetermineMeilleurCoup();
			}

			if (valeur == 0) {

				this.MenacePairImpair();
				this.Claimeven();
				this.BaseInverse();
				this.LowInverse();
				this.Aftereven();
				this.Vertical();

			} else if (valeur > 2) {
				this.poids -= 2;
			}

			else {
				this.poids += 3;
			}
		}
	}

	public void Claimeven() {
		/*
		 * Méthode qui regarde les cases dans les lignes pair (dans le cas HvsM)
		 * ou impair (dans le cas MvsH) et regarde la case en dessous et renvoie
		 * le poids correspondant si l'on joue cette case (empeche de jouer une
		 * case qui va le faire perdre et une case qui va permettre à l'adversaire
		 * de bloquer un coup gagnant de la machine en jouant par dessus)
		 */
		int t = this.plateauDeCeNoeud.getTypePartie();
		int ligne = this.listeArbreFils.get(colonneJouee).get_plateau().premiereCaseVide(this.colonneJouee)+1;
		if(ligne >= this.plateauDeCeNoeud.getLigne()) ligne = 0;
		if(t == 1)
		{
			if (this.listeArbreFils != null) {	
				if (this.listeArbreFils.get(colonneJouee).get_plateau().partieTerminee(ligne,this.colonneJouee) == t) {
					this.poids = this.poids - 1000;
				}
				
				this.listeArbreFils.get(colonneJouee).get_plateau().set_pion(ligne, colonneJouee, 2);
				if (this.listeArbreFils.get(colonneJouee).get_plateau().partieTerminee(ligne,colonneJouee) == 2) {
					this.poids = this.poids - 1000;
				}
			}
		}
		if(t == 2)
		{
			if (this.listeArbreFils != null) {	
				if (this.listeArbreFils.get(colonneJouee).get_plateau().partieTerminee(ligne,this.colonneJouee) == t) {
					this.poids = this.poids - 1000;
				}
				this.listeArbreFils.get(colonneJouee).get_plateau().set_pion(ligne, colonneJouee, 1);
				if (this.listeArbreFils.get(colonneJouee).get_plateau().partieTerminee(ligne,colonneJouee) == 1) {
					this.poids = this.poids - 1000;
				}
			}
		}
	}

	public void BaseInverse() {
		int nbadversaire = 0;
		int forcepoids = 399;
		int ligne = this.plateauDeCeNoeud.premiereCaseVide(this.colonneJouee) + 1;
		if (ligne >= this.plateauDeCeNoeud.getLigne())
			ligne = this.plateauDeCeNoeud.getLigne() - 1;/* ligne du pion ajouté*/
		int couladv = 0;
		int couleur = this.plateauDeCeNoeud.getTab()[ligne][this.colonneJouee];/*couleur du pion ajouté*/
		if (couleur == 1)
			couladv = 2;
		if (couleur == 2)
			couladv = 1;
		int i;
		int j = 0;

		if (this.colonneJouee <= this.plateauDeCeNoeud.getColonne() - 4)/*test horizontal*/
		{
			for (i = 0; i < 4; i++) {/*test le basinverse du pion vers le 4 cases vers la droite*/
				if (this.plateauDeCeNoeud.getTab()[ligne][this.colonneJouee + i] == couladv) {
					/* test si la couleur du pion de la case est la même que celle du pion ajouté si non adversaire++*/
					nbadversaire++;
				}
				if (this.plateauDeCeNoeud.getTab()[ligne][this.colonneJouee + i] == couleur) {
					/* test si la couleur du pion de la case est la même que celle du pion ajouté si non adversaire--*/
					nbadversaire--;
				}
			}
			nbadversaire++;
			if (nbadversaire >= 2)
				this.poids = this.poids + forcepoids;
			nbadversaire = 0;
		}
		if (this.colonneJouee - 1 >= 0)/* test horizontal*/
		{
			if (this.colonneJouee - 1 <= this.plateauDeCeNoeud.getColonne() - 4)/*test pour pas depasser*/
			{
				for (i = -1; i < 3; i++) {
					if (this.plateauDeCeNoeud.getTab()[ligne][this.colonneJouee + i] == couladv) {
						/* test si la couleur du pion de la case est la même que celle du pion ajouté si non adversaire++*/
						nbadversaire++;
					}
					if (this.plateauDeCeNoeud.getTab()[ligne][this.colonneJouee + i] == couleur) {
						/* test si la couleur du pion de la case est la même que celle du pion ajouté si non adversaire--*/
						nbadversaire--;
					}
				}
				nbadversaire++;
				if (nbadversaire >= 2)
					this.poids = this.poids + forcepoids;
				nbadversaire = 0;

			}
		}
		if (this.colonneJouee - 2 >= 0)/* test horizontal*/
		{
			if (this.colonneJouee - 2 <= this.plateauDeCeNoeud.getColonne() - 4) {
				for (i = -2; i < 2; i++) {
					if (this.plateauDeCeNoeud.getTab()[ligne][this.colonneJouee + i] == couladv) {
						nbadversaire++;
					}
					if (this.plateauDeCeNoeud.getTab()[ligne][this.colonneJouee + i] == couleur) {
						nbadversaire--;
					}
				}
				nbadversaire++;
				if (nbadversaire >= 2)
					this.poids = this.poids + forcepoids;
				nbadversaire = 0;
			}
		}
		if (this.colonneJouee - 3 >= 0)/* test horizontal*/
		{
			if (this.colonneJouee - 3 <= this.plateauDeCeNoeud.getColonne() - 4) {
				for (i = -3; i < 1; i++) {
					if (this.plateauDeCeNoeud.getTab()[ligne][this.colonneJouee + i] == couladv) {
						nbadversaire++;
					}
					if (this.plateauDeCeNoeud.getTab()[ligne][this.colonneJouee + i] == couleur) {
						nbadversaire--;
					}
				}
				nbadversaire++;
				if (nbadversaire >= 2)/* si trop de pion adverse besoin de contrer*/
					this.poids = this.poids + forcepoids;
				nbadversaire = 0;
			}
		}

		if (this.colonneJouee <= this.plateauDeCeNoeud.getColonne() - 4)/* test diagonal haut droite*/
		{
			if (ligne >= 3) {
				for (i = 0; i < 4; i++) {
					if (this.plateauDeCeNoeud.getTab()[ligne + j][this.colonneJouee + i] == couladv) {
						nbadversaire++;
					}
					if (this.plateauDeCeNoeud.getTab()[ligne + j][this.colonneJouee + i] == couleur) {
						nbadversaire--;
					}
					j--;
				}
				nbadversaire++;
				if (nbadversaire >= 2)
					this.poids = this.poids + forcepoids;
				nbadversaire = 0;
				j = 0;
			}
		}
		if (this.colonneJouee - 1 >= 0)/* test diagonal haut droite*/
		{
			if (this.colonneJouee - 1 <= this.plateauDeCeNoeud.getColonne() - 4) {
				if (ligne + 1 >= 3) {
					if (ligne + 1 < this.plateauDeCeNoeud.getLigne()) {
						for (i = 0; i < 4; i++) {
							if (this.plateauDeCeNoeud.getTab()[ligne + 1 + j][this.colonneJouee - 1 + i] == couladv) {
								nbadversaire++;
							}
							if (this.plateauDeCeNoeud.getTab()[ligne + 1 + j][this.colonneJouee - 1 + i] == couleur) {
								nbadversaire--;
							}
							j--;
						}
						nbadversaire++;
						if (nbadversaire >= 2)
							this.poids = this.poids + forcepoids;
						nbadversaire = 0;
						j = 0;
					}
				}
			}
		}
		if (this.colonneJouee - 2 >= 0)/* test diagonal haut droite*/
		{
			if (this.colonneJouee - 2 <= this.plateauDeCeNoeud.getColonne() - 4) {
				if (ligne + 2 >= 3) {
					if (ligne + 2 < this.plateauDeCeNoeud.getLigne()) {
						for (i = 0; i < 4; i++) {
							if (this.plateauDeCeNoeud.getTab()[ligne + 2 + j][this.colonneJouee - 2 + i] == couladv) {
								nbadversaire++;
							}
							if (this.plateauDeCeNoeud.getTab()[ligne + 2 + j][this.colonneJouee - 2 + i] == couleur) {
								nbadversaire--;
							}
							j--;
						}
						nbadversaire++;
						if (nbadversaire >= 2)
							this.poids = this.poids + forcepoids;
						nbadversaire = 0;
						j = 0;
					}
				}
			}
		}
		if (this.colonneJouee - 3 >= 0)/* test diagonal haut droite*/
		{
			if (this.colonneJouee - 3 <= this.plateauDeCeNoeud.getColonne() - 4) {
				if (ligne + 3 >= 3) {
					if (ligne + 3 < this.plateauDeCeNoeud.getLigne()) {
						for (i = 0; i < 4; i++) {
							if (this.plateauDeCeNoeud.getTab()[ligne + 3 + j][this.colonneJouee - 3 + i] == couladv) {
								nbadversaire++;
							}
							if (this.plateauDeCeNoeud.getTab()[ligne + 3 + j][this.colonneJouee - 3 + i] == couleur) {
								nbadversaire--;
							}
							j--;
						}
						nbadversaire++;
						if (nbadversaire >= 2)
							this.poids = this.poids + forcepoids;
						nbadversaire = 0;
						j = 0;
					}
				}
			}
		}

		if (this.colonneJouee >= 0)/* test diagonal haut gauche*/
		{
			if (this.colonneJouee <= this.plateauDeCeNoeud.getColonne() - 4) {
				if (ligne >= 0) {
					if (ligne < this.plateauDeCeNoeud.getLigne() - 4) {
						for (i = 0; i < 4; i++) {
							if (this.plateauDeCeNoeud.getTab()[ligne + j][this.colonneJouee + i] == couladv) {
								nbadversaire++;
							}
							if (this.plateauDeCeNoeud.getTab()[ligne + j][this.colonneJouee + i] == couleur) {
								nbadversaire--;
							}
							j++;
						}
						nbadversaire++;
						if (nbadversaire >= 2)
							this.poids = this.poids + forcepoids;
						nbadversaire = 0;
						j = 0;
					}
				}
			}
		}

		if (this.colonneJouee - 1 >= 0)/* test diagonal haut gauche*/
		{
			if (this.colonneJouee - 1 <= this.plateauDeCeNoeud.getColonne() - 4) {
				if (ligne - 1 >= 0) {
					if (ligne - 1 < this.plateauDeCeNoeud.getLigne() - 4) {
						for (i = 0; i < 4; i++) {
							if (this.plateauDeCeNoeud.getTab()[ligne - 1 + j][this.colonneJouee - 1 + i] == couladv) {
								nbadversaire++;
							}
							if (this.plateauDeCeNoeud.getTab()[ligne - 1 + j][this.colonneJouee - 1 + i] == couleur) {
								nbadversaire--;
							}
							j++;
						}
						nbadversaire++;
						if (nbadversaire >= 2)
							this.poids = this.poids + forcepoids;
						nbadversaire = 0;
						j = 0;
					}
				}
			}
		}

		if (this.colonneJouee - 2 >= 0)/* test diagonal haut gauche*/
		{
			if (this.colonneJouee - 2 <= this.plateauDeCeNoeud.getColonne() - 4) {
				if (ligne - 2 >= 0) {
					if (ligne - 2 < this.plateauDeCeNoeud.getLigne() - 4) {
						for (i = 0; i < 4; i++) {
							if (this.plateauDeCeNoeud.getTab()[ligne - 2 + j][this.colonneJouee - 2 + i] == couladv) {
								nbadversaire++;
							}
							if (this.plateauDeCeNoeud.getTab()[ligne - 2 + j][this.colonneJouee - 2 + i] == couleur) {
								nbadversaire--;
							}
							j++;
						}
						nbadversaire++;
						if (nbadversaire >= 2)
							this.poids = this.poids + forcepoids;
						nbadversaire = 0;
						j = 0;
					}
				}
			}
		}

		if (this.colonneJouee - 3 >= 0)/* test diagonal haut gauche*/
		{
			if (this.colonneJouee - 3 <= this.plateauDeCeNoeud.getColonne() - 4) {
				if (ligne - 3 >= 0) {
					if (ligne - 3 < this.plateauDeCeNoeud.getLigne() - 4) {
						for (i = 0; i < 4; i++) {
							if (this.plateauDeCeNoeud.getTab()[ligne - 3 + j][this.colonneJouee - 3 + i] == couladv) {
								nbadversaire++;
							}
							if (this.plateauDeCeNoeud.getTab()[ligne - 3 + j][this.colonneJouee - 3 + i] == couleur) {
								nbadversaire--;
							}
							j++;
						}
						nbadversaire++;
						if (nbadversaire >= 2)
							this.poids = this.poids + forcepoids;
						nbadversaire = 0;
						j = 0;
					}
				}
			}
		}
	}

	public void LowInverse() {
		int forceDuPoids = 1;
		int i, j;
		int nbCaseVide = (this.plateauDeCeNoeud.getLigne() * this.plateauDeCeNoeud.getColonne())
				- this.plateauDeCeNoeud.getNbTour();
		/* nbCaseVide verifie combien il y a de case vide, si c'est pair, alors 
		 * il y a un nombre pair de colonne paire et impaire.*/
		if (nbCaseVide % 2 == 0) {
			for (i = 0; i < this.plateauDeCeNoeud.getColonne(); i++) {
				j = this.plateauDeCeNoeud.premiereCaseVide(i);
				if (i != this.colonneJouee && j % 2 == 0) { 
					/* ne prend pas en compte la colonne Jouee et regarde si il y a un nombre impaire de case vide au dessus.*/
					this.poids += forceDuPoids * (j / 2); /* nombre de case deux à  deux.*/
				}
			}
		} else {
			for (i = 0; i < this.plateauDeCeNoeud.getColonne(); i++) {
				j = this.plateauDeCeNoeud.premiereCaseVide(i);
				if (i != this.colonneJouee && j % 2 == 1) { 	
					/* ne prend pas en compte la colonne Jouee et regarde si il y a un nombre impaire de case vide au dessus.*/
					this.poids += forceDuPoids * ((j / 2) + 1); /* nombre de case deux à deux.*/
				}
			}
		}
	}

	public void Aftereven() {
		/*
		 * Méthode qui recherche les groupes Aftereven pouvant etre complété
		 * par un Claimeven sur les lignes pair (dans le cas d'une partie HvsM)
		 * ou sur les lignes impair (dans le cas d'un partie MvsH) et ajoute les
		 * poids correspondant.
		 */
		int i, j, p, n = 0;
		int nbZero = 0, nbUn = 0, nbDeux = 0;
		int forceDuPoids = 2;
		int t = this.plateauDeCeNoeud.getTypePartie();
		int tab2[] = new int[4];
		ArrayList<int[]> quatrePion = new ArrayList<int[]>();
		int tab[][] = new int[this.plateauDeCeNoeud.getLigne()][this.plateauDeCeNoeud.getColonne()];
		tab = this.plateauDeCeNoeud.getTab();
		if (t == 1) {
			n = this.plateauDeCeNoeud.getLigne() - 2;
		}
		if (t == 2) {
			n = this.plateauDeCeNoeud.getLigne() - 3;
		}
		for (j = n; j >= 0; j = j - 2) {
			for (i = 0; i < this.plateauDeCeNoeud.getColonne() - 3; i++) {
				for (p = 0; p < 4; p++) {
					tab2[p] = tab[j][i + p];
				}
				quatrePion.add(tab2);
				tab2 = new int[4];
			}
		}
		for (int[] temp : quatrePion) {
			for (p = 0; p < 4; p++) {
				if (temp[p] == 1) {
					nbUn++;
				}
				if (temp[p] == 2) {
					nbDeux++;
				}
				if (temp[p] == 0) {
					nbZero++;
				}
			}
			if (t == 1) {
				if (nbDeux == 2 && nbZero == 2) {
					this.poids += forceDuPoids;
					nbZero = 0;
					nbUn = 0;
					nbDeux = 0;
				}
				if (nbDeux == 3 && nbZero == 1) {
					this.poids += (forceDuPoids * 2);
					nbZero = 0;
					nbUn = 0;
					nbDeux = 0;
				} else {
					nbZero = 0;
					nbUn = 0;
					nbDeux = 0;
				}
			}
			if (t == 2) {
				if (nbUn == 2 && nbZero == 2) {
					this.poids += forceDuPoids;
					nbZero = 0;
					nbUn = 0;
					nbDeux = 0;
				}
				if (nbUn == 3 && nbZero == 1) {
					this.poids += (forceDuPoids * 2);
					nbZero = 0;
					nbUn = 0;
					nbDeux = 0;
				} else {
					nbZero = 0;
					nbUn = 0;
					nbDeux = 0;
				}
			}
		}
	}

	public void Vertical() {
		/*
		 * Méthode qui regarde pour la colonne Jouée de ce Noeud, si la couleur
		 * du pion de la derniere case de cette colonne est la même que la case
		 * du dessous et la même que la couleur du joueur alors on ajoute + 2 au
		 * poids de ce noeud
		 */
		int forceDuPoids = 200;
		int i = this.plateauDeCeNoeud.premiereCaseVide(this.colonneJouee);
		
		if (i < this.plateauDeCeNoeud.getLigne() - 3) {
			if ((this.plateauDeCeNoeud.get_pion(i + 1, this.colonneJouee) == this.plateauDeCeNoeud.get_pion(i + 2,
					this.colonneJouee)) || (i%2 == 0))
				{this.poids += forceDuPoids;
			System.out.println("ligne = "+i +"colonne"+this.colonneJouee );}
			
		}
	}

	public void MenacePairImpair() {
		if (this.colonneJouee >= 0) {
			int forceDuPoids = 100;
			int ligneJouee = this.plateauDeCeNoeud.premiereCaseVide(this.colonneJouee) + 1;
			if (ligneJouee < this.plateauDeCeNoeud.getLigne()) {
				int pionJouee = this.plateauDeCeNoeud.get_pion(ligneJouee, this.colonneJouee);
				int i = 0;
				int possible = 0; /* correspond au nombre de pion de même couleur vu*/


				/* limitation de la recherche sur 3 case autour du pion jouee au max
				 *  ( extremite ) ( Ces valeurs ne changent pas )*/

				int gauche = 0;
				if (this.colonneJouee - 3 > gauche) {
					gauche = this.colonneJouee - 3;
				}
				int droite = this.plateauDeCeNoeud.getColonne() - 1;
				if (this.colonneJouee + 3 < droite) {
					droite = this.colonneJouee + 3;
				}
				int haut = 0;
				if (ligneJouee - 3 > haut) {
					haut = ligneJouee - 3;
				}
				int bas = this.plateauDeCeNoeud.getLigne() - 1;
				if (ligneJouee + 3 < bas) {
					bas = ligneJouee + 3;
				}

				int g, d, h, b; /*Vairable haut/bas/gauche/droite parcourant les boucles*/
				int G = gauche;
				int D = droite;
				int H = haut;
				int B = bas; /*Variable haut/bas/gauche/droite temporaire representant les extremites de chaque cas*/

				/*Ligne:
				 * 		 Droite*/
				for (d = this.colonneJouee; d <= droite; d++) {
					if (this.plateauDeCeNoeud.get_pion(ligneJouee, d) != pionJouee
							&& this.plateauDeCeNoeud.get_pion(ligneJouee, d) != 0) {
						D = d - 1; /* extremite droite temporaire n'etant pas un pion adverse*/
						d = droite;
					} else {
						D = d;
					}
				}
						/*Gauche*/
				for (g = this.colonneJouee; g >= gauche; g--) {
					if (this.plateauDeCeNoeud.get_pion(ligneJouee, g) != pionJouee
							&& this.plateauDeCeNoeud.get_pion(ligneJouee, g) != 0) {
						G = g + 1; /* extremite droite temporaire n'etant pas un pion adverse*/
						g = gauche;
					} else {
						G = g;
					}
				}
				/* Verification*/
				if (D - G >= 3) { /* si il y a au moins 4 cases avec soit des pions
				 					de meme couleur, soit des cases vides pour pouvoir rentrer dans la 
				 					boucle au moins une fois*/
					d = D - 1; 

					while (d < D) {
						possible = 0;
						g = G;
						d = g + 3; /* g et d sont sont les extremite des 4 cases
						 				que nous sommes entrain de verifier*/
						for (i = g; i <= d; i++) {
							if (this.plateauDeCeNoeud.get_pion(ligneJouee, i) == pionJouee) {
								possible++;
							}
						}
						if (possible == 3) { /*si 3 pion sont trouves
							// Si Blanc à joué sur ligne impaire OU Si Noir à joué sur ligne*/
							if ((pionJouee == 1 && ligneJouee % 2 == 0) || (pionJouee == 2 && ligneJouee % 2 == 1)) {
								this.poids ++;
								this.poids += forceDuPoids*6;
							}
							else {
								this.poids += forceDuPoids * 3;
							}
						} else if (possible == 2) { /* Si 2 pion sont trouves*/
							this.poids += forceDuPoids;
						}
						G++; /* décalage du lot des 4 cases*/
					}
				}

				/* Diagonales Haut Gauche/Bas Droite*/

				G = gauche;
				D = droite;
				H = haut;
				B = bas; /* re-initialisation*/
					/* Haut-Gauche*/
				g = this.colonneJouee;
				h = ligneJouee;
				while (g >= gauche && h >= haut) {
					if (this.plateauDeCeNoeud.get_pion(h, g) != pionJouee
							&& this.plateauDeCeNoeud.get_pion(h, g) != 0) {
						G = g + 1;
						H = h + 1; /* coordonnée de la derniere case n'etant pas un pion adverse*/
						g = gauche;
					} else {
						G = g;
						H = h;
					}
					g--;
					h--;
				}
					/* Bas-Droite*/
				d = this.colonneJouee;
				b = ligneJouee;
				while (d <= droite && b <= bas) {
					if (this.plateauDeCeNoeud.get_pion(b, d) != pionJouee
							&& this.plateauDeCeNoeud.get_pion(b, d) != 0) {
						D = d - 1;
						B = b - 1; /*coordonnée de la derniere case n'etant pas un pion adverse*/
						d = droite;
					} else {
						D = d;
						B = b;
					}
					d++;
					b++;
				}
				/* Verification*/
				if (D - G >= 3) { 
					/* si il y a au moins 4 cases avec soit des pions de meme couleur, soit
					 *  des cases vides pour rentrer dans la boucle au moins une fois*/
					d = D - 1;
					b = B - 1; 
					while (d < D && b < B) {
						possible = 0;
						g = G;
						d = g + 3;
						h = H;
						b = h + 3;
						while (g <= d && h <= b) {
							if (this.plateauDeCeNoeud.get_pion(h, g) == pionJouee) {
								possible++;
							} else if (this.plateauDeCeNoeud.get_pion(h, g) == 0) {
								i = h; /* retient la ligne de la case vide*/
							}
							g++;
							h++;
						}
						if (possible == 3) { 
							/* Si 3 pion sont trouves Si Blanc à joué sur ligne impaire OU Si Noir à joué sur ligne*/
							if ((pionJouee == 1 && i % 2 == 0) || (pionJouee == 2 && i % 2 == 1)) {
								this.poids ++;
								this.poids += forceDuPoids*6;
							}

							else {
								this.poids += forceDuPoids * 3;
							}
						} else if (possible == 2) { /* Si 2 pion sont trouves*/
							this.poids += forceDuPoids;
						}

						G++;
						H++; /*decalage des 4 cases*/
					}
				}

				/* Diagonales Bas Gauche/Haut Droite*/

				G = gauche;
				D = droite;
				H = haut;
				B = bas; /*re-initialisation*/
				/* Bas-Gauche*/
				g = this.colonneJouee;
				b = ligneJouee;
				while (g >= gauche && b <= bas) {
					if (this.plateauDeCeNoeud.get_pion(b, g) != pionJouee
							&& this.plateauDeCeNoeud.get_pion(b, g) != 0) {
						G = g + 1;
						B = b - 1; /*coordonnée de la derniere case n'étant pas un pion adverse*/
						g = gauche;
					} else {
						G = g;
						B = b;
					}
					g--;
					b++;
				}
					/* Haut-Droite*/
				d = this.colonneJouee;
				h = ligneJouee;
				while (d <= droite && h >= haut) {
					if (this.plateauDeCeNoeud.get_pion(h, d) != pionJouee
							&& this.plateauDeCeNoeud.get_pion(h, d) != 0) {
						D = d - 1;
						H = h + 1; /* coordonnée de la derniere case n'etant pas un pion adverse*/
						d = droite;
					} else {
						D = d;
						H = h;
					}
					d++;
					h--;
				}
					/* Verification*/	
				if (D - G >= 3) { /* si il y a au moins 4 cases avec soit des pions de meme couleur, soit des cases vides*/
					d = D - 1;
					h = H + 1; /* pour rentrer dans la boucle au moins une fois*/
					while (d < D && h > H) {
						possible = 0;
						g = G;
						d = g + 3;
						b = B;
						h = b - 3;
						while (g <= d && b >= h) {
							if (this.plateauDeCeNoeud.get_pion(b, g) == pionJouee) {
								possible++;
							} else if (this.plateauDeCeNoeud.get_pion(b, g) == 0) {
								i = b; /* retient la ligne de la case vide*/
							}
							g++;
							b--;
						}
						if (possible == 3) { 
							/* Si 3 pion sont trouves Si Blanc à joué sur ligne impaire OU Si Noir à joué sur ligne*/
							if ((pionJouee == 1 && i % 2 == 0) || (pionJouee == 2 && i % 2 == 1)) {
								this.poids ++;
								this.poids += forceDuPoids*6;
							}

							else {
								this.poids += forceDuPoids * 3;
							}
						} else if (possible == 2) { /* Si 2 pion sont trouves*/
							this.poids += forceDuPoids;
						}

						G++;
						B--; /* decalage des 4 cases*/
					}
				}
			}
		}
	}
	public Noeud RechecheNoeudPlusFort() {
		/*
		 * Retourne le Noeud le plus fort de la liste listeArbreFils
		 */
		int i;
		int valmax = 0;
		Noeud max = this.listeArbreFils.get(0);
		for (i = 1; i < plateauDeCeNoeud.getColonne(); i++) {
			if (valmax < this.listeArbreFils.get(i).get_poids()) {
				max = this.listeArbreFils.get(i);
				valmax = this.listeArbreFils.get(i).get_poids();
			}
		}
		return max;
	}

	public Noeud VerifieNoeudPresent(int col) {
		return null;
	}
}
