package interfaceGraphique;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import gestionnaireStats.Statistiques;

public class FenetreStatistiques extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel panel3;
	private JLabel statistiquesHvsM;
	private JLabel statistiquesMvsH;
	private JLabel statistiquesHvsH;
	private JTextArea ratioHvsM;
	private JTextArea ratioMvsH;
	private JTextArea ratioHvsH;
	 

	public FenetreStatistiques(Statistiques HvsM, Statistiques MvsH, Statistiques HvsH) {
		panel3 = new JPanel();
		statistiquesHvsM = new JLabel("Homme VS Machine :");
		statistiquesMvsH = new JLabel("Machine VS Homme :");
		statistiquesHvsH = new JLabel("Homme VS Homme :");
		ratioHvsM = new JTextArea(30,50);
		ratioMvsH = new JTextArea(30,50);
		ratioHvsH = new JTextArea(30,50);
		
		/* Affichage de la fenètre Statistiques et des ratios correspondant à chaque type de partie*/
		this.setTitle("Fenetre Statistiques");
		this.setSize(450, 250);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		panel3.setLayout(null);
		panel3.add(statistiquesHvsM);
		statistiquesHvsM.setBounds(10, 30, 150, 30);
		panel3.add(statistiquesMvsH);
		statistiquesMvsH.setBounds(10, 90, 150, 30);
		panel3.add(statistiquesHvsH);
		statistiquesHvsH.setBounds(10, 150, 150, 30);
		ratioHvsM.append(Double.toString(Math.round(HvsM.ratio(0))));
		ratioHvsM.append(" % de Victoire");
		ratioHvsM.setEditable(false);
		ratioHvsM.setBounds(170, 38, 110, 15);
		panel3.add(ratioHvsM);
		ratioMvsH.append(Double.toString(Math.round(MvsH.ratio(0))));
		ratioMvsH.append(" % de Victoire");
		ratioMvsH.setEditable(false);
		ratioMvsH.setBounds(170, 98, 110, 25);
		panel3.add(ratioMvsH);
		ratioHvsH.append(Double.toString(Math.round(HvsH.ratio(0))));
		ratioHvsH.append(" % de Victoire");
		ratioHvsH.setEditable(false);
		ratioHvsH.setBounds(170, 158, 110, 25);
		panel3.add(ratioHvsH);
		this.add(panel3);
		this.setVisible(true);
	}
}