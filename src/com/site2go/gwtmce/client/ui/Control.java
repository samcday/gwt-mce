package com.site2go.gwtmce.client.ui;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

public class Control
	extends JavaScriptObject
{
	protected Control() { }

	/**
	 * Removes the control. This means it will be removed from the DOM and any events tied to it will also be removed.
	 */
	public final native void remove() /*-{
		this.remove();
	}-*/;

	/**
	 * Renders the control to the specified container element.
	 * @param element HTML DOM element to add control to.
	 */
	public final native void renderTo(Element element) /*-{
		this.renderTo(element);
	}-*/;
}
