package is.godswill.iterators;

import is.godswill.resources.BibleElement;

import java.lang.reflect.ParameterizedType;
import java.util.Iterator;

import org.apache.sling.api.resource.Resource;

public class BibleElementIterator<T extends BibleElement> implements
		Iterator<T> {
	private final Iterator<Resource> iterator;

	public BibleElementIterator(Iterator<Resource> iterator) {
		this.iterator = iterator;
	}

	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	@SuppressWarnings("unchecked")
	public T next() {
		Class<T> cls = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

		return this.iterator.next().adaptTo(cls);
	}

	public void remove() {
		this.iterator.remove();

	}

}