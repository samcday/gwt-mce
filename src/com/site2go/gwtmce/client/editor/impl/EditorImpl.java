package com.site2go.gwtmce.client.editor.impl;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.IFrameElement;
import com.site2go.gwt.util.client.FunctionProxy;
import com.site2go.gwtmce.client.util.PropertiesObject;
import com.site2go.gwtmce.client.dom.Selection;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.editor.UndoManager;
import com.site2go.gwtmce.client.event.Dispatcher;
import com.site2go.gwtmce.client.plugin.Plugin;
import com.site2go.gwtmce.client.theme.Theme;
import com.site2go.gwtmce.client.ui.ControlManager;
import com.site2go.gwtmce.client.util.WindowManager;

/**
 * Native overlay type for the tinymce.Editor JS class. You should probably not
 * be interacting with this class directly, it's primarily for internal use by
 * {@link Editor}.
 * 
 * This overlay matches the underlying Editor class interface very closely, for
 * documentation see http://tinymce.moxiecode.com/wiki.php/API3:class.tinymce.Editor
 * 
 */
public class EditorImpl
	extends JavaScriptObject
{
	public static final native EditorImpl constructor(Editor editor, String id,
			PropertiesObject settings) /*-{
		var ed = new $wnd.tinymce.Editor(id, settings);
		ed.__gwtmceEditor = editor;
		
		return ed;
	}-*/;

	protected EditorImpl()
	{
	}
	
	/**
	 * Editor implementations are wrapped inside the actual {@link Editor} (if
	 * they've been created via gwt-mce anyway). This somewhat ugly shim returns
	 * the static reference we have to said Editor instance. This hack is ok for
	 * our purposes, since an unmanaged tinymce.Editor would never actually trigger
	 * events that gwt-mce listens for.
	 * @return
	 */
	public static native final Editor getEditor(EditorImpl editorImpl) /*-{
		return editorImpl.__gwtmceEditor;
	}-*/;

	public native final void destroy()
	/*-{
		this.destroy();
	}-*/;

	public native final boolean execCommand(String commandName, boolean ui,
			Object val, Object o) /*-{
		return this.execCommand(commandName, ui, val, o) ? true : false;
	}-*/;

	public native final void focus(boolean sf) /*-{
		this.focus(sf);
	}-*/;

	public native final Element getBody()/*-{
		return this.getBody();
	}-*/;

	public native final Element getContainer() /*-{
		return this.getContainer();
	}-*/;

	public native final String getContent(PropertiesObject options) /*-{
		return this.getContent(options);
	}-*/;

	public native final Element getContentAreaContainer() /*-{
		return this.getContentAreaContainer();
	}-*/;

	public native final ControlManager getControlManager() /*-{
		return this.controlManager;
	}-*/;

	public native final Document getDoc() /*-{
		return this.getDoc();
	}-*/;

	public native final Element getElement() /*-{
		return this.getElement();
	}-*/;

	public native final String getId() /*-{
		return this.id;
	}-*/;

	public final IFrameElement getIframe()
	{
		return Document.get().getElementById(this.getId() + "_ifr").cast();
	}

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

	public native final PropertiesObject getSettings() /*-{
		return this.settings;
	}-*/;

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
	
	public native final void hide() /*-{
		this.hide();
	}-*/;

	public native final boolean isDirty() /*-{
		return this.isDirty();
	}-*/;

	public native final boolean isHidden() /*-{
		return this.isHidden();
	}-*/;

	public native final void remove() /*-{
		this.remove();
	}-*/;

	public native final void render() /*-{
		this.render();
	}-*/;

	public native final String save(Object o) /*-{
		return this.save(o);
	}-*/;

	public final String save()
	{
		return this.save(null);
	}

	public native final String setContent(String h, PropertiesObject options) /*-{
		return this.setContent(h, options);
	}-*/;

	public final String setContent(String h)
	{
		return this.setContent(h, null);
	}

	public native final boolean setProgressState(boolean show, float timeout,
			Object obj) /*-{
		return this.setProgressState(show, timeout, obj);
	}-*/;
	
	public final boolean setProgressState(boolean show, float timeout)
	{
		return this.setProgressState(show, timeout, null);
	}

	public native final void show() /*-{
		this.show();
	}-*/;

	public native final String translate(String s) /*-{
		return this.translate(s);
	}-*/;

	public native final void addCommand(String commandName, FunctionProxy fn)
	/*-{
		this.addCommand(commandName, fn);
	}-*/;

	/**
	 * Retrieves one of the dispatchers. Tidier way to expose the individual event Dispatchers,
	 * rather than exposing them all one by one.
	 * @param eventName
	 * @return
	 */
	public final Dispatcher getDispatcher(String eventName)
	{
		String property = "on" + eventName.substring(0, 1).toUpperCase() + eventName.substring(1);
		return this.getDispatcherImpl(property);
	}

	private native final Dispatcher getDispatcherImpl(String p)
	/*-{
		return this[p];
	}-*/;
}
