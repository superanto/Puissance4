package gestionnaireStats;

public class Statistiques {

	public static final int HvsM = 0; /* Type de partie où l'Homme commence contre la Machine*/
	public static final int MvsH = 1; /*Type de partie où la machine commence contre l'Homme*/
	public static final int HvsH = 2; /*Type de partie où deux Hommes s'affrontent*/

	private int j1j2, victoire, defaite, nb_parties;

	public Statistiques() {
		this.victoire = 0;
		this.defaite = 0;
		this.nb_parties = 0;
	}

	public Statistiques(int type) {
		this.j1j2 = type;
		this.victoire = 0;
		this.defaite = 0;
		this.nb_parties = 0;
	}

	public double ratio(int choix) {

		if (choix == HvsM) {
			return ((double) this.victoire / (double) this.nb_parties) * 100;
			/* retourne pourcentage victoire sur nombres de parties*/
		} 
		else if (choix == MvsH) {
			return ((double) this.defaite / (double) this.nb_parties) * 100; 
			/* retourne pourcentage défaite sur nombres de parties*/
			}
		else {
			/* retourne pourcentage match nul sur nombres de parties*/
			return (((double) this.nb_parties - ((double) this.victoire + (double) this.defaite))
					/ (double) this.nb_parties) * 100;
		}
	}

	public int incrementation() {
		return 0;
	}

	public int get_j1j2() {
		return j1j2;
	}

	public void set_total(int vic, int def, int part) {
		this.victoire = vic;
		this.defaite = def;
		this.nb_parties = part;
	}

	public int get_victoire() {
		return this.victoire;
	}

	public int get_defaite() {
		return this.defaite;
	}

	public int get_nb_parties() {
		return this.nb_parties;
	}

}