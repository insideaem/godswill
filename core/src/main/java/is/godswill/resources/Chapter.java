package is.godswill.resources;

import is.godswill.iterators.VerseIterator;

import java.util.Iterator;

import org.apache.sling.api.resource.Resource;

public class Chapter extends BibleElement {

	public Chapter(Resource resource) {
		super(resource);

	}

	@Override
	public Book getParent() {
		return this.getResource().getParent().adaptTo(Book.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<Verse> getChildren() {
		return new VerseIterator(this.listChildren());
	}

}
