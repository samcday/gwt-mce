package com.site2go.gwtmce.client.theme;

public class AdvancedTheme
	extends Theme {
	protected AdvancedTheme() {};

	public final native AdvancedThemeSettings getSettings() /*-{
		return this.settings;
	}-*/;
}
