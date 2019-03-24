package models;

public class Element {	
	private String nom;
	private int evaluation;
		
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public int getEvaluation() {
		return evaluation;
	}
	
	public void setEvaluation(int evaluation) {
		this.evaluation = evaluation;
	}
		
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(!(obj instanceof Element)) {
			return false;
		}		
		Element other = (Element) obj;		
		if(nom == null) {
			return other.nom == null; 
		}		
		return this.nom.equals(other.nom);
	}

}
