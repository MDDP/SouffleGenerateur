package generateur;

import java.util.ArrayList;
import java.util.Hashtable;

import util.PrestigeInvalideException;

public class Personnage {
	private Hashtable<String,Integer> caras = new Hashtable<String,Integer>(), tcs = new Hashtable<String,Integer>();
	private Culture culture;
	private Classe classe;
	private Ethnies ethnie;
	private Prestige prestige;
	public final String nom;
	private int niveau, age;
	/**
	 * Liste des clés pour les Caras et les TCs dans les tables de hashage
	 */
	public final static String[] listeCaras = {"San", "For", "Phy", "Mag", "Men", "Agi", "Tec", "Cha", "Per", "Dis",
			"Soc", "Int"}, listeTCs = {"San", "For", "Phy", "Mag", "Men", "Agi", "Tec", "Cha"};
	
	public Personnage (String nom, int age, String culture, String classe, String ethnie, String[] ordreCara) throws Exception {
		this.nom = nom;
		// Formatage des String à cause des espaces.
		culture = ethnie.replace(' ', '_');
		ethnie = ethnie.replace(' ', '_');
		classe = classe.replace(' ', '_');

		Culture tmpcul = new Culture(culture);
		Classe tmpcla = new Classe(classe);
		if (!ethnie.equals("Ethnie"))
			personnage(age, tmpcul, tmpcla, Ethnies.valueOf(ethnie.toUpperCase()), ordreCara);
		else
			personnage(age, tmpcul, tmpcla, null, ordreCara);
	}

	public Personnage (String nom, int age, String culture, String classe, String ethnie) throws Exception {
		this.nom = nom;
		// Formatage des String à cause des espaces.
		culture = ethnie.replace(' ', '_');
		ethnie = ethnie.replace(' ', '_');
		classe = classe.replace(' ', '_');

		Culture tmpcul = new Culture(culture);
		Classe tmpcla = new Classe(classe);
		if (!ethnie.equals("Ethnie"))
			personnage(age, tmpcul, tmpcla,Ethnies.valueOf(ethnie.toUpperCase()), tmpcla.ordreCarac());
		else
			personnage(age, tmpcul, tmpcla,null, tmpcla.ordreCarac());
	}
	
	/**
	 * Constructeur pour le chargement
	 */
	public Personnage (String nom, int age, String culture, String classe, String ethnie, String prestige, int niveau,
			Hashtable<String,Integer> caras, Hashtable<String,Integer> tcs) throws Exception {
		this.nom = nom;
		this.age = age;
		this.niveau = niveau;
		this.culture = new Culture(culture);
		this.ethnie = Ethnies.valueOf(ethnie.toUpperCase());
		this.classe = new Classe(classe);
		if (prestige.length() != 0) this.prestige = new Prestige(prestige);
		
		this.caras = caras;
		this.tcs = tcs;
	}
	
	/**
	 * Fonction auxiliaire pour la création d'un Personnage
	 */
	private void personnage (int age, Culture culture, Classe classe, Ethnies ethnie, String[] ordreCara) {
		this.niveau = 1;
		this.age = age;
		this.culture = culture;
		this.classe = classe;
		this.ethnie = ethnie;
		
		// Oon randomise les caracs du perso
		rndCara(ordreCara);

		// Puis on rajoute les modificateurs de classe et de race
		int[] mcr = this.culture.getMCR();
		int[] tcc = this.classe.getTCC();
		for(int i = 0; i < 8; i++) {
			caras.put(listeCaras[i], caras.get(listeCaras[i])+mcr[i]);
			int tmp = tcs.get(listeTCs[i]);
			tmp = (tmp + tcc[i] < 0)?0:tmp + tcc[i];
			tcs.put(listeTCs[i], tmp);
		}
		for(int i = 8; i < 12; i++)
			caras.put(listeCaras[i], caras.get(listeCaras[i])+mcr[i]);
	}

	
	/**
	 * @return les informations du personnage (comme sur la fiche)
	 */
	public String presentation () {
		String ans = "Informations du personnage";
		ans += "\nNom: " + nom + "   Culture: " + culture.getNom() + ((ethnie!=null)?"   Ethnie: " + ethnie.nom:"");
		ans += "\nAge: " + age + "   Niveau: " + niveau + "   Classe: " + classe.getNom() + "   Prestige: " + ((prestige!=null)?prestige.getNom():"");
		return ans;
	}
	
	/**
	 * @return les caracs et tcs principaux
	 */
	public String caracP () {
		String ans = "Caractéristiques principales";
		ans += "\nSan: " + tcs.get("San") + "%| " + caras.get("San") + "    Tec: " + tcs.get("Tec") + "%| " + caras.get("Tec");
		ans += "\nFor: " + tcs.get("For") + "%| " + caras.get("For") + "    Phy: " + tcs.get("Phy") + "%| " + caras.get("Phy");
		ans += "\nMag: " + tcs.get("Mag") + "%| " + caras.get("Mag") + "    Men: " + tcs.get("Men") + "%| " + caras.get("Men");
		ans += "\nAgi: " + tcs.get("Agi") + "%| " + caras.get("Agi") + "    Cha: " + tcs.get("Cha") + "%| " + caras.get("Cha");
		return ans;
	}
	
	/**
	 * @return les caracs secondaires
	 */
	public String caracS () {
		String ans = "Caractéristiques secondaires"; 
		ans += "\nPer: " + caras.get("Per");
		ans += "\nDis: " + caras.get("Dis");
		ans += "\nSoc: " + caras.get("Soc");
		ans += "\nInt: " + caras.get("Int");
		return ans;
	}
	
	/**
	 * @return les caracs auxiliaires (PVs, esquive, puissance tec...)
	 */
	public String caracA () {
		String ans = "Autres caractéristiques"; 
		ans += "\nPVs: " + (5 + (caras.get("San") + caras.get("Phy"))/5);
		ans += "\nEsq: " + (caras.get("Agi") + caras.get("Cha"))/5 + "   Ini: " + caras.get("Agi")/10;
		ans += "\nAtt: " + caras.get("For")/10 + "   Def: " + caras.get("Phy")/10;
		ans += "\nPui: " + caras.get("Mag")/10 + "   Res: " + caras.get("Men")/10;
		ans += "\nSeuil critique:+" + caras.get("Cha")/100;
		return ans;
	}
	
	/**
	 * Assigne des caracs random selon prioritées de la classe
	 */
	private void rndCara(String[] ordre) {
		// Création d'une liste de cara, que l'on trie.
		ArrayList<Integer> rndcara = new ArrayList<Integer>();
		for (int i = 0; i < 12; i++) {
			int a = ((int)(Math.random()*8+1)), b =((int)(Math.random()*8+1));
			int tmp = Math.max(a, b)*10 + Math.min(a, b);
			rndcara.add((tmp%5 > 2)? (tmp/5+1)*5 : (tmp/5)*5);
		}
		rndcara.sort(null);
		
		//Puis on assigne les valeurs selon l'ordre de la classe dans la table de hashage avec sa clé(nom de la cara).
		for (int i = 0; i < ordre.length; i++) {
			int tmp = rndcara.remove(rndcara.size()-1);
			if (culture.culture.equals(Cultures.JVIRZUL) && i < 2) tmp += 5;
			caras.put(ordre[i], tmp);
		}
		
		//On finit par les TCs
		for (int i = 0; i < 8; i++) {
			int tmp = (int)(Math.random()*20+1);
			if (tmp < 5) tmp = 5;
			tcs.put(listeTCs[i], tmp);
		}
	}

	/**
	 * Up des cara sur montée de niveau
	 */
	public String upCara() {
		if (niveau == 30) return "Niveau max";
		niveau++;
		String ans = "";
		for (int i = 0; i < 8; i++) {
			int a = ((int)(Math.random()*100));
			if (a <= tcs.get(listeTCs[i])) {
				caras.put(listeCaras[i], caras.get(listeCaras[i]) + ((a < 5)?6:5));
				ans += listeCaras[i] + " augmenté\n";
			}
		}
		return ans;
	}
	
	/**
	 * @return True si le personnage à ce niveau précis peut obtenir un prestige, False sinon.
	 */
	public boolean pretPrestige() {
		return classe.niveauPourPrestige() <= niveau && prestige == null;
	}
	
	/**
	 * @return la liste des prestiges possibles pour une classe donnée.
	 */
	public String[] prestigesPossibles() {
		return classe.prestigesPossibles();
	}
	
	/**
	 * @param name le nom du prestige à donner.
	 * @throws PrestigeInvalideException indique que le prestige n'a pas été choisi correctement, soit que l'élément de la liste déroulante des prestiges était l'élément par défaut.
	 */
	public void setPrestige(String name) throws PrestigeInvalideException{
		// Cas particulier à cause de l'apostrophe
		if (name.equals(Prestiges.MAITRE_D_ARMES.nom)) name = "MAITRE_D_ARMES";
		// Formatage du nom
		name = name.replace(' ', '_');
		prestige = new Prestige(name);
		
		// Ajout des TCP aux TCs
		int[] tcp = prestige.getTCP();
		for(int i = 0; i < 8; i++) {
			int tmp = tcs.get(listeTCs[i]);
			tmp = (tmp + tcp[i] < 0)?0:tmp + tcp[i];
			tcs.put(listeTCs[i], tmp);
		}
	}
	
	/**
	 * @return le personnage au format html, pour l'impression
	 */
	public String impression () {
		//bullshit incompréhensible
		String ans = "<!DOCTYPE html>\n<head>\n"
				+ "	<title>" + nom + "</title>\n"
				+ "	<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>\n"
				+ "	<style type='text/css'>\n"
				+ "	<!--\n"
				+ "		p {margin: 0; padding: 0;}	.ft00{font-size:18px;font-family:Times;color:#000000;}\n"
				+ "		.ft01{font-size:14px;font-family:Times;color:#000000;}\n"
				+ "		.ft02{font-size:17px;font-family:Times;color:#000000;}\n"
				+ "		.ft03{font-size:14px;font-family:Times;color:#000000;}\n"
				+ "		.ft04{font-size:11px;font-family:Times;color:#000000;}\n"
				+ "		.ft05{font-size:9px;font-family:Times;color:#000000;}\n"
				+ "		.ft06{font-size:11px;font-family:Times;color:#000000;}\n"
				+ "	-->\n"
				+ "	</style>\n"
				+ "</head>\n"
				+ "<body bgcolor='#A0A0A0' vlink='blue' link='blue'>\n"
				+ "	<div id='page1-div' style='position:relative;width:892px;height:1263px;'>\n"
				+ "		<img width='892' height='1263' src='fond.png' alt='background image'/>\n"
				//informations du personnage
				+ "		<p style='position:absolute;top:10px;left:365px;white-space:nowrap' class='ft00'><b>Fiche&#160;du&#160;Personnage</b></p>\n"
				+ "		<p style='position:absolute;top:38px;left:7px;white-space:nowrap' class='ft01'><b>Nom</b>&#160;" + nom +"</p>\n"
				+ "		<p style='position:absolute;top:38px;left:295px;white-space:nowrap' class='ft01'><b>Ethnie</b>&#160;" + ethnie.nom + "</p>\n"
				+ "		<p style='position:absolute;top:38px;left:596px;white-space:nowrap' class='ft01'><b>Culture</b>&#160;" + culture.getNom() + "</p>\n"
				+ "		<p style='position:absolute;top:64px;left:7px;white-space:nowrap' class='ft01'><b>Age</b>&#160;" + age + "</p>\n"
				+ "		<p style='position:absolute;top:64px;left:148px;white-space:nowrap' class='ft01'><b>Niveau</b>&#160;" + niveau + "</p>\n"
				+ "		<p style='position:absolute;top:64px;left:295px;white-space:nowrap' class='ft01'><b>Classe</b>&#160;" + classe.getNom() + "</p>\n"
				+ "		<p style='position:absolute;top:64px;left:596px;white-space:nowrap' class='ft01'><b>Prestige</b>&#160;" + ((prestige!=null)?prestige.getNom():"") + "</p>\n"
				//caractéristiques
				+ "		<p style='position:absolute;top:100px;left:40px;white-space:nowrap' class='ft02'><b>Caract&eacute;ristiques&#160;Principales</b></p>\n"
				//rajout du <p> pour la cara (l'espacer d'environ 83px suffit)
				+ "		<!-- Pour modifier les TCs, modifier la valeur suivant TC:&#160; -->\n"
				+ "		<!-- Pour modifier les caractérisitque, modifier les valeurs sous les lignes TC juste avant la balise </p> -->\n"
				+ "		<p style='position:absolute;top:133px;left:63px;white-space:nowrap' class='ft03'><b>San</b></p>\n"
				+ "		<p style='position:absolute;top:133px;left:165px;white-space:nowrap' class='ft03'><b>Tec</b></p>\n"
				+ "		<p style='position:absolute;top:136px;left:187px;white-space:nowrap' class='ft04'>(mag,&#160;mar,&#160;mec)</p>\n"
				+ "		<!-- Santé -->\n"
				+ "		<p style='position:absolute;top:168px;left:7px;white-space:nowrap' class='ft01'>TC:&#160;" + tcs.get("San") + "%</p>\n"
				+ "		<p style='position:absolute;top:168px;left:90px;white-space:nowrap' class='ft01'>" + caras.get("San") + "</p>\n"
				+ "		<!-- Technique -->\n"
				+ "		<p style='position:absolute;top:168px;left:148px;white-space:nowrap' class='ft01'>TC:&#160;" + tcs.get("Tec") + "%</p>\n"
				+ "		<p style='position:absolute;top:168px;left:231px;white-space:nowrap' class='ft01'>" + caras.get("Tec") + "</p>\n"
				+ "		<p style='position:absolute;top:200px;left:65px;white-space:nowrap' class='ft03'><b>For</b></p>\n"
				+ "		<p style='position:absolute;top:200px;left:204px;white-space:nowrap' class='ft03'><b>Phy</b></p>\n"
				+ "		<!-- Force -->\n"
				+ "		<p style='position:absolute;top:235px;left:7px;white-space:nowrap' class='ft01'>TC:&#160;" + tcs.get("For") + "%</p>\n"
				+ "		<p style='position:absolute;top:235px;left:90px;white-space:nowrap' class='ft01'>" + caras.get("For") + "</p>\n"
				+ "		<!-- Physique -->\n"
				+ "		<p style='position:absolute;top:235px;left:148px;white-space:nowrap' class='ft01'>TC:&#160;" + tcs.get("Phy") + "%</p>\n"
				+ "		<p style='position:absolute;top:235px;left:231px;white-space:nowrap' class='ft01'>" + caras.get("Phy") + "</p>\n"
				+ "		<p style='position:absolute;top:267px;left:62px;white-space:nowrap' class='ft03'><b>Mag</b></p>\n"
				+ "		<p style='position:absolute;top:267px;left:202px;white-space:nowrap' class='ft03'><b>Men</b></p>\n"
				+ "		<!-- Magie -->\n"
				+ "		<p style='position:absolute;top:302px;left:7px;white-space:nowrap' class='ft01'>TC:&#160;" + tcs.get("Mag") + "%</p>\n"
				+ "		<p style='position:absolute;top:302px;left:90px;white-space:nowrap' class='ft01'>" + caras.get("Mag") + "</p>\n"
				+ "		<!-- Mental -->\n"
				+ "		<p style='position:absolute;top:302px;left:148px;white-space:nowrap' class='ft01'>TC:&#160;" + tcs.get("Men") + "%</p>\n"
				+ "		<p style='position:absolute;top:302px;left:231px;white-space:nowrap' class='ft01'>" + caras.get("Men") + "</p>\n"
				+ "		<p style='position:absolute;top:334px;left:65px;white-space:nowrap' class='ft03'><b>Agi</b></p>\n"
				+ "		<p style='position:absolute;top:334px;left:203px;white-space:nowrap' class='ft03'><b>Cha</b></p>\n"
				+ "		<!-- Agilité -->\n"
				+ "		<p style='position:absolute;top:369px;left:7px;white-space:nowrap' class='ft01'>TC:&#160;" + tcs.get("Agi") + "%</p>\n"
				+ "		<p style='position:absolute;top:369px;left:90px;white-space:nowrap' class='ft01'>" + caras.get("Agi") + "</p>\n"
				+ "		<!-- Chance -->\n"
				+ "		<p style='position:absolute;top:369px;left:148px;white-space:nowrap' class='ft01'>TC:&#160;" + tcs.get("Cha") + "%</p>\n"
				+ "		<p style='position:absolute;top:369px;left:231px;white-space:nowrap' class='ft01'>" + caras.get("Cha") + "</p>\n"
				//trucs inutiles encore (à part les PVs)
				+ "		<p style='position:absolute;top:100px;left:365px;white-space:nowrap' class='ft02'><b>&Eacute;tat&#160;du&#160;Personnage</b></p>\n"
				+ "		<!-- PVs -->\n"
				+ "		<p style='position:absolute;top:130px;left:301px;white-space:nowrap' class='ft01'><b>Points de Vie&#160;&#160;</b>" + (5 + (caras.get("San") + caras.get("Phy"))/5) + "</p>\n"
				+ "		<p style='position:absolute;top:148px;left:301px;white-space:nowrap' class='ft05'>(San+Phy)/5&#160;+&#160;5</p>\n"
				+ "		<p style='position:absolute;top:169px;left:301px;white-space:nowrap' class='ft01'><b>Alt&eacute;rations</b></p>\n"
				+ "		<p style='position:absolute;top:258px;left:500px;white-space:nowrap' class='ft00'><b>Comp&eacute;tences&#160;et&#160;Sorts</b></p>\n"
				+ "		<p style='position:absolute;top:407px;left:40px;white-space:nowrap' class='ft02'><b>Caractéristiques&#160;Secondaires</b></p>\n"
				//caractéristiques secondaires
				+ "		<!-- Perception -->\n"
				+ "		<p style='position:absolute;top:436px;left:30px;white-space:nowrap' class='ft03'><b>Per</b></p>\n"
				+ "		<p style='position:absolute;top:456px;left:33px;white-space:nowrap' class='ft03'>" + caras.get("Per") + "</p>\n"
				+ "		<!-- Discrétion -->\n"
				+ "		<p style='position:absolute;top:436px;left:100px;white-space:nowrap' class='ft03'><b>Dis</b></p>\n"
				+ "		<p style='position:absolute;top:456px;left:103px;white-space:nowrap' class='ft03'>" + caras.get("Dis") + "</p>\n"
				+ "		<!-- Social -->\n"
				+ "		<p style='position:absolute;top:436px;left:169px;white-space:nowrap' class='ft03'><b>Soc</b></p>\n"
				+ "		<p style='position:absolute;top:456px;left:172px;white-space:nowrap' class='ft03'>" + caras.get("Soc") + "</p>\n"
				+ "		<!-- Intimidation -->\n"
				+ "		<p style='position:absolute;top:436px;left:242px;white-space:nowrap' class='ft03'><b>Int</b></p>\n"
				+ "		<p style='position:absolute;top:456px;left:245px;white-space:nowrap' class='ft03'>" + caras.get("Int") + "</p>\n"
				//autres caractéristiques
				+ "		<!-- Esquive -->\n"
				+ "		<p style='position:absolute;top:485px;left:7px;white-space:nowrap' class='ft03'><b>Esquive</b></p>\n"
				+ "		<p style='position:absolute;top:503px;left:7px;white-space:nowrap' class='ft05'>(Cha+Agi)/5</p>\n"
				+ "		<p style='position:absolute;top:495px;left:75px;white-space:nowrap' class='ft03'>" + ((caras.get("Agi") + caras.get("Cha"))/5) + "</p>\n"
				+ "		<!-- Initiative -->\n"
				+ "		<p style='position:absolute;top:485px;left:152px;white-space:nowrap' class='ft03'><b>Initiative</b></p>\n"
				+ "		<p style='position:absolute;top:503px;left:152px;white-space:nowrap' class='ft05'>Agi/10</p>\n"
				+ "		<p style='position:absolute;top:495px;left:227px;white-space:nowrap' class='ft03'>" + (caras.get("Agi")/10) + "</p>\n"
				+ "		<!-- Attaque -->\n"
				+ "		<p style='position:absolute;top:525px;left:7px;white-space:nowrap' class='ft03'><b>Attaque</b></p>\n"
				+ "		<p style='position:absolute;top:543px;left:7px;white-space:nowrap' class='ft05'>For/10</p>\n"
				+ "		<p style='position:absolute;top:535px;left:75px;white-space:nowrap' class='ft03'>" + (caras.get("For")/10) + "</p>\n"
				+ "		<!-- Puissance -->\n"
				+ "		<p style='position:absolute;top:525px;left:152px;white-space:nowrap' class='ft03'><b>Puissance</b></p>\n"
				+ "		<p style='position:absolute;top:543px;left:152px;white-space:nowrap' class='ft05'>Mag/10</p>\n"
				+ "		<p style='position:absolute;top:535px;left:227px;white-space:nowrap' class='ft03'>" + (caras.get("Mag")/10) + "</p>\n"
				+ "		<!-- Défense -->\n"
				+ "		<p style='position:absolute;top:564px;left:7px;white-space:nowrap' class='ft03'><b>D&eacute;fense</b></p>\n"
				+ "		<p style='position:absolute;top:583px;left:7px;white-space:nowrap' class='ft05'>Phy/10</p>\n"
				+ "		<p style='position:absolute;top:574px;left:75px;white-space:nowrap' class='ft03'>" + (caras.get("Phy")/10) + "</p>\n"
				+ "		<!-- Résistance -->\n"
				+ "		<p style='position:absolute;top:564px;left:152px;white-space:nowrap' class='ft03'><b>R&eacute;sistance</b></p>\n"
				+ "		<p style='position:absolute;top:583px;left:152px;white-space:nowrap' class='ft05'>Men/10</p>\n"
				+ "		<p style='position:absolute;top:574px;left:227px;white-space:nowrap' class='ft03'>" + (caras.get("Men")/10) + "</p>\n"
				//reste des trucs inutiles
				+ "		<p style='position:absolute;top:608px;left:113px;white-space:nowrap' class='ft02'><b>Armure</b></p>\n"
				+ "		<p style='position:absolute;top:755px;left:123px;white-space:nowrap' class='ft02'><b>Arme</b></p>\n"
				+ "		<p style='position:absolute;top:785px;left:20px;white-space:nowrap' class='ft01'>Main gauche</p>\n"
				+ "		<p style='position:absolute;top:785px;left:209px;white-space:nowrap' class='ft01'>Main droite</p>\n"
				+ "		<p style='position:absolute;top:947px;left:30px;white-space:nowrap' class='ft00'><b>Armes&#160;mani&eacute;es</b></p>\n"
				+ "		<p style='position:absolute;top:951px;left:487px;white-space:nowrap' class='ft00'><b>Inventaire</b></p>\n"
				+ "		<p style='position:absolute;top:982px;left:717px;white-space:nowrap' class='ft02'><b>Petits&#160;Objets</b></p>\n"
				+ "		<p style='position:absolute;top:1134px;left:745px;white-space:nowrap' class='ft02'><b>Argent</b></p>\n"
				+ "		<p style='position:absolute;top:1230px;left:235px;white-space:nowrap' class='ft06'><i>Armures&#160;L&eacute;g&egrave;res&#160;et&#160;Moyennes</i></p>\n"
				+ "		<p style='position:absolute;top:1230px;left:503px;white-space:nowrap' class='ft06'><i>Armures&#160;L&eacute;g&egrave;res</i></p>\n"
				+ "	</div>\n"
				+ "</body>\n"
				+ "</html>";
		return ans;
	}
	
	/**
	 * @return le personnage mis au format se sauvagarde
	 */
	public String save () {
		String ans = "personnage\n"
				+ "nom " + nom + "\n" 
				+ "age " + age + "\n"
				+ "niveau " + niveau + "\n"
				+ "ethnie " + ethnie.nom + "\n"
				+ "culture " + culture.getNom() + "\n"
				+ "classe " + classe.getNom() + "\n" 
				+ "prestige " + ((prestige != null)?prestige.getNom():"") + "\n"
				+ "San " + tcs.get("San") + " " + caras.get("San") + "\n"
				+ "For " + tcs.get("For") + " " + caras.get("For") + "\n"
				+ "Phy " + tcs.get("Phy") + " " + caras.get("Phy") + "\n"
				+ "Mag " + tcs.get("Mag") + " " + caras.get("Mag") + "\n"
				+ "Men " + tcs.get("Men") + " " + caras.get("Men") + "\n"
				+ "Agi " + tcs.get("Agi") + " " + caras.get("Agi") + "\n"
				+ "Tec " + tcs.get("Tec") + " " + caras.get("Tec") + "\n"
				+ "Cha " + tcs.get("Cha") + " " + caras.get("Cha") + "\n"
				+ "Per " + caras.get("Per") + "\n"
				+ "Dis " + caras.get("Dis") + "\n"
				+ "Soc " + caras.get("Soc") + "\n"
				+ "Int " + caras.get("Int")
				;
		return ans;
	}
}
