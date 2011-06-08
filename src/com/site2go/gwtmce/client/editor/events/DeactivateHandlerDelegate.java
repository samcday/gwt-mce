package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;

public class DeactivateHandlerDelegate
	extends MCEEventHandlerDelegate<DeactivateHandler>
{
	public DeactivateHandlerDelegate(DeactivateHandler handler)
	{
		super(handler);
	}

	@Override
	public void delegate(DeactivateHandler handler, FunctionArguments args)
	{
		Editor ed = args.getArg(0);
		Editor newEd = args.getArg(1);

		handler.onDeactivate(ed, newEd);
	}
}
