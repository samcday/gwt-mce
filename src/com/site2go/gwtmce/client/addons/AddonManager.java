package com.site2go.gwtmce.client.addons;

import com.google.gwt.core.client.JavaScriptObject;
import com.site2go.gwt.util.client.FunctionProxy;
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

		this._add(name, addonFactoryImpl);
	}

	private final native void _add(String name, AddonFactoryImpl addon) /*-{
		this.add(name, addon);
	}-*/;

	/**
	 * Returns the specified add on by the short name.
	 * @param name Add-on to look for.
	 * @return Theme or plugin add-on instance or null if not found.
	 */
	public final native AddonFactoryImpl get(String name) /*-{
		return this.get(name);
	}-*/;

	/**
	 * Loads an add-on from a specific url.
	 * @param name Short name of the add-on that gets loaded.
	 * @param url URL to the add-on that will get loaded.
	 * @param callback Optional callback to execute ones the add-on is loaded.
	 */
	public native final void load(String name, String url,
			FunctionProxy callback) /*-{
		this.load(name, url, callback);
	}-*/;
	
	/**
	 * Loads a language pack for the specified add-on.
	 * @param name Short name of the add-on.
	 */
	public native final void requireLanguagePack(String name) /*-{
		this.requireLanguagePack(name);
	}-*/;
}
