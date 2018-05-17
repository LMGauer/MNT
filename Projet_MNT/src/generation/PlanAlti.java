package generation;

import java.util.ArrayList;

/**
 * Classe PlanALti possedant pour une altitude donnee un ensemble de courbe de
 * niveau fermee
 * 
 * @param altitude
 *            (double) Altitude des courbes de niveau
 * @param courbes
 *            (ArrayList<CourbeNiveau>) Liste des courbes de niveau fermee de
 *            l'altitude du PlanAlti
 */
public class PlanAlti {

	private double altitude;
	private ArrayList<CourbeNiveau> courbes = new ArrayList<CourbeNiveau>();

	/**
	 * Constructeur de PlanAlti
	 */
	public PlanAlti() {

	}

	/**
	 * Constructeur de PlanAlti avec altitude
	 * 
	 * @param alti
	 *            (double) altitude du PlanAlti
	 */
	public PlanAlti(double alti) {
		this.altitude = alti;
	}

	/**
	 * Constructeur de PlanAlti avec comme parametre l'altitude et la liste des
	 * courbes de niveau
	 * 
	 * @param alti
	 *            (double) Altitude du PlanAlti
	 * @param courbes
	 *            (ArrayList<CourbeNiveau>) Liste des courbes de niveau a
	 *            l'altitude definie
	 */
	public PlanAlti(double alti, ArrayList<CourbeNiveau> courbes) {
		this.altitude = alti;
		this.courbes = courbes;
	}

	/**
	 * Ensemble des getters et setters de la classe PlanAlti
	 */
	public double getAltitude() {
		return this.altitude;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public ArrayList<CourbeNiveau> getCourbes() {
		return this.courbes;
	}

	public void setPoints(ArrayList<CourbeNiveau> courbes) {
		this.courbes = courbes;
	}

	/**
	 * Methode permettant d'ajouter une courbe de niveau a l'attribut courbes
	 * 
	 * @param courbe
	 *            (CourbeNiveau) Courbe de Niveau a ajouter
	 */
	public void addCourbe(CourbeNiveau courbe) {
		this.courbes.add(courbe);
	}

	/**
	 * Redefinition de la methode toString()
	 */
	@Override
	public String toString() {
		String tempp = "Altitude : " + this.getAltitude() + " | ";
		for (CourbeNiveau courbe : this.getCourbes()) {
			tempp += courbe;
		}
		return tempp;
	}

}
