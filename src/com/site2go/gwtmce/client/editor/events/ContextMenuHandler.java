package com.site2go.gwtmce.client.editor.events;

import com.google.gwt.dom.client.NativeEvent;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandler;

public interface ContextMenuHandler
	extends MCEEventHandler
{
	public void onContextMenu(Editor sender, NativeEvent ev);
}
