package gDC;

import java.io.File;

/*
 * Interface comprenant des m�thodes � d�finir selon le type d'�l�ments que l'on souhaite 
 * sauvegarder ou charger
 */

public interface LectureEtEcriture {
	public void lecture(File lec);

	public void ecriture(File lec);

}