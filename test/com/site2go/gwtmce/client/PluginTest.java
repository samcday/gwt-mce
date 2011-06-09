package com.site2go.gwtmce.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;
import com.site2go.gwtmce.client.addons.AddonInfo;
import com.site2go.gwtmce.client.addons.AddonFactoryImpl.AddonFactory;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.editor.EditorSettings;
import com.site2go.gwtmce.client.plugin.PluginInterface;
import com.site2go.gwtmce.client.plugin.PluginManager;

public class PluginTest
	extends GWTTestCase
	implements PluginInterface
{
	private Editor editor;
	private Element el;

	private boolean initCalled;
	private boolean execCommandCalled;

	private boolean testingExecCommand;
	private String testExecCommandName;
	private boolean testExecCommandUi;
	private Object testExecCommandObj;

	private AddonInfo addonInfo;

	@Override
	public String getModuleName()
	{
		return "com.site2go.gwtmce.GWTMCETest";
	}

	@Override
	protected void gwtSetUp() throws Exception
	{
		this.addonInfo = AddonInfo.create("Testcase Theme", "Sam", "sambro.is-super-awesome.com", "sambro.is-super-awesome.com", "1.666");
		Document doc = Document.get();

		// Create a node to be cannibalized by editor.
		this.el = doc.createDivElement();
		this.el.setInnerHTML("<b>Test content!</b>");
		this.el.setId("targetElement");
		doc.getBody().appendChild(this.el);

		PluginManager.get().add("testcaseplugin", new AddonFactory<PluginInterface>()
		{
			@Override
			public PluginInterface instantiate()
			{
				return PluginTest.this;
			}
		});

		// Set up basic editor settings. Bare-ass minimum to ensure we have a functioning simple themed editor.
		EditorSettings settings = new EditorSettings();
		settings.setTheme("simple");
		settings.getPluginsCollection().add("-testcaseplugin");
		this.editor = new Editor(el.getId(), settings);

		this.editor.render();
	}

	public void testInit()
	{
		delayTestFinish(1000);
		
		new Timer()
		{
			@Override
			public void run()
			{
				assertTrue(initCalled);
				finishTest();
			}
		}.schedule(500);
	}
	
	public void testExecCommand()
	{
		this.testingExecCommand = true;

		this.testExecCommandName = "testThemeCommand";
		this.testExecCommandUi = true;
		this.testExecCommandObj = "DATA";
	
		this.editor.execCommand(this.testExecCommandName, this.testExecCommandUi, this.testExecCommandObj);

		assertTrue(this.execCommandCalled);

		this.testingExecCommand = false;
	}
	
	public void testGetInfo()
	{
		AddonInfo addonInfo = this.editor.getPlugin("testcaseplugin").getInfo();
		assertEquals(this.addonInfo.getLongName(), addonInfo.getLongName());
		assertEquals(this.addonInfo.getAuthor(), addonInfo.getAuthor());
		assertEquals(this.addonInfo.getAuthorURL(), addonInfo.getAuthorURL());
		assertEquals(this.addonInfo.getInfoURL(), addonInfo.getInfoURL());
		assertEquals(this.addonInfo.getVersion(), addonInfo.getVersion());
	}

	@Override
	public AddonInfo getInfo()
	{
		return this.addonInfo;
	}

	@Override
	public void init(Editor editor, String url)
	{
		assertTrue(GWT.getModuleBaseURL().startsWith(url));
		assertEquals(this.editor, editor);
		assertFalse(this.initCalled);
		this.initCalled = true;
	}

	@Override
	public boolean execCommand(String cmd, boolean ui, Object value)
	{
		if(this.testingExecCommand)
		{
			assertFalse(this.execCommandCalled);
			assertEquals(this.testExecCommandName, cmd);
			assertEquals(this.testExecCommandUi, ui);
			assertEquals(this.testExecCommandObj, value);
			this.execCommandCalled = true;

			return true;
		}

		return false;
	}
}
