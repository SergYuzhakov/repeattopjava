<%--
  Created by IntelliJ IDEA.
  User: Serg
  Date: 18.10.2020
  Time: 15:06
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update/Add</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>${action} Meal</h2>

<style>
    dl {
        background: none repeat scroll 0 0 #FAFAFA;
        margin: 8px 0;
        padding: 0;
    }

    dt {
        display: inline-block;
        width: 170px;
    }


    dd {
        display: inline-block;
        margin-left: 8px;
        vertical-align: top;
    }

</style>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<form action="meals" method="post">
    <input type="hidden" name="id" value="${meal.id}">
    <dl>
        <dt>Calories:</dt>
        <dd><input type="number" name="calories" value="${meal.calories}"/></dd>
    </dl>
    <dl>
        <dt>DateTime:</dt>
        <dd><input type="datetime-local" name="date" value="${meal.dateTime}"/>
        </dt>
    </dl>
    <dl>
        <dt>Description:</dt>
        <dd><input type="text" placeholder="Breakfast,Dinner,etc" name="description" size="40"
                   value="${meal.description}"/>
        </dt>
    </dl>
    <button type="submit">Save</button>
    <button onclick="goBack()">Cancel</button>
</form>

<script>
    function goBack() {
        window.history.back();
    }
</script>

</body>
</html>
