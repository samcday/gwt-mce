package com.site2go.gwtmce.client.addons;

import com.google.gwt.core.client.JavaScriptObject;
import com.site2go.gwtmce.client.addons.AddonFactoryImpl.AddonFactory;

/**
 * The {@link AddonManager} is the class used by both Theme and Addon Managers.
 * We incorporate the base functionality here.<br>
 * <br>
 * 
 * The {@link #add(String, AddonFactoryImpl)} method is somewhat different to the
 * native tinymce equivalent. Instead of passing in an anonymous function that
 * can be instantiated, users of this class pass in an {@link AddonFactoryImpl}
 * implementation. This factory will be called each time TinyMCE requires a
 * Theme/Plugin to be created for an editor instance.
 * 
 * @author Sam
 * 
 */
public abstract class AddonManager<T>
	extends JavaScriptObject
{
	protected AddonManager()
	{
	}

	/**
	 * Adds a instance of the add-on by it's short name.
	 * @param name Short name/id for the add-on.
	 * @param addon Theme or plugin {@link AddonFactoryImpl} impl to add.
	 */
	public final void add(String name, AddonFactory<T> addonFactory)
	{
		/**
		 * Wrap the factory in another factory! :)
		 */
		AddonFactoryImpl addonFactoryImpl = AddonFactoryImpl.create(addonFactory);

		this.addImpl(name, addonFactoryImpl);
	}

	private final native void addImpl(String name, AddonFactoryImpl addon) /*-{
		this.add(name, addon);
	}-*/;

	/**
	 * Loads an add-on from a specific url.
	 * @param name Short name of the add-on that gets loaded.
	 * @param url URL to the add-on that will get loaded.
	 * @param callback Optional callback to execute ones the add-on is loaded.
	 */
	public native final void load(String name, String url, AddonLoadedHandler handler) /*-{
		this.load(name, url, function() {
			handler && handler.@com.site2go.gwtmce.client.addons.AddonManager.AddonLoadedHandler::onAddonLoaded()();
		});
	}-*/;
	
	/**
	 * Loads a language pack for the specified add-on.
	 * @param name Short name of the add-on.
	 */
	public native final void requireLanguagePack(String name) /*-{
		this.requireLanguagePack(name);
	}-*/;

	public static interface AddonLoadedHandler {
		public void onAddonLoaded();
	}
}
