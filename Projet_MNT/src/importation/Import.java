package importation;

import java.nio.file.Path;
import java.nio.file.Paths;

import generation.Coord;

/**
 * Cette classe permet de gerer l'importation de fichiers de MNT
 * 
 * @param fichier
 *            (String) Chemin du fichier
 * @param suffixe
 *            (String) Type de fichier
 * 
 */

public abstract class Import extends MNT {

	private String fichier;
	private String suffixe;

	/**
	 * Ensemble des getters et setters
	 */
	public String getSuffixe() {
		return suffixe;
	}

	public void setSuffixe(String suffixe) {
		this.suffixe = suffixe;
	}

	public String getFichier() {
		return fichier;
	}

	public void setFichier(String fichier) {
		this.fichier = fichier;
	}

	/**
	 * Permet de verifier si le fichier est du bon format
	 * 
	 * @param f
	 *            Chemin du fichier considere
	 * @return (boolean) Retourne si le fichier est bien du format considere
	 */
	public boolean isSufType(String f) {
		return f.endsWith(suffixe);
	}

	/**
	 * Permet de creer un Path a partir du chemin du fichier
	 * 
	 * @param fichier
	 *            Chemin du fichier considere
	 * @return (Path) Retourne le chemin du fichier
	 */
	public Path createPath(String fichier) {
		Path f = Paths.get(fichier).toAbsolutePath();
		return f;
	}

	/**
	 * Constructeur de la classe Import
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
	public Import(String fichier, Coord origine, double dl, int x, int y) {
		super();
		this.origine = origine;
		this.dl = dl;
		this.x = x;
		this.y = y;
		this.fichier = fichier;

	}

	/**
	 * Constructeur de la classe Import
	 * 
	 * @param fichier
	 *            (String) fichier Chemin du fichier considere
	 */
	public Import(String fichier) {
		super();
		this.fichier = fichier;
	}

}
