package s4.spring.td2.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import io.github.jeemv.springboot.vuejs.VueJS;
import io.github.jeemv.springboot.vuejs.utilities.Http;
import s4.spring.td2.entities.Organization;
import s4.spring.td2.repositories.OrgasRepository;

@Controller
@RequestMapping("/vue/")
public class TestVueController {
	
	@Autowired
	private VueJS vue;
	
	@Autowired
	private OrgasRepository orgasRepo;	
	
	@GetMapping("test")
	public String test(Model model) {
		model.addAttribute("vue",vue);
		vue.addMethod("update", "this.message='Message modifié !';");
		vue.addMethod("testAjax", "var self=this;"
				+ Http.post("/vue/test/ajax", "{v:self.inputValue}", "self.ajaxMessage=response.data;self.alertVisible=true;"));
		vue.addData("message", "Hello World");
		vue.addData("alertVisible", false);
		vue.addData("inputValue");
		vue.addData("ajaxMessage");
		return "vueJs/test";
	}
	
	@PostMapping("test/ajax")
	@ResponseBody
	public String testAjax(@RequestBody String v) {
		return "Test ok : "+v;
	}
	
	@GetMapping("/orgas")
	public String index(Model model) {
		model.addAttribute("vue",vue);
		List<Organization> orgas=orgasRepo.findAll();
		vue.addData("orgas", orgas);
		vue.addData("dialog", false);
		vue.addDataRaw("headers", "["
				+ "{text: 'Name', value:'name'},"
				+ "{text: 'Domain', value:'domain'},"
				+ "{text: 'Aliases', value:'aliases'}]");
		vue.addDataRaw("editedItem", "{}");
		vue.addDataRaw("editedIndex", "-1");
		vue.addComputed("formTitle", "(this.itemIndex==-1)?'Nouvelle orga':'Modification orga'");
		vue.addMethod("close", "this.dialog=false");
		vue.addMethod("save","var self=this;"+Http.post("/rest/orgas/create", "self.editedItem",
				"self.ajaxMessage = response.data; self.dialog=false;self.orgas.push(response.data);"));		
		return "vueJs/index";
	}
	
}