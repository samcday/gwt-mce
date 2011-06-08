package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandler;

import com.google.gwt.dom.client.NativeEvent;

public interface KeyPressHandler
	extends MCEEventHandler
{
	public void onKeyPress(Editor sender, NativeEvent ev);
}
