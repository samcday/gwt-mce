package com.site2go.gwtmce.client.editor;

import com.google.gwt.core.client.JavaScriptObject;

public class UndoLevel
	extends JavaScriptObject
{
	protected UndoLevel() { }

	public native final String getContent()
	/*-{
		return this.content;
	}-*/;
}
