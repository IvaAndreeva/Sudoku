
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
[<!ATTLIST tag oldValue CDATA #IMPLIED>]>
<html lang="en">
<head>
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/style/welcome.css" />
<script type="text/javascript" src="<c:url value="/js/guess.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/jquery.js" />"></script>
<title>Sudoku</title>
</head>
<body>
	<p>Sudoku Board:</p>
	<div id="guess-div">
		<table id="${sudokuId}" class="sudoku_table">
			<c:forEach items="${cellsGrid}" var="cellRow" varStatus="row">
				<tr>
					<c:forEach items="${cellRow}" var="cell" varStatus="column">
						<c:set var="class" scope="session" value="" />
						<c:choose>
							<c:when test="${column.index == 0}">
								<c:set var="class" scope="session" value="${class} border-left" />
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when
										test="${(column.index+1) % cellsXcells.verticalCount == 0}">
										<c:set var="class" scope="session"
											value="${class} border-right" />
									</c:when>
								</c:choose>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${row.index == 0}">
								<c:set var="class" scope="session" value="${class} border-top" />
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when
										test="${(row.index+1) % cellsXcells.horizontalCount == 0}">
										<c:set var="class" scope="session"
											value="${class} border-bottom" />
									</c:when>
								</c:choose>
							</c:otherwise>
						</c:choose>

						<td class="${class}"><c:choose>
								<c:when test="${cell.canChange}">
									<input oldValue="${cell.valueToShow}"
										id="${row.index}_${column.index}" type="text"
										class="sudoku_guess" value="${cell.valueToShow}" />
								</c:when>
								<c:otherwise>
								${cell.valueToShow}
							</c:otherwise>
							</c:choose></td>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
		<c:choose>
			<c:when test="${!gameOver}">
				<input type="button" value="Check" onclick="guess()" />
			</c:when>
			<c:otherwise>GAME OVER!</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${gameFinished}">
			GAME FINISHED!
			</c:when>
		</c:choose>
	</div>
</body>
</html>