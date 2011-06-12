package com.site2go.gwtmce.client.ui;

import com.site2go.gwtmce.client.util.PropertiesObject;

/**
 * Convience class to pass options to a Button {@link Control}.
 * @author Sam
 *
 */
public class ButtonOptions 
	extends PropertiesObject {
	public static final ButtonOptions create(String title, String cmd) {
		ButtonOptions opts = PropertiesObject.create().cast();
		opts.setProperty("title", title);
		opts.setProperty("cmd", cmd);
		
		return opts;
	}
	
	protected ButtonOptions() {}
}
