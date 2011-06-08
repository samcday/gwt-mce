package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;

public class ExecCommandHandlerDelegate
	extends MCEEventHandlerDelegate<ExecCommandHandler>
{
	public ExecCommandHandlerDelegate(ExecCommandHandler handler)
	{
		super(handler);
	}

	@Override
	public void delegate(ExecCommandHandler handler, FunctionArguments args)
	{
		Editor ed = args.getArg(0);
		String cmd = args.getArg(1);
		boolean ui = args.getArg(2);
		handler.onExecCommand(ed, cmd, ui);
	}
}
