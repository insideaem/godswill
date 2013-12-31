package is.godswill.servlets;

import is.godswill.resources.Bible;
import is.godswill.resources.Book;
import is.godswill.resources.Chapter;
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

@SlingServlet(extensions = { "json" }, selectors = { "list" }, methods = { "GET" }, resourceTypes = { "godswill/bible" }, metatype = false)
public class BibleServlet extends SlingSafeMethodsServlet {

	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws ServletException,
			IOException {
		Bible bible = request.getResource().adaptTo(Bible.class);

		Iterator<Book> books = bible.getChildren();

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		JSONWriter jsonWriter = new JSONWriter(response.getWriter());

		try {
			jsonWriter.object();
			WriterUtil.writeBible(jsonWriter, bible);
			jsonWriter.key("loaded").value(true);
			jsonWriter.key("books");

			jsonWriter.array();

			while (books.hasNext()) {
				Book book = books.next();
				jsonWriter.object();
				WriterUtil.writeBook(jsonWriter, book);

				// Add chapters
				jsonWriter.key("chapters").array();
				Iterator<Chapter> chapters = book.getChildren();
				while (chapters.hasNext()) {
					Chapter chapter = chapters.next();
					jsonWriter.object();
					WriterUtil.writeChapter(jsonWriter, chapter);
					jsonWriter.key("loaded").value(false);
					jsonWriter.endObject();
				}
				jsonWriter.endArray();

				jsonWriter.endObject();
			}

			jsonWriter.endArray();

			jsonWriter.endObject();
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
