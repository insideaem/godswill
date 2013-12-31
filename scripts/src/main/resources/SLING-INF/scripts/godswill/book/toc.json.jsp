<%@ page session="false" %>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.jcr.*,java.util.*, is.godswill.Chapter, org.apache.sling.api.resource.Resource"%>
<%@ taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0" %>
<%@ page trimDirectiveWhitespaces="true" %>
<sling:defineObjects />

<%	
	Iterator<Resource> chapters = resource.listChildren();
%>
[
	<%
		while(chapters.hasNext()){
			Chapter chapter = chapters.next().adaptTo(Chapter.class);
	%>
		{
			"id": "<%=chapter.getId()%>",
			"text": "<%=chapter.getName()%>",
			"path": "<%=chapter.getPath()%>"
		}
	<%
			if(chapters.hasNext()){
				%>,<%
			}
		}
	%>
]