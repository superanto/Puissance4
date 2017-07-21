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
	 * C'est ici qu'on d�finit les fonctions de sauvegarde et de chargement. La
	 * fonction �criture est d�finie dans les sous classes
	 */

	public void sauvegarde() {
		File ecrire = new File("ChargeSauvegarde.txt");
		ecriture(ecrire);
	}

	public void charge() {
		File lire = new File("ChargeSauvegarde.txt");
		lecture(lire);
	}
}
