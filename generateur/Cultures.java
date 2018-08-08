package generateur;

public enum Cultures {
	TYFELDIEN(0,"Tyfeldien"),
	WHELDIVEUN(1,"Wheldiveun"),
	LENE(2,"Lene"),
	ELTIRAN(3,"Eltiran"),
	YRIAN(4,"Yrian"),
	MOGRURVIK(5,"Mogrurvik"),
	DETI_VERESK(6,"Deti Veresk"),
	PETIT_BARBARE(7,"Petit Barbare"),
	DEMI(8,"Demi"),
	NOKTAN(9,"Noktan"),
	DRYANI(10,"Dryani"),
	AYARIL(11,"Ayaril"),
	JVIRZUL(12,"Jvirzul");
	
	public final int id;
	public final String nom;
	
	private Cultures(int id, String nom) {
		this.id = id; this.nom = nom;
	}
}
