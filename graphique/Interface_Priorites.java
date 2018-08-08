package graphique;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import generateur.Personnage;
import util.PrioriteFormatException;

class Interface_Priorites extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private final String[] priorites = new String[12];
	private CaraComboBox[] ccb = new CaraComboBox[12];
	
	public Interface_Priorites () {
		setVisible(true);
		setTitle("Gestionnaire de Priorité");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
		setSize(400,350);
		
		Container boxes = new Container();
		boxes.setLayout(new GridLayout(12,2));
		for (int i = 0; i < 12; i++) {
			boxes.add(new JLabel(i + 1 + "e carac : "));
			ccb[i] = new CaraComboBox();
			boxes.add(ccb[i]);
		}
		
		add(boxes);
		
		Container bis = new Container();
		bis.setLayout(new GridLayout());
		JButton reset = new JButton("reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boxes.removeAll();
				for (int i = 0; i < 12; i++) {
					boxes.add(new JLabel(i + 1 + "e carac : "));
					ccb[i] = new CaraComboBox();
					boxes.add(ccb[i]);
				}
				revalidate();
			}
		});
		bis.add(reset);
		add(bis);
	}
	
	/**
	 * @return
	 * @throws PrioriteFormatException si la liste créée contient une erreur i.e. si la liste contient deux fois la même valeur, ou si une valeur n'est pas assignée.
	 */
	public String[] getPriorites () throws PrioriteFormatException {
		for (int i = 0; i < 12; i++) priorites[i] = (String) ccb[i].getSelectedItem();
		for (int i = 0; i < 11; i++) {
			if (priorites[i].equals("Carac à choisir"))  throw new PrioriteFormatException();
			for (int j = i+1; j < 12; j++)
				if (priorites[i].equals(priorites[j])) throw new PrioriteFormatException();
		}
		return priorites;
	}
	
	/**
	 * Liste déroulante pour les caractéristiques
	 * @author Mael
	 */
	class CaraComboBox extends JComboBox<String> {
		private static final long serialVersionUID = 1L;
		
		public CaraComboBox () {
			this.addItem("Carac à choisir");
			for (String s : Personnage.listeCaras) {
				this.addItem(s);
				this.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						for (CaraComboBox box : ccb)
							if (box != e.getSource()) box.removeItem(getSelectedItem());
					}
				});
			}
		}
	}
}
