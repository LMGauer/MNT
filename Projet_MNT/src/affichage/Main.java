package affichage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import generation.Coord;
import importation.ASC;
import importation.Alea;
import importation.MNT;

/**
 * Classe qui gere l'ensemble des creations des objets ainsi que les boites de
 * dialogue
 *
 */
public class Main {

	public static void main(String args[]) {

		JOptionPane.showMessageDialog(null, "Bienvenue !", "Affichage & Visualisation MNT", 1); // Generation
																								// boite
																								// de
																								// dialogue

		Object[] options = new Object[2]; // Options disponibles
		options[0] = "Aleatoire";
		options[1] = "Fichier .asc";

		MNT fichier;

		int gen = JOptionPane.showOptionDialog(null, "Choisir le mode de generation du MNT", "Choix", 1, 3, null,
				options, 0); // Choix de la generation du MNT

		if (gen == 0) { // Si le choix est aleatoirement

			int largeur = 0;
			int longueur = 0;
			int hmin = 0;
			int hmax = 0;

			// Selection des differents parametres
			String largeu = JOptionPane.showInputDialog(null, "Veuillez entrer la largeur du MNT", "Dimension", 3);
			largeur = Integer.parseInt(largeu);
			String longueu = JOptionPane.showInputDialog(null, "Veuillez entrer la longueur du MNT", "Dimension", 3);
			longueur = Integer.parseInt(longueu);
			String hmi = JOptionPane.showInputDialog(null, "Veuillez entrer l'altitude minimale", "Altitude", 3);
			hmin = Integer.parseInt(hmi);
			String hma = JOptionPane.showInputDialog(null, "Veuillez entrer l'altitude maximale", "Altitude", 3);
			hmax = Integer.parseInt(hma);

			Coord orig = new Coord(0, 0);
			double dl = 1;

			fichier = new Alea(orig, dl, largeur, longueur, hmin, hmax); // Creation
																			// du
																			// MNT

		}

		else { // Si le choix de generation est avec un fichier .asc

			String acces = (String) JOptionPane.showInputDialog(null, "Entrez le nom du fichier :", "Format .asc", 3); // Demande
																														// du
																														// chemin
																														// d'acces
			fichier = new ASC(acces);

		}

		// Demande des pas pour la generation des courbes de niveau
		String nom_pas1 = JOptionPane.showInputDialog(null, "Veuillez entrer le pas1", "Entier positif",
				JOptionPane.QUESTION_MESSAGE);
		int pas1 = Integer.parseInt(nom_pas1);
		String nom_pas2 = JOptionPane.showInputDialog(null,
				"Veuillez entrer le pas2. Attention il doit etre un multiple du pas1 et donc plus grand que le pas1. \n Si un seul pas est attendu entrer la meme valeur que pas1.",
				"Entier positif", JOptionPane.QUESTION_MESSAGE);
		int pas2 = Integer.parseInt(nom_pas2);

		if (pas2 % pas1 != 0) {
			JOptionPane.showMessageDialog(null,
					"Le parametre pas2 ne respecte pas les attentes. Il sera fixe tel que pas2 = 5*pas1 !", "Erreur !",
					0);
			pas2 = 5 * pas1;
		}

		JFrame appliMNT = new JFrame();

		fichier.grille(); // Initialisation du tableau de donnees dans l'objet
							// MNT

		appliMNT.setTitle("MNT");
		appliMNT.setSize(fichier.getGrille()[0].length, fichier.getGrille().length);
		appliMNT.setLocationRelativeTo(null);
		appliMNT.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Panneau mainPanel = new Panneau(gen, fichier, pas1, pas2); // Affichage
																	// du
																	// tableau

		appliMNT.setContentPane(mainPanel);

		appliMNT.setVisible(true);
	}
}
