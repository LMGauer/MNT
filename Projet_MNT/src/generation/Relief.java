package generation;

import java.util.ArrayList;

import importation.MNT;

/**
 * La classe relief est une classe qui comporte une liste de PlanAlti ave cun
 * pas des courbes de niveau
 * 
 * @param pas
 *            (double) Pas entre les differentes courbes de niveau
 * @param reference
 *            (Coord) Coordonnee du point de reference dans le tableau
 * @param table
 *            (ArrayListe<PlanAlti>) Liste des PlanAlti
 * @param max
 *            (double) altitude maximale atteinte dans la table d'altitude
 * @param min
 *            (double) altitude minimale atteinte dans la table d'altitude
 */

public class Relief {

	private double pas;
	private Coord reference;
	private ArrayList<PlanAlti> table = new ArrayList<PlanAlti>();
	private double max;
	private double min;

	/**
	 * Constructeur d'un objet de la classe relief
	 */
	public Relief() {

	}

	/**
	 * Constructeur d'un objet de la classe relief e partir d'un objet MNT
	 * 
	 * @param source
	 *            (MNT) Objet genere precedement avec les donnees d'un MNT ou
	 *            generee aleaetoirement
	 * @param pas
	 *            (double) Pas entre les courbes de niveau
	 */

	public Relief(MNT source, double pas) {
		this.pas = pas;

		int fin_courbe = (int) source.getMax(); // On determine la fin de la
												// generation des courbes de
												// niveau
		int deb_courbe = (int) (source.getMin() - source.getMin() % pas); // On
																			// determine
																			// le
																			// debut
																			// de
																			// la
																			// generation
																			// des
																			// courbes
																			// de
																			// niveau
		double[][] table = source.getGrille(); // On recupere la grille des
												// donnees de l'objet MNT

		this.max = source.getMax();
		this.min = source.getMin();

		for (int alt = deb_courbe; alt <= fin_courbe; alt += pas) { // On fait
																	// une
																	// boucle
																	// sur
																	// l'altitude
																	// pour
																	// creer
																	// des

			// courbes de niveau a toute les altitudes

			int[][] parcouru = new int[source.getY()][source.getX()]; // On
																		// initialise
																		// un
																		// tableau
																		// de la
																		// meme
																		// taille
																		// que
																		// la
																		// grille
																		// remplie
																		// de 0.
																		// Si
																		// une
																		// case
																		// est
																		// par
																		// la
																		// suite
																		// egale
																		// a 1,
																		// c'est
																		// que
																		// la
																		// case
																		// a
																		// deja
																		// ete
																		// visitee

			PlanAlti plan = new PlanAlti(alt); // On cree un nouveau PlanAlti

			for (int i = 0; i < table.length; i++) {
				for (int j = 0; j < table[0].length; j++) { // On parcourt la
															// grille

					int[] point = new int[2]; // On recupere les coordonnees de
												// la case que l'on regarde
												// actuellement et
												// on les stocke dans un objet
												// int[]
					point[0] = i;
					point[1] = j;

					if (parcouru[i][j] == 1) // Si la case a deja ete visite on
												// continu et on la saute
						continue;

					else {
						parcouru[i][j] = 1;
						CourbeNiveau courbe = new CourbeNiveau(); // Sinon on
																	// creer une
																	// nouvelle
																	// instance
																	// de
																	// CourbeNiveau

						while (voisin(point[0], point[1], source, alt, parcouru)[0] != -1) { // Tant
																								// que
																								// le
																								// point
																								// observe
																								// a
																								// un
																								// voisin
																								// on
																								// continu
							parcouru[point[0]][point[1]] = 1; // Il a ete visite

							int[] voisin_alt = voisin(point[0], point[1], source, alt, parcouru); // On
																									// recherche
																									// un
																									// voisin
																									// altimetrique
																									// car
																									// il
																									// en
																									// a
																									// un
																									// (condition
																									// du
																									// while)

							courbe.addPoint(new Coord(
									alt / table[voisin_alt[0]][voisin_alt[1]] * (voisin_alt[0] - point[0]) + point[0],
									alt / table[voisin_alt[0]][voisin_alt[1]] * (voisin_alt[1] - point[1]) + point[1])); // On
																															// initialise
																															// un
																															// nouveau
																															// point
																															// d'un
																															// courbe
																															// de
																															// niveau

							point[0] = voisin_alt[0]; // On change le point
														// observe pour
														// continuer sur la meme
														// courbe de
														// niveau
							point[1] = voisin_alt[1];

						}

						// affiche(parcouru);

						point[0] = i; // On se remet sur le meme point pour
										// rechercher les autres voisins et la
										// suite
										// de la courbe de niveau si elle n'est
										// pas fermee (comme en bord de grille)
						point[1] = j;

						ArrayList<Coord> temp_list = new ArrayList<Coord>(); // On
																				// creer
																				// une
																				// liste
																				// temporaire

						while (voisin(point[0], point[1], source, alt, parcouru)[0] != -1) { // On
																								// refait
																								// exactement
																								// la
																								// meme
																								// action
							parcouru[point[0]][point[1]] = 1;
							int[] voisin_alt = voisin(point[0], point[1], source, alt, parcouru);

							temp_list.add(new Coord(
									alt / table[voisin_alt[0]][voisin_alt[1]] * (voisin_alt[0] - point[0]) + point[0],
									alt / table[voisin_alt[0]][voisin_alt[1]] * (voisin_alt[1] - point[1]) + point[1]));

							point[0] = voisin_alt[0];
							point[1] = voisin_alt[1];
						}

						if (temp_list.size() > 0) {
							for (int k = temp_list.size() - 1; k > -1; k--) {
								courbe.addPoint(temp_list.get(k)); // On ajoute
																	// les
																	// points a
																	// la courbe
																	// de niveau
																	// dans
																	// le
																	// bonne
																	// ordre
																	// pour
																	// qu'ils se
																	// suivent
																	// bien

							}
						}

						if (courbe.estVide()) // On teste si la courbe de niveau
												// creee est vide
							continue;

						else
							plan.addCourbe(new CourbeNiveau(courbe.getPoints())); // Si
																					// elle
																					// ne
																					// l'est
																					// pas
																					// on
																					// la
																					// rajoute
																					// au
																					// plan
																					// altimetrique
																					// en
																					// creant
																					// un
																					// nouvel
																					// objet
					}
				}
			}
			this.table.add(new PlanAlti(alt, plan.getCourbes())); // On ajoute
																	// le plan
																	// altimetrique
																	// a notre
																	// table de
																	// l'objet
																	// relief
			System.out.println("Altitude : " + alt);
		}
		System.out.println("## Relief cr�� ##");
	}

	/**
	 * Ensemble des getters et setters de la classe MNT
	 */

	public double getPas() {
		return pas;
	}

	public void setPas(double pas) {
		this.pas = pas;
	}

	public Coord getReference() {
		return reference;
	}

	public void setReference(Coord reference) {
		this.reference = reference;
	}

	public ArrayList<PlanAlti> getTable() {
		return table;
	}

	public void setTable(ArrayList<PlanAlti> table) {
		this.table = table;
	}

	/**
	 * Methode permettant d'ajouter un PlanAlti a la table de l'objet Relief
	 * 
	 * @param plan
	 *            (PlanAlti) Objet PlanAlti a ajouter dans la table de
	 *            l'instance de la classe Relief
	 */
	public void addTable(PlanAlti plan) {
		this.table.add(plan);
	}

	/**
	 * Redefinition de la methode toString() permettant d'afficher clairement un
	 * objet de la classe relief plutot que son lien d'acces
	 */
	@Override
	public String toString() {
		String tempr = "Pas = " + this.getPas() + "\n";
		tempr += "Min = " + this.min + "\n";
		tempr += "Max = " + this.max + "\n";
		for (PlanAlti plan : this.getTable()) {
			tempr += plan;
			tempr += "\n";
		}
		return tempr;
	}

	/**
	 * Methode static qui renvoie un booleon sur le test : les points pt1 et pt2
	 * encadre le nombre alti
	 * 
	 * @param pt1
	 *            (double) altitude du point 1
	 * @param pt2
	 *            (double) altitude du point 2
	 * @param alti
	 *            (double) altitude a regarder
	 * @return (boolean) renvoie true si pt1 et pt2 encadre alti et false sinon
	 */
	public static Boolean encadre(double pt1, double pt2, double alti) {
		if ((pt1 < alti && pt2 >= alti) | (pt1 >= alti && pt2 < alti)) { // Test
																			// d'encadrement
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Methode static qui permet de rechercher un voisin altimetrique (qui
	 * encadre une alt avec un autre point voisin)
	 * 
	 * @param i
	 *            (int) Ligne de la case observee
	 * @param j
	 *            (int) Colonne de la case observee
	 * @param mnt
	 *            (MNT) objet de la classe MNT qui possede les donnees dans la
	 *            grille
	 * @param alt
	 *            (int) altitude observe pour rechercher un voisin de la casse
	 *            mnt.getGrille()[i][j]
	 * @param parcouru
	 *            (int[][]) tableau des passages deja effectue pour ne pas
	 *            parcourir une case deux fois
	 * @return (int[]) La position du voisin du point (i, j) qui encadre
	 *         l'altitud alt
	 */
	public static int[] voisin(int i, int j, MNT mnt, int alt, int[][] parcouru) {
		int[] pos = new int[2];
		int x = mnt.getX() - 1;
		int y = mnt.getY() - 1;
		double[][] grille = mnt.getGrille(); // On recupere la grille

		if (j < x - 1) { // On teste la case a droite
			if (encadre(grille[i][j], grille[i][j + 1], alt) && parcouru[i][j + 1] == 0) {
				pos[0] = i;
				pos[1] = j + 1;
				return pos;
			}
		}

		if (i < y - 1 && j < x - 1) { // On teste la case en bas a droite
			if (encadre(grille[i][j], grille[i + 1][j + 1], alt) && parcouru[i + 1][j + 1] == 0) {
				pos[0] = i + 1;
				pos[1] = j + 1;
				return pos;
			}
		}

		if (i < y - 1) { // On teste la case en-dessous
			if (encadre(grille[i][j], grille[i + 1][j], alt) && parcouru[i + 1][j] == 0) {
				pos[0] = i + 1;
				pos[1] = j;
				return pos;
			}
		}

		if (i < y - 1 && j > 0) { // On teste la case en bas a gauche
			if (encadre(grille[i][j], grille[i + 1][j - 1], alt) && parcouru[i + 1][j - 1] == 0) {
				pos[0] = i + 1;
				pos[1] = j - 1;
				return pos;
			}
		}

		if (i > 0 && j < x - 1) { // On teste la case au-dessus a droite
			if (encadre(grille[i][j], grille[i - 1][j + 1], alt) && parcouru[i - 1][j + 1] == 0) {
				pos[0] = i - 1;
				pos[1] = j + 1;
				return pos;
			}
		}

		if (i > 0) { // On teste la case du dessus
			if (encadre(grille[i][j], grille[i - 1][j], alt) && parcouru[i - 1][j] == 0) {
				pos[0] = i - 1;
				pos[1] = j;
				return pos;
			}
		}

		if (i > 0 && j > 0) { // On teste la case au-dessus a gauche
			if (encadre(grille[i][j], grille[i - 1][j - 1], alt) && parcouru[i - 1][j - 1] == 0) {
				pos[0] = i - 1;
				pos[1] = j - 1;
				return pos;
			}
		}

		if (j > 0) { // On teste la case a gauche
			if (encadre(grille[i][j], grille[i][j - 1], alt) && parcouru[i][j - 1] == 0) {
				pos[0] = i;
				pos[1] = j - 1;
				return pos;
			}
		}
		pos[0] = -1;
		pos[1] = -1;
		return pos;
	}

	public static void affiche(int[][] parcouru) {
		String tab = "_____________________________________\n";
		for (int[] ligne : parcouru) {
			for (int elt : ligne) {
				tab += "| " + elt + " ";
			}
			tab += "|";
			tab += "\n";
		}
		System.out.println(tab);
	}
}
