package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwtmce.client.editor.ContentObject;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.editor.impl.EditorImpl;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;
import com.site2go.gwtmce.client.util.PropertiesObject;

public class GetContentHandlerDelegate
	extends MCEEventHandlerDelegate<GetContentHandler>
{
	public GetContentHandlerDelegate(GetContentHandler handler)
	{
		super(handler);
	}

	@Override
	public void delegate(GetContentHandler handler, FunctionArguments args)
	{
		Editor editor = EditorImpl.getEditor((EditorImpl)args.getArg(0));
		ContentObject co = ContentObject.wrap((PropertiesObject)args.getArg(1));
		
		handler.onGetContent(editor, co);
	}
}
