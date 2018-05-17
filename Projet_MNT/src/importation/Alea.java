package importation;

import generation.Coord;

/**
 * Classe Alea permettant de creer un MNT aleatoire
 * 
 * @param hmin
 *            (double) altitude minimale du MNT a generer
 * @param hmax
 *            (double) altitude maximale du MNT a generer
 *
 */
public class Alea extends MNT {
	private double hmin;
	private double hmax;

	/**
	 * Constructeur de Alea
	 * 
	 * @param origine
	 *            (Point) Point de reference du MNT (herite de MNT)
	 * @param dl
	 *            (double) Largeur entre deux case (en metre) (herite de MNT)
	 * @param x
	 *            (int) Largeur de la grille (herite de MNT)
	 * @param y
	 *            (int) Hauteur de la grille (herite de MNT)
	 * @param hmin
	 *            (double) Altitude minimale
	 * @param hmax
	 *            (double) Altitude maximale
	 */
	public Alea(Coord origine, double dl, int x, int y, double hmin, double hmax) {
		super();
		this.origine = origine;
		this.dl = dl;
		this.x = x;
		this.y = y;
		this.hmax = hmax;
		this.hmin = hmin;
		super.setGrille(new double[y][x]);
	}

	/**
	 * Ensemble des getters et des setters de la classe Alea
	 */

	public double getHmin() {
		return hmin;
	}

	public void setHmin(double hmin) {
		this.hmin = hmin;
	}

	public double getHmax() {
		return hmax;
	}

	public void setHmax(double hmax) {
		this.hmax = hmax;
	}

	/**
	 * Redefinition de la methode toString() pour la classe Alea
	 */
	@Override
	public void grille() {
		for (int i = 0; i < super.getY(); i++) {
			for (int j = 0; j < super.getX(); j++) {
				super.setXY(j, i, Math.random() * (hmax - hmin) + hmin);
			}
		}
	}
}
