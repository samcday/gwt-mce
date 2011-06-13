package com.site2go.gwtmce.client.dom;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.site2go.gwtmce.client.util.PropertiesObject;

public class Serializer 
	extends JavaScriptObject {
	protected Serializer() {}

	public native final String serialize(Element el, PropertiesObject o) /*-{
		return this.serialize(el, o);
	}-*/;
	
	public static class SerializerOptions 
		extends PropertiesObject {
		protected SerializerOptions() {}

		/**
		 * If set, tells the {@link Serializer} to only serialize the inner
		 * contents of node.
		 */
		public final void setGetInner(boolean getInner) {
			this.setProperty("getInner", getInner);
		}
		
		public final boolean getGetInner() {
			return this.getProperty("getInner");
		}
	}
}
