package com.site2go.gwtmce.client.theme;

import com.google.gwt.dom.client.Element;
import com.site2go.gwtmce.client.addons.AddonInfo;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.theme.Theme.ThemeRenderOptions;

public interface ThemeInterface
{
	/**
	 * Initializes the addon.
	 * 
	 * @param editor
	 *            Editor instance that created the theme instance.
	 * @param url
	 *            Absolute URL where the theme is located/was loaded from.
	 */
	public void init(Editor editor, String url);

	/**
	 * Meta info method, this method gets executed when TinyMCE wants to present
	 * information about the addon for example in the about/help dialog.
	 * 
	 * @return Returns an object with meta information about the addon the
	 *         current items are longname, author, authorurl, infourl and
	 *         version.
	 */
	public AddonInfo getInfo();
	
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
