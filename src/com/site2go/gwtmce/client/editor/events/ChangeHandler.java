package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.editor.UndoLevel;
import com.site2go.gwtmce.client.editor.UndoManager;
import com.site2go.gwtmce.client.event.MCEEventHandler;

public interface ChangeHandler
	extends MCEEventHandler
{
	public void onChange(Editor sender, UndoLevel level, UndoManager um);
}
