<%--
  Created by IntelliJ IDEA.
  User: Serg
  Date: 31.01.2021
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">${pageContext.response.locale}</a>
    <div class="dropdown-menu">
        <a class="dropdown-item" href="?lang=en">English</a>
        <a class="dropdown-item" href="?lang=ru">Русский</a>
    </div>
</li>
