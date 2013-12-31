package is.godswill.adapters;

import is.godswill.resources.Bible;
import is.godswill.resources.Book;
import is.godswill.resources.Chapter;
import is.godswill.resources.Verse;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.adapter.AdapterFactory;
import org.apache.sling.api.resource.Resource;

@Component(metatype = false)
@Service(value = org.apache.sling.api.adapter.AdapterFactory.class)
public class MainAdapterFactory implements AdapterFactory {

	@Property(name = "adapters")
	public static final String[] ADAPTER_CLASSES = { Verse.class.getName(),
			Chapter.class.getName(), Book.class.getName(),
			Bible.class.getName() };

	@Property(name = "adaptables")
	public static final String[] ADAPTABLE_CLASSES = { Resource.class.getName() };

	public <AdapterType> AdapterType getAdapter(Object adaptable,
			Class<AdapterType> type) {
		if (adaptable instanceof Resource) {
			return getAdapter((Resource) adaptable, type);
		}
		return null;
	}

	/**
	 * Adapter
	 * 
	 * @param resource
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <AdapterType> AdapterType getAdapter(Resource resource,
			Class<AdapterType> type) {
		AdapterType result = null;
		if (resource != null) {
			if (type == Verse.class) {
				return (AdapterType) MainAdapter.adaptToVerse(resource);
			} else if (type == Book.class) {
				return (AdapterType) MainAdapter.adaptToBook(resource);
			} else if (type == Chapter.class) {
				return (AdapterType) MainAdapter.adaptToChapter(resource);
			} else if (type == Bible.class) {
				return (AdapterType) MainAdapter.adaptToBible(resource);

			}

		}

		return result;
	}

}
