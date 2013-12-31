package is.godswill.resources;

import java.util.Iterator;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

public class Verse extends BibleElement {
	private final String text;
	private final String label;

	public Verse(Resource resource) {
		super(resource);

		ValueMap values = resource.adaptTo(ValueMap.class);
		text = values.get("text", String.class);
		Chapter chapter = this.getParent();
		Book book = chapter.getParent();
		String chapterPrefix = book.getTitle() + ". " + chapter.getName();
		label = chapterPrefix + ":" + this.getName();

	}

	public String getLabel() {
		return this.label;
	}

	public String getText() {
		return text;
	}

	@Override
	public Chapter getParent() {
		return this.getResource().getParent().adaptTo(Chapter.class);
	}

	@Override
	public <T extends BibleElement> Iterator<T> getChildren() {
		throw new RuntimeException("Should not be called");
	}

}
