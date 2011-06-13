package com.site2go.gwtmce.client.editor.events;

import com.google.gwt.dom.client.Element;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandler;
import com.site2go.gwtmce.client.ui.ControlManager;
import com.site2go.gwtmce.client.util.PropertiesObject;

public interface NodeChangeHandler
	extends MCEEventHandler {
	public void onNodeChange(Editor ed, ControlManager cm, Element e, boolean isCollapsed, PropertiesObject o);
}
