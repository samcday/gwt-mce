package com.site2go.gwtmce.client.addons;

import com.site2go.gwtmce.client.editor.Editor;

public interface AddonInterface {
	/**
	 * Initializes the addon.
	 * 
	 * @param editor
	 *            Editor instance that created the theme instance.
	 * @param url
	 *            Absolute URL where the addon is located/was loaded from.
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
}
