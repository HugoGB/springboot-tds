Vue.component('m-data-table',{
	"props":{
		header:{
			"type":[Array],"required":true
			}
		,noData:{
			"default":"Aucun élément à afficher"
			}
		,items:{
			"type":[Array],"required":true
			}
		}
	,"template":"<template>  <div>    <v-toolbar flat color=\"white\">      <v-toolbar-title>Data Table</v-toolbar-title>      <v-divider        class=\"mx-2\"        inset        vertical      ></v-divider>      <v-spacer></v-spacer>                       </v-toolbar>    <v-data-table      :headers=\"headers\"      :items=\"items\"      class=\"elevation-1\"    >      <template v-slot:items=\"props\">        <td v-if=\"header in headers\">props.item[header.value]</td>                <td class=\"justify-center layout px-0\">          <v-icon            small            class=\"mr-2\"            @click=\"$emit('editItem',props.item))\"          >            edit          </v-icon>          <v-icon            small            @click=\"$emit('deleteItem',props.item))\"          >            delete          </v-icon>        </td>      </template>      <template v-slot:no-data>        {{noData}}      </template>    </v-data-table>  </div></template>"
	}
);