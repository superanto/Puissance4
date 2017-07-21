package gDC;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import gestionnaireStats.Statistiques;

public class GslStatistiques extends Gsl {
	/* Classe pour sauvegarder ou charger les données Statistiques*/

	public GslStatistiques() {
		this.gestionPlateau = null;
		this.statsHvsM = null;
		this.statsMvsH = null;
		this.statsHvsH = null;
	}

	public GslStatistiques(Statistiques StHvsM, Statistiques StMvsH, Statistiques StHvsH) {
		this.gestionPlateau = null;
		this.statsHvsM = StHvsM;
		this.statsMvsH = StMvsH;
		this.statsHvsH = StHvsH;
	}

	public void lecture(File lec) {
		/*
		 * Méthode dans lequel on ouvre et on lit un fichier texte pour
		 * récupérer les données utiles aux statistiques
		 */
		String chaine = "";
		int i = 0;
		int ligne;
		int tab[] = new int[9];
		try {

			FileReader fr = new FileReader(lec);
			while (((ligne = fr.read()) != -1) && ((char) ligne != '\n')) {
				/*Ici on déplace le curseur de lecture pour lire la deuxième ligne*/
			}
			while (((ligne = fr.read()) != -1)) {
				chaine += (char) ligne;
			}
			fr.close();

			/*Compose le String crée en mot*/
			String[] splited = chaine.split(" ");

			/* parcourir les mots*/
			for (String current : splited) {
				tab[i] = Integer.parseInt(current);
				i++;
			}

			/*On affecte la valeur des 3 objets*/
			this.statsHvsM = new Statistiques(0);
			this.statsHvsM.set_total(tab[0], tab[1], tab[2]);

			this.statsMvsH = new Statistiques(1);
			this.statsMvsH.set_total(tab[3], tab[4], tab[5]);

			this.statsHvsH = new Statistiques(2);
			this.statsHvsH.set_total(tab[6], tab[7], tab[8]);

		} catch (IOException e) {
			System.out.println("Erreur de lecture de Statistiques");
		}
	}

	public void ecriture(File lec) {
		/*
		 * Méthode dans lequel on ouvre et on écrit dans un fichier texte pour
		 * conserver les données utiles aux statistiques
		 */
		String chaine = "";
		try {

			int ligne;
			FileReader fr = new FileReader(lec);
			while (((ligne = fr.read()) != -1) && ((char) ligne != '\n')) {
				/*On ne va lire que la première ligne qu'on affecte dans chaine*/
				chaine += (char) ligne;
			}
			chaine += " \n";
			fr.close();

		} catch (IOException e) {
			System.out.println("Erreur de lecture de Statistiques !");
		}

		chaine += this.statsHvsM.get_victoire() + " " + this.statsHvsM.get_defaite() + " "
				+ this.statsHvsM.get_nb_parties() + " ";
		chaine += this.statsMvsH.get_victoire() + " " + this.statsMvsH.get_defaite() + " "
				+ this.statsMvsH.get_nb_parties() + " ";
		chaine += this.statsHvsH.get_victoire() + " " + this.statsHvsH.get_defaite() + " "
				+ this.statsHvsH.get_nb_parties();

		FileWriter fw;

		try {
			/* On écrit dans le texte toute la chaine avec les nouvelles données Statistiques*/
			fw = new FileWriter(lec);
			fw.write(chaine);
			fw.close();

		} catch (IOException e) {
			System.out.println("Erreur d'écriture des Statistiques!");
		}
	}

	public Statistiques get_HM() {
		return this.statsHvsM;
	}

	public Statistiques get_MH() {
		return this.statsMvsH;
	}

	public Statistiques get_HH() {
		return this.statsHvsH;
	}

}
