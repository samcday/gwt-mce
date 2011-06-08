package com.site2go.gwtmce.client.editor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.IFrameElement;
import com.site2go.gwt.util.client.FunctionProxy;
import com.site2go.gwtmce.client.util.PropertiesObject;
import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwt.util.client.FunctionProxy.FunctionHandler;
import com.site2go.gwtmce.client.dom.Selection;
import com.site2go.gwtmce.client.editor.events.ActivateHandler;
import com.site2go.gwtmce.client.editor.events.ActivateHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.BeforeExecCommandHandler;
import com.site2go.gwtmce.client.editor.events.BeforeExecCommandHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.BeforeGetContentHandler;
import com.site2go.gwtmce.client.editor.events.BeforeGetContentHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.BeforeRenderUIHandler;
import com.site2go.gwtmce.client.editor.events.BeforeRenderUIHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.BeforeSetContentHandler;
import com.site2go.gwtmce.client.editor.events.BeforeSetContentHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.ChangeHandler;
import com.site2go.gwtmce.client.editor.events.ChangeHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.ClickHandler;
import com.site2go.gwtmce.client.editor.events.ClickHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.ContextMenuHandler;
import com.site2go.gwtmce.client.editor.events.ContextMenuHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.DblClickHandler;
import com.site2go.gwtmce.client.editor.events.DblClickHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.DeactivateHandler;
import com.site2go.gwtmce.client.editor.events.DeactivateHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.EventHandler;
import com.site2go.gwtmce.client.editor.events.EventHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.ExecCommandHandler;
import com.site2go.gwtmce.client.editor.events.ExecCommandHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.GetContentHandler;
import com.site2go.gwtmce.client.editor.events.GetContentHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.HandlerRegistration;
import com.site2go.gwtmce.client.editor.events.InitHandler;
import com.site2go.gwtmce.client.editor.events.InitHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.KeyDownHandler;
import com.site2go.gwtmce.client.editor.events.KeyDownHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.KeyPressHandler;
import com.site2go.gwtmce.client.editor.events.KeyPressHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.KeyUpHandler;
import com.site2go.gwtmce.client.editor.events.KeyUpHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.MouseDownHandler;
import com.site2go.gwtmce.client.editor.events.MouseDownHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.MouseUpHandler;
import com.site2go.gwtmce.client.editor.events.MouseUpHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.PostRenderHandler;
import com.site2go.gwtmce.client.editor.events.PostRenderHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.PreInitHandler;
import com.site2go.gwtmce.client.editor.events.PreInitHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.RedoHandler;
import com.site2go.gwtmce.client.editor.events.RedoHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.RemoveHandler;
import com.site2go.gwtmce.client.editor.events.RemoveHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.SetContentHandler;
import com.site2go.gwtmce.client.editor.events.SetContentHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.SetProgressStateHandler;
import com.site2go.gwtmce.client.editor.events.SetProgressStateHandlerDelegate;
import com.site2go.gwtmce.client.editor.events.UndoHandler;
import com.site2go.gwtmce.client.editor.events.UndoHandlerDelegate;
import com.site2go.gwtmce.client.event.Dispatcher;
import com.site2go.gwtmce.client.event.MCEEventHandlerDelegate;
import com.site2go.gwtmce.client.plugin.Plugin;
import com.site2go.gwtmce.client.theme.Theme;
import com.site2go.gwtmce.client.ui.ControlManager;
import com.site2go.gwtmce.client.util.WindowManager;

/**
 * This class contains the core logic for a TinyMCE editor.
 * 
 * @author Sam
 * 
 */
public class Editor
	extends JavaScriptObject
{
	public static final Editor create(String id, EditorInitOptions settings)
	{
		JavaScriptObject editor = constructor(id, settings.getProperties());
		return editor.cast();
	}

	private static final native JavaScriptObject constructor(String id,
			PropertiesObject settings) /*-{
		return new $wnd.tinymce.Editor(id, settings);
	}-*/;

	protected Editor()
	{
	}

	/**
	 * Destroys the editor instance by removing all events, element references
	 * or other resources that could leak memory. This method will be called
	 * automatically when the page is unloaded but you can also call it directly
	 * if you know what you are doing.
	 */
	public final void destroy() 
	{
		MCEEventHandlerDelegate.removeEditorFunctionProxies(this);
		this.destroyImpl();
	}

	private native final void destroyImpl()
	/*-{
		this.destroy();
	}-*/;
	
	/**
	 * Executes a command on the current instance. These commands can be TinyMCE
	 * internal commands prefixed with "mce" or they can be build in browser
	 * commands such as "Bold". A compleate list of browser commands is
	 * available on MSDN or Mozilla.org. This function will dispatch the
	 * execCommand function on each plugin, theme or the execcommand_callback
	 * option if none of these return true it will handle the command as a
	 * internal browser command.
	 * 
	 * @param commandName Command name to execute, for example mceLink or Bold.
	 * @param ui True/false state if a UI (dialog) should be presented or not.
	 * @param val Optional command value, this can be anything.
	 * @param o Optional arguments object.
	 * @return True/false if the command was executed or not.
	 */
	public native final boolean execCommand(String commandName, boolean ui,
			Object val, Object o) /*-{
		return this.execCommand(commandName, ui, val, o) ? true : false;
	}-*/;

	public final boolean execCommand(String commandName, boolean ui, Object val)
	{
		return this.execCommand(commandName, ui, val, null);
	}

	public final boolean execCommand(String commandName, boolean ui)
	{
		return this.execCommand(commandName, ui, null);
	}

	/**
	 * Fires an event using the relevant dispatcher.  
	 */
	public final void dispatch(String eventName, Object... args)
	{
		Dispatcher d = this.getDispatcher(eventName);
		if(d == null) return;

		d.dispatch(args);
	}

	/**
	 * Focuses/activates the editor. This will set this editor as the activeEditor in the tinymce collection it will also place DOM focus inside the editor.
	 */
	public native final void focus(boolean sf) /*-{
		this.focus(sf);
	}-*/;

	public final void focus()
	{
		this.focus(false);
	}

	/**
	 * Returns the iframes body element.
	 */
	public native final Element getBody()/*-{
		return this.getBody();
	}-*/;

	/**
	 * Returns the editors container element. The container element wrappes in all the elements added to the page for the editor. Such as UI, iframe etc.
	 * @return
	 */
	public native final Element getContainer() /*-{
		return this.getContainer();
	}-*/;

	/**
	 * Gets the content from the editor instance, this will cleanup the content
	 * before it gets returned using the different cleanup rules options.
	 * 
	 * @param obj
	 *            Optional content object, this gets passed around through the
	 *            whole get process.
	 * @return Cleaned content string, normally HTML contents.
	 */
	public native final String getContent(ContentObject options) /*-{
		return this.getContent(options);
	}-*/;

	public final String getContent()
	{
		return this.getContent(null);
	}

	/**
	 * Returns the editors content area container element. The this element is the one who holds the iframe or the editable element.
	 * @return
	 */
	public native final Element getContentAreaContainer() /*-{
		return this.getContentAreaContainer();
	}-*/;

	/**
	 * Returns {@link ControlManager} instance for the editor.
	 * @return
	 */
	public native final ControlManager getControlManager() /*-{
		return this.controlManager;
	}-*/;

	/**
	 * Returns the iframes document object.
	 * @return
	 */
	public native final Document getDoc() /*-{
		return this.getDoc();
	}-*/;

	/**
	 * Returns the target element/textarea that got replaced with a TinyMCE
	 * editor instance.
	 */
	public native final Element getElement() /*-{
		return this.getElement();
	}-*/;

	/**
	 * Returns {@link Editor} instance id, normally the same as the div/textarea
	 * that was replaced.
	 */
	public native final String getId() /*-{
		return this.id;
	}-*/;

	/**
	 * Gets iframe element for this editor.
	 * @return
	 */
	public final IFrameElement getIframe()
	{
		return Document.get().getElementById(this.getId() + "_ifr").cast();
	}
	
	/**
	 * Returns a Name/Value object containting plugin instances.
	 */
	public native final Plugin getPlugin(String name) /*-{
		return this.plugins[name];
	}-*/;
	
	public native final Selection getSelection() /*-{
		alert(this);
		alert(this.selection);
		alert(this.selection.getEnd);
		//return null;
		return this.selection;
	}-*/;

	/**
	 * Returns name/value collection with editor settings.
	 * 
	 * @return
	 */
	public native final EditorInitOptions getSettings() /*-{
		return this.settings;
	}-*/;

	/**
	 * Reference to the {@link Theme} instance that was used to generate the UI.
	 */
	public native final Theme getTheme() /*-{
		return this.theme;
	}-*/;
	
	public native final UndoManager getUndoManager() /*-{
		return this.undoManager;
	}-*/;

	public native final WindowManager getWindowManager()
	/*-{
		return this.windowManager;
	}-*/;
	
	/**
	 * Hides the editor and shows any textarea/div that the editor is supposed
	 * to replace.
	 */
	public native final void hide() /*-{
		this.hide();
	}-*/;

	/**
	 * Returns true/false if the editor is dirty or not. It will get dirty if
	 * the user has made modifications to the contents.
	 */
	public native final boolean isDirty() /*-{
		return this.isDirty();
	}-*/;

	/**
	 * Returns true/false if the editor is hidden or not.
	 */
	public native final boolean isHidden() /*-{
		return this.isHidden();
	}-*/;

	/**
	 * Removes the editor from the dom and EditorManager collection.
	 */
	public native final void remove() /*-{
		this.remove();
	}-*/;

	/**
	 * Renders the editor/adds it to the page.
	 */
	public native final void render() /*-{
		this.render();
	}-*/;

	/**
	 * Saves the contents from a editor out to the textarea or div element that
	 * got converted into an editor instance. This method will move the HTML
	 * contents from the editor into that textarea or div by getContent so all
	 * events etc that method has will get dispatched as well.
	 * 
	 * @param o
	 *            Optional content object, this gets passed around through the
	 *            whole save process.
	 * @return HTML string that got set into the textarea/div.
	 */
	public native final String save(Object o) /*-{
		return this.save(o);
	}-*/;

	public final String save()
	{
		return this.save(null);
	}

	/**
	 * Sets the specified content to the editor instance, this will cleanup the content before it gets set using the different cleanup rules options.
	 * @param h Content to set to editor, normally HTML contents but can be other formats as well.
	 * @param o Optional content object, this gets passed around through the whole set process.
	 * @return HTML string that got set into the editor.
	 */
	public native final String setContent(String h, ContentObject options) /*-{
		return this.setContent(h, options);
	}-*/;

	public final String setContent(String h)
	{
		return this.setContent(h, null);
	}

	/**
	 * Sets the progress state, this will display a throbber/progess for the
	 * editor. This is ideal for asycronous operations like an AJAX save call.
	 * 
	 * @param show
	 *            Boolean state if the progress should be shown or hidden.
	 * @param timeout
	 *            Optional time to wait before the progress gets shown.
	 * @param obj
	 *            Optional object to pass to the progress observers.
	 * @return Same as the input state.
	 */
	public native final boolean setProgressState(boolean show, float timeout,
			Object obj) /*-{
		return this.setProgressState(show, timeout, obj);
	}-*/;
	
	public final boolean setProgressState(boolean show, float timeout)
	{
		return this.setProgressState(show, timeout, null);
	}

	/**
	 * Shows the editor and hides any textarea/div that the editor is supposed to replace.
	 */
	public native final void show() /*-{
		this.show();
	}-*/;

	/**
	 * Translates the specified string by replacing variables with language pack items it will also check if there is a key mathcin the input.
	 * @param s
	 */
	public native final void translate(String s) /*-{
		return this.translate(s);
	}-*/;

	/**
	 * Adds a custom command to the editor, you can also override existing
	 * commands with this method. The command that you add can be executed with
	 * execCommand.
	 * 
	 * @param commandName
	 *            Command name to add/override.
	 * @param callback
	 *            Callback to execute when the command occurs.
	 */
	public final void addCommand(final String commandName, final CustomEditorCommandCallback cb)
	{
		this.addCommandImpl(commandName, FunctionProxy.create(new FunctionHandler()
		{
			@Override
			public Object onFunctionCall(FunctionProxy func, FunctionArguments args)
			{
				boolean ui = args.getArg(0);
				Object val = args.getArg(1);

				return cb.onCommand(Editor.this, commandName, ui, val);
			}
		}));
	}

	private native final void addCommandImpl(String commandName, FunctionProxy fn)
	/*-{
		this.addCommand(commandName, fn);
	}-*/;

	/**
	 * Retrieves one of the dispatchers
	 * @param eventName
	 * @return
	 */
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
	 * Fires when the editor is activated.
	 * 
	 * @param handler
	 */
	public final HandlerRegistration addActivateHandler(ActivateHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("activate");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new ActivateHandlerDelegate(handler));
	}

	/**
	 * Fires before a command gets executed for example "Bold".
	 * @param handler
	 * @return
	 */
	public final HandlerRegistration addBeforeExecCommandHandler(BeforeExecCommandHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("beforeExecCommand");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new BeforeExecCommandHandlerDelegate(handler));
	}

	/**
	 * Fires before contents is extracted from the editor using for example getContent.
	 * @param handler
	 * @return
	 */
	public final HandlerRegistration addBeforeGetContentHandler(BeforeGetContentHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("beforeGetContent");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new BeforeGetContentHandlerDelegate(handler));
	}
	
	/**
	 * Fires before the initialization of the editor.
	 * @param handler
	 * @return
	 */
	public final HandlerRegistration addBeforeRenderUIHandler(BeforeRenderUIHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("beforeRenderUI");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new BeforeRenderUIHandlerDelegate(handler));
	}
	
	/**
	 * Fires before new contents is added to the editor. Using for example setContent.
	 * @param handler
	 * @return
	 */
	public final HandlerRegistration addBeforeSetContentHandler(BeforeSetContentHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("beforeSetContent");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new BeforeSetContentHandlerDelegate(handler));
	}

	public final HandlerRegistration addChangeHandler(ChangeHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("change");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new ChangeHandlerDelegate(handler));
	}

	/**
	 * Adds a handler for event fired when something in the body of the editor
	 * is clicked.
	 * 
	 * @param handler
	 */
	public final HandlerRegistration addClickHandler(ClickHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("click");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new ClickHandlerDelegate(handler));
	}

	public final HandlerRegistration addContextMenuHandler(ContextMenuHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("contextMenu");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new ContextMenuHandlerDelegate(handler));
	}

	public final HandlerRegistration addDblClickHandler(DblClickHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("dblClick");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new DblClickHandlerDelegate(handler));
	}

	/**
	 * Fires when the editor is deactivated.
	 * @param handler
	 */
	public final HandlerRegistration addDeactivateHandler(DeactivateHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("deactivate");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new DeactivateHandlerDelegate(handler));
	}

	/**
	 * Adds a handler for all events fired.
	 * @param handler
	 */
	public final HandlerRegistration addEventHandler(EventHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("event");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new EventHandlerDelegate(handler));
	}

	public final HandlerRegistration addExecCommandHandler(ExecCommandHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("execCommand");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new ExecCommandHandlerDelegate(handler));
	}

	public final HandlerRegistration addGetContentHandler(GetContentHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("getContent");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new GetContentHandlerDelegate(handler));
	}

	/**
	 * Adds a handler for event fired after the initialization of the editor is
	 * done.
	 * 
	 * @param handler
	 */
	public final HandlerRegistration addInitHandler(InitHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("init");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new InitHandlerDelegate(handler));
	}
	
	public final HandlerRegistration addKeyDownHandler(KeyDownHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("keyDown");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new KeyDownHandlerDelegate(handler));
	}

	public final HandlerRegistration addKeyPressHandler(KeyPressHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("keyPress");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new KeyPressHandlerDelegate(handler));		
	}
	
	public final HandlerRegistration addKeyUpHandler(KeyUpHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("keyUp");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new KeyUpHandlerDelegate(handler));
	}

	public final HandlerRegistration addMouseDownHandler(MouseDownHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("mouseDown");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new MouseDownHandlerDelegate(handler));
	}

	public final HandlerRegistration addMouseUpHandler(MouseUpHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("mouseUp");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new MouseUpHandlerDelegate(handler));
	}

	public final HandlerRegistration addPostRenderHandler(PostRenderHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("postRender");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new PostRenderHandlerDelegate(handler));
	}

	/**
	 * Adds a handler for event fired before the initialization of the editor.
	 * @param id
	 * @param settings
	 * @return
	 */
	public final HandlerRegistration addPreInitHandler(PreInitHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("preInit");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new PreInitHandlerDelegate(handler));
	}

	public final HandlerRegistration addRedoHandler(RedoHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("redo");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new RedoHandlerDelegate(handler));
	}

	public final HandlerRegistration addRemoveHandler(RemoveHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("remove");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new RemoveHandlerDelegate(handler));
	}
	
	public final HandlerRegistration addSetContentHandler(SetContentHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("setContent");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new SetContentHandlerDelegate(handler));
	}

	public final HandlerRegistration addSetProgressStateHandler(SetProgressStateHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("setProgressState");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new SetProgressStateHandlerDelegate(handler));
	}

	public final HandlerRegistration addUndoHandler(UndoHandler handler)
	{
		Dispatcher dispatcher = this.getDispatcher("undo");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new UndoHandlerDelegate(handler));
	}
}
