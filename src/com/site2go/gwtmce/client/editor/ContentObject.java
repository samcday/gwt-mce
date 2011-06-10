package com.site2go.gwtmce.client.editor;

import com.site2go.gwtmce.client.util.PropertiesObject;

/**
 * This object gets passed around during get/set content operations on the editor.
 * It can be used to set some specific options during the process (see {@link #setNoEvents(boolean)}
 * and {@link #setFormat(com.site2go.gwt.tinymce.client.editor.ContentObject.ContentFormat)}.
 * It can also be used to retrieve and/or modify the content in question via {@link #setContent(String)}
 * @author Sam
 *
 */
public class ContentObject {
	protected PropertiesObject obj;

	public static final ContentObject wrap(PropertiesObject obj) {
		ContentObject co = obj.getProperty("__gwtmceContentObject");
		if(co == null) {
			co = new ContentObject(obj);
		}
		
		return co;
	}
	
	protected ContentObject(PropertiesObject obj) {
		this.obj = obj;
	}

	public ContentObject() {
		this.obj = PropertiesObject.create();
		this.obj.setProperty("__gwtmceContentObject", this);
	}

	public ContentObject(boolean noEvents, ContentFormat format)
	{
		this();

		this.setNoEvents(noEvents);
		this.setFormat(format);
	}

	public void setNoEvents(boolean noEvents)
	{
		this.obj.setProperty("no_events", noEvents);
	}

	public boolean getNoEvents()
	{
		return this.obj.getProperty("no_events", false);
	}

	public void setFormat(ContentObject.ContentFormat format)
	{
		this.obj.setProperty("format", format.getValue());
	}

	public ContentObject.ContentFormat getFormat()
	{
		String format = this.obj.getProperty("format", "html");
		if(format.equals("raw"))
			return ContentFormat.RAW;
		else
			return ContentFormat.HTML;
	}

	public String getContent()
	{
		return this.obj.getProperty("content", "");
	}

	public void setContent(String content)
	{
		this.obj.setProperty("content", content);
	}
	
	/**
	 * Returns the underlying {@link PropertiesObject} for this ContentObject.
	 * Use this only if you need to set a nonstandard option for a custom plugin.
	 * @return
	 */
	public PropertiesObject getProperties() {
		return this.obj;
	}

	/**
	 * Enumeration to specify how content should be treated.
	 * @author Sam
	 *
	 */
	public static enum ContentFormat
	{
		/**
		 * Content should be treated as-is. No formatting or validation.
		 */
		RAW("raw"),

		/**
		 * Content should be treated as HTML, and should be processed and validated.
		 */
		HTML("html");

		private String value;
		private ContentFormat(String value)
		{
			this.value = value;
		}
		
		public String getValue()
		{
			return value;
		}
	}
}