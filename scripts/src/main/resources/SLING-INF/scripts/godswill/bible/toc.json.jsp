<%@ page session="false" %>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.jcr.*, java.util.Iterator, is.godswill.*, org.apache.sling.api.resource.Resource"%>
<%@ taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0" %>
<%@ page trimDirectiveWhitespaces="true" %>
<sling:defineObjects />

<%	
	Bible bible = resource.adaptTo(Bible.class);
	Iterator<Resource> books = bible.listChildren();
%>
[
	<%
		while(books.hasNext()){
			Book book = books.next().adaptTo(Book.class);
	%>
		{
			"id": "<%=bible.getId()%>",
			"text": "<%=book.getName()%>",
			"path": "<%=book.getPath()%>"
		}
	<%
			if(books.hasNext()){
				%>,<%
			}
		}
	%>
]