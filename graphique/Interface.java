package graphique;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import generateur.Personnage;
import util.Utilitaire;

public abstract class Interface extends JFrame{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Zone d'affichage et débugage.
	 */
	public JTextArea affichage;
	
	protected Personnage personnage;
	/**
	 * Les différentes zones de texte à afficher à l'interface concernant les informations du personnage.
	 */
	protected JTextArea tinfos, tcarp, tcars, tcara;
	
	/**
	 * Permet de mettre à jour les différents textes composant l'affichage.
	 * @param m un message à afficher directement sur l'affichage ne concernant pas le personnage directement.
	 */
	public void update(String m) {
		tinfos.setText(personnage.presentation());
		tcarp.setText(personnage.caracP());
		tcars.setText(personnage.caracS());
		tcara.setText(personnage.caracA());
		affichage.setText(m + (personnage.pretPrestige()?"\nVous pouvez choisir un prestige pour ce personnage.":""));
	}
	
	/**
	 * Crée un <code>Container</code> contenant les boutons utilisés par l'interface graphique particulière.
	 * @param perso permet de savoir quels boutons sont à créer pour l'interface.
	 * @return le <code>Container</code> avec les boutons nécessaires.
	 */
	protected Container boutons (boolean perso, Interface i) {
		Container d = new Container();
		d.setLayout(new FlowLayout());
		
		JButton quitter = new JButton("Fermer");
		quitter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (i instanceof InterfacePerso) i.dispose();
				else System.exit(1);
			}
		});
		d.add(quitter);
		
		if (perso) {
			JButton up = new JButton("Monter d'un niveau");
			up.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					update(personnage.upCara());
				}
			});
			d.add(up);
			
			JButton prestige = new JButton("Choisir un prestige");
			prestige.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					if (personnage.pretPrestige())
						new Interface_Prestige(i, personnage);
					else
						affichage.setText("Ce personnage n'est pas éligible à un prestige");
				}
			});
			d.add(prestige);
			
			JButton exporter = new JButton("Exporter le personnage");
			exporter.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					Utilitaire.impression(personnage);
					affichage.setText("Fiche imprimée à Fiches/" + personnage.nom + ".html");
				}
			});
			d.add(exporter);
			
			JButton save = new JButton("Sauvegarder le personnage");
			save.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					Utilitaire.save(personnage);
					affichage.setText("Sauvegarde effectuée à Saves/" + personnage.nom + ".txt");
				}
			});
			d.add(save);
		}
		
		return d;
	}
}