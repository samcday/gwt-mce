package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandler;

public interface ActivateHandler
	extends MCEEventHandler
{
	/**
	 * Fires when the editor is activated.
	 * @author Sam
	 */
	public void onActivate(Editor editor, Editor previous);
}
