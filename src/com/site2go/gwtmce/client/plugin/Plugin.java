package com.site2go.gwtmce.client.plugin;

import com.google.gwt.core.client.JavaScriptObject;
import com.site2go.gwtmce.client.addons.AddonInfo;
import com.site2go.gwtmce.client.editor.Editor;

public class Plugin
	extends JavaScriptObject
	implements PluginInterface
{
	protected Plugin() { }

	@Override
	public final native AddonInfo getInfo() /*-{
		return this.getInfo();
	}-*/;

	@Override
	public final native void init(Editor editor, String url)
	/*-{
		return this.init(editor, url);
	}-*/;

	@Override
	public final native boolean execCommand(String cmd, boolean ui, Object value)
	/*-{
		if(!this.execCommand) return false;
		return this.execCommand(cmd, ui, value);
	}-*/;

	/*@Override
	public final native Object createControl(String controlName, Object controlManager) /*-{
		return this.createControl(controlName, controlManager);
	}-*/;
}
