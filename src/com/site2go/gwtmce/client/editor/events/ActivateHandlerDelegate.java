package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;

public class ActivateHandlerDelegate
	extends MCEEventHandlerDelegate<ActivateHandler>
{
	public ActivateHandlerDelegate(ActivateHandler handler)
	{
		super(handler);
	}

	@Override
	public void delegate(ActivateHandler handler, FunctionArguments args)
	{
		Editor ed = args.getArg(0);
		Editor previous = args.getArg(1);
		handler.onActivate(ed, previous);
	}
}
