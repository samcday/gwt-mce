package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandler;

public interface DeactivateHandler
	extends MCEEventHandler
{
	public void onDeactivate(Editor ed, Editor newEd);
}
