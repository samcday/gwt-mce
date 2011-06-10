package com.site2go.gwtmce.client.util.events;

import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.editor.impl.EditorImpl;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;

public class AddEditorHandlerDelegate
	extends MCEEventHandlerDelegate<AddEditorHandler>
{
	public AddEditorHandlerDelegate(AddEditorHandler handler)
	{
		super(handler);
	}

	@Override
	public void delegate(AddEditorHandler handler, FunctionArguments args)
	{
		Editor editor = EditorImpl.getEditor((EditorImpl)args.getArg(1));
		handler.onAddEditor(editor);
	}
}
