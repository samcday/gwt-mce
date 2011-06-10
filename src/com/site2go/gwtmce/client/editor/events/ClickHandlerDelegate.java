package com.site2go.gwtmce.client.editor.events;

import com.google.gwt.dom.client.NativeEvent;
import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.editor.impl.EditorImpl;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;

public class ClickHandlerDelegate
	extends MCEEventHandlerDelegate<ClickHandler>
{
	public ClickHandlerDelegate(ClickHandler handler)
	{
		super(handler);
	}

	@Override
	public void delegate(ClickHandler handler, FunctionArguments args)
	{
		Editor ed = EditorImpl.getEditor((EditorImpl)args.getArg(0));
		NativeEvent ev = args.getArg(1);
		
		handler.onClick(ed, ev);
	}
}
