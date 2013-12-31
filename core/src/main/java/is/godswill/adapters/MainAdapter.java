package is.godswill.adapters;

import is.godswill.resources.Bible;
import is.godswill.resources.Book;
import is.godswill.resources.Chapter;
import is.godswill.resources.Verse;

import org.apache.sling.api.resource.Resource;

public class MainAdapter {

	public static Verse adaptToVerse(Resource resource) {
		return new Verse(resource);
	}

	public static Book adaptToBook(Resource resource) {
		return new Book(resource);

	}

	public static Chapter adaptToChapter(Resource resource) {
		return new Chapter(resource);
	}

	public static Bible adaptToBible(Resource resource) {
		Bible result = null;
		if (resource.isResourceType(Bible.RESOURCE_TYPE)) {
			result = new Bible(resource);
		}

		return result;
	}

}