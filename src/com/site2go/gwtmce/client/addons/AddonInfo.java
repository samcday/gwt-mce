package com.site2go.gwtmce.client.addons;

import com.google.gwt.core.client.JavaScriptObject;

public class AddonInfo
	extends JavaScriptObject
{
	public static final native AddonInfo create(String longName, String author, String authorURL, String infoURL, String version) /*-{
		var addonInfo = {
			longname: longName,
			author: author,
			authorurl: authorURL,
			infourl: infoURL,
			version: version
		};
		
		return addonInfo;
	}-*/;
	
	protected AddonInfo() { }
	
	public final native String getLongName() /*-{
		return this.longname;
	}-*/;
	
	public final native String getAuthor() /*-{
		return this.author;
	}-*/;
	
	public final native String getAuthorURL() /*-{
		return this.authorurl;
	}-*/;
	
	public final native String getInfoURL() /*-{
		return this.infourl;
	}-*/;
	
	public final native String getVersion() /*-{
		return this.version;
	}-*/;
}
