package is.godswill.iterators;

import is.godswill.resources.Verse;

import java.util.Iterator;

import org.apache.sling.api.resource.Resource;

public class VerseIterator extends BibleElementIterator<Verse> {

	public VerseIterator(Iterator<Resource> iterator) {
		super(iterator);
	}

}