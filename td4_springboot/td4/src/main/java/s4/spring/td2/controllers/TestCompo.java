package s4.spring.td2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import io.github.jeemv.springboot.vuejs.VueJS;

@Controller
public class TestCompo {
	@Autowired
	private VueJS vue;
	
	@GetMapping("testCompo")
	public String idx(Model model) {
		model.addAttribute("vue", vue);
		vue.addMethod("validate", "console.log('Validation...');msg='Annulation';");
		vue.addMethod("cancel", "console.log('Cancellation...');msg='Validation';");
		vue.addData("dlgTitle","Titre du dialogue");
		vue.addData("dlgWidth",600);
		vue.addData("msg","Voulez-vous afficher une alerte ?");
		vue.addData("validatecaption","Valider");
		vue.addData("cancelcaption","Annuler");		
		vue.addDataRaw("headers", "[{text:'Nom',value:'nom'}]");
		vue.addDataRaw("items", "[{text:'SMITH'},{value:'DUPONT'}]");
		return ("vueJs/idx");
	}
	
}
