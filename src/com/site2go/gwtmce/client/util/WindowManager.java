package com.site2go.gwtmce.client.util;

import com.google.gwt.core.client.JavaScriptObject;
import com.site2go.gwtmce.client.util.PropertiesObject;

public class WindowManager
	extends JavaScriptObject
{
	protected WindowManager() { }
	
	public final native void open(PropertiesObject s, PropertiesObject p)
	/*-{
		this.open(s, p);
	}-*/;
}
