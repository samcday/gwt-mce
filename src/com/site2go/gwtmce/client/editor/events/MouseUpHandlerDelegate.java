package com.site2go.gwtmce.client.editor.events;

import com.google.gwt.dom.client.NativeEvent;
import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;

public class MouseUpHandlerDelegate
	extends MCEEventHandlerDelegate<MouseUpHandler>
{
	public MouseUpHandlerDelegate(MouseUpHandler handler)
	{
		super(handler);
	}

	@Override
	public void delegate(MouseUpHandler handler, FunctionArguments args)
	{
		Editor sender = args.getArg(0);
		NativeEvent ev = args.getArg(1);
		
		handler.onMouseUp(sender, ev);
	}
}
