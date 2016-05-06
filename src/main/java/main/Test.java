package main;

import interfaceGraphique.FenetreMenu;
/*
 * Classe servant à réaliser des Tests
 */
//import plateauDeJeu.Plateau;

public class Test {
	public static void main(String[] args) {

		/*
		 * int i, j; Plateau p = new Plateau(3, 4); int tabEcr[][] = new
		 * int[3][4]; for (i = 0; i < 3; i++) { for (j = 0; j < 4; j++) {
		 * tabEcr[i][j] = i; } } p.setTab(tabEcr); GslPlateau a = new
		 * GslPlateau(p); a.sauvegarde(); GslPlateau b = new GslPlateau();
		 * b.charge(); int temp[][] = b.getPlateau().getTab(); for (i = 0; i <
		 * 3; i++) { for (j = 0; j < 4; j++) { System.out.println("allo:" +
		 * temp[i][j]); } }
		 */

		/*
		 * GslStatistiques g = new GslStatistiques(); g.charge(); // int i;
		 * System.out.println(g.get_HM().get_victoire());
		 */

		/*
		 * Plateau p = new Plateau(5, 5); int tab[][] = p.getTab(); int i, j;
		 * for (i = 0; i < 5; i++) { for (j = 0; j < 5; j++) {
		 * System.out.println(tab[i][j]); } }
		 */
		new FenetreMenu();
	}
}