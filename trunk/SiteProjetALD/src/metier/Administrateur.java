package metier;

import java.util.ArrayList;

import dao.DAOAdministrateur;

public class Administrateur implements DAOAdministrateur{
	
	@Override
	public double getCA() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getCAMois() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNbAnonceMois() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNbAnonceAnnee() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addMotCle(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMotCle(int pos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<MotClef> getMotCle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMotCle(int pos, String newName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCategorie(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCategorie(int pos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Categorie> getCategorie() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCategorie(int pos, String newName) {
		// TODO Auto-generated method stub
		
	}

}