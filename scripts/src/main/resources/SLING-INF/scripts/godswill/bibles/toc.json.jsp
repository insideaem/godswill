<%@ page session="false" %>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.jcr.*, org.apache.sling.api.resource.Resource"%>
<%@ taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0" %>
<%@ page trimDirectiveWhitespaces="true" %>
<sling:defineObjects />

<%	
	String node = request.getParameter("node");
	Node root = null;
	
	if(node==null || "root".equals(node)){
		root = currentNode.getNodes().nextNode();
	}
	else{
		root = resourceResolver.getResource("/content/godswill/"+node).adaptTo(Node.class);
	}
	
	NodeIterator children = root.getNodes();
%>
{"id" : "<%=root.getProperty("sid").getValue().getString()%>", "text": "<%=root.getName()%>",
children: [
	<%
		while(children.hasNext()){
			Node child = children.nextNode();
			String contentType = child.getProperty("contentType").getValue().getString();
			
			boolean isVerse = "verse".equals(contentType);
			boolean isChapter = "chapter".equals(contentType);
			boolean isBook = "book".equals(contentType);
			boolean isBible = "bible".equals(contentType);
			String iconCls = isBook ? "book" : "";
			String text = isBook ? child.getProperty("name").getValue().getString() : child.getName();
			String sortNumber = child.getName();
			if(isBook){
				try{
				sortNumber = child.getProperty("bookNumber").getValue().getString() ;
				}
				catch(Exception e){
					sortNumber = e.getMessage();
				}
			}
	%>
		{
			"id": "<%=child.getProperty("sid").getValue().getString()%>",
			"text": "<%=text%>",
			"iconCls": "<%=iconCls%>",
			"sort": "<%=sortNumber%>",
			"leaf" : "<%=isVerse%>"
		}
	<%
			if(children.hasNext()){
				%>,<%
			}
		}
	%>
]}