package com.site2go.gwtmce.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;
import com.site2go.gwtmce.client.addons.AddonInfo;
import com.site2go.gwtmce.client.addons.AddonFactoryImpl.AddonFactory;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.editor.EditorInitOptions;
import com.site2go.gwtmce.client.theme.ThemeInterface;
import com.site2go.gwtmce.client.theme.ThemeManager;
import com.site2go.gwtmce.client.theme.Theme.ThemeRenderOptions;

public class ThemeTest
	extends GWTTestCase
	implements ThemeInterface
{
	private Editor editor;
	private Element el;

	private boolean initCalled;
	private boolean destroyCalled;
	private boolean renderUiCalled;
	private boolean execCommandCalled;

	private boolean testingExecCommand;
	private String testExecCommandName;
	private boolean testExecCommandUi;
	private Object testExecCommandObj;
	
	private AddonInfo addonInfo;

	private Element editorContainer;
	private Element sizeContainer;

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
		this.el.setInnerHTML("<p><b>Test content!</b></p>");
		this.el.setId("targetElement");
		doc.getBody().appendChild(this.el);

		// Set up basic editor settings. Bare-ass minimum to ensure we have a functioning simple themed editor.
		EditorInitOptions settings = EditorInitOptions.create();
		settings.setTheme("-testtheme");
		this.editor = Editor.create(el.getId(), settings);

		ThemeManager.get().add("testtheme", new AddonFactory<ThemeInterface>()
		{
			@Override
			public ThemeInterface instantiate()
			{
				return ThemeTest.this;
			}
		});

		this.editor.render();
	}

	@Override
	protected void gwtTearDown() throws Exception
	{
		this.editor.remove();
		this.el.removeFromParent();
	}

	public void testInit()
	{
		delayTestFinish(1000);
		
		new Timer()
		{
			@Override
			public void run()
			{
				assertTrue(renderUiCalled);
				assertTrue(initCalled);
				
				assertEquals(editorContainer, editor.getContainer());

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
		assertEquals(this.addonInfo.getLongName(), this.editor.getTheme().getInfo().getLongName());
		assertEquals(this.addonInfo.getAuthor(), this.editor.getTheme().getInfo().getAuthor());
		assertEquals(this.addonInfo.getAuthorURL(), this.editor.getTheme().getInfo().getAuthorURL());
		assertEquals(this.addonInfo.getInfoURL(), this.editor.getTheme().getInfo().getInfoURL());
		assertEquals(this.addonInfo.getVersion(), this.editor.getTheme().getInfo().getVersion());
	}

	@Override
	public void destroy()
	{
		
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
	public ThemeRenderOptions renderUI(Element targetNode, int width,
			int height, int deltaWidth, int deltaHeight)
	{
		assertFalse(this.renderUiCalled);
		assertEquals(this.el.getId(), targetNode.getId());

		this.editorContainer = Document.get().createDivElement();
		this.editorContainer.setClassName("mceEditor defaultSkin");
		this.editorContainer.setId(this.editor.getId() + "_container");
		
		targetNode.getParentElement().insertBefore(this.editorContainer, targetNode);

		DivElement sizeContainer = Document.get().createDivElement();
		sizeContainer.setClassName("mceLayout");
		this.editorContainer.appendChild(sizeContainer);

		DivElement iframe = Document.get().createDivElement();
		iframe.setClassName("mceIframeContainer");
		iframe.setId("iframecontainer");
		sizeContainer.appendChild(iframe);
		
		this.renderUiCalled = true;
		return ThemeRenderOptions.create(iframe, this.editorContainer.getId(), sizeContainer, 0);
	}
}
