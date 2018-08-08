package generateur;

public enum Prestiges {
	ARCHISCRIBE(0,"Archiscribe",0),
	HIEROPHANTE(1,"Hierophante",0),
	ELU_DE_LA_NATURE(2,"Elu de la Nature",1),
	CHANGE_FORME(3,"Change Forme",1),
	DAMNE(4,"Damne",2),
	PURGATEUR(5,"Purgateur",2),
	ARCHIMAGE(6,"Archimage",3),
	VOLEUR_DE_SORTS(7,"Voleur de Sorts",3),
	INQUISITEUR(8,"Inquisiteur",4),
	PALADIN(9,"Paladin",4),
	RODEUR(10,"Rodeur",5),
	PISTOLIER(11,"Pistolier",5),
	ASSASSIN_THAUMATURGE(12,"Assassin Thaumaturge",6),
	MARCHE_OMBRE(13,"Marche Ombre",6),
	MAITRE_DES_PIEGES(14,"Maitre des Pieges",6),
	BRUTE(15,"Brute",7),
	MAITRE_D_ARMES(16,"Maitre d'Armes",7),
	CHEVAUCHEUR(17,"Chevaucheur",8),
	CHEF_DE_MEUTE(18,"Chef de Meute",8),
	MAGELAME(19,"Magelame", 9),
	DANSEUR_DE_LAMEs(20,"Danseur de Lames",9);
	
	public final int id;
	public final String nom;
	/**
	 * Id de la classe dont dépend le prestige
	 */
	public final int idClasse;
	
	private Prestiges(int id, String nom, int idClasse) {
		this.id = id; this.nom = nom; this.idClasse = idClasse;
	}
}
