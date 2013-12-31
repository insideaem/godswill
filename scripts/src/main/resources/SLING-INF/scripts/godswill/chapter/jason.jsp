<%@ page session="false" %>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.jcr.*,java.util.*, is.godswill.*, org.apache.sling.api.resource.Resource"%>
<%@ taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0" %>
<%@ page trimDirectiveWhitespaces="true" %>
<sling:defineObjects />

<%
	// Current resource is of the chapter
	Chapter chapter = resource.adaptTo(Chapter.class);
	Book book = chapter.getParent();
	
	Iterator<Resource> verses = resource.listChildren();
%>

{
	id: "<%=chapter.getId()%>", 
	book: "<%=book.getName()%>", 
	chapter: "<%=chapter.getName() %>", 
	
	verses: [
	<%
		while(verses.hasNext()){
			Verse verse = verses.next().adaptTo(Verse.class);
			String path = verse.getPath();
	%>
		<sling:include flush="true" path="<%=path%>"/>
	<%
			if(verses.hasNext()){
				%>,<%
			}
		}
	%>
	]
}