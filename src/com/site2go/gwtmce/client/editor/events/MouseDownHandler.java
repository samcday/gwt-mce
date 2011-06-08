package com.site2go.gwtmce.client.editor.events;

import com.google.gwt.dom.client.NativeEvent;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandler;

public interface MouseDownHandler
	extends MCEEventHandler
{
	public void onMouseDown(Editor editor, NativeEvent ev);
}
