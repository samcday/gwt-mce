package com.site2go.gwtmce.client.theme;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.site2go.gwtmce.client.addons.AddonInfo;
import com.site2go.gwtmce.client.editor.Editor;

public class Theme
	extends JavaScriptObject
	implements ThemeInterface
{
	public static final class ThemeRenderOptions
		extends JavaScriptObject
	{
		protected ThemeRenderOptions() { }
		
		public static final native ThemeRenderOptions create(Element iframeContainer, String editorContainer, Element sizeContainer, int deltaHeight)
		/*-{
			var options = {
				iframeContainer : iframeContainer,
				editorContainer : editorContainer,
				sizeContainer : sizeContainer,
				deltaHeight : deltaHeight
			};

			return options;
		}-*/;
	}

	protected Theme() { }

	@Override
	public final native ThemeRenderOptions renderUI(Element targetNode, int width, int height, int deltaWidth, int deltaHeight)/*-{
		return this.renderUI({
			targetNode: targetNode,
			width: width,
			height: height,
			deltaWidth: deltaWidth,
			deltaHeight: deltaHeight
		});
	}-*/;

	@Override
	public final native AddonInfo getInfo() /*-{
		return this.getInfo();
	}-*/;

	@Override
	public final native void init(Editor editor, String url)
	/*-{
		return this.init(editor, url);
	}-*/;

	public final native void destroy()
	/*-{
		this.destroy();
	}-*/;
	
	public final native boolean execCommand(String cmd, boolean ui, Object o)
	/*-{
		return false;
	}-*/;
}
