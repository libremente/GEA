<html>
<head>
<script type="text/javascript">
	function creaFineLavoriComitato(atto) {
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
		xmlhttp.open("POST", "http://localhost:8080/alfresco/service/crl/atto/esamecommissioni/finelavoricomitato?alf_ticket=${session.ticket}", true);
		xmlhttp.setRequestHeader("Content-Type", "application/json");
		xmlhttp.send(atto);
	}
	
	function creaFineLavoriComitatoCommissione(){
		var currentForm = document.forms[0];
		var idAtto = currentForm.idAtto.options[currentForm.idAtto.selectedIndex].value;
		var commissione = currentForm.commissione.value;
		var dataFineLavoriComitato = currentForm.dataFineLavoriComitato.value;
	
		
		var attoCustom = {
			"target":{
				"commissione": ""+commissione+""
			},
			"atto": {
				"id": ""+idAtto+"",
				"stato": "fine lavori comitato",
				"commissioni": [
					{"commissione":
				 		{
				 			"descrizione": ""+commissione+"",
							"dataFineLavoriComitato": ""+dataFineLavoriComitato+"",
							"stato": "fine lavori comitato"
						}
					},
					{"commissione":
				 		{
				 			"descrizione": "commissioneStaticaTest",
							"dataFineLavoriComitato": "2012-08-09",
							"stato": "fine lavori comitato"
						}
					}
				
				]
				 
				}
		};
		
	
		
		creaFineLavoriComitato(JSON.stringify(attoCustom));

	}
	
</script>
</head>
<body>
	<div>
		<h1>Test fine lavori comitato commissione atto</h1>
	</div>
	
	<form action="javascript:creaFineLavoriComitatoCommissione()">
	<table>
		<tr>
			<td>
				Atto:
			</td>
			<td>
				<select name="idAtto">
					<#list atti as atto>
						<option value="${atto.nodeRef}">${atto.name}</option>
					</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td>Commissione:</td>
			<td>
				<input type="text" name="commissione"/>
			</td>
		</tr>
		<tr>
			<td>Data fine lavori</td>
			<td>
				<input type="text" name="dataFineLavoriComitato"/>
			</td>
		</tr>
	
		<tr>
			<td></td>
			<td><input type="submit" value="fine lavori comitato"></td>
		</tr>
	</table>
	
	
		
	</form>
	
	<h2>Risposta:</h2>
	<div id="myDiv">
		
	</div>
</body>
</html>

