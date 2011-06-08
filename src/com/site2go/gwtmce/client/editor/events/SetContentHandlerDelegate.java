package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwtmce.client.editor.ContentObject;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;

public class SetContentHandlerDelegate
	extends MCEEventHandlerDelegate<SetContentHandler>
{
	public SetContentHandlerDelegate(SetContentHandler handler)
	{
		super(handler);
	}

	@Override
	public void delegate(SetContentHandler handler, FunctionArguments args)
	{
		Editor ed = args.getArg(0);
		ContentObject co = args.getArg(1);
		
		handler.onSetContent(ed, co);
	}
}
