package s4.spring.td2.Components;

import java.io.IOException;
import io.github.jeemv.springboot.vuejs.components.VueComponent;

public class ConfirmButton {

	public static void main(String[] args) throws IOException {
		VueComponent compo=new VueComponent("m-confirm-button");
        compo.setProps("title","message","validatecaption","cancelcaption","type","size");
        compo.addProp("width", 500);
        compo.addData("dialog",false); 
        compo.addMethod("validation", "this.$emit('validation');this.dialog=false;");
        compo.addMethod("cancellation", "this.$emit('cancellation');this.dialog=false;");        
        compo.setDefaultTemplateFile();
        compo.createFile(false);
	}

}
