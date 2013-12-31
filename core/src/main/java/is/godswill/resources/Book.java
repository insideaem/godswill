package is.godswill.resources;

import is.godswill.iterators.ChapterIterator;

import java.util.Iterator;

import org.apache.sling.api.resource.Resource;

public class Book extends BibleElement {

	public Book(Resource resource) {
		super(resource);
	}

	@Override
	public Bible getParent() {
		return this.getResource().getParent().adaptTo(Bible.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<Chapter> getChildren() {
		return new ChapterIterator(this.listChildren());
	}

}
