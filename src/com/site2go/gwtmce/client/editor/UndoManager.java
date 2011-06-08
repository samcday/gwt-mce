package com.site2go.gwtmce.client.editor;

import com.google.gwt.core.client.JavaScriptObject;

public class UndoManager
	extends JavaScriptObject
{
	protected UndoManager() { }

	public native final UndoLevel add() /*-{
		return this.add();
	}-*/;

	/**
	 * Removes all undo levels.
	 */
	public native final void clear() /*-{
		this.clear();
	}-*/;

	/**
	 * Returns true/false if the undo manager has any redo levels.
	 * @return
	 */
	public native final boolean hasRedo() /*-{
		return this.hasRedo() ? true : false;
	}-*/;

	/**
	 * Returns true/false if the undo manager has any undo levels.
	 * @return
	 */
	public native final boolean hasUndo() /*-{
		return this.hasUndo() ? true : false;
	}-*/;

	/**
	 * Redoes the last action.
	 */
	public native final UndoLevel redo() /*-{
		return this.redo();
	}-*/;

	/**
	 * Undoes the last action.
	 */
	public native final UndoLevel undo() /*-{
		return this.undo();
	}-*/;
}
