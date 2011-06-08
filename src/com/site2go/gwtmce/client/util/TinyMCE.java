package com.site2go.gwtmce.client.util;

import com.google.gwt.core.client.JavaScriptObject;
import com.site2go.gwtmce.client.util.PropertiesObject;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.editor.events.HandlerRegistration;
import com.site2go.gwtmce.client.event.Dispatcher;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;
import com.site2go.gwtmce.client.util.events.AddEditorHandler;
import com.site2go.gwtmce.client.util.events.AddEditorHandlerDelegate;
import com.site2go.gwtmce.client.util.events.RemoveEditorHandler;
import com.site2go.gwtmce.client.util.events.RemoveEditorHandlerDelegate;

/**
 * Core namespace with core functionality for the TinyMCE API all sub classes will be added to this namespace/object.
 * @author Sam
 */
public class TinyMCE
	extends JavaScriptObject
{ 
	public static final native TinyMCE get()
	/*-{
		return $wnd.tinymce;
	}-*/;

	protected TinyMCE() { }

	/**
	 * Initializes a set of editors. This method will create a bunch of editors based in the input.
	 * @param settings Settings object to be passed to each editor instance.
	 */
	public final native void init(PropertiesObject settings) /*-{
		this.init(settings);
	}-*/;

	/**
	 * Executes a specific command on the currently active {@link Editor}.
	 * 
	 * @param command
	 *            Command to perform for example Bold.
	 * @param ui
	 *            Optional boolean state if a UI should be presented for the
	 *            command or not.
	 * @param param
	 *            Optional value parameter like for example an URL to a link.
	 * @return true/false if the command was executed or not.
	 */
	public final native boolean execCommand(String command, boolean ui,
			String param) /*-{
		return this.execCommand(command, ui, param);
	}-*/;

	/**
	 * Returns a {@link Editor} instance by id.
	 * 
	 * @param id
	 *            Editor instance id to return.
	 * @return Editor instance to return.
	 */
	public final native Editor get(String id) /*-{
		return this.get(id);
	}-*/;

	/**
	 * Returns the currently active {@link Editor}, if applicable.
	 * 
	 * @return the currently active {@link Editor}
	 */
	public final native Editor getActiveEditor() /*-{
		return this.activeEditor;
	}-*/;

	/**
	 * Calls the save method on all editor instances in the collection.
	 */
	public final native void triggerSave() /*-{
		this.triggerSave();
	}-*/;

	/**
	 * Absolute baseURI for the installation path of TinyMCE.
	 * @return
	 */
	public native final String getBaseURL()
	/*-{
		return this.baseURL;
	}-*/;
	
	private final Dispatcher getDispatcher(String eventName)
	{
		String property = "on" + eventName.substring(0, 1).toUpperCase() + eventName.substring(1);
		return this.getDispatcherImpl(property);
	}

	private native final Dispatcher getDispatcherImpl(String p)
	/*-{
		return this[p];
	}-*/;

	/**
	 * Fires when a new editor instance is added to the tinymce collection.
	 * @param handler
	 * @return
	 */
	public final HandlerRegistration addAddEditorHandler(AddEditorHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("addEditor");
		return MCEEventHandlerDelegate.registerEventDelegateFunctionProxy(dispatcher, new AddEditorHandlerDelegate(handler));
	}

	/**
	 * Fires when an editor instance is removed from the tinymce collection.
	 * @param handler
	 * @return
	 */
	public final HandlerRegistration addRemoveEditorHandler(RemoveEditorHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("removeEditor");
		return MCEEventHandlerDelegate.registerEventDelegateFunctionProxy(dispatcher, new RemoveEditorHandlerDelegate(handler));
	}
}
