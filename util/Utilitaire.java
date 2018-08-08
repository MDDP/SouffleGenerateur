package util;

import java.io.*;
import java.util.Hashtable;
import java.util.Scanner;

import generateur.Personnage;

public class Utilitaire {
	/**
	 * Prend un personnage et en imprime la fiche dans un fichier.
	 * @param p le personnage à "imprimer"
	 */
	public static void impression(Personnage p) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Fiches/"+p.nom+".html")));
			out.print(p.impression());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Prend un personnage en argument et l'enregistre, afin de le recharger plus tard
	 * @param p le personnage à "sauvegarder"
	 */
	public static void save (Personnage p) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Saves/"+p.nom+".txt")));
			out.print(p.save());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Personnage load (String fichier) throws SaveInvalideException {
		try {
			String path = "Saves/" + fichier;
			String tmp1;
			String[] tmp2;
			Scanner in = new Scanner(new File (path));
			if (!in.nextLine().equals("personnage")) {
				in.close();
				throw new SaveInvalideException();
			}
			
			// Extraction des informations principales
			String nom = in.nextLine().substring(4);
			int age = Integer.parseInt(in.nextLine().substring(4));
			int niveau = Integer.parseInt(in.nextLine().substring(7));
			String ethnie = in.nextLine().substring(7);
			String culture = in.nextLine().substring(8);
			String classe = in.nextLine().substring(7);
			String prestige = in.nextLine().substring(9);
			
			// Extraction des caras et tcs
			Hashtable<String, Integer> caras = new Hashtable<String, Integer>();
			Hashtable<String, Integer> tcs = new Hashtable<String, Integer>();
			// Primaires
			for (int i = 0; i < 8; i++) {
				tmp1 = in.nextLine();
				tmp2 = tmp1.split(" ");
				tcs.put(tmp2[0], Integer.parseInt(tmp2[1]));
				caras.put(tmp2[0], Integer.parseInt(tmp2[2]));
			}
			// Secondaires
			for (int i = 0; i < 4; i++) {
				tmp1 = in.nextLine();
				tmp2 = tmp1.split(" ");
				caras.put(tmp2[0], Integer.parseInt(tmp2[1]));
			}
			
			in.close();
			return new Personnage(nom, age, culture, classe, ethnie, prestige, niveau, caras, tcs);
		} catch (Exception e) {
			System.out.println(e);
			throw new SaveInvalideException();
		}
	}
}
