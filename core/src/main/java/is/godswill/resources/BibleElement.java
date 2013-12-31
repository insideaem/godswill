package is.godswill.resources;

import is.godswill.utils.IDBuilder;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceWrapper;
import org.apache.sling.api.resource.ValueMap;

public abstract class BibleElement extends ResourceWrapper {

	private final String id;
	private BibleElement next;
	private final boolean nextAndPreviousInited = false;
	private BibleElement previous;
	private String name;
	private final String title;

	public BibleElement(Resource resource) {
		super(resource);
		this.id = IDBuilder.buildIdFromPath(resource.getPath());

		ValueMap values = resource.adaptTo(ValueMap.class);
		this.name = values.get("name", String.class);
		if (StringUtils.isBlank(name)) {
			this.name = super.getName();
		}
		this.title = values.get("title", String.class);
	}

	public abstract <T extends BibleElement> Iterator<T> getChildren();

	@Override
	public abstract BibleElement getParent();

	public String getTitle() {
		return title;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public final String getId() {
		return id;
	}

	public String getShortPath() {
		return getPath().replaceFirst("/content/godswill/", "");
	}

	public BibleElement getNext() {
		if (!nextAndPreviousInited) {
			this.initNextAndPrevious();
		}
		return this.next;
	}

	public BibleElement getPrevious() {
		if (!nextAndPreviousInited) {
			this.initNextAndPrevious();
		}
		return this.previous;
	}

	private final void initNextAndPrevious() {
		// Find next element
		BibleElement parent = this.getParent();

		Iterator<BibleElement> children = parent.getChildren();
		BibleElement current;
		while (children.hasNext()) {
			current = children.next();
			String currentId = current.getId();

			if (this.getId().equals(currentId)) {
				// Current element found
				if (children.hasNext()) {
					this.next = children.next();
				}
				break;
			}
			// Update previous verse to current
			previous = current;
		}
	}
}
