package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.editor.impl.EditorImpl;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;

public class BeforeRenderUIHandlerDelegate
	extends MCEEventHandlerDelegate<BeforeRenderUIHandler>
{
	public BeforeRenderUIHandlerDelegate(BeforeRenderUIHandler handler)
	{
		super(handler);
	}

	@Override
	public void delegate(BeforeRenderUIHandler handler, FunctionArguments args)
	{
		Editor ed = EditorImpl.getEditor((EditorImpl)args.getArg(0));
		handler.onBeforeRenderUI(ed);
	}
}
