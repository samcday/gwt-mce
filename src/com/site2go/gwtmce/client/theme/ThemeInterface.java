package com.site2go.gwtmce.client.theme;

import com.google.gwt.dom.client.Element;
import com.site2go.gwtmce.client.addons.AddonInterface;
import com.site2go.gwtmce.client.theme.Theme.ThemeRenderOptions;

public interface ThemeInterface
	extends AddonInterface
{
	/**
	 * This method is responsible for rendering/generating the overall user
	 * interface with toolbars, buttons, iframe containers etc.
	 * 
	 * @param o
	 *            Object parameter containing the targetNode DOM node that will
	 *            be replaced visually with an editor instance.
	 * @return Returns an object with items like iframeContainer,
	 *         editorContainer, sizeContainer, deltaWidth, deltaHeight.
	 */
	public ThemeRenderOptions renderUI(Element targetNode, int width, int height, int deltaWidth, int deltaHeight);

	/**
	 * This method is invoked when the editor is shutting down, giving the Theme
	 * a chance to do any custom cleanup.
	 */
	public void destroy();

	public boolean execCommand(String cmd, boolean ui, Object value);
}
