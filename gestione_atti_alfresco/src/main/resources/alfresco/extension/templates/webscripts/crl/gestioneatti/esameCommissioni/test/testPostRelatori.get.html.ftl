<html>
<head>
<script type="text/javascript">
	function creaRelatore(atto) {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				document.getElementById("myDiv").innerHTML = "status: "+xmlhttp.status;
			}
		}
		xmlhttp.open("POST", "http://localhost:8080/alfresco/service/crl/atto/esamecommissioni/relatori?alf_ticket=${session.ticket}", true);
		xmlhttp.setRequestHeader("Content-Type", "application/json");
		xmlhttp.send(atto);
	}
	
	function creaRelatoreCommissione(){
		var currentForm = document.forms[0];
		var idAtto = currentForm.idAtto.value;
		var commissione = currentForm.commissione.options[currentForm.commissione.selectedIndex].value;
		var relatore1 = currentForm.relatore1.options[currentForm.relatore1.selectedIndex].value;
		var dataNomina1 = currentForm.dataNomina1.value;
		var dataUscita1 = currentForm.dataUscita1.value;
		var relatore2 = currentForm.relatore2.options[currentForm.relatore2.selectedIndex].value;
		var dataNomina2 = currentForm.dataNomina2.value;
		var dataUscita2 = currentForm.dataUscita2.value;
		
		var attoCustom = {
			"target":{
				"commissione": ""+commissione+""
			},
			"atto": {
				"id": ""+idAtto+"",
				"stato": "nominato relatore",
				"commissioni": [
					{"commissione":
				 		{
				 			"descrizione": ""+commissione+"",
							"relatori":[
								{"relatore":
									{
										"descrizione" : ""+relatore1+"",
										"dataNomina" : ""+dataNomina1+"",
										"dataUscita" : ""+dataUscita1+""
									}
								},
								{"relatore":
								 	{
								 		"descrizione" : ""+relatore2+"",
										"dataNomina" : ""+dataNomina2+"",
										"dataUscita" : ""+dataUscita2+""
								 	}
								}
							],
							"stato": "nominato relatore"
						}	
					},
					{"commissione":
				 		{
				 			"descrizione": "commissioneStaticaTest",
							"relatori":[],
							"stato": "nominato relatore"
						}
					}
				
				]
				 
				}
		};
		
	
		
		creaRelatore(JSON.stringify(attoCustom));

	}
	
</script>
</head>
<body>
	<div>
		<h1>Test creazione modifica relatore in commissione atto</h1>
	</div>
	
	<form action="javascript:creaRelatoreCommissione()">
	<table>
		<tr>
			<td>Atto:</td>
			<td>
				<input type="text" name="idAtto" value="${atto.nodeRef}" readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<td>Commissioni:</td>
			<td>
				<select name="commissione">
				<#list commissioni as commissione>
					<option value="${commissione.name}">${commissione.name}</option>
				</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td>Relatore 1:</td>
			<td>
				<select name="relatore1">
				<#list relatori as relatore>
					<option value="${relatore.name}">${relatore.name}</option>
				</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td>Data nomina:</td>
			<td>
				<input type="text" name="dataNomina1">
			</td>
		</tr>
		<tr>
			<td>Data uscita:</td>
			<td>
				<input type="text" name="dataUscita1">
			</td>
		</tr>
		
		<tr>
			<td>Relatore 2:</td>
			<td>
				<select name="relatore2">
				<#list relatori as relatore>
					<option value="${relatore.name}">${relatore.name}</option>
				</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td>Data nomina (relatore 2):</td>
			<td>
				<input type="text" name="dataNomina2">
			</td>
		</tr>
		<tr>
			<td>Data uscita (relatore 2):</td>
			<td>
				<input type="text" name="dataUscita2">
			</td>
		</tr>
		
		<tr>
		<td></td>
		<td><input type="submit"></td>
		</tr>
	</table>
	
	
		
	</form>
	
	<h2>Risposta:</h2>
	<div id="myDiv">
		
	</div>
</body>
</html>

