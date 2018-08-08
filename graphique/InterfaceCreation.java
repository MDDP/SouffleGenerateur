package graphique;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import generateur.Classes;
import generateur.Cultures;
import generateur.Ethnies;
import generateur.Personnage;
import util.ClasseInvalideException;
import util.CultureInvalideException;
import util.PrestigeInvalideException;
import util.PrioriteFormatException;
import util.SaveInvalideException;
import util.Utilitaire;

public class InterfaceCreation extends Interface {
	private static final long serialVersionUID = 1L;
	private boolean setPriorites = false;
	private Interface_Priorites interface_prio;
	
	/**
	 * Fenêtre principale du programme
	 */
	public InterfaceCreation() {
		super();
		setSize(300,500);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(6,1));
		setTitle("Créateur de Personnage");
		
		Container i = new Container();
		i.setLayout(new FlowLayout());
		
		i.add(new JLabel("Nom: "));
		JTextArea nom = new JTextArea();
		nom.setEditable(true);
		nom.setText("Personnage");
		i.add(nom);
		
		i.add(new JLabel("Âge: "));
		JTextArea age = new JTextArea();
		age.setEditable(true);
		age.setText("18");
		i.add(age);
		
		Container b = new Container();
		b.setLayout(new FlowLayout());
		
		//Liste déroulante avec les cultures
		JComboBox<String> cultures = new JComboBox<String>();
		cultures.addItem("Culture");
		for (Cultures r : Cultures.values()) cultures.addItem(r.nom);
		b.add(cultures);
		
		//Liste déroulante avec les ethnies (optionnelle)
		JComboBox<String> ethnies = new JComboBox<String>();
		ethnies.addItem("Ethnie");
		for (Ethnies e : Ethnies.values()) ethnies.addItem(e.nom);
		b.add(ethnies);
		
		//Liste déroulante avec les classes
		JComboBox<String> classes = new JComboBox<String>();
		classes.addItem("Classe");
		for (Classes c : Classes.values()) classes.addItem(c.nom);
		b.add(classes);
		
		add(i);
		add(b);
		
		JButton creation = new JButton("Créer le personnage");
		creation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run(){
						try {
							if (setPriorites) {
								new InterfacePerso(new Personnage(nom.getText(),Integer.parseInt(age.getText()),
										cultures.getSelectedItem().toString(),classes.getSelectedItem().toString(),
										ethnies.getSelectedItem().toString(),interface_prio.getPriorites()));
								interface_prio.dispose();
								setPriorites = false;
							} else
								new InterfacePerso(new Personnage(nom.getText(),Integer.parseInt(age.getText()),
										cultures.getSelectedItem().toString(),classes.getSelectedItem().toString(),
										ethnies.getSelectedItem().toString()));
						// Gestion des erreurs et affichage des messages d'erreur correspondant.
						} catch (NumberFormatException e) { affichage.setText("Veuillez entrez un âge correct.");
						} catch (PrioriteFormatException e) {
							affichage.setText("Liste de priorité incorrecte. Veuillez vérifier votre liste.");
							interface_prio.dispose();
							setPriorites = false;
						} catch (CultureInvalideException e) { affichage.setText("Veuillez choisir une culture ou une ethnie.");
						} catch (ClasseInvalideException e) { affichage.setText("Veuillez choisir une classe.");
						} catch (PrestigeInvalideException e) { affichage.setText("Veuillez choisir un prestige");
						} catch (Exception e) { affichage.setText("Problème rencontré."); e.toString(); }
					}
				});
			}
		});
		Container c = new Container();
		c.setLayout(new FlowLayout());
		c.add(creation);
		add(c);
		
		
		Container boutons = boutons(false, this);
		JButton Bpriorites = new JButton("Priorités de carac");
		Bpriorites.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setPriorites = true;
				interface_prio = new Interface_Priorites();
			}
		});
		boutons.add(Bpriorites);
		add(boutons);
		
		
		Container d = new Container();
		d.setLayout(new FlowLayout());
		//Liste contenant le nom de toutes les sauvegardes
		JComboBox<String> liste = new JComboBox<String>();
		load(liste);
		d.add(liste);
		
		JButton load = new JButton("Charger Perso");
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String tmp = (String) liste.getSelectedItem();
				if (tmp.equals("Sélection du personnage") || tmp.equals("TemplateSave.txt"))
					affichage.setText("Mauvaise sélection de sauvegarde.");
				else {
					affichage.setText(tmp);
					try {
						new InterfacePerso(Utilitaire.load(tmp));
					} catch (SaveInvalideException e) {
						System.out.println(e);
						affichage.setText("Sauvegarde incorrecte.");
						liste.removeItem(tmp);
						load(liste);
					}
				}
			}
		});
		d.add(load);
		JButton refresh = new JButton("Refresh Sélec");
		refresh.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				load(liste);
			}
		});
		d.add(refresh);
		add(d);
		
		affichage = new JTextArea();
		affichage.setEditable(false);
		add(affichage);
	}
	
	private void load (JComboBox<String> liste) {
		liste.removeAllItems();
		File dossier = new File("Saves/");
		liste.addItem("Sélection du personnage");
		//Sauf le template
		for (String s : dossier.list()) if (!s.equals("TemplateSave.txt")) liste.addItem(s);
	}
}
