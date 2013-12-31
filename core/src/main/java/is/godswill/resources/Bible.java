package is.godswill.resources;

import is.godswill.iterators.BookIterator;

import java.util.Iterator;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

public class Bible extends BibleElement {

	private final String title;
	private final String description;

	public static final String RESOURCE_TYPE = "godswill/bible";

	public Bible(Resource resource) {
		super(resource);

		ValueMap values = resource.adaptTo(ValueMap.class);
		title = values.get("title", String.class);
		description = values.get("description", String.class);
	}

	@Override
	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public BibleElement getParent() {
		throw new RuntimeException("Should not be called");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<Book> getChildren() {
		return new BookIterator(this.listChildren());
	}
}
