package is.godswill.iterators;

import is.godswill.resources.Bible;

import java.util.Iterator;

import org.apache.sling.api.resource.Resource;

public class BibleIterator extends BibleElementIterator<Bible> {

	public BibleIterator(Iterator<Resource> iterator) {
		super(iterator);
	}

}