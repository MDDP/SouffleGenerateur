package generateur;

import java.util.ArrayList;

import util.ClasseInvalideException;

class Classe {
	public final Classes classe;
	int SAN = 0, FOR = 1, PHY = 2, MAG = 3, MEN = 4, AGI = 5, TEC = 6, CHA = 7, PER = 8, DIS = 9, SOC = 10, INT = 11;
	/**
	 * Taux de croissances de classe.
	 */
	private final int[] tcc = new int[8];

	public Classe (String nom) throws ClasseInvalideException {
		try {
			this.classe = Classes.valueOf(nom.toUpperCase());
			settcc();
		} catch (Exception e) {
			throw new ClasseInvalideException();
		}
	}

	/**
	 * Initialise les TCC.
	 */
	private void settcc() {
		switch (classe.id) {
			case(0):
				tcc[PHY] = -10; tcc[FOR]=10; tcc[TEC] = 15; tcc[MAG] = 20; break;
			case(1):
				tcc[PHY] = -10; tcc[FOR]=10; tcc[TEC] = 15; tcc[MAG] = 20; break;
			case(2):
				tcc[FOR] = -10; tcc[CHA]=10; tcc[TEC] = 15; tcc[SAN] = 20; break;
			case(3):
				tcc[PHY] = -10; tcc[MEN]=10; tcc[TEC] = 15; tcc[MAG] = 20; break;
			case(4):
				tcc[AGI] = -10; tcc[FOR]=10; tcc[TEC] = 15; tcc[MAG] = 20; break;
			case(5):
				tcc[MAG] = -10; tcc[AGI]=10; tcc[FOR] = 15; tcc[TEC] = 20; break;
			case(6):
				tcc[PHY] = -10; tcc[FOR]=10; tcc[TEC] = 15; tcc[AGI] = 20; break;
			case(7):
				tcc[AGI] = -10; tcc[PHY]=10; tcc[FOR] = 15; tcc[SAN] = 20; break;
			case(8):
				tcc[MAG] = -10; tcc[AGI]=10; tcc[FOR] = 15; tcc[TEC] = 20; break;
			case(9):
				tcc[PHY] = -10; tcc[TEC]=10; tcc[FOR] = 15; tcc[AGI] = 20; break;
			case(10):
				tcc[MAG] = -10; tcc[PHY]=10; tcc[FOR] = 15; tcc[TEC] = 20; break;
			case(11):
				tcc[AGI] = -10; tcc[TEC]=10; tcc[PHY] = 15; tcc[SAN] = 20; break;
			case(12):
				tcc[MAG] = -10; tcc[PHY]=10; tcc[FOR] = 15; tcc[TEC] = 20; break;
			case(13):
				tcc[PHY] = -10; tcc[FOR]=10; tcc[TEC] = 15; tcc[AGI] = 20; break;
			case(14):
				tcc[AGI] = -10; tcc[PHY]=10; tcc[FOR] = 15; tcc[TEC] = 20; break;
			case(15):
				tcc[PHY] = -10; tcc[FOR]=10; tcc[TEC] = 15; tcc[AGI] = 20; break;
		}
	}
	
	/**
	 * @return l'ordre par défaut intéressant des caracs pour une classe
	 */
	public String[] ordreCarac() {
		String[] l = Personnage.listeCaras;
		switch (classe.id) {
			case(0):
				String[] a = {l[MAG],l[TEC],l[FOR],l[PER],l[MEN],l[SOC],l[PHY],l[SAN],l[AGI],l[CHA],l[DIS],l[INT]}; return a;
			case(1):
				String[] b = {l[MAG],l[TEC],l[SOC],l[PHY],l[MEN],l[FOR],l[SAN],l[PER],l[INT],l[AGI],l[CHA],l[DIS]}; return b;
			case(2):
				String[] c = {l[SAN],l[TEC],l[MAG],l[CHA],l[INT],l[MEN],l[PER],l[AGI],l[DIS],l[SOC],l[FOR],l[PHY]}; return c;
			case(3):
				String[] d = {l[MAG],l[TEC],l[PER],l[MEN],l[SOC],l[CHA],l[SAN],l[DIS],l[AGI],l[PHY],l[INT],l[FOR]}; return d;
			case(4):
				String[] e = {l[TEC],l[MAG],l[FOR],l[PHY],l[MEN],l[PER],l[INT],l[SAN],l[DIS],l[SOC],l[AGI],l[CHA]}; return e;
			case(5):
				String[] f = {l[TEC],l[FOR],l[PER],l[DIS],l[SAN],l[PHY],l[AGI],l[MEN],l[INT],l[MAG],l[SOC],l[CHA]}; return f;
			case(6):
				String[] g = {l[DIS],l[TEC],l[CHA],l[FOR],l[PER],l[AGI],l[INT],l[PHY],l[MEN],l[SAN],l[MAG],l[SOC]}; return g;
			case(7):
				String[] h = {l[PHY],l[FOR],l[TEC],l[SAN],l[INT],l[AGI],l[MEN],l[PER],l[CHA],l[SOC],l[DIS],l[MAG]}; return h;
			case(8):
				String[] i = {l[TEC],l[SOC],l[INT],l[FOR],l[CHA],l[PER],l[DIS],l[PHY],l[MEN],l[AGI],l[SAN],l[MAG]}; return i;
			case(9):
				String[] j = {l[TEC],l[AGI],l[FOR],l[SOC],l[INT],l[CHA],l[PER],l[DIS],l[SAN],l[MEN],l[MAG],l[PHY]}; return j;
			case(10):
				String[] k = {l[TEC],l[PER],l[FOR],l[PHY],l[SAN],l[SOC],l[CHA],l[MEN],l[DIS],l[INT],l[AGI],l[MAG]}; return k;
			case(11):
				String[] q = {l[SAN],l[PHY],l[TEC],l[INT],l[MEN],l[FOR],l[SOC],l[PER],l[AGI],l[CHA],l[DIS],l[MAG]}; return q;
			case(12):
				String[] m = {l[TEC],l[FOR],l[PHY],l[AGI],l[PER],l[DIS],l[MEN],l[SAN],l[CHA],l[SOC],l[INT],l[MAG]}; return m;
			case(13):
				String[] n = {l[AGI],l[TEC],l[FOR],l[CHA],l[DIS],l[PER],l[SAN],l[PHY],l[SOC],l[MEN],l[INT],l[MAG]}; return n;
			case(14):
				String[] o = {l[PHY],l[MEN],l[TEC],l[SAN],l[FOR],l[INT],l[PER],l[CHA],l[AGI],l[SOC],l[DIS],l[MAG]}; return o;
			case(15):
				String[] p = {l[AGI],l[TEC],l[PER],l[DIS],l[CHA],l[FOR],l[MEN],l[PHY],l[SAN],l[SOC],l[INT],l[MAG]}; return p;
		}
		return null;
	}
	
	public int[] getTCC() {
		return tcc;
	}
	
	public String getNom() {
		return classe.nom;
	}
	
	/**
	 * @return le niveau nécessaire pour une classe donnée pour obtenir un prestige.
	 */
	public int niveauPourPrestige() {
		if (classe == Classes.ROUBLARD) return 16;
		else if (classe.id < 10) return 20;
		else return 31;
	}
	
	/**
	 * @return la liste des noms des prestiges possibles pour une classe donnée.
	 */
	public String[] prestigesPossibles() {
		ArrayList<String> tmp = new ArrayList<String>();
		for (Prestiges p : Prestiges.values()) 
			if (p.idClasse == classe.id) tmp.add(p.nom);
		return tmp.toArray(new String[tmp.size()]);
	}
}
