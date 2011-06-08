package com.site2go.gwtmce.client.editor.events;

import com.google.gwt.core.client.JavaScriptObject;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandler;

public interface BeforeExecCommandHandler
	extends MCEEventHandler
{
	/**
	 * Small wrapper for the "context" object passed in for beforeexeccommand handler. Calling terminate() on this object from the handler will ensure
	 * the command being executed is suppressed.
	 * @author Sam
	 *
	 */
	public static class ExecCommandContextObject
		extends JavaScriptObject
	{
		protected ExecCommandContextObject() { }
		
		public final native void terminate()
		/*-{
			this.terminate = true;
		}-*/;
	}

	public void onBeforeExecCommand(Editor sender, String command, boolean ui, ExecCommandContextObject context);
}
