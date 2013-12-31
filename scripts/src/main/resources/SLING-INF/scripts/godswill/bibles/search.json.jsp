<%@ page session="false" %>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.jcr.query.*, javax.jcr.*, java.util.*, org.apache.sling.api.resource.Resource"%>
<%@ taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0" %>
<%@ page trimDirectiveWhitespaces="true" %>
<sling:defineObjects />
<%	
	response.setHeader("Cache-Control","max-age=3600");
	Workspace ws = currentNode.getSession().getWorkspace();
	QueryManager qm = ws.getQueryManager();
	
	// Current node contains all bibles
	String query = request.getParameter("q");
	String bible = request.getParameter("b");
	if(bible==null)
		bible = "kjv";
	String xPathQueryString = "/jcr:root/content/godswill/"+bible+"//element(*,sling:Folder)[@sling:resourceType='godswill/verse' and jcr:contains(@text,'"+query+"')] order by @jcr:score descending";
	
	Query xPathQuery = qm.createQuery(xPathQueryString, Query.XPATH);
	QueryResult queryResult = xPathQuery.execute();
	/*
	String[] columnNames = queryResult.getColumnNames();
	for(String c : columnNames){
		out.print(c);
		out.print("<br/>");
	}
	out.flush();
	*/
	RowIterator rowIterator = queryResult.getRows();
	NodeIterator nodeIterator = queryResult.getNodes();
	
%>[<%
		while(rowIterator.hasNext()){
			Node node = nodeIterator.nextNode();
			String versePath = node.getPath();
			String id = node.getName();
			
			Row result = rowIterator.nextRow();
			String score = result.getValue("jcr:score").getString();
			request.setAttribute("score",score);
	%>
	"<%=score+"#"+id%>"
	<%
		if(rowIterator.hasNext()){
		%>,<%
		}
	}
	%>]