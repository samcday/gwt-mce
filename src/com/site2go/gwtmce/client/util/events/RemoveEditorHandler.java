package com.site2go.gwtmce.client.util.events;

import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandler;

public interface RemoveEditorHandler
	extends MCEEventHandler
{
	public void onRemoveEditor(Editor editor);
}
