package is.godswill.iterators;

import is.godswill.resources.Book;

import java.util.Iterator;

import org.apache.sling.api.resource.Resource;

public class BookIterator extends BibleElementIterator<Book> {

	public BookIterator(Iterator<Resource> iterator) {
		super(iterator);
	}

}