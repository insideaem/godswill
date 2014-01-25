package is.godswill.servlets;

import is.godswill.resources.Verse;
import is.godswill.utils.WriterUtil;

import java.io.IOException;

import javax.jcr.Session;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;
import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.io.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SlingServlet(extensions = { "json" }, selectors = { "search" }, methods = { "GET" }, resourceTypes = {
		"godswill/bible", "godswill/book", "godswill/chapter" }, metatype = false)
public class SearchServlet extends SlingSafeMethodsServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void doGet(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {
		Workspace workspace = request.getResourceResolver()
				.adaptTo(Session.class).getWorkspace();

		// Current node contains all bibles
		String query = request.getParameter("q");
		String searchPath = request.getResource().getPath();

		String xPathQueryString = "/jcr:root"
				+ searchPath
				+ "//element(*,sling:Folder)[@sling:resourceType='godswill/verse' and jcr:contains(@text,'"
				+ query + "')] order by @jcr:score descending";

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		JSONWriter jsonWriter = new JSONWriter(response.getWriter());

		try {
			QueryManager queryManager = workspace.getQueryManager();
			Query xPathQuery = queryManager.createQuery(xPathQueryString,
					Query.XPATH);

			xPathQuery.setLimit(100);
			QueryResult queryResult = xPathQuery.execute();

			RowIterator rowIterator = queryResult.getRows();

			// Add verses
			jsonWriter.array();
			while (rowIterator.hasNext()) {
				Row result = rowIterator.nextRow();

				String versePath = result.getPath();
				Verse verse = request.getResourceResolver()
						.getResource(versePath).adaptTo(Verse.class);

				jsonWriter.object();
				WriterUtil.writeVerse(jsonWriter, verse);
				jsonWriter.endObject();

			}
			jsonWriter.endArray();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
