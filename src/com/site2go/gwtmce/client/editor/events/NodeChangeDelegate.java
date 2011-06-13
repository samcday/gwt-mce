package com.site2go.gwtmce.client.editor.events;

import com.google.gwt.dom.client.Element;
import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;
import com.site2go.gwtmce.client.ui.ControlManager;
import com.site2go.gwtmce.client.util.PropertiesObject;

public class NodeChangeDelegate 
	extends MCEEventHandlerDelegate<NodeChangeHandler> {
	public NodeChangeDelegate(NodeChangeHandler handler)
	{
		super(handler);
	}

	@Override
	public void delegate(NodeChangeHandler handler, FunctionArguments args)
	{
		Editor ed = args.getArg(0);
		ControlManager cm = args.getArg(1);
		Element e = args.getArg(2);
		boolean isCollapsed = args.getArg(3);
		PropertiesObject o = args.getArg(4);
		
		handler.onNodeChange(ed, cm, e, isCollapsed, o);
	}
}
