<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="./css/styles.css">
<title>Manter Revista</title>
</head>
<body>
	<div>
		<jsp:include page="menu.jsp"/>
	</div>
	<div class="form-style" align="center">
		<h1>Revista</h1>
		<form action="revista" method="post">
			<table>
				<tr>
					<td colspan="3">
						<input type="number" min="0" id="cod" name="cod" placeholder="Código"
						value='<c:out value="${revista.codigo}"></c:out>'>
					</td>
					<td>
						<input type="submit" id="Button" name="Button" value="Buscar">
					</td>
				</tr>
				<tr>
					<td colspan="4"><input type="text" id="nome" name="nome" placeholder="Nome"
					value='<c:out value="${revista.nome}"></c:out>'></td>
				</tr>
				<tr>
					<td colspan="4"><input type="text" id="qtdpags" name="qtdpags" placeholder="Qtd. Págs." 
					value='<c:out value="${revista.qtdPaginas}"></c:out>'></td>
				</tr>
				<tr>
					<td colspan="4"><input type="text" id="ISSN" name="ISSN" placeholder="ISSN " 
					value='<c:out value="${revista.ISSN}"></c:out>'></td>
				</tr>
				<tr>
					<td><input type="submit" id="Button" name="Button" value="Cadastrar"></td>
					<td><input type="submit" id="Button" name="Button" value="Alterar"></td>
					<td><input type="submit" id="Button" name="Button" value="Excluir"></td>
					<td><input type="submit" id="Button" name="Button" value="Listar"></td>
				</tr>
			</table>
		</form>
	</div>
	
	<br><br>
	
	<div align="center">
		<c:if test="${not empty err}">
			<h4 class="err"><b><c:out value="${err}"/></b></h4>
		</c:if>
		<c:if test="${not empty saida}">
			<p><b><c:out value="${saida}"/></b></p>
		</c:if>
	</div>
	
	<br><br>
	
	<div class="list-style" align="center">
		<c:if test="${not empty revistas}">
			<table class="table-style">
				<thead>
					<tr>
						<th class="t-style">Cod</th>
						<th class="t-style">Nome</th>
						<th class="t-style">Qtd. Págs</th>
						<th class="t-style">ISSN</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="r" items="${revistas}">
						<tr>
							<td class="t-style"><c:out value="${r.codigo}"/></td>
							<td class="t-style"><c:out value="${r.nome}"/></td>
							<td class="t-style"><c:out value="${r.qtdPaginas}"/></td>
							<td class="t-style"><c:out value="${r.ISSN}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
</html>