package com.site2go.gwtmce.client.dom;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

public class Selection
	extends JavaScriptObject
{
	protected Selection() { }
	
	public native final Element getStart() /*-{
		return this.getStart();
	}-*/;

	public native final Element getEnd() /*-{
		return this.getEnd();
	}-*/;
	
	public native final boolean isCollapsed() /*-{
		return this.isCollapsed();
	}-*/;
}
