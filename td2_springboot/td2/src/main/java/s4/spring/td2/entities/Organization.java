package s4.spring.td2.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Organization {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	private String domain;
	private String aliases;
	private String city;
	
	@OneToMany(mappedBy="organization", cascade=CascadeType.ALL)
	private List<Groupe> groupes;
	
	public Organization() {
		groupes = new ArrayList<>();
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDomain() {
		return domain;
	}
	
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getAliases() {
		return aliases;
	}
	
	public void setAliases(String aliases) {
		this.aliases = aliases;
	}
	
	@Override
	public String toString() {
		return name+" at "+city;
	}

	public List<Groupe> getGroupes() {
		return groupes;
	}

	public void setGroupes(List<Groupe> groupes) {
		this.groupes = groupes;
	}

	public void addGroupe(Groupe groupe) {
		groupes.add(groupe);
		groupe.setOrganization(this);
	}

}
