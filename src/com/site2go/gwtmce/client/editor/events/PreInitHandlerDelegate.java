package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;

public class PreInitHandlerDelegate
	extends MCEEventHandlerDelegate<PreInitHandler>
{
	public PreInitHandlerDelegate(PreInitHandler handler)
	{
		super(handler);
	}

	@Override
	public void delegate(PreInitHandler handler, FunctionArguments args)
	{
		Editor ed = args.getArg(0);
		
		handler.onPreInit(ed);
	}
}
