package gDC;

import java.io.File;

/*
 * Interface comprenant des méthodes à  définir selon le type d'éléments que l'on souhaite 
 * sauvegarder ou charger
 */

public interface LectureEtEcriture {
	public void lecture(File lec);

	public void ecriture(File lec);

}