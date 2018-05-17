package generation;

/**
 * Classe Coord permettant la creation de coordonnee avec une abscisse et une
 * ordonnee
 * 
 * @param x
 *            (double) valeur de l'abscisse
 * @param y
 *            (double) valeur de l'ordonnee
 */
public class Coord {

	private double x;
	private double y;

	/**
	 * Constructeur de Coord
	 */
	public Coord() {

	}

	/**
	 * Constructeur de coord avec l'abscisse et l'ordonnee
	 * 
	 * @param x
	 *            (double) abscisse du nouveau point
	 * @param y
	 *            (double) ordonnee du nouveau point
	 */
	public Coord(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Ensemble des getters et des setters de la classe Coord
	 */

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Redefinitoin de la methode toString() pour la classe Coord
	 */

	@Override
	public String toString() {
		String tempc = "";
		tempc += "(" + this.getX() + "; " + this.getY() + ")";
		return tempc;
	}
}
