<html>
<head>
</head>
<body>
	<div>
		<h1>Test upload record atto</h1>
	</div>
	
	<form action="/alfresco/service/crl/atto/record?alf_ticket=${session.ticket}" method="post" enctype="multipart/form-data" accept-charset="utf-8">
	<table>
		<tr>
			<td>
				Atto:
			</td>
			<td>
				<select name="id">
					<#list attiResults as atto>
						<option value="${atto.nodeRef}">${atto.name}</option>
					</#list>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				Tipologia:
			</td>
			<td>
				<select name="tipologia">
					<option value="Tipologia1">Tipologia1</option>
					<option value="Tipologia2">Tipologia2</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				Pubblico:
			</td>
			<td>
				<select name="pubblico">
					<option value="true">Si</option>
					<option value="false">NO</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>File:</td>
			<td>
				<input type="file" name="file">
			</td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" value="Salva record"></td>
		</tr>
	</table>
	</form>
</body>
</html>

