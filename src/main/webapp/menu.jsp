<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="./css/styles.css">
<title></title>
<!-- 
  --Todos os .jsp's estão encapsulados para o usuário, sendo acessados através
  --dos Servlets.
-->
</head>
<body>
	<header>
		<a href="${pageContext.request.contextPath}/home">Home</a> | 
		<a href="${pageContext.request.contextPath}/aluno">Aluno</a> | 
		<a href="${pageContext.request.contextPath}/livro">Livro</a> |
		<a href="${pageContext.request.contextPath}/revista">Revista</a> |
		<a href="${pageContext.request.contextPath}/alugar">Alugar</a> |
	</header>
	<br>
	<hr>
	<br>
</body>
</html>