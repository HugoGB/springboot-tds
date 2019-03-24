package s4.spring.td2.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import s4.spring.td2.entities.Organization;
import s4.spring.td2.repositories.OrgasRepository;

@RestController
public class RestOrgasController {	
	@Autowired
	private OrgasRepository orgasRepo;		
	
	@ResponseBody
	@GetMapping("")
	public List<Organization> get(){
		return orgasRepo.findAll();
	}	
	
	@PostMapping("create")
	@ResponseBody
	public Organization post(Organization orga) {
		return orgasRepo.saveAndFlush(orga);
	}
		
}