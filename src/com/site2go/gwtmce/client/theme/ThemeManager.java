package com.site2go.gwtmce.client.theme;

import com.site2go.gwtmce.client.addons.AddonManager;

/**
 * This class handles the loading of themes.
 * 
 * @author Sam Day
 * 
 */
public class ThemeManager
	extends AddonManager<ThemeInterface>
{
	public static final native ThemeManager get() /*-{
		return $wnd.tinymce.ThemeManager;
	}-*/;
	protected ThemeManager() { }
}
