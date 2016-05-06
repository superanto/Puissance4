package gDC;

import java.io.File;

import gestionnaireStats.Statistiques;
import plateauDeJeu.Plateau;

public abstract class Gsl implements LectureEtEcriture {
	protected Plateau gestionPlateau;
	protected Statistiques statsHvsM;
	protected Statistiques statsMvsH;
	protected Statistiques statsHvsH;

	/*
	 * C'est ici qu'on définit les fonctions de sauvegarde et de chargement La
	 * fonction ecriture est définie dans les sous classes
	 */

	public void sauvegarde() {
		File ecrire = new File("test.txt");
		ecriture(ecrire);
	}

	public void charge() {
		File lire = new File("test.txt");
		lecture(lire);
	}
}
