package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.editor.UndoLevel;
import com.site2go.gwtmce.client.editor.UndoManager;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;

public class UndoHandlerDelegate
	extends MCEEventHandlerDelegate<UndoHandler>
{
	public UndoHandlerDelegate(UndoHandler handler)
	{
		super(handler);
	}

	@Override
	public void delegate(UndoHandler handler, FunctionArguments args)
	{
		Editor ed = args.getArg(0);
		UndoLevel level = args.getArg(1);
		UndoManager um = args.getArg(2);

		handler.onUndo(ed, level, um);
	}
}
