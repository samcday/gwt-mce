package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwtmce.client.editor.ContentObject;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.editor.impl.EditorImpl;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;
import com.site2go.gwtmce.client.util.PropertiesObject;

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
		Editor ed = EditorImpl.getEditor((EditorImpl)args.getArg(0));
		ContentObject co = ContentObject.wrap((PropertiesObject)args.getArg(1));
		
		handler.onSetContent(ed, co);
	}
}
