package com.site2go.gwtmce.client.util;

import java.util.HashMap;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Overlay for a set of properties contained in an Object. Usually used to pass in configuration settings to a function. e.g<br>
 * var options = { blah : true, meh : null, kek : "value" };
 * Not really type-safe, so assumes caller knows what they're doing.
 * @author Sam Day
 */
public class PropertiesObject extends JavaScriptObject
{
	/**
	 * This is the only way to create a {@link PropertiesObject}.
	 * @return
	 */
	public static final <T extends PropertiesObject> T create()
	{
		TypeMarshaller.init();
		return JavaScriptObject.createObject().cast();
	}
	protected PropertiesObject() {}
	
	/**
	 * Sets multiple parameters contained in a {@link HashMap} all at once.
	 * @param parameters
	 */
	public final void setProperties(HashMap<String, Object> properties)
	{
		for(String property : properties.keySet())
		{
			this.setProperty(property, properties.get(property));
		}
	}

	public native final void setProperty(String name, Object value) /*-{ this[name] = value; }-*/;
	public native final void setProperty(String name, int value) /*-{ this[name] = value; }-*/;
	public native final void setProperty(String name, boolean value) /*-{ this[name] = value; }-*/;
	public native final <T> T getProperty(String name) /*-{ return $wnd._gwtMarshalTypeFromJS(this[name]); }-*/;
	public native final <T> T getProperty(String name, T defaultVal) /*-{
		if(typeof(this[name]) == "undefined") return defaultVal;
		return $wnd._gwtMarshalTypeFromJS(this[name]);
	}-*/;
}
