package s4.spring.td2.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Organization getOrganization() {
		return organization;
	}
	
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
	public List<Groupe> getGroupes() {
		return groupes;
	}
	
	public void setGroupes(List<Groupe> groupes) {
		this.groupes = groupes;
	}
	
	@ManyToOne
	private Organization organization;
	
	@ManyToMany
	private List<Groupe> groupes;
	
	public User() {
		groupes = new ArrayList<>();
	}
	
}
