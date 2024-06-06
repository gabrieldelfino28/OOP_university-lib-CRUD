<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="./css/styles.css">
<title>Manter Aluno</title>
</head>
<body>
	<div>
		<jsp:include page="menu.jsp"/>
	</div>
	<div class="form-style" align="center">
		<h1>Aluno</h1>
		<form action="aluno" method="post">
			<table>
				<tr>
					<td colspan="3">
						<input type="number" min="0" id="ra" name="ra" placeholder="RA"
						value='<c:out value="${aluno.RA}"></c:out>'>
					</td>
					<td>
						<input type="submit" id="Button" name="Button" value="Buscar">
					</td>
				</tr>
				<tr>
					<td colspan="4"><input type="text" id="nome" name="nome" placeholder="Nome"
					value='<c:out value="${aluno.nome}"></c:out>'></td>
				</tr>
				<tr>
					<td colspan="4"><input type="text" id="email" name="email" placeholder="Email " 
					value='<c:out value="${aluno.email}"></c:out>'></td>
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
		<c:if test="${not empty alunos}">
			<table class="table-style">
				<thead>
					<tr>
						<th class="t-style">RA</th>
						<th class="t-style">Nome</th>
						<th class="t-style">Email</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="a" items="${alunos}">
						<tr>
							<td class="t-style"><c:out value="${a.RA}"/></td>
							<td class="t-style"><c:out value="${a.nome}"/></td>
							<td class="t-style"><c:out value="${a.email}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
</html>