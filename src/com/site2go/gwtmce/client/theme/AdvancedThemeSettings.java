package com.site2go.gwtmce.client.theme;

import com.google.gwt.dom.client.Element;
import com.site2go.gwt.util.client.FunctionProxy;
import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwt.util.client.FunctionProxy.FunctionHandler;
import com.site2go.gwtmce.client.util.PropertiesObject;

public class AdvancedThemeSettings 
	extends PropertiesObject {
	protected AdvancedThemeSettings() {};
	
	public final void setToolbarLocation(ToolbarLocation location) {
		this.setProperty("theme_advanced_toolbar_location", location.getValue());
	}

	public final void setLayoutManager(LayoutManager manager) {
		this.setProperty("theme_advanced_layout_manager", manager.getValue());
	}
	
	public final void setStatusBarLocation(StatusBarLocation location) {
		this.setProperty("theme_advanced_statusbar_location", location.getValue());
	}

	public final void setCustomLayout(final AdvancedThemeCustomLayoutHandler handler) {
		this.setProperty("theme_advanced_custom_layout", FunctionProxy.create(new FunctionHandler() {
			@Override
			public Object onFunctionCall(FunctionProxy func, FunctionArguments args) {
				AdvancedThemeSettings settings = args.getArg(0);
				Element tableBody = args.getArg(1);
				AdvancedThemeCustomLayoutOptions opts = args.getArg(2);
				Element parent = args.getArg(3);

				return handler.onRenderCustomLayout(settings, tableBody, opts, parent);
			}
		}));
	}
	
	public static interface AdvancedThemeCustomLayoutHandler {
		public Element onRenderCustomLayout(AdvancedThemeSettings settings, Element tableBody, AdvancedThemeCustomLayoutOptions opts, Element parent);
	}
	
	public static class AdvancedThemeCustomLayoutOptions
		extends PropertiesObject {
		protected AdvancedThemeCustomLayoutOptions() {};

		public final int getDeltaHeight() {
			return this.getProperty("deltaHeight");
		}
		
		public final void setDeltaHeight(int value) {
			this.setProperty("deltaHeight", value);
		}
		
		public final Element getTargetNode() {
			return this.getProperty("targetNode");
		}
		
		public final void setTargetNode(Element el) {
			this.setProperty("targetNode", el);
		}
	}
	
	public static enum StatusBarLocation {
		TOP("top"),
		BOTTOM("bottom"),
		NONE("none");
		
		private String value;
		
		private StatusBarLocation(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return this.value;
		}
	};

	public static enum ToolbarLocation {
		TOP("top"),
		BOTTOM("bottom"),
		EXTERNAL("external");

		private String value;

		private ToolbarLocation(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return this.value;
		}
	}
	
	public static enum LayoutManager {
		SIMPLE_LAYOUT("SimpleLayout"),
		ROW_LAYOUT("RowLayout"),
		CUSTOM_LAYOUT("CustomLayout");

		private String value;

		private LayoutManager(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return this.value;
		}
	}
}
