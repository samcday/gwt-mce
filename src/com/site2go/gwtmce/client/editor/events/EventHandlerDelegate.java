package com.site2go.gwtmce.client.editor.events;

import com.google.gwt.dom.client.NativeEvent;
import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.editor.impl.EditorImpl;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;

public class EventHandlerDelegate
	extends MCEEventHandlerDelegate<EventHandler>
{
	public EventHandlerDelegate(EventHandler handler)
	{
		super(handler);
	}

	@Override
	public void delegate(EventHandler handler, FunctionArguments args)
	{
		Editor ed = EditorImpl.getEditor((EditorImpl)args.getArg(0));
		NativeEvent ev = args.getArg(1);
		handler.onEvent(ed, ev);
	}
}
