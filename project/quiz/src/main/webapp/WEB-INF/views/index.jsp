<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>This is a page for you to solve some quizes</title>
</head>
<body>
 <form action="quizSubmitted" method="post">
        <c:if test="${empty answer}">
            <label> 1. 1 + 1 = </label> 
            <input type="hidden" name="question1" value="1">
            <input type="text" name="answer1" />
            <br />
            <label> 2. 5 + 5 = </label> 
            <input type="hidden" name="question2" value="2">
            <input type="text" name="answer2" />
            <br />
            <label> 3. 10 + 5 = </label> 
            <input type="hidden" name="question3" value="3">
            <input type="text" name="answer3" />
            <br />
            <label> 4. 10 + 10 = </label> 
            <input type="hidden" name="question4" value="4">
            <input type="text" name="answer4" />
            <br />
            <label> 5. 5 * 5 = </label> 
            <input type="hidden" name="question5" value="5">
            <input type="text" name="answer5" />
            <br />
            <label> 6. 6 * 5 = </label> 
            <input type="hidden" name="question6" value="6">
            <input type="text" name="answer6" />
            <br />
            <label> 7. 10 + 7 = </label> 
            <input type="hidden" name="question7" value="7">
            <input type="text" name="answer7" />
            <br />
            <label> 8. 8 * 5 = </label> 
            <input type="hidden" name="question8" value="8">
            <input type="text" name="answer8" />
            <br />
            <label> 9. 9 * 5 = </label> 
            <input type="hidden" name="question9" value="9">
            <input type="text" name="answer9" />
            <br />
            <label> 10. 10 * 5 = </label> 
            <input type="hidden" name="question10" value="10">
            <input type="text" name="answer10" />
            <br />
            <button>Submit</button>
        </c:if>
        
        <c:if test="${!empty answer}">
            <h1>Correct answer.</h1>
        </c:if>
    </form>
</body>
</html>