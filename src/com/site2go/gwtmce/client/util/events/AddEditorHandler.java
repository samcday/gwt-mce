package com.site2go.gwtmce.client.util.events;


import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandler;

public interface AddEditorHandler
	extends MCEEventHandler
{
	public void onAddEditor(Editor editor);
}
