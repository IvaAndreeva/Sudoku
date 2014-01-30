
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
<title>Sudoku</title>
</head>
<body>
	<form id="sudoku_properties" action='/sudoku-runner/guess' method="get">
		<p>What size you want the sudoku to be?</p>
		<select name="size" form="sudoku_properties">
			<c:forEach items="${sizes}" var="size">
				<option value="${size}">${size}</option>
			</c:forEach>
		</select>
		<p>What difficulty do you want to play on?</p>
		<select name="difficulty" form="sudoku_properties">
			<c:forEach items="${difficulties}" var="difficulty">
				<option value="${difficulty}">${difficulty}</option>
			</c:forEach>
		</select> <input type="submit" value="Generate" />
	</form>
</body>
</html>