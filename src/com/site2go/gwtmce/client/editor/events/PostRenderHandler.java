package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandler;

public interface PostRenderHandler
	extends MCEEventHandler
{
	public void onPostRender(Editor ed);
}
