package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandler;

public interface ExecCommandHandler
	extends MCEEventHandler
{
	public void onExecCommand(Editor ed, String cmd, boolean ui);
}
