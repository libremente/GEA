<html>
<head>
<script type="text/javascript">
	
	
	
	function aggiornaDatiCustom(){
		var currentForm = document.forms[0];
		var idAtto = currentForm.idAtto.options[currentForm.idAtto.selectedIndex].value;
		var tipoTemplate = currentForm.tipoTemplate.options[currentForm.tipoTemplate.selectedIndex].value;
		

		var letteraCustom = {
			"idAtto" : ""+idAtto+"",
			"tipoTemplate" : ""+tipoTemplate+""
			
		};
		
		var letteraCustomString = JSON.stringify(letteraCustom);
		
		var testForm = document.createElement("form");
 		testForm.setAttribute("method","post");
 		testForm.setAttribute("action","http://localhost:9090/alfresco/service/crl/template/lettere/crealettera?alf_ticket=${session.ticket}");
 		
 		var input = document.createElement("input");
 		input.setAttribute("type","hidden");
		input.setAttribute("name","json");
		input.setAttribute("value",letteraCustomString);
 		
 		testForm.appendChild(input);
 		document.getElementsByTagName('body')[0].appendChild(testForm);
 		
 		testForm.submit();
		
	}
	
</script>
</head>
<body>
	<div>
		<h1>Test creazione lettera</h1>
	</div>
	
	<form action="javascript:aggiornaDatiCustom()">
	<table>
		<tr>
			<td>IdAtto:</td>
			<td>
				<select name="idAtto">
				<#list atti as atto>
					<option value="${atto.nodeRef}">${atto.name}</option>
				</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td>IdLettera:</td>
			<td>
				<select name="tipoTemplate">
				<#list lettere as lettera>
					<option value="${lettera.type}">${lettera.type}</option>
				</#list>
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

