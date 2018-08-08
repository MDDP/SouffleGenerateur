package generateur;

public enum Ethnies {
	TYFELDIEN(0,"Tyfeldien"),
	WHELDIVEUN(1,"Wheldiveun"),
	LENE(2,"Lene"),
	ELTIRAN(3,"Eltiran"),
	YRIAN(4,"Yrian"),
	MOGRURVIK(5,"Mogrurvik"),
	DETI_VERESK(6,"Deti Veresk"),
	PETIT_BARBARE(7,"Petit Barbare"),
	DEMI(8,"Demi"),
	ELFE(9,"Elfe"),
	JVIRZUL(10,"Jvirzul");
	
	public final int id;
	public final String nom;
	
	private Ethnies(int id, String nom) {
		this.id = id; this.nom = nom;
	}
}
