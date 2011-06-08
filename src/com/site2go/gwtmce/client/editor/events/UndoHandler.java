package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.editor.UndoLevel;
import com.site2go.gwtmce.client.editor.UndoManager;
import com.site2go.gwtmce.client.event.MCEEventHandler;

public interface UndoHandler
	extends MCEEventHandler
{
	public void onUndo(Editor sender, UndoLevel level, UndoManager um);
}
