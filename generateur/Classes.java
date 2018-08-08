package generateur;

public enum Classes {
	SCRIBOMAGE(0,"Scribomage"),
	CHAMAN(1,"Chaman"),
	DEMONANCIEN(2,"Demonancien"),
	ADEPTE(3,"Adepte"),
	MOINE(4,"Moine"),
	TRAPPEUR(5,"Trappeur"),
	ROUBLARD(6,"Roublard"),
	GUERRIER(7,"Guerrier"),
	DRESSEUR(8,"Dresseur"),
	BRETTEUR(9,"Bretteur"),
	INGENIEUR(10,"Ingenieur"),
	GUERRIER_CANNONIER(11,"Guerrier Cannonier"),
	MEDECIN_DE_GUERRE(12,"Medecin de Guerre"),
	TRACEUR(13,"Traceur"),
	SAPEUR(14,"Sapeur"),
	OISEAU_DE_PROIE(15,"Oiseau de Proie");
	
	public final int id;
	public final String nom;
	
	private Classes(int id, String nom) {
		this.id = id; this.nom = nom;
	}
}
