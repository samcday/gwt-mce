package com.site2go.gwtmce.client.plugin;

import com.site2go.gwtmce.client.addons.AddonInfo;
import com.site2go.gwtmce.client.editor.Editor;

public interface PluginInterface
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
	 * 
	 * @param controlName Control name to create for example "mylistbox"
	 * @param controlManager Control manager/factory to use to create the control.
	 * @return Returns a new control instance or null.
	 */
	//public Object createControl(String controlName, Object controlManager);

	public boolean execCommand(String cmd, boolean ui, Object value);
}
