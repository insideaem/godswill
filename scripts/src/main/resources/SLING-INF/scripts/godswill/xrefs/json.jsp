<%@ page session="false" %>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.jcr.*, org.apache.sling.api.resource.Resource, org.apache.commons.lang.WordUtils"%>
<%@ taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0" %>
<%@ page trimDirectiveWhitespaces="true" %>
<sling:defineObjects />
<%	
	// Current node contains all references as property
	Value[] references = currentNode.getProperty("references").getValues();
	int counter = 0;

	for(Value reference : references){
		counter++;
		String ref = reference.getString();
%><%=ref%><%
		if(counter<references.length){
		%>,<%
		}
	}
%>