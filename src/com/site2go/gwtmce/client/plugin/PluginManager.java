package com.site2go.gwtmce.client.plugin;

import com.site2go.gwtmce.client.addons.AddonManager;

public class PluginManager
	extends AddonManager<PluginInterface>
{
	public static final native PluginManager get() /*-{
		return $wnd.tinymce.PluginManager;
	}-*/;

	protected PluginManager()
	{
	}
}
