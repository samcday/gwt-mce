package com.site2go.gwtmce.client.editor.events;

import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;

public class BeforeExecCommandHandlerDelegate
	extends MCEEventHandlerDelegate<BeforeExecCommandHandler>
{
	public BeforeExecCommandHandlerDelegate(BeforeExecCommandHandler handler)
	{
		super(handler);
	}
	
	@Override
	public void delegate(BeforeExecCommandHandler handler,
			FunctionArguments args)
	{
		Editor sender = args.getArg(0);
		String cmd = args.getArg(1);
		boolean ui = args.getArg(2);
		BeforeExecCommandHandler.ExecCommandContextObject context = args.getArg(4);

		handler.onBeforeExecCommand(sender, cmd, ui, context);
	}
}
