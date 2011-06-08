package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;

public class SetProgressStateHandlerDelegate
	extends MCEEventHandlerDelegate<SetProgressStateHandler>
{
	public SetProgressStateHandlerDelegate(SetProgressStateHandler handler)
	{
		super(handler);
	}

	@Override
	public void delegate(SetProgressStateHandler handler, FunctionArguments args)
	{
		Editor sender = args.getArg(0);
		boolean enabled = args.getArg(1);
		
		Object timeoutObj = args.getArg(2);
		float timeout;
		if(timeoutObj instanceof Double)
		{
			double timeoutDbl = (Double)timeoutObj;
			timeout = (float)timeoutDbl;
		}
		else
		{
			timeout = (Float)timeoutObj;
		}

		Object o = args.getArg(3);

		handler.onSetProgressState(sender, enabled, timeout, o);
	}
}
