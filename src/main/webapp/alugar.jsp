<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="./css/styles.css">
<title>Alugar Exemplar</title>
</head>
<body>
	<div>
		<jsp:include page="menu.jsp"/>
	</div>
	<div class="form-style" align="center">
		<h1>Aluguel</h1>
		<form action="alugar" method="post">
			<table>
				<tr>
				<td>
					<label>Data Retirada</label>
				</td>
					<td colspan="2">
						<input type="date" id="dataRet" name="dataRet"
						value='<c:out value="${aluguel.dataRetirada}"></c:out>'>
					</td>
					<td>
						<input type="submit" id="Button" name="Button" value="Buscar">
					</td>
				</tr>
				
				<!--ComboBox: Aluno -->
				<tr>
					<td colspan="4">
					<select id="alunoRA" name="alunoRA">
							<option value ="0">Escolha um Aluno</option>
							<c:forEach var="a" items="${alunos}">
								<c:if test="${(empty aluguel) || (a.RA ne aluguel.aluno.RA)}">
									<option value="${a.RA}">
										<c:out value="${a}"/>
									</option>
								</c:if>
								<c:if test="${a.RA eq aluguel.aluno.RA}">
								<option value="${a.RA}" selected="selected">
									<c:out value="${a}" />
								</option>
							</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
	
				<!--ComboBox: Examplares -->
				<tr>
					<td colspan="4">
						<select id="codExemplar" name="codExemplar">
							<option value ="0">Escolha um Exemplar</option>
							<c:forEach var="e" items="${exemplares}">
								<c:if test="${(empty aluguel) || (e.codigo ne aluguel.exemplar.codigo)}">
									<option value="${e.codigo}">
										<c:out value="${e}"/>
									</option>
								</c:if>
								<c:if test="${e.codigo eq aluguel.exemplar.codigo}">
								<option value="${e.codigo}" selected="selected">
									<c:out value="${e}" />
								</option>
							</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td><label>Data Devolução</label></td>
					<td colspan="5">
					<input type="date" id="dataDev" name="dataDev"
					value='<c:out value="${aluguel.dataDevolucao}"></c:out>'></td>
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
		<c:if test="${not empty alugueis}">
			<table class="table-style">
				<thead>
					<tr>
						<th class="t-style">Data Retirada</th>
						<th class="t-style">Aluno</th>
						<th class="t-style">Exemplar</th>
						<th class="t-style">Data Devolução</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="al" items="${alugueis}">
						<tr>
							<td class="t-style"><c:out value="${al.dataRetirada}"/></td>
							<td class="t-style"><c:out value="${al.aluno}"/></td>
							<td class="t-style"><c:out value="${al.exemplar}"/></td>
							<td class="t-style"><c:out value="${al.dataDevolucao}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
</html>