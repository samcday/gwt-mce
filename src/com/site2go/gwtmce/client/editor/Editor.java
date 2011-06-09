package com.site2go.gwtmce.client.editor;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.IFrameElement;
import com.site2go.gwt.util.client.FunctionProxy;
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
import com.site2go.gwtmce.client.editor.impl.EditorImpl;
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
{
	private EditorImpl impl;
	private EditorInitOptions settings;

	/**
	 * Constructs a editor instance by id.
	 * @param id Unique id for the editor.
	 * @param s Optional settings string for the editor.
	 */
	public Editor(String id, EditorInitOptions s) {
		this.settings = s;
		this.impl = EditorImpl.constructor(id, s.getProperties());
	}

	/**
	 * Destroys the editor instance by removing all events, element references
	 * or other resources that could leak memory. This method will be called
	 * automatically when the page is unloaded but you can also call it directly
	 * if you know what you are doing.
	 */
	public void destroy() 
	{
		MCEEventHandlerDelegate.removeEditorFunctionProxies(this);
		this.impl.destroy();
	}

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
	public boolean execCommand(String commandName, boolean ui,
			Object val, Object o) {
		return this.impl.execCommand(commandName, ui, val, o);
	};

	public boolean execCommand(String commandName, boolean ui, Object val)
	{
		return this.execCommand(commandName, ui, val, null);
	}

	public boolean execCommand(String commandName, boolean ui)
	{
		return this.execCommand(commandName, ui, null);
	}

	/**
	 * Fires an event using the relevant dispatcher.  
	 */
	public void dispatch(String eventName, Object... args)
	{
		Dispatcher d = this.impl.getDispatcher(eventName);
		if(d == null) return;

		d.dispatch(args);
	}

	/**
	 * Focuses/activates the editor. This will set this editor as the activeEditor in the tinymce collection it will also place DOM focus inside the editor.
	 */
	public void focus(boolean sf) {
		this.impl.focus(sf);
	};

	public final void focus()
	{
		this.focus(false);
	}

	/**
	 * Returns the iframes body element.
	 */
	public Element getBody() {
		return this.impl.getBody();
	};

	/**
	 * Returns the editors container element. The container element wrappes in all the elements added to the page for the editor. Such as UI, iframe etc.
	 * @return
	 */
	public Element getContainer() {
		return this.impl.getContainer();
	};

	/**
	 * Gets the content from the editor instance, this will cleanup the content
	 * before it gets returned using the different cleanup rules options.
	 * 
	 * @param obj
	 *            Optional content object, this gets passed around through the
	 *            whole get process.
	 * @return Cleaned content string, normally HTML contents.
	 */
	public String getContent(ContentObject options) {
		return this.impl.getContent(options);
	};

	public String getContent()
	{
		return this.getContent(null);
	}

	/**
	 * Returns the editors content area container element. The this element is the one who holds the iframe or the editable element.
	 * @return
	 */
	public Element getContentAreaContainer() {
		return this.impl.getContentAreaContainer();
	}

	/**
	 * Returns {@link ControlManager} instance for the editor.
	 * @return
	 */
	public ControlManager getControlManager() {
		return this.impl.getControlManager();
	}

	/**
	 * Returns the iframes document object.
	 * @return
	 */
	public Document getDoc() {
		return this.impl.getDoc();
	}

	/**
	 * Returns the target element/textarea that got replaced with a TinyMCE
	 * editor instance.
	 */
	public Element getElement() {
		return this.impl.getElement();
	}

	/**
	 * Returns {@link Editor} instance id, normally the same as the div/textarea
	 * that was replaced.
	 */
	public String getId() {
		return this.impl.getId();
	}

	/**
	 * Gets iframe element for this editor.
	 * @return
	 */
	public IFrameElement getIframe()
	{
		return Document.get().getElementById(this.getId() + "_ifr").cast();
	}

	/**
	 * Returns a Name/Value object containing plugin instances.
	 */
	public Plugin getPlugin(String name) {
		return this.impl.getPlugin(name);
	}

	public Selection getSelection() {
		return this.impl.getSelection();
	}

	/**
	 * Returns name/value collection with editor settings.
	 * 
	 * @return
	 */
	public EditorInitOptions getSettings() {
		return this.settings;
	}

	/**
	 * Reference to the {@link Theme} instance that was used to generate the UI.
	 */
	public Theme getTheme() {
		return this.impl.getTheme();
	}
	
	public UndoManager getUndoManager() {
		return this.impl.getUndoManager();
	}

	public WindowManager getWindowManager() {
		return this.impl.getWindowManager();
	}
	
	/**
	 * Hides the editor and shows any textarea/div that the editor is supposed
	 * to replace.
	 */
	public void hide() {
		this.impl.hide();
	}

	/**
	 * Returns true/false if the editor is dirty or not. It will get dirty if
	 * the user has made modifications to the contents.
	 */
	public boolean isDirty() {
		return this.impl.isDirty();
	}

	/**
	 * Returns true/false if the editor is hidden or not.
	 */
	public boolean isHidden() {
		return this.impl.isHidden();
	}

	/**
	 * Removes the editor from the dom and EditorManager collection.
	 */
	public void remove() {
		this.impl.remove();
	}

	/**
	 * Renders the editor/adds it to the page.
	 */
	public void render() {
		this.impl.render();
	}

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
	public String save(Object o) {
		return this.impl.save(o);
	}

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
	public String setContent(String h, ContentObject options) {
		return this.impl.setContent(h, options);
	}

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
	public boolean setProgressState(boolean show, float timeout,
			Object obj) {
		return this.impl.setProgressState(show, timeout, obj);
	}
	
	public final boolean setProgressState(boolean show, float timeout)
	{
		return this.setProgressState(show, timeout, null);
	}

	/**
	 * Shows the editor and hides any textarea/div that the editor is supposed to replace.
	 */
	public void show() {
		this.impl.show();
	}

	/**
	 * Translates the specified string by replacing variables with language pack items it will also check if there is a key mathcin the input.
	 * @param s
	 */
	public String translate(String s) {
		return this.impl.translate(s);
	}

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
	public void addCommand(final String commandName, final CustomEditorCommandCallback cb)
	{
		this.impl.addCommand(commandName, FunctionProxy.create(new FunctionHandler()
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
	
	/**
	 * Fires when the editor is activated.
	 * 
	 * @param handler
	 */
	public final HandlerRegistration addActivateHandler(ActivateHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("activate");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new ActivateHandlerDelegate(handler));
	}

	/**
	 * Fires before a command gets executed for example "Bold".
	 * @param handler
	 * @return
	 */
	public final HandlerRegistration addBeforeExecCommandHandler(BeforeExecCommandHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("beforeExecCommand");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new BeforeExecCommandHandlerDelegate(handler));
	}

	/**
	 * Fires before contents is extracted from the editor using for example getContent.
	 * @param handler
	 * @return
	 */
	public final HandlerRegistration addBeforeGetContentHandler(BeforeGetContentHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("beforeGetContent");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new BeforeGetContentHandlerDelegate(handler));
	}
	
	/**
	 * Fires before the initialization of the editor.
	 * @param handler
	 * @return
	 */
	public final HandlerRegistration addBeforeRenderUIHandler(BeforeRenderUIHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("beforeRenderUI");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new BeforeRenderUIHandlerDelegate(handler));
	}
	
	/**
	 * Fires before new contents is added to the editor. Using for example setContent.
	 * @param handler
	 * @return
	 */
	public final HandlerRegistration addBeforeSetContentHandler(BeforeSetContentHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("beforeSetContent");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new BeforeSetContentHandlerDelegate(handler));
	}

	public final HandlerRegistration addChangeHandler(ChangeHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("change");
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
		Dispatcher dispatcher = this.impl.getDispatcher("click");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new ClickHandlerDelegate(handler));
	}

	public final HandlerRegistration addContextMenuHandler(ContextMenuHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("contextMenu");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new ContextMenuHandlerDelegate(handler));
	}

	public final HandlerRegistration addDblClickHandler(DblClickHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("dblClick");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new DblClickHandlerDelegate(handler));
	}

	/**
	 * Fires when the editor is deactivated.
	 * @param handler
	 */
	public final HandlerRegistration addDeactivateHandler(DeactivateHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("deactivate");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new DeactivateHandlerDelegate(handler));
	}

	/**
	 * Adds a handler for all events fired.
	 * @param handler
	 */
	public final HandlerRegistration addEventHandler(EventHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("event");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new EventHandlerDelegate(handler));
	}

	public final HandlerRegistration addExecCommandHandler(ExecCommandHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("execCommand");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new ExecCommandHandlerDelegate(handler));
	}

	public final HandlerRegistration addGetContentHandler(GetContentHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("getContent");
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
		Dispatcher dispatcher = this.impl.getDispatcher("init");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new InitHandlerDelegate(handler));
	}
	
	public final HandlerRegistration addKeyDownHandler(KeyDownHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("keyDown");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new KeyDownHandlerDelegate(handler));
	}

	public final HandlerRegistration addKeyPressHandler(KeyPressHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("keyPress");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new KeyPressHandlerDelegate(handler));		
	}
	
	public final HandlerRegistration addKeyUpHandler(KeyUpHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("keyUp");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new KeyUpHandlerDelegate(handler));
	}

	public final HandlerRegistration addMouseDownHandler(MouseDownHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("mouseDown");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new MouseDownHandlerDelegate(handler));
	}

	public final HandlerRegistration addMouseUpHandler(MouseUpHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("mouseUp");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new MouseUpHandlerDelegate(handler));
	}

	public final HandlerRegistration addPostRenderHandler(PostRenderHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("postRender");
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
		Dispatcher dispatcher = this.impl.getDispatcher("preInit");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new PreInitHandlerDelegate(handler));
	}

	public final HandlerRegistration addRedoHandler(RedoHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("redo");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new RedoHandlerDelegate(handler));
	}

	public final HandlerRegistration addRemoveHandler(RemoveHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("remove");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new RemoveHandlerDelegate(handler));
	}
	
	public final HandlerRegistration addSetContentHandler(SetContentHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("setContent");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new SetContentHandlerDelegate(handler));
	}

	public final HandlerRegistration addSetProgressStateHandler(SetProgressStateHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("setProgressState");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new SetProgressStateHandlerDelegate(handler));
	}

	public final HandlerRegistration addUndoHandler(UndoHandler handler)
	{
		Dispatcher dispatcher = this.impl.getDispatcher("undo");
		return MCEEventHandlerDelegate.registerEditorEventDelegateFunctionProxy(this, dispatcher, new UndoHandlerDelegate(handler));
	}
}
