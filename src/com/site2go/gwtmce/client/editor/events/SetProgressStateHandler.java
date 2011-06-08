package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandler;

public interface SetProgressStateHandler
	extends MCEEventHandler
{
	public void onSetProgressState(Editor sender, boolean enabled, float timeout, Object o);
}
