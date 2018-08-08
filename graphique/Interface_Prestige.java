package graphique;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import generateur.Personnage;
import util.PrestigeInvalideException;

/**
 * Interface spécifique pour initialiser un prestige.<br>
 * Contient un objet JComboBox et un objet JButton.
 * @see JComboBox
 * @see JButton
 * @author Mael
 * 
 */
class Interface_Prestige extends JFrame{
	private static final long serialVersionUID = 1L;

	/**
	 * @param interf l'interface du personnage dont dépend la fenêtre instanciée. Utile seulement pour la rafraichir et le message d'erreur.
	 * @param personnage le personnage dont on va choisir le prestige.
	 */
	public Interface_Prestige(Interface interf, Personnage personnage) {
		setSize(100,100);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
		
		JComboBox<String> choixPrestige = new JComboBox<String>();
		choixPrestige.addItem("Prestige");
		for (String s : personnage.prestigesPossibles()) choixPrestige.addItem(s);
		add(choixPrestige);
		
		JButton confirmation = new JButton("Confirmer");
		confirmation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					personnage.setPrestige((String) choixPrestige.getSelectedItem());
					interf.update(((String) choixPrestige.getSelectedItem()) + " prestige choisi.");
					dispose();
				} catch (PrestigeInvalideException e1) {
					interf.affichage.setText("Prestige invalide, veuillez choisir un prestige valide.");
				}
			}
		});
		add(confirmation);
	}
}
