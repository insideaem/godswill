<%@ page session="false" %>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.jcr.*,java.util.*, is.godswill.*, org.apache.sling.api.resource.ResourceUtil, org.apache.sling.api.resource.Resource, org.apache.commons.lang.WordUtils"%>
<%@ taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0" %>
<%@ page trimDirectiveWhitespaces="true" %>
<sling:defineObjects />

<%
	// Current node is of type verse
	Verse verse = resource.adaptTo(Verse.class);
	String id = verse.getId();

	// Find next and prev node
	Verse prevVerse = null;
	Verse nextVerse = null;
	Verse current = null;
	
	Chapter chapter = verse.getParent();
	Iterator<Resource> verses = chapter.listChildren();
	while(verses.hasNext()){
		current = verses.next().adaptTo(Verse.class);
		String currentId = current.getId();
		
		if(id.equals(currentId)){
			// Current node found
			if(verses.hasNext()){
				nextVerse = verses.next().adaptTo(Verse.class);
			}
			break;
		}
		prevVerse = current;
	}
	
	String score = (String)request.getAttribute("score");
	if(score==null)
		score = "0";
	
	Book book = chapter.getParent();
	String chapterPrefix = WordUtils.capitalize(book.getName() +". "+chapter.getName());
	String label = chapterPrefix+":"+verse.getName();
	
	Resource reference=null;
	String referencePath=null;
	/*
	int firstSlashIndex = sid.indexOf('/');
	String idWithoutBible = sid.substring(firstSlashIndex+1);
	String referencePath = "/content/xrefs/" + idWithoutBible;
	Resource reference = resourceResolver.getResource(referencePath);
	*/
	
	String text = verse.getText();
	
	String next = chapterPrefix+":"+ (nextVerse == null ? "" : nextVerse.getName());
	String prev = chapterPrefix+":"+ (prevVerse == null ? "" : prevVerse.getName());
%>

{
	id: "<%=id%>", 
	text: "<%=text%>",
	label: "<%=label%>",
	score: "<%=score%>",
	next: "<%=next%>",
	prev: "<%=prev%>",
	references: <%if(reference != null){%>"<sling:include flush="true" path="<%=referencePath%>"/>"<%}else{%>""<%}%>
}