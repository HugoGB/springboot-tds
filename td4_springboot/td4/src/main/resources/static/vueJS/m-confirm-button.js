Vue.component('m-confirm-button',{
	"props":{
		validatecaption:{
			"type":[String]
			}
		,size:{
			"type":[String]
			}
		,width:{
			"default":500
			}
		,cancelcaption:{
			"type":[String]
			}
		,title:{
			"type":[String]
			}
		,message:{
			"type":[String]
			}
		,type:{
			"type":[String]
			}
		}
	,"data":function() {
		 return {
			"dialog":false
			}
		;
		}
	,"methods":{
		"cancellation":function (){
			this.
			$emit('cancellation');
			this.dialog=false;
			}
		,"validation":function (){
			this.
			$emit('validation');
			this.dialog=false;
			}
		}
	,"template":"<v-dialog v-model=\"dialog\" :width=\"width\">  <template v-slot:activator=\"{ on }\">    <v-btn color=\"red lighten-2\" dark v-on=\"on\"><slot></slot></v-btn>  </template>   <v-card>    <v-card-title class=\"headline grey lighten-2\" primary-title>      {{title}}    </v-card-title>     <v-card-text>      {{message}}    </v-card-text>     <v-divider></v-divider>     <v-card-actions>      <v-spacer></v-spacer>      <v-btn color=\"warning\" @click=\"cancellation\">{{cancelcaption}}</v-btn>      <v-btn color=\"success\" @click=\"validation\">{{validatecaption}}</v-btn>    </v-card-actions>  </v-card></v-dialog>"
	}
);