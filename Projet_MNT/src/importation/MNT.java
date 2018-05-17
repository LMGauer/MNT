package importation;

import generation.Coord;

/**
 * Classe MNT qui permet d'organiser les donnees comme on le souhaite pour
 * generaliser les methodes quelque soit le type de generation des donnees
 * 
 * @param origine
 *            (Point) objet de la classe Point renseignant sur le point de
 *            reference de geolocalisation du MNT
 * @param dl
 *            (double) pas entre deux case (en metre)
 * @param x
 *            (int) largeur de la grille
 * @param y
 *            (int) longueur de la grille
 * @param grille
 *            (double[][]) grille de donnee altimetrique
 * @param nodata
 *            (double) Valeur lorsque qu'il n'y a pas de donnee (-9999, -1, ...)
 */
public abstract class MNT {
	protected Coord origine;
	protected double dl;
	protected int x;
	protected int y;
	private double[][] grille;
	private double nodata;

	/**
	 * Methode abstract pour remplir un objet de la classe MNT
	 */
	public abstract void grille();

	/**
	 * Ensemble des getters et des setters
	 */
	public double getNodata() {
		return nodata;
	}

	public void setNodata(double nodata) {
		this.nodata = nodata;
	}

	public Coord getOrigine() {
		return origine;
	}

	public void setOrigine(Coord origine) {
		this.origine = origine;
	}

	public double getDl() {
		return dl;
	}

	public void setDl(double dl) {
		this.dl = dl;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double[][] getGrille() {
		return this.grille;
	}

	public void setGrille(double[][] grille) {
		this.grille = grille;
	}

	/**
	 * Methode permettant d'ajouter l'element elt a la position x,y de la grille
	 * de l'objet MNT
	 * 
	 * @param x
	 *            (int) Ligne de la grille ou il faut ajouter l'element
	 * @param y
	 *            (int) Colonne de la grille ou il faut ajouter l'element
	 * @param elt
	 *            (double) Element a ajouter dans le tableau
	 */
	public void setXY(int x, int y, double elt) {
		grille[y][x] = elt;
	}

	/**
	 * Redefinition de la methode toString() pour l'affichage d'une instance de
	 * la classe
	 */
	@Override
	public String toString() {

		String temp = "Largeur = " + this.x + "\n";
		temp += "Hauteur = " + this.y;
		for (int i = 0; i < this.grille.length; i++) { // Pour tour les lignes
														// de grille
			temp += "\n| ";
			for (int j = 0; j < this.grille[0].length; j++) { // Pour les cases
																// des lignes
				temp += grille[i][j] + " | ";
			}
		}
		return temp;
	}

	/**
	 * Methode permettant d'obtenir le minimum d'altitude de la grille de
	 * l'instance
	 * 
	 * @return (double) le minimum de grille
	 */
	public double getMin() {
		double[][] table = this.getGrille(); // Initialisation des parametres
		double min = this.getMax();

		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				if (table[i][j] < min && table[i][j] != nodata) {
					min = table[i][j]; // On recupere un nouveau minimum
				}
			}
		}
		return min;
	}

	/**
	 * methode qui retourne le maximum de la grille de l'instance de MNT
	 * 
	 * @return (double) le maximum de la grille
	 */
	public double getMax() {
		double[][] table = this.getGrille();
		double max = table[0][0]; // Initialisation des parametres

		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[0].length; j++) {
				if (table[i][j] > max) {
					max = table[i][j]; // On recupere le nouveau maximum
				}
			}
		}
		return max;
	}
}
