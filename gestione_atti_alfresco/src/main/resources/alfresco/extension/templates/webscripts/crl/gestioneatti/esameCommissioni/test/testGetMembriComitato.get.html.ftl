<html>
<head>
</head>
<body>
	<div>
		<h1>Test get membri comitato</h1>
	</div>
	
	<form action="/alfresco/service/crl/atto/esamecommissioni/membricomitato?alf_ticket=${session.ticket}" method="get">
	<table>
		<tr>
			<td>
				Atto:
			</td>
			<td>
				<select name="idAtto">
					<#list attiResults as atto>
						<option value="${atto.nodeRef}">${atto.name}</option>
					</#list>
				</select>
			</td>
			<td>
				Nome commissione:
			</td>
			<td>
				<input type="text" name="commissione" />
			</td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" value="Get Membri Comitato"></td>
		</tr>
	</table>
	</form>
</body>
</html>


