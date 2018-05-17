package generation;

import java.util.ArrayList;

/**
 * Classe CourbeNiveau qui est une liste de point representant une courbe de
 * niveau ferme
 * 
 * @param points
 *            (ArrayList<Coord>) Liste de points de la Courbe de niveau
 */
public class CourbeNiveau {
	private ArrayList<Coord> points = new ArrayList<Coord>();

	/**
	 * Constructeur de CourbeNiveau
	 */
	public CourbeNiveau() {

	}

	/**
	 * Constructeur de CourbeNiveau avec comme parametre la liste de point
	 * 
	 * @param points
	 *            (ArrayList<Coord>) Liste des points de la courbe de niveau a
	 *            ajouter
	 */
	public CourbeNiveau(ArrayList<Coord> points) {
		this.points = points;
	}

	/**
	 * Ensemble des getters et setters de CourbeNiveau
	 */
	public ArrayList<Coord> getPoints() {
		return this.points;
	}

	public void setPoints(ArrayList<Coord> points) {
		this.points = points;
	}

	/**
	 * Methode permettant d'ajouter un point dans la liste des points de la
	 * courbe de niveau
	 * 
	 * @param pt
	 *            (Coord) Point a ajouter a la liste des points de la courbe de
	 *            niveau
	 */
	public void addPoint(Coord pt) {
		this.points.add(pt);
	}

	/**
	 * Redefinition de la methode toString()
	 */
	@Override
	public String toString() {
		String tempc = "#";

		for (Coord pt : this.getPoints()) {
			tempc += pt + " ;";
		}
		return tempc + " #; ";
	}

	/**
	 * Methode permettant de determiner si une courbe de niveau est vide ou non
	 * 
	 * @return (boolean) renvoie true si la courbe de niveau est vide et false
	 *         sinon
	 */
	public boolean estVide() {
		if (this.getPoints().size() == 0)
			return true;
		else
			return false;
	}

	/**
	 * Methode permettant de mettre en forme l'ensemble des donnees des points
	 * en deux liste X et Y pour faciliter l'affichage des courbes de niveau
	 * 
	 * @return (double[][]) renvoie une liste de 2 listes X, Y
	 */

	public double[][] conversion() {
		double[] X = new double[this.getPoints().size()];
		double[] Y = new double[this.getPoints().size()];

		for (int i = 0; i < this.getPoints().size(); i++) {
			X[i] = this.getPoints().get(i).getX();
			Y[i] = this.getPoints().get(i).getY();
		}

		double[][] tab = new double[2][];

		tab[1] = X;
		tab[0] = Y;
		return tab;
	}
}
