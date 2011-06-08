package com.site2go.gwtmce.client.editor.events;


import com.site2go.gwtmce.client.editor.ContentObject;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandler;

public interface GetContentHandler
	extends MCEEventHandler
{
	public void onGetContent(Editor ed, ContentObject co);
}
