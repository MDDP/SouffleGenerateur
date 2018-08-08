package generateur;

import util.CultureInvalideException;

class Culture {
	public final Cultures culture;
	int SAN = 0, FOR = 1, PHY = 2, MAG = 3, MEN = 4, AGI = 5, TEC = 6, CHA = 7, PER = 8, DIS = 9, SOC = 10, INT = 11;
	/**
	 * Modifcateurs de carac
	 */
	private final int[] mcr = new int[12];

	public Culture (String nom) throws CultureInvalideException {
		try {
			this.culture = Cultures.valueOf(nom.toUpperCase());
			setmcr();
		} catch (Exception e) {
			throw new CultureInvalideException();
		}
	}

	/**
	 * Initialise les modificateur de caractéristiques propres à la culture
	 */
	private void setmcr () {
		switch(culture.id) {
		case(0):
			mcr[SAN] = -5; mcr[MAG] = mcr[SOC] = 5; break;
		case(1):
			mcr[MAG] = -5; mcr[PHY] = mcr[TEC] = 5; break;
		case(2):
			mcr[MAG] = -5; mcr[PHY] = mcr[MEN] = 5; break;
		case(3):
			mcr[MAG] = -5; mcr[FOR] = mcr[TEC] = 5; break;
		case(4):
			mcr[MAG] = -5; mcr[TEC] = mcr[SOC] = 5; break;
		case(5):
			mcr[MEN] = -5; mcr[FOR] = mcr[PER] = 5; break;
		case(6):
			mcr[PHY] = -5; mcr[AGI] = mcr[TEC] = 5; break;
		case(7):
			mcr[INT] = -5; mcr[SAN] = mcr[MAG] = 5; break;
		case(8):
			mcr[MEN] = -5; mcr[SAN] = mcr[TEC] = 5; break;
		case(9):
			mcr[MEN] = -5; mcr[MEN] = mcr[INT] = 5; break;
		case(10):
			mcr[PHY] = -5; mcr[TEC] = mcr[DIS] = 5; break;
		case(11):
			mcr[SAN] = -5; mcr[CHA] = mcr[SOC] = 5; break;
		case(12):
			mcr[SOC] = -5; break;
		}
	}

	public int[] getMCR() {
		return mcr;
	}

	public String getNom() {
		return culture.nom;
	}
}
