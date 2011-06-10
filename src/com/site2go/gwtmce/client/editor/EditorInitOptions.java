/**
 * 
 */
package com.site2go.gwtmce.client.editor;

import com.site2go.gwtmce.client.util.PropertiesObject;
import com.site2go.gwtmce.client.util.MCEUtil;

public class EditorInitOptions
	extends PropertiesObject
{
	/**
	 * Enumeration to define how editors should be initialized on the page.
	 * @author Sam
	 */
	public static enum EditorMode
	{
		/**
		 * Converts all textarea elements to editors when the page loads.
		 */
		TEXTAREAS("textareas"),

		/**
		 * Converts all textarea elements with the textarea_trigger attribute set to "true". It will also convert all textarea elements that match the editor_selector setting or don't match the editor_deselector setting.
		 * Note: The textarea_trigger option is deprecated. We strongly recommend you to use the editor_selector/editor_deselector options instead since they validate with W3C specifications.
		 */
		SPECIFIC_TEXTAREAS("specific_textareas"),
		
		/**
		 * Converts elements of the specified names, as listed in the elements option. These elements can be any kind -- for example, textareas or divs.
		 */
		EXACT("exact"),

		/**
		 * Does not convert any elements. Later, your page can call the tinyMCE.execCommand("mceAddControl", true, "id"); function.
		 */
		NONE("none");

		private String value;
		private EditorMode(String value)
		{
			this.value = value;
		}
		
		public String getValue()
		{
			return this.value;
		}
	}

	/**
	 * Defines how dialogs/popups should be opened.
	 * Note that this currently only has an effect in Internet Explorer (in other browsers, "window" is always used).
	 * @author Sam
	 *
	 */
	public static enum EditorDialogType
	{
		/**
		 * opens a modal dialog.
		 */
		MODAL("modal"),

		/**
		 * opens a normal window
		 */
		WINDOW("window");

		private String value;
		private EditorDialogType(String value)
		{
			this.value = value;
		}
		
		public String getValue()
		{
			return this.value;
		}
	}

	protected EditorInitOptions() { }

	public final void setSkin(String skin)
	{
		this.setProperty("skin", skin);
	}
	
	public final void setPlugins(String[] plugins)
	{
		this.setProperty("plugins", MCEUtil.implode(plugins, ","));
	}
	
	public final void setTheme(String theme)
	{
		this.setProperty("theme", theme);
	}

	public final void setMode(EditorInitOptions.EditorMode mode)
	{
		this.setProperty("mode", mode.getValue());
	}
	
	public final void setContentCss(String contentCss)
	{
		this.setProperty("content_css", contentCss);
	}

	public final void setContentCss(String[] contentCss)
	{
		this.setProperty("content_css", MCEUtil.implode(contentCss, ","));
	}

	public final void setForcedRootBlock(String element)
	{
		this.setProperty("forced_root_block", element);
	}

	public final void setInlineStyles(boolean enable)
	{
		this.setProperty("inline_styles", enable);
	}

	public final void setDoctype(String doctype)
	{
		this.setProperty("doctype", doctype);
	}
	
	public final void setDialogType(EditorInitOptions.EditorDialogType type)
	{
		this.setProperty("dialog_type", type.getValue());
	}
}