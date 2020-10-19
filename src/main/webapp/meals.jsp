<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %><%--
  Created by IntelliJ IDEA.
  User: Serg
  Date: 17.10.2020
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<p><a href="meals?action=add">Add Meal</a></p>

<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>

<c:forEach items="${meals}" var="meal">
<tr>
    <tr style="color:${meal.excess ? '#FF4500' : '228B22'}">
    <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealTo"/>
    <td>
        <%= TimeUtil.toString(meal.getDateTime()) %>
    </td>
    <td>${meal.description}</td>
    <td>${meal.calories}</td>
    <td><a href="meals?action=edit&id=${meal.id}">Update</a></td>
    <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
    </td>
</tr>

</c:forEach>

</table>

</body>
</html>
