<%@ page session="false" %>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.jcr.*,java.util.*, is.godswill.*, org.apache.sling.api.resource.Resource"%>
<%@ taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0" %>
<%@ page trimDirectiveWhitespaces="true" %>
<sling:defineObjects />

<%	
	Iterator<Resource> verses = resource.listChildren();
%>
[
	<%
		while(verses.hasNext()){
			Verse verse = verses.next().adaptTo(Verse.class);
	%>
		{
			"id": "<%=verse.getId()%>",
			"text": "<%=verse.getName()%>",
			iconCls: "verse",
			leaf: true
		}
	<%
			if(verses.hasNext()){
				%>,<%
			}
		}
	%>
]