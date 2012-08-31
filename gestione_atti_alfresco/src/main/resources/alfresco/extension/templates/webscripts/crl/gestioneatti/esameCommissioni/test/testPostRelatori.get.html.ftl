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
				document.getElementById("myDiv").innerHTML = xmlhttp.responseText;
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
		var relatore = currentForm.relatore.options[currentForm.relatore.selectedIndex].value;
		var dataNomina = currentForm.dataNomina.value;
		var dataUscita = currentForm.dataUscita.value;
		
		var attoCustom = {
		"atto": {
			"id": ""+idAtto+"",
			"commissione": ""+commissione+"",
			"relatori" : [
				{"relatore":
				 	{
						"descrizione" : ""+relatore+"",
						"dataNomina" : ""+dataNomina+"",
						"dataUscita" : ""+dataUscita+""
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
			<td>Relatore:</td>
			<td>
				<select name="relatore">
				<#list relatori as relatore>
					<option value="${relatore.name}">${relatore.name}</option>
				</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td>Data nomina:</td>
			<td>
				<input type="text" name="dataNomina">
			</td>
		</tr>
		<tr>
			<td>Data uscita:</td>
			<td>
				<input type="text" name="dataUscita">
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

