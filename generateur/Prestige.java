package generateur;

import util.PrestigeInvalideException;

class Prestige {
	public final Prestiges prestige;
	int SAN = 0, FOR = 1, PHY = 2, MAG = 3, MEN = 4, AGI = 5, TEC = 6, CHA = 7;
	/**
	 * Taux de croissances de prestige
	 */
	private final int[] tcp = new int[8];
	
	public Prestige (String nom) throws PrestigeInvalideException {
		try {
			this.prestige = Prestiges.valueOf(nom.toUpperCase());
			settcp();
		} catch (Exception e) {
			throw new PrestigeInvalideException();
		}
	}
	
	/**
	 * Initialise les TCP.
	 */
	private void settcp() {
		switch (prestige.id) {
			case(0):
				tcp[CHA] = -10; tcp[TEC] = 25; tcp[MAG] = 20; break;
			case(1):
				tcp[SAN] = -10; tcp[TEC] = 25; tcp[FOR] = 20; break;
			case(2):
				tcp[AGI] = -10; tcp[MAG] = 25; tcp[MEN] = 20; break;
			case(3):
				tcp[AGI] = -10; tcp[PHY] = 25; tcp[MEN] = 20; break;
			case(4):
				tcp[PHY] = -10; tcp[MAG] = 25; tcp[CHA] = 20; break;
			case(5):
				tcp[MEN] = -10; tcp[MAG] = 25; tcp[FOR] = 20; break;
			case(6):
				tcp[PHY] = -10; tcp[MAG] = 25; tcp[MEN] = 20; break;
			case(7):
				tcp[SAN] = -10; tcp[MEN] = 25; tcp[FOR] = 20; break;
			case(8):
				tcp[SAN] = -10; tcp[MAG] = 25; tcp[FOR] = 20; break;
			case(9):
				tcp[AGI] = -10; tcp[PHY] = 25; tcp[MEN] = 20; break;
			case(10):
				tcp[PHY] = -10; tcp[AGI] = 25; tcp[TEC] = 20; break;
			case(11):
				tcp[PHY] = -10; tcp[TEC] = 25; tcp[AGI] = 20; break;
			case(12):
				tcp[PHY] = -10; tcp[MAG] = 25; tcp[TEC] = 20; break;
			case(13):
				tcp[PHY] = -10; tcp[AGI] = 25; tcp[TEC] = 20; break;
			case(14):
				tcp[PHY] = -10; tcp[TEC] = 25; tcp[FOR] = 20; break;
			case(15):
				tcp[AGI] = -10; tcp[FOR] = 25; tcp[PHY] = 20; break;
			case(16):
				tcp[CHA] = -10; tcp[TEC] = 25; tcp[FOR] = 20; break;
			case(17):
				tcp[CHA] = -10; tcp[AGI] = 25; tcp[FOR] = 20; break;
			case(18):
				tcp[MEN] = -10; tcp[FOR] = 25; tcp[AGI] = 20; break;
			case(19):
				tcp[PHY] = -10; tcp[AGI] = 25; tcp[FOR] = 20; break;
			case(20):
				tcp[PHY] = -10; tcp[TEC] = 25; tcp[AGI] = 20; break;
		}
	}
	
	public String getNom() {
		return prestige.nom;
	}
	
	public int[] getTCP() {
		return tcp;
	}
}
