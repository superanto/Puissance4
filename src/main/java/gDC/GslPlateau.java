package gDC;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import plateauDeJeu.Plateau;

public class GslPlateau extends Gsl {
	// Classe pour sauvegarder ou charger les données Plateau

	public GslPlateau() {
		this.gestionPlateau = null;
		this.statsHvsM = null;
		this.statsMvsH = null;
		this.statsHvsH = null;
	}

	public GslPlateau(Plateau PlatSave) {
		this.gestionPlateau = PlatSave;
		this.statsHvsM = null;
		this.statsMvsH = null;
		this.statsHvsH = null;
	}

	public Plateau getPlateau() {
		return this.gestionPlateau;
	}

	public void lecture(File lec) {
		/*
		 * Méthode dans lequel on ouvre et on lit un fichier texte pour
		 * récupérer les données utiles au Plateau
		 */

		String chaine = "";
		int i, j, position = 6;

		try {

			int ligne;
			FileReader fr = new FileReader(lec);
			while (((ligne = fr.read()) != -1) && ((char) ligne != '\n')) {
				chaine += (char) ligne;
			}
			fr.close();
			System.out.println(chaine);

		} catch (IOException e) {
			System.out.println("Exception de type IOException");
		}
		if (chaine != null) {

			int type = Character.getNumericValue(chaine.charAt(0));
			int ligne = Character.getNumericValue(chaine.charAt(2));
			int colonne = Character.getNumericValue(chaine.charAt(4));
			int tableau[][] = new int[ligne][colonne];
			this.gestionPlateau = new Plateau(ligne, colonne);
			this.gestionPlateau.setTypePartie(type);

			for (i = 0; i < ligne; i++) {
				for (j = 0; j < colonne; j++) {
					tableau[i][j] = Character.getNumericValue(chaine.charAt(position));
					position = position + 2;
				}
			}
			this.gestionPlateau.setTab(tableau);

		}

	}

	public void ecriture(File lec) {
		/*
		 * Méthode dans lequel on ouvre et on écrit dans un fichier texte pour
		 * conserver les données utiles au Plateau
		 */

		String chaine = "";
		int i, j;
		int ligne;
		int t = this.gestionPlateau.getTypePartie();
		int l = this.gestionPlateau.getLigne();
		int c = this.gestionPlateau.getColonne();
		int tabtemp[][] = this.gestionPlateau.getTab();
		if (this.gestionPlateau != null) {
			chaine = t + " " + l + " " + c;
			for (i = 0; i < l; i++) {
				for (j = 0; j < c; j++) {
					chaine += " " + tabtemp[i][j];
				}
			}
		}
		chaine += " \n";
		/*
		 * On écrit dans le fichier le nombre de lignes, de colonnes et le
		 * tableau rempli de valeurs
		 */
		try {
			FileReader fr = new FileReader(lec);
			while (((ligne = fr.read()) != -1) && ((char) ligne != '\n')) {
				// On déplace le curseur
			}
			while (((ligne = fr.read()) != -1)) {
				chaine += (char) ligne;
			}
			fr.close();
		} catch (IOException e) {
			System.out.println("Erreur de lecture !");
		}

		FileWriter fw;

		try {
			fw = new FileWriter(lec);
			fw.write(chaine);
			fw.close();
			System.out.println(chaine);

		} catch (IOException e) {
			System.out.println("Erreur d'écriture !");
		}

	}
}
