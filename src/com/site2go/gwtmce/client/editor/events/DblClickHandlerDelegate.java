package com.site2go.gwtmce.client.editor.events;

import com.google.gwt.dom.client.NativeEvent;
import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.editor.impl.EditorImpl;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;

public class DblClickHandlerDelegate
	extends MCEEventHandlerDelegate<DblClickHandler>
{
	public DblClickHandlerDelegate(DblClickHandler handler)
	{
		super(handler);
	}

	@Override
	public void delegate(DblClickHandler handler, FunctionArguments args)
	{
		Editor sender = EditorImpl.getEditor((EditorImpl)args.getArg(0));
		NativeEvent ev = args.getArg(1);

		handler.onDblClick(sender, ev);
	}
}
