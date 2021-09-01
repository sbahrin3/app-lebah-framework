<%@page import="lebah.util.UIDGenerator"%>
<%
String refKey = (String) session.getAttribute("_ref_key");
if ( refKey == null ) {
	refKey = "default";
}

session.invalidate();
String randomNo = UIDGenerator.getUID();

response.sendRedirect("c/" + refKey + "?rndId=" + randomNo); 
//response.sendRedirect("http://192.168.0.9:9292/MMDIS_UI/index#!/dashboard");
%>

Logging out... please wait...
