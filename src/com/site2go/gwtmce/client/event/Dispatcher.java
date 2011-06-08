package com.site2go.gwtmce.client.event;

import com.google.gwt.core.client.JavaScriptObject;
import com.site2go.gwt.util.client.FunctionProxy;
import com.site2go.gwtmce.client.util.PropertiesObject;

public class Dispatcher
	extends JavaScriptObject
{
	protected Dispatcher()
	{
	}

	public final void add(FunctionProxy observer)
	{
		this.add(observer, false);
	}

	public final void add(FunctionProxy observer, boolean addToTop)
	{
		if(addToTop)
			this.addToTopImpl(observer);
		else
			this.addImpl(observer);
	}
	
	private native final void addImpl(FunctionProxy observer)
	/*-{
		this.add(observer);
	}-*/;
	
	private native final void addToTopImpl(FunctionProxy observer)
	/*-{
		this.addToTop(observer);
	}-*/;
	
	public native final FunctionProxy remove(FunctionProxy observer)
	/*-{
		return this.remove(observer);
	}-*/;
	
	public final void dispatch(Object... args)
	{
		PropertiesObject argsNative = PropertiesObject.create();
		argsNative.setProperty("length", args.length);
		
		for(int i = 0; i < args.length; i++)
			argsNative.setProperty("" + i, args[i]);

		this.dispatchImpl(argsNative);
	}

	private native final void dispatchImpl(PropertiesObject args)
	/*-{
		var theArgs = [];
		for(var i = 0; i < args.length; i++)
			theArgs[i] = args[""+i];

		this.dispatch.apply(this, theArgs);
	}-*/;
}
