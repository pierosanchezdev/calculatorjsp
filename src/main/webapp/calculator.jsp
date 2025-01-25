<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Calculadora JSP</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="calculator">
        <form method="post" action="${pageContext.request.contextPath}/calculator">
            <input type="text" name="result" id="result" value="<%= request.getAttribute("result") != null ? request.getAttribute("result") : "" %>" readonly>
            <div class="buttons">
                <button type="submit" name="action" value="%">%</button>
                <button type="submit" name="action" value="CE">CE</button>
                <button type="submit" name="action" value="C">C</button>
                <button type="submit" name="action" value="DEL">⌫</button>

                <button type="submit" name="action" value="1/x">1/x</button>
                <button type="submit" name="action" value="x^2">x²</button>
                <button type="submit" name="action" value="√x">√x</button>
                <button type="submit" name="action" value="/">÷</button>

                <button type="submit" name="action" value="7">7</button>
                <button type="submit" name="action" value="8">8</button>
                <button type="submit" name="action" value="9">9</button>
                <button type="submit" name="action" value="*">×</button>

                <button type="submit" name="action" value="4">4</button>
                <button type="submit" name="action" value="5">5</button>
                <button type="submit" name="action" value="6">6</button>
                <button type="submit" name="action" value="-">−</button>

                <button type="submit" name="action" value="1">1</button>
                <button type="submit" name="action" value="2">2</button>
                <button type="submit" name="action" value="3">3</button>
                <button type="submit" name="action" value="+">+</button>

                <button type="submit" name="action" value="±">±</button>
                <button type="submit" name="action" value="0">0</button>
                <button type="submit" name="action" value=".">.</button>
                <button type="submit" name="action" value="=">=</button>
            </div>
        </form>
    </div>
</body>
</html>
