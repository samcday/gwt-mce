package com.site2go.gwtmce.client.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.site2go.gwt.util.client.FunctionProxy;
import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwt.util.client.FunctionProxy.FunctionHandler;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.editor.events.HandlerRegistration;
import com.site2go.gwtmce.client.util.TinyMCE;

/**
 * This system is somewhat convoluted, but necessary for the cause! In a nutshell, this base class defines a standard way of adding an "event delegate" to any of the TinyMCE
 * {@link Dispatcher}s. This allows us to unobtrusively register event handlers from client code in the same fashion as we normally would with a widget etc.<br/><br/>
 * 
 * The drawback of this approach (in my mind) is the fact that due to the way overlay types work, we can't really store one of these somewhere and just add multiple handlers to it.
 * Instead, everytime a new handler is registered from client code, we have to create one of these to "wrap" the handler to ensure its fired correctly when the event occurs in
 * Javascript-land. I guess this may not be a big issue, unless we had huge numbers of handlers being registered (which doesn't usually happen really).<br/><br/>
 * 
 * I think this approach may need to be tested for memory leaks too. >_>
 * 
 * @author Sam
 *
 * @param <T>
 */
public abstract class MCEEventHandlerDelegate<T extends MCEEventHandler>
	implements FunctionHandler
{
	private T handler;

	/**
	 * Creates a function proxy that proxies to an event delegate. The important thing to note here is the fact that the created functionproxy is added to a static map collection.
	 * This is so that we can reverse lookup a handler to find its equivalent JS functionproxy when we need to remove an event handler.
	 * @param delegate
	 */
	private static final Map<Dispatcher, Map<MCEEventHandler, FunctionProxy>> delegateFunctionProxiesByDispatcher = new HashMap<Dispatcher, Map<MCEEventHandler, FunctionProxy>>();
	private static final Map<Editor, List<Dispatcher>> editorFunctionProxies = new HashMap<Editor, List<Dispatcher>>();
	public static final HandlerRegistration registerEditorEventDelegateFunctionProxy(Editor ed, final Dispatcher d, final MCEEventHandlerDelegate<?> delegate)
	{
		// Create the function proxy for the delegate.
		FunctionProxy fp = FunctionProxy.create(delegate);

		// Add this function proxy to our internal hash collection. This hash allows us to quickly clean up any references we should no longer be holding on to when
		// a handler is de-registered, or an editor becomes completely defunct.
		if(!delegateFunctionProxiesByDispatcher.containsKey(d))
			delegateFunctionProxiesByDispatcher.put(d, new HashMap<MCEEventHandler, FunctionProxy>());
		delegateFunctionProxiesByDispatcher.get(d).put(delegate.handler, fp);

		// Add our low level function proxy to Dispatcher.
		d.add(fp);

		// Add dispatcher to list hashed by editor. We need this for editor cleanup.
		if(!editorFunctionProxies.containsKey(ed))
			editorFunctionProxies.put(ed, new ArrayList<Dispatcher>());

		if(!editorFunctionProxies.get(ed).contains(d))
			editorFunctionProxies.get(ed).add(d);

		return new HandlerRegistration()
		{
			@Override
			public void removeHandler()
			{
				MCEEventHandlerDelegate.removeEventHandler(delegate.handler, d);
			}
		};
	}

	/**
	 * This is similar to {@link #registerEditorEventDelegateFunctionProxy(Dispatcher, MCEEventHandlerDelegate)}, except it doesn't concern itself with maintaining a hash of the
	 * {@link FunctionProxy}, as it is only used on dispatchers that last the lifetime of the page, like {@link TinyMCE}.
	 * @param d
	 * @param delegate
	 * @return
	 */
	public static final HandlerRegistration registerEventDelegateFunctionProxy(final Dispatcher d, final MCEEventHandlerDelegate<?> delegate)
	{
		FunctionProxy fp = FunctionProxy.create(delegate);
		d.add(fp);

		// Add this function proxy to our internal hash collection. This hash allows us to quickly clean up any references we should no longer be holding on to when
		// a handler is de-registered, or an editor becomes completely defunct.
		if(!delegateFunctionProxiesByDispatcher.containsKey(d))
			delegateFunctionProxiesByDispatcher.put(d, new HashMap<MCEEventHandler, FunctionProxy>());
		delegateFunctionProxiesByDispatcher.get(d).put(delegate.handler, fp);
		
		return new HandlerRegistration()
		{
			@Override
			public void removeHandler()
			{
				MCEEventHandlerDelegate.removeEventHandler(delegate.handler, d);
			}
		};
	}

	public static final void removeEventHandler(MCEEventHandler handler, Dispatcher d)
	{
		FunctionProxy fp = delegateFunctionProxiesByDispatcher.get(d).get(handler);
		assert(fp != null);
		d.remove(fp);
	}

	public static final void removeEditorFunctionProxies(Editor ed)
	{
		// Ensures we don't hold references to dispatchers that are now defunct.
		List<Dispatcher> dispatchers = editorFunctionProxies.remove(ed);

		// If there were dispatchers we cared about on the editor, we clear them from our hash too.
		if(dispatchers != null)
			for(Dispatcher dispatcher : dispatchers)
				delegateFunctionProxiesByDispatcher.remove(dispatcher);
	}

	public MCEEventHandlerDelegate(T handler)
	{
		this.handler = handler;
	}

	@Override
	public Object onFunctionCall(FunctionProxy func, FunctionArguments args)
	{
		this.delegate(this.handler, args);
		return null;
	}

	//private abstract void 
	public abstract void delegate(T handler, FunctionArguments args);
}
