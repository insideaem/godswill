package is.godswill.servlets;

import is.godswill.resources.Chapter;
import is.godswill.resources.Verse;
import is.godswill.utils.WriterUtil;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.io.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SlingServlet(extensions = { "json" }, selectors = { "list" }, methods = { "GET" }, resourceTypes = { "godswill/chapter" }, metatype = false)
public class ChapterServlet extends SlingSafeMethodsServlet {

	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {
		Chapter chapter = request.getResource().adaptTo(Chapter.class);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		JSONWriter jsonWriter = new JSONWriter(response.getWriter());

		try {

			jsonWriter.object();
			WriterUtil.writeChapter(jsonWriter, chapter);
			jsonWriter.key("loaded").value(true);

			// Add verses
			jsonWriter.key("verses").array();
			Iterator<Verse> verses = chapter.getChildren();

			while (verses.hasNext()) {
				Verse verse = verses.next();
				jsonWriter.object();
				WriterUtil.writeVerse(jsonWriter, verse);
				jsonWriter.endObject();
			}
			jsonWriter.endArray();

			jsonWriter.endObject();
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
