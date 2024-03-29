package metier;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import dao.DAOCategorie;
import dao.DAOMotClef;
import dao.DAOOffre;
import daoHibernate.DAOCategorieHibernate;
import daoHibernate.DAOMotClefHibernate;
import daoHibernate.DAOOffreHibernate;

public class Administrateur {
	
	private DAOOffre offre;
	private DAOCategorie categorie;
	private DAOMotClef motclef;

	public Administrateur(){
		offre = new DAOOffreHibernate();
		categorie = new DAOCategorieHibernate();
		motclef = new DAOMotClefHibernate();
	}
	
	public Administrateur(DAOOffre daoOffre, DAOCategorie daoCategorie, DAOMotClef daoMotClef){
		offre = daoOffre;
		categorie = daoCategorie;
		motclef = daoMotClef;
	}
	
	public DAOOffre getOffre() {
		return offre;
	}

	public void setOffre(DAOOffre offre) {
		this.offre = offre;
	}

	public DAOCategorie getCategorie() {
		return categorie;
	}

	public void setCategorie(DAOCategorie categorie) {
		this.categorie = categorie;
	}

	public DAOMotClef getMotclef() {
		return motclef;
	}

	public void setMotclef(DAOMotClef motclef) {
		this.motclef = motclef;
	}
	
	public double getCACourant(int mois, int annee) throws Exception {
		
		Calendar calendar = Calendar.getInstance();
		Date dateAujourdhui = calendar.getTime();
		calendar.set(annee, mois, 1);
		Date dateDebutMois = calendar.getTime();
				
		double ca = 0.0;
		
		//Recherche des offres entre les date de d�but et de fin de mois
		for (Offre o : offre.loadAll()){
			if(o.getEncherit().size() != 0)
				if(o.getDateFin().after(dateDebutMois) && o.getDateFin().before(dateAujourdhui)){
		        	//Recherche de l'ench�re maximum
		        	ca += o.getMiseAPrix();
		        }
		}
		
		return ca;
	}
	
	
	public double getCAMois(int mois, int annee) throws Exception {
					
		Calendar calendar = Calendar.getInstance();
		calendar.set(annee, mois, 1);
		Date dateDebutMois = calendar.getTime();
		calendar.set(annee, mois, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date dateFinMois = calendar.getTime();
		
		double ca = 0.0;
		
		//Recherche des offres entre les date de d�but et de fin de mois
		for (Offre o : offre.loadAll()){
			if(o.getEncherit().size() != 0)
				if(o.getDateFin().after(dateDebutMois) && o.getDateFin().before(dateFinMois)){
		        	//Recherche de l'ench�re maximum
		        	ca += o.getMiseAPrix();
		        }
		}
		
		return ca;
	}
	
	public Map<String, Double> getCADerniersMois() throws Exception {
		Map<String, Double>  resultat = new LinkedHashMap<String, Double>();
		Calendar calendar = Calendar.getInstance();
		String mois;
		Double caMois;
		
		for(int i = 0; i < 12 ; i++){
			calendar.add(Calendar.MONTH, -1);
			DateFormat dateFormat = new SimpleDateFormat("MMM yyyy");
			mois = dateFormat.format(calendar.getTime());
			caMois = this.getCAMois(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
			resultat.put(mois,caMois);
		}
		
		return resultat;
	}
 
	public int getNbAnnoncesMois(int mois, int annee) throws Exception {
		
		ArrayList<Offre> offres = offre.loadAll();
		
		//On pr�pare les dates entre lesquelles on veut calculer la CA
		Calendar calendar = Calendar.getInstance();
		calendar.set(annee, mois, 1);
		Date dateDebutMois = calendar.getTime();
		calendar.set(annee, mois, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date dateFinMois = calendar.getTime();
		
		int nbAnnonce = 0;
				
		//Recherche des offres aux dates inf�rieur � aujourd'hui
		for (Offre o : offres)
			 if(o.getDateDepot().after(dateDebutMois) && o.getDateDepot().before(dateFinMois))
		        	nbAnnonce += 1;
		
		return nbAnnonce;
	}

	public Map<String, Integer> getNbAnnoncesDerniersMois() throws Exception {
		
		Map<String, Integer>  resultat = new LinkedHashMap<String, Integer>();
		Calendar calendar = Calendar.getInstance();
		String mois;
		int nbAnnonces;
		
		for(int i = 0; i < 12 ; i++){
			calendar.add(Calendar.MONTH, -1);
			DateFormat dateFormat = new SimpleDateFormat("MMM yyyy");
			mois = dateFormat.format(calendar.getTime());
			nbAnnonces = this.getNbAnnoncesMois(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
			resultat.put(mois,nbAnnonces);
		}
		
		return resultat;
	}
	
	public ArrayList<Offre> getAnnoncesMotClef() throws Exception{
		ArrayList<Offre> annoncesMotClef= new ArrayList<Offre>();
		String motClef = "";
		//On place tous les mots clefs dans une requ�te qui va servir pour findThem !
		for(MotClef mc : motclef.loadAll())
			motClef += mc.getLibelleMotClef() + " ";
		
		for (Offre o : offre.findThem(motClef, -1, -1, -1, -1))
				annoncesMotClef.add(o);
				
		return annoncesMotClef;
	}
	
	public ArrayList<Offre> getAnnoncesDouteuses() throws Exception{
		ArrayList<Offre> annoncesDouteuses = new ArrayList<Offre>();
		
		for (Offre o : offre.loadAll())
			if(o.getSuspecte() > 0)
				annoncesDouteuses.add(o);
				
		return annoncesDouteuses;
	}
	
	public int nbOffresPourCategorie(int idCategorie) throws Exception{		
		int nbOffres = 0;
		ArrayList<Offre> offres = offre.loadAll();
				
		//Recherche des offres aux dates inf�rieur � aujourd'hui
		for (Offre o : offres)
			 if(o.getCategorie().getIdCategorie() == idCategorie)
		        	nbOffres+= 1;
		
		return nbOffres;
	}

}
