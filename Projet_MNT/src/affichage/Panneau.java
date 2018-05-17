package affichage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import generation.CourbeNiveau;
import generation.PlanAlti;
import generation.Relief;
import importation.MNT;

/**
 * Classe permettant l'affichage graphique d'un MNT et de courbes de niveau
 * 
 * @param (Relief)
 *            relief1 Relief associe au pas1
 * @param (Relief)
 *            relief2 Relief associe au pas2
 * @param (double)
 *            zoomFactor Valeur du zoom
 * @param (double)
 *            prevZoomFactor Valeur precedente du zoom
 * @param (boolean)
 *            zoom Necessite de zoom en reponse d'une action de la souris
 * @param (boolean)
 *            dragger Informe d'une combinaison deplacer/clic gauche de la
 *            souris
 * @param (double)
 *            xOffset Position initiale du curseur
 * @param (double)
 *            yOffset Position initiale du curseur avant chaque deplacement
 * @param (double)
 *            xDiff deplacement horizontal du curseur
 * @param (double)
 *            yDiff deplacement vertical du curseur
 * @param (boolean)
 *            released L'utilisateur ne clique pas
 * @param (int)
 *            affiche Precise le mode d'affichage: MNT ou courbes de niveau
 * 
 */
public class Panneau extends JPanel
		implements KeyListener, MouseWheelListener, MouseListener, MouseMotionListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private final Relief relief1;
	private final Relief relief2;
	private double zoomFactor = 1;
	private double prevZoomFactor = 1;
	private boolean zoomer;
	private boolean dragger;
	private double xOffset = 0;
	private double yOffset = 0;
	private int xDiff;
	private int yDiff;
	private boolean released;
	private Point startPoint;
	int affiche;

	/**
	 * Constructeur de la classe panneau
	 * 
	 * @param gen
	 *            (int) Precise le type de fichier: Genere aleatoirement ou
	 *            extrait
	 * @param f
	 *            (MNT) fichier heritant de la classe MNT: ASC ou Alea
	 * @param pas1
	 *            (int) premier pas possible
	 * @param pas2
	 *            (int) deuxieme pas possible
	 */
	public Panneau(int gen, MNT f, int pas1, int pas2) {

		this.setBackground(Color.WHITE);
		this.setFocusable(true);
		this.addKeyListener(this);

		Object[] options2 = new Object[2];
		options2[0] = "Affichage du MNT";
		options2[1] = "Affichage des courbes de niveau";

		this.affiche = JOptionPane.showOptionDialog(null, "Choix du mode d'affichage", "Affichage", 1, 3, null,
				options2, 0); // boite de dialogue
		System.out.println("Ce qu'on veut " + affiche);

		double[][] tab = f.getGrille(); // grille des altitudes

		this.relief1 = new Relief(f, pas1);
		this.relief2 = new Relief(f, pas2);

		addMouseWheelListener(this);
		addMouseMotionListener(this);
		addMouseListener(this); // sensibilite de la souris pour le zoom

		if (this.affiche == 0) { // affichage du MNT
			try {
				BufferedImage image = new BufferedImage(f.getX(), f.getY(), BufferedImage.TYPE_INT_RGB); // Creation
																											// de
																											// l'image

				double min = f.getMin();
				double max = f.getMax();
				for (int i = 0; i < tab.length; i++) {
					for (int j = 0; j < tab[0].length; j++) { // on parcourt la
																// grille

						int a;
						if (tab[i][j] == f.getNodata()) { // Si pas de donnees
							a = 0;
						} else {
							a = (int) (((tab[i][j] - min) / (max - min)) * 255); // valeur
																					// de
																					// la
																					// composante
																					// rouge
																					// d'un
																					// pixel
						}

						Color newColor = new Color(a, 100, 0);
						image.setRGB(j, i, newColor.getRGB());
					}
				}

				File output = new File("MNT.png"); // creation de l'image
				ImageIO.write(image, "png", output); // ecriture de l'image
			}

			catch (Exception e) {
			}
		}
	}

	/**
	 * Methode d'affichage du JPannel considere
	 */
	@Override
	public void paintComponent(Graphics g) { // affichage courbe de niveau a
												// partir de deux tableaux (x et
												// y)
		super.paintComponent(g);

		if (this.affiche == 0) { // on affiche le MNT seul
			try {
				Image img = ImageIO.read(new File("MNT.png"));
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);// affichage
																				// de
																				// l'image

			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Erreur image de fond: " + e.getMessage());
			}
		} else { // affichage des courbes de niveau
			for (PlanAlti plan : this.relief1.getTable()) { // on parcourt tous
															// les plans
															// altimetriques
															// (dependant du pas
															// choisi)
				for (CourbeNiveau crb : plan.getCourbes()) { // on parcourt
																// toutes
																// lescourbes de
																// niveau
					Graphics2D g2d = (Graphics2D) g;

					if (zoomer) { // zoom
						AffineTransform at = new AffineTransform(); // methode
																	// affine de
																	// zoom
						double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
						double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY(); // mesure
																														// du
																														// roulement
																														// de
																														// la
																														// molette

						double zoomDiv = zoomFactor / prevZoomFactor; // calcul
																		// du
																		// facteur
																		// de
																		// zoom

						xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
						yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel; // Calcul
																				// de
																				// la
																				// position
																				// du
																				// curseur
																				// apres
																				// zoom

						at.translate(xOffset, yOffset);
						at.scale(zoomFactor, zoomFactor);
						prevZoomFactor = zoomFactor;
						g2d.transform(at);
						zoomer = false;
					}
					if (dragger) { // si mouvement de la souris clic gauche
									// maintenu
						AffineTransform at = new AffineTransform();
						at.translate(xOffset + xDiff, yOffset + yDiff);
						at.scale(zoomFactor, zoomFactor);
						g2d.transform(at);

						if (released) { // fin du deplacement
							xOffset += xDiff;
							yOffset += yDiff;
							dragger = false;
						}

					}
					if (plan.getAltitude() % relief2.getPas() == 0) { // Si
																		// PlanAlti
																		// de
																		// pas2
						if (relief2.getPas() == relief1.getPas()) { // Si
																	// l'utilisateur
																	// ne
																	// souhaite
																	// pass deux
																	// types de
																	// courbes
																	// de niveau
							g2d.setPaint(Color.black);
						} else {
							g2d.setPaint(Color.red);
						}
					} else {
						g2d.setPaint(Color.black);
					}
					double[][] tab = crb.conversion(); // On agence les donnees
														// commes on le souhait
														// epour faciliter leur
														// affichage
					double[] listeX = tab[0];
					double[] listeY = tab[1];
					GeneralPath polyline = new GeneralPath(GeneralPath.WIND_EVEN_ODD, listeX.length); // On
																										// creer
																										// l'objet
																										// graphique
					polyline.moveTo(listeX[0], listeY[0]);
					for (int index = 1; index < listeX.length; index++) {
						polyline.lineTo(listeX[index], listeY[index]);
					}
					g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
					g2d.draw(polyline); // On dessine notre courbe de niveau

				}
			}
		}
	}

	/**
	 * Methode qui permet de gerer le mouvement de la roulette de la souris
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		zoomer = true;
		// Zoom +
		if (e.getWheelRotation() < 0) {
			zoomFactor *= 1.1;
			repaint();
		}
		// Zoom -
		if (e.getWheelRotation() > 0) {
			zoomFactor /= 1.1;
			repaint();
		}
	}

	/**
	 * Ensemble des methodes de gestion de l'affichage et du deplacement de la
	 * carte avec la souris
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point curPoint = e.getLocationOnScreen();
		xDiff = curPoint.x - this.startPoint.x;
		yDiff = curPoint.y - this.startPoint.y;

		dragger = true;
		repaint();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		released = false;
		startPoint = MouseInfo.getPointerInfo().getLocation();

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		released = true;
		repaint();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
