package com.site2go.gwtmce.client.ui;

/**
 * This class is the base class for all container controls like toolbars. This
 * class should not be instantiated directly other container controls should
 * inherit from this one.
 * 
 * @author Sam
 * 
 */
public class Container
	extends Control
{
	protected Container() { }

	/**
	 * Adds a control to the collection of controls for the container.
	 * @param control Control instance to add to the container.
	 */
	public final native void add(Control control) /*-{
		this.add(control);
	}-*/;
}
