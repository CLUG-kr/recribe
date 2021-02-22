<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script>
alert("............");
</script>

<c:choose>
	<c:when test="${result == 0}">
		<script>
			alert("회원가입에 실패하였습니다. 다시 가입해 주시기 바랍니다.");
			history.back();
		</script>  
	</c:when>
	<c:when test="${result == 1}">
		<script>
			alert("회원가입에 성공했습니다! 이제 레크라이브를 즐겨보세요.");
			location.href="${pageContext.request.contextPath}/main";
		</script>
	</c:when>
</c:choose>