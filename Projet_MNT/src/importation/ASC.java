package importation;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import generation.Coord;

/**
 * Classe permettant d'extraire des MNT de format .asc
 * 
 */
public class ASC extends Import {
	/**
	 * Constructeur de la classe ASC
	 * 
	 * @param fichier
	 *            (String) Chemin du fichier considere
	 */
	public ASC(String fichier) {
		super(fichier);
		super.setSuffixe(".asc");
	}

	/**
	 * Constructeur de la classe ASC
	 * 
	 * @param fichier
	 *            (String) Chemin du fichier considere
	 * @param origine
	 *            (Coord) Coordonnees de l'origine du MNT
	 * @param dl
	 *            (double) Distance entre 2 points
	 * @param x
	 *            (int) longueur du MNT
	 * @param y
	 *            (int) largeur du MNT
	 */
	public ASC(String fichier, Coord origine, double dl, int x, int y) {
		super(fichier, origine, dl, x, y);
	}

	/**
	 * Permet de remplir la grille des altitudes
	 */
	@Override
	public void grille() {
		Path p = super.createPath(super.getFichier());
		System.out.println(p);
		try (BufferedReader reader = Files.newBufferedReader(p)) { // ouverture
																	// d'un
																	// buffer en
																	// lecture

			double[] tab = new double[6];

			for (int i = 0; i < 6; i++) { // extraction des parametres
											// caracteristiques occupant les 6
											// premieres lignes
				String line = reader.readLine();
				String[] parts = line.split(" ");
				int inc = parts.length - 1;
				String nombre = parts[inc];
				tab[i] = Double.parseDouble(nombre);// change le String en
													// double
			}
			super.setX((int) tab[0]);
			super.setY((int) tab[1]);
			super.setOrigine(new Coord(tab[2], tab[3]));
			super.setDl(tab[4]);
			super.setNodata(tab[5]); // attribution des parametres
			this.setGrille(new double[this.getY()][this.getX()]); // initialise
																	// la grille
			for (int i = 0; i < super.getY(); i++) { // parcourt les lignes
				String line = reader.readLine();
				String[] parts = line.split(" ");
				for (int j = 0; j < super.getX(); j++) { // parcourt les
															// colonnes
					if (parts[j].equals("")) {
						super.setXY(j, i, super.getNodata()); // cas ou les
																// donnees ne
																// sont pas
																// renseignees
					}

					else {
						super.setXY(j, i, Double.parseDouble(parts[j]));
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
