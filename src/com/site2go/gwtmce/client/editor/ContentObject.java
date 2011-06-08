package com.site2go.gwtmce.client.editor;

import com.site2go.gwtmce.client.util.PropertiesObject;

/**
 * This object gets passed around during get/set content operations on the editor. It can be used to set some specific options
 * during the process (see {@link #setNoEvents(boolean)} and {@link #setFormat(com.site2go.gwt.tinymce.client.editor.ContentObject.ContentFormat)}.
 * It can also be used to retrieve and/or modify the content in question via {@link 
 * @author Sam
 *
 */
public class ContentObject
	extends PropertiesObject
{
	protected ContentObject() { }

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

	public static final ContentObject create(boolean noEvents, ContentObject.ContentFormat format)
	{
		ContentObject o = PropertiesObject.create().cast();
		
		o.setNoEvents(noEvents);
		o.setFormat(format);
		
		return o.cast();
	}

	public final void setNoEvents(boolean noEvents)
	{
		this.setProperty("no_events", noEvents);
	}

	public final boolean getNoEvents()
	{
		return this.getProperty("no_events", false);
	}

	public final void setFormat(ContentObject.ContentFormat format)
	{
		this.setProperty("format", format.getValue());
	}

	public final ContentObject.ContentFormat getFormat()
	{
		String format = this.getProperty("format", "html");
		if(format.equals("raw"))
			return ContentFormat.RAW;
		else
			return ContentFormat.HTML;
	}

	public final String getContent()
	{
		return this.getProperty("content", "");
	}

	public final void setContent(String content)
	{
		this.setProperty("content", content);
	}
}