package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.editor.UndoLevel;
import com.site2go.gwtmce.client.editor.UndoManager;
import com.site2go.gwtmce.client.editor.impl.EditorImpl;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;

public class ChangeHandlerDelegate
	extends MCEEventHandlerDelegate<ChangeHandler>
{
	public ChangeHandlerDelegate(ChangeHandler handler)
	{
		super(handler);
	}

	@Override
	public void delegate(ChangeHandler handler, FunctionArguments args)
	{
		Editor ed = EditorImpl.getEditor((EditorImpl)args.getArg(0));
		UndoLevel l = args.getArg(1);
		UndoManager um = args.getArg(2);

		handler.onChange(ed, l, um);
	}
}
