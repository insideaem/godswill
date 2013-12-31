package is.godswill.servlets;

import is.godswill.iterators.BibleIterator;
import is.godswill.resources.Bible;
import is.godswill.utils.WriterUtil;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.io.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SlingServlet(extensions = { "json" }, selectors = { "list" }, methods = { "GET" }, resourceTypes = { "godswill/bibles" }, metatype = false)
public class ListBibleServlet extends SlingSafeMethodsServlet {

	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {
		Resource root = request.getResource();

		Iterator<Bible> bibles = new BibleIterator(root.listChildren());

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		JSONWriter jsonWriter = new JSONWriter(response.getWriter());

		try {

			jsonWriter.array();

			while (bibles.hasNext()) {
				Bible bible = bibles.next();
				if (bible != null) {
					jsonWriter.object();
					WriterUtil.writeBible(jsonWriter, bible);
					jsonWriter.key("loaded").value(false);
					jsonWriter.endObject();
				}
			}

			jsonWriter.endArray();
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
