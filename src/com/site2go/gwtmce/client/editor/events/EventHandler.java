package com.site2go.gwtmce.client.editor.events;

import com.google.gwt.dom.client.NativeEvent;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandler;

public interface EventHandler
	extends MCEEventHandler
{
	/**
	 * This event gets executed when a DOM event occurs inside the editor contents like a key down or mouse click.
	 */
	public void onEvent(Editor ed, NativeEvent ev);
}
