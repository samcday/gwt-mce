package com.site2go.gwtmce.client.addons;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.site2go.gwt.util.client.FunctionProxy;
import com.site2go.gwtmce.client.util.PropertiesObject;
import com.site2go.gwt.util.client.FunctionProxy.FunctionArguments;
import com.site2go.gwt.util.client.FunctionProxy.FunctionHandler;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.plugin.Plugin;
import com.site2go.gwtmce.client.plugin.PluginInterface;
import com.site2go.gwtmce.client.theme.Theme;
import com.site2go.gwtmce.client.theme.ThemeInterface;

/**
 * Theme base class.
 * 
 * This object should only be created by classes that implement
 * {@link ThemeInterface}. Further, these implementation classes should be
 * singletons.
 * 
 * @author Sam
 * 
 */
public class AddonFactoryImpl
	extends JavaScriptObject
{
	protected AddonFactoryImpl() { }

	public static <T> AddonFactoryImpl create(final AddonFactory<T> addonFactoryImpl)
	{
		FunctionProxy bootstrapper = FunctionProxy.create(new FunctionHandler()
		{
			@Override
			public Object onFunctionCall(FunctionProxy func, FunctionArguments args)
			{
				final T addonImpl = (T)addonFactoryImpl.instantiate();
				AddonFactoryImpl addon = func.cast();

				// Attach methods depending on type of addon.
				if(addonImpl instanceof ThemeInterface)
					addon.setThemeFunctions((ThemeInterface)addonImpl, (Theme)func.cast());
				else
					addon.setPluginFunctions((PluginInterface)addonImpl, (Plugin)func.cast());

				return addon;
			}
		});

		return bootstrapper.cast();
	}

	private final void setThemeFunctions(final ThemeInterface themeImpl, Theme theme)
	{
		FunctionProxy.createAndAttach(new FunctionHandler()
		{
			@Override
			public Object onFunctionCall(FunctionProxy func,
					FunctionArguments args)
			{
				themeImpl.init((Editor) args.getArg(0), (String) args.getArg(1));
				return null;
			}
		}, theme, "init");
		
		FunctionProxy.createAndAttach(new FunctionHandler()
		{
			@Override
			public Object onFunctionCall(FunctionProxy func,
					FunctionArguments args)
			{
				themeImpl.destroy();
				return null;
			}
		}, theme, "destroy");

		FunctionProxy.createAndAttach(new FunctionHandler()
		{
			@Override
			public Object onFunctionCall(FunctionProxy func,
					FunctionArguments args)
			{
				return themeImpl.getInfo();
			}
		}, theme, "getInfo");

		FunctionProxy.createAndAttach(new FunctionHandler()
		{
			@Override
			public Object onFunctionCall(FunctionProxy func,
					FunctionArguments args)
			{
				PropertiesObject s = args.getArg(0);
				Element targetNode = s.getProperty("targetNode");
				int width = s.getProperty("width");
				int height = s.getProperty("height");
				int deltaWidth = s.getProperty("deltaWidth");
				int deltaHeight = s.getProperty("deltaHeight");

				return themeImpl.renderUI(targetNode, width, height, deltaWidth, deltaHeight);
			}
		}, theme, "renderUI");

		FunctionProxy.createAndAttach(new FunctionHandler()
		{
			@Override
			public Object onFunctionCall(FunctionProxy func,
					FunctionArguments args)
			{
				return themeImpl.execCommand((String) args.getArg(0), (Boolean)args.getArg(1), args.getArg(2));
			}
		}, theme, "execCommand");
	}

	private final void setPluginFunctions(final PluginInterface pluginImpl, Plugin plugin)
	{
		FunctionProxy.createAndAttach(new FunctionHandler()
		{
			@Override
			public Object onFunctionCall(FunctionProxy func,
					FunctionArguments args)
			{
				pluginImpl.init((Editor) args.getArg(0), (String) args.getArg(1));
				return null;
			}
		}, plugin, "init");

		FunctionProxy.createAndAttach(new FunctionHandler()
		{
			@Override
			public Object onFunctionCall(FunctionProxy func,
					FunctionArguments args)
			{
				return pluginImpl.getInfo();
			}
		}, plugin, "getInfo");

		FunctionProxy.createAndAttach(new FunctionHandler()
		{
			@Override
			public Object onFunctionCall(FunctionProxy func,
					FunctionArguments args)
			{
				String cmd = args.getArg(0);
				boolean ui = (args.getArg(1) != null) ? (Boolean)args.getArg(1) : false;
				Object value = args.getArg(2);

				return pluginImpl.execCommand(cmd, ui, value);
			}
		}, plugin, "execCommand");
	}

	public static interface AddonFactory<T>
	{
		public T instantiate();
	}
}
