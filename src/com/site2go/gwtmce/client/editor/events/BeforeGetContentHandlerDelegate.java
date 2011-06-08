package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwtmce.client.editor.ContentObject;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;

public class BeforeGetContentHandlerDelegate
	extends MCEEventHandlerDelegate<BeforeGetContentHandler>
{
	public BeforeGetContentHandlerDelegate(BeforeGetContentHandler handler)
	{
		super(handler);
	}

	@Override
	public void delegate(BeforeGetContentHandler handler, FunctionArguments args)
	{
		Editor ed = args.getArg(0);
		ContentObject o = args.getArg(1);
		handler.onBeforeGetContent(ed, o);
	}
}
