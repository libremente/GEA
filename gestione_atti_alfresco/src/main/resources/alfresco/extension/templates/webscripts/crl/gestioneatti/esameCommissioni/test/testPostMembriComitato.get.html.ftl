<html>
<head>
<script type="text/javascript">
	function creaMembroComitato(atto) {
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
		xmlhttp.open("POST", "http://localhost:8080/alfresco/service/crl/atto/esamecommissioni/membricomitato?alf_ticket=${session.ticket}", true);
		xmlhttp.setRequestHeader("Content-Type", "application/json");
		xmlhttp.send(atto);
	}
	
	function creaMembroComitatoCommissione(){
		var currentForm = document.forms[0];
		var idAtto = currentForm.idAtto.value;
		var commissione = currentForm.commissione.options[currentForm.commissione.selectedIndex].value;
		var membro1 = currentForm.membro1.options[currentForm.membro1.selectedIndex].value;
		var dataNomina1 = currentForm.dataNomina1.value;
		var dataUscita1 = currentForm.dataUscita1.value;
		var coordinatore1 = currentForm.coordinatore1.options[currentForm.coordinatore1.selectedIndex].value;
		var membro2 = currentForm.membro2.options[currentForm.membro2.selectedIndex].value;
		var dataNomina2 = currentForm.dataNomina2.value;
		var dataUscita2 = currentForm.dataUscita2.value;
		var coordinatore2 = currentForm.coordinatore2.options[currentForm.coordinatore2.selectedIndex].value;
		var presenzaComitato = currentForm.presenzaComitato.options[currentForm.presenzaComitato.selectedIndex].value;
		var dataIstituzioneComitato = currentForm.dataIstituzioneComitato.value;
		
		var attoCustom = {
			"target":{
				"commissione": ""+commissione+""
			},
			"atto": {
				"id": ""+idAtto+"",
				"stato": "comitato ristretto",
				"commissioni": [
					{"commissione":
				 		{
				 			"descrizione": ""+commissione+"",
				 			"presenzaComitatoRistretto": ""+presenzaComitato+"",
				 			"dataIstituzioneComitato": ""+dataIstituzioneComitato+"",
							"comitatoRistretto": {
								"comitatoRistretto":{
										"componenti":[
											{"componente": 
												{
												"descrizione" : ""+membro1+"",
												"dataNomina" : ""+dataNomina1+"",
												"dataUscita" : ""+dataUscita1+"",
												"coordinatore" : ""+coordinatore1+""
												}
											},
											{"componente": 
												{
												"descrizione" : ""+membro2+"",
												"dataNomina" : ""+dataNomina2+"",
												"dataUscita" : ""+dataUscita2+"",
												"coordinatore" : ""+coordinatore2+""
												}
											}	
										]
									}
								},
							"stato": "comitato ristretto"
						}	
					},
					{"commissione":
				 		{
				 			"descrizione": "commissioneStaticaTest",
							"comitatoRistretto": "",
							"stato": "comitato ristretto"
						}
					}
				
				]
				 
				}
		};
		
		
	
		
		creaMembroComitato(JSON.stringify(attoCustom));

	}
	
</script>
</head>
<body>
	<div>
		<h1>Test creazione modifica membri comitato in commissione atto</h1>
	</div>
	
	<form action="javascript:creaMembroComitatoCommissione()">
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
			<td>Presenza Comitato:</td>
			<td>
				<select name="presenzaComitato">
					<option value="true">SI</option>
					<option value="false">NO</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>Data Istituzione Comitato:</td>
			<td>
				<input type="text" name="dataIstituzioneComitato">
			</td>
		</tr>
		<tr>
			<td>Membro 1:</td>
			<td>
				<select name="membro1">
				<#list membri as membro>
					<option value="${membro.name}">${membro.name}</option>
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
			<td>Coordinatore:</td>
			<td>
				<select name="coordinatore1">
					<option value="true">SI</option>
					<option value="false">NO</option>
				</select>
			</td>
		</tr>
		
		<tr>
			<td>Membro 2:</td>
			<td>
				<select name="membro2">
				<#list membri as membro>
					<option value="${membro.name}">${membro.name}</option>
				</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td>Data nomina:</td>
			<td>
				<input type="text" name="dataNomina2">
			</td>
		</tr>
		<tr>
			<td>Data uscita:</td>
			<td>
				<input type="text" name="dataUscita2">
			</td>
		</tr>
		<tr>
			<td>Coordinatore:</td>
			<td>
				<select name="coordinatore2">
					<option value="true">SI</option>
					<option value="false">NO</option>
				</select>
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

