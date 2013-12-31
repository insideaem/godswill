package is.godswill.utils;

import is.godswill.resources.Bible;
import is.godswill.resources.Book;
import is.godswill.resources.Chapter;
import is.godswill.resources.Verse;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.io.JSONWriter;

public class WriterUtil {

	public static void writeBible(JSONWriter jsonWriter, Bible bible)
			throws JSONException {
		jsonWriter.key("id").value(bible.getId());
		jsonWriter.key("path").value(bible.getShortPath());
		jsonWriter.key("name").value(bible.getName());
		jsonWriter.key("title").value(bible.getTitle());
		jsonWriter.key("description").value(bible.getDescription());
	}

	public static void writeBook(JSONWriter jsonWriter, Book book)
			throws JSONException {
		jsonWriter.key("id").value(book.getId());
		jsonWriter.key("title").value(book.getTitle());
		jsonWriter.key("name").value(book.getName());
		jsonWriter.key("path").value(book.getShortPath());

		Book next = (Book) book.getNext();
		if (next != null) {
			jsonWriter.key("next").value(next.getId());
		}
		Book previous = (Book) book.getPrevious();
		if (previous != null) {
			jsonWriter.key("previous").value(previous.getId());
		}

	}

	public static void writeChapter(JSONWriter jsonWriter, Chapter chapter)
			throws JSONException {
		jsonWriter.key("id").value(chapter.getId());
		jsonWriter.key("path").value(chapter.getShortPath());
		jsonWriter.key("name").value(chapter.getName());

		Chapter next = (Chapter) chapter.getNext();
		if (next != null) {
			jsonWriter.key("next").value(next.getId());
		}
		Chapter previous = (Chapter) chapter.getPrevious();
		if (previous != null) {
			jsonWriter.key("previous").value(previous.getId());
		}

	}

	public static void writeVerse(JSONWriter jsonWriter, Verse verse)
			throws JSONException {
		jsonWriter.key("id").value(verse.getId());
		jsonWriter.key("text").value(verse.getText());
		jsonWriter.key("label").value(verse.getLabel());
		jsonWriter.key("name").value(verse.getName());
		Verse next = (Verse) verse.getNext();
		if (next != null) {
			jsonWriter.key("next").value(next.getId());
		}
		Verse previous = (Verse) verse.getPrevious();
		if (previous != null) {
			jsonWriter.key("previous").value(previous.getId());
		}

	}

}
