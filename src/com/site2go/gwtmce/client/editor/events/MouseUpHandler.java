package com.site2go.gwtmce.client.editor.events;

import com.google.gwt.dom.client.NativeEvent;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandler;

public interface MouseUpHandler
	extends MCEEventHandler
{
	public void onMouseUp(Editor sender, NativeEvent ev);
}
