package is.godswill.iterators;

import is.godswill.resources.Chapter;

import java.util.Iterator;

import org.apache.sling.api.resource.Resource;

public class ChapterIterator extends BibleElementIterator<Chapter> {

	public ChapterIterator(Iterator<Resource> iterator) {
		super(iterator);
	}

}