package com.site2go.gwtmce.client.ui;

import com.google.gwt.core.client.JavaScriptObject;
import com.site2go.gwtmce.client.util.PropertiesObject;

/**
 * This class is responsible for managing UI control instances. It's both a factory and a collection for the controls.
 * @author Sam Day
 */
public class ControlManager
	extends JavaScriptObject
{
	protected ControlManager() { }

	/**
	 * Adds a control to the control collection inside the manager.
	 * @param control instance to add to collection.
	 */
	public final native void add(Control control) /*-{
		this.add(control);
	}-*/;
	
	/**
	 * Creates a button control instance by id.
	 * @param id Unique id for the new button instance. For example "bold".
	 * @param settings Optional settings object for the control.
	 * @return Control instance that got created and added.
	 */
	public final native Control createButton(String id, ButtonOptions settings) /*-{
		return this.createButton(id, settings);
	}-*/;

	/**
	 * Creates a separator control instance.
	 * @return Control instance that got created and added.
	 */
	public final native Control createSeparator() /*-{
		return this.createSeparator();
	}-*/;
	
	/**
	 * Creates a toolbar container control instance by id.
	 * @param id Unique id for the new toolbar container control instance. For example "toolbar1".
	 * @param settings Optional settings object for the control.
	 * @return Control instance that got created and added.
	 */
	public final native Toolbar createToolbar(String id, PropertiesObject settings) /*-{
		return this.createToolbar(id, settings);
	}-*/;
}
