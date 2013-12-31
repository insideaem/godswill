<%@ page session="false" %>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.jcr.query.*, javax.jcr.*, java.util.*, org.apache.sling.api.resource.Resource"%>
<%@ taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0" %>
<%@ page trimDirectiveWhitespaces="true" %>
<sling:defineObjects />
{
  "login": <%=!"anonymous".equals(request.getAttribute("org.osgi.service.http.authentication.remote.user"))%>,
  "user": "<%=request.getAttribute("org.osgi.service.http.authentication.remote.user")%>"
}