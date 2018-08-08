package graphique;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JTextArea;

import generateur.Personnage;

class InterfacePerso extends Interface {
	private static final long serialVersionUID = 1L;
	private final int tailleX = 600, tailleY = 450;
	
	/**
	 * @param p le personnage que l'on presente
	 */
	public InterfacePerso(Personnage p) {
		super();
		this.personnage = p;
		
		setTitle(personnage.nom);
		setSize(tailleX,tailleY);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(4,1));
		
		// Conteneur des informations du personnage
		Container infos = new Container();
		infos.setLayout(new FlowLayout());
		infos.setSize(tailleX,50);
		tinfos = new JTextArea(p.presentation());
		tinfos.setBackground(new Color(238,238,238));
		tinfos.setEditable(false);
		infos.add(tinfos);
		
		add(infos);
		
		//Conteneurs des Caractéristiques du personnage
		//Primaires
		Container caracp = new Container();
		caracp.setLayout(new FlowLayout());
		caracp.setSize(tailleX/3, 500);
		tcarp = new JTextArea(p.caracP());
		tcarp.setBackground(new Color(238,238,238));
		tcarp.setEditable(false);
		caracp.add(tcarp);
		//Secondaires
		Container caracs = new Container();
		caracs.setLayout(new FlowLayout());
		caracs.setSize(tailleX/3, 500);
		tcars = new JTextArea(p.caracS());
		tcars.setBackground(new Color(238,238,238));
		tcars.setEditable(false);
		caracs.add(tcars);
		//Autres
		Container caraca = new Container();
		caraca.setLayout(new FlowLayout());
		caraca.setSize(tailleX/3, 500);
		tcara = new JTextArea(p.caracA());
		tcara.setBackground(new Color(238,238,238));
		tcara.setEditable(false);
		caraca.add(tcara);
		
		//Conteneur des 3 précédents
		Container carac = new Container();
		carac.setSize(tailleX, 400);
		carac.setLayout(new GridLayout(1,2));
		carac.add(caracp);
		carac.add(caracs);
		carac.add(caraca);
		add(carac);
		
		Container boutons = boutons(true, this);
		add(boutons);
		
		//Ajout de la zone d'affichage
		affichage = new JTextArea();
		affichage.setEditable(false);
		add(affichage);
		
		update("");
	}
}
