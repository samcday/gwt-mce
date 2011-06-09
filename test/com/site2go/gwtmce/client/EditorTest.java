package com.site2go.gwtmce.client;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;
import com.site2go.gwtmce.client.editor.ContentObject;
import com.site2go.gwtmce.client.editor.CustomEditorCommandCallback;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.editor.EditorSettings;
import com.site2go.gwtmce.client.editor.UndoLevel;
import com.site2go.gwtmce.client.editor.UndoManager;
import com.site2go.gwtmce.client.editor.ContentObject.ContentFormat;
import com.site2go.gwtmce.client.editor.events.ActivateHandler;
import com.site2go.gwtmce.client.editor.events.BeforeExecCommandHandler;
import com.site2go.gwtmce.client.editor.events.BeforeGetContentHandler;
import com.site2go.gwtmce.client.editor.events.BeforeRenderUIHandler;
import com.site2go.gwtmce.client.editor.events.BeforeSetContentHandler;
import com.site2go.gwtmce.client.editor.events.ChangeHandler;
import com.site2go.gwtmce.client.editor.events.ClickHandler;
import com.site2go.gwtmce.client.editor.events.ContextMenuHandler;
import com.site2go.gwtmce.client.editor.events.DblClickHandler;
import com.site2go.gwtmce.client.editor.events.DeactivateHandler;
import com.site2go.gwtmce.client.editor.events.ExecCommandHandler;
import com.site2go.gwtmce.client.editor.events.GetContentHandler;
import com.site2go.gwtmce.client.editor.events.HandlerRegistration;
import com.site2go.gwtmce.client.editor.events.InitHandler;
import com.site2go.gwtmce.client.editor.events.KeyDownHandler;
import com.site2go.gwtmce.client.editor.events.KeyPressHandler;
import com.site2go.gwtmce.client.editor.events.KeyUpHandler;
import com.site2go.gwtmce.client.editor.events.MouseDownHandler;
import com.site2go.gwtmce.client.editor.events.MouseUpHandler;
import com.site2go.gwtmce.client.editor.events.PostRenderHandler;
import com.site2go.gwtmce.client.editor.events.RedoHandler;
import com.site2go.gwtmce.client.editor.events.SetContentHandler;
import com.site2go.gwtmce.client.editor.events.SetProgressStateHandler;
import com.site2go.gwtmce.client.editor.events.UndoHandler;

public class EditorTest
	extends GWTTestCase
{
	private Editor editor;
	private Element el;

	private TestInitHandlers initHandlers;

	@Override
	public String getModuleName()
	{
		return "com.site2go.gwtmce.GWTMCETest";
	}

	@Override
	protected void gwtSetUp() throws Exception
	{
		Document doc = Document.get();

		// Create a node to be cannibalized by editor.
		this.el = doc.createDivElement();
		this.el.setInnerHTML("<b>Test content!</b>");
		this.el.setId("targetElement");
		doc.getBody().appendChild(this.el);

		// Set up basic editor settings. Bare-ass minimum to ensure we have a functioning simple themed editor.
		EditorSettings settings = new EditorSettings();
		this.editor = new Editor(el.getId(), settings);
		settings.setTheme("simple");

		this.initHandlers = new TestInitHandlers(this.editor);

		this.editor.addInitHandler(initHandlers);
		this.editor.addPostRenderHandler(initHandlers);
		this.editor.addBeforeRenderUIHandler(initHandlers);

		this.editor.render();
	}
	
	@Override
	protected void gwtTearDown() throws Exception
	{
		this.editor.remove();
		this.editor = null;
		this.el.removeFromParent();
		this.el = null;
	}

	public void testInitHandlers()
	{
		delayTestFinish(1000);

		new Timer()
		{
			@Override
			public void run()
			{
				assertTrue(initHandlers.initHandlerCalled);
				assertTrue(initHandlers.beforeRenderUIHandlerCalled);
				assertTrue(initHandlers.postRenderHandlerCalled);
				finishTest();
			}
		}.schedule(500);
	}

	/**
	 * Basic test to ensure ExecCommandHandler and BeforeExecCommandHandlers function correctly.
	 */
	public void testExecCommandHandlers()
	{
		final String theCommand = "test";

		// Setup the event handlers for execCommand related events.
		TestExecCommandHandlers testExecCommandHandler = new TestExecCommandHandlers(
				this.editor, theCommand, false);
		HandlerRegistration beforeHandlerRegistration = this.editor
				.addBeforeExecCommandHandler(testExecCommandHandler);
		HandlerRegistration handlerRegistration = this.editor
				.addExecCommandHandler(testExecCommandHandler);

		// Execute a command.
		this.editor.execCommand(theCommand, false);

		// Ensure both handlers were called.
		assertTrue(testExecCommandHandler.beforeCalled);
		assertTrue(testExecCommandHandler.called);

		beforeHandlerRegistration.removeHandler();
		handlerRegistration.removeHandler();
	}
	
	/**
	 * Test case to ensure that a call to editor execCommand() with a fake command will return false.
	 */
	public void testFakeExecCommand()
	{
		// TODO: seems to be broken.
		//assertFalse(this.editor.execCommand("CLEARLYAFAKECOMMANDRIGHTHERELOL", true));
	}
	
	/**
	 * Test case to ensure a valid browser command call with execCommand will return true.
	 */
	public void testValidExecCommand()
	{
		assertTrue(this.editor.execCommand("bold", false));
	}

	/**
	 * Test to ensure that a terminated command will not make it any further than the BeforeExecCommandHandler.
	 */
	public void testTerminateExecCommand()
	{
		HandlerRegistration beforeHandlerRegistration = this.editor.addBeforeExecCommandHandler(new BeforeExecCommandHandler()
		{
			@Override
			public void onBeforeExecCommand(Editor sender, String command, boolean ui,
					ExecCommandContextObject context)
			{
				context.terminate();
			}
		});

		TestExecCommandHandlers testExecCommandHandler = new TestExecCommandHandlers(
				this.editor, "testCommand", true);
		HandlerRegistration handlerRegistration = this.editor
		.addExecCommandHandler(testExecCommandHandler);

		this.editor.execCommand("testCommand", true);

		assertFalse(testExecCommandHandler.called);

		handlerRegistration.removeHandler();
		beforeHandlerRegistration.removeHandler();
	}

	/**
	 * Test to check that custom commands are fired correctly.
	 */
	public void testExecCustomCommand()
	{
		final String commandName = "myCustomCommand";
		Object testObj = new Object();
		TestCustomCommandCallback testCommandCallback = new TestCustomCommandCallback(this.editor, commandName, true, testObj);
		this.editor.addCommand(commandName, testCommandCallback);

		this.editor.execCommand(commandName, true, testObj);
		assertTrue(testCommandCallback.called);
	}
	
	public void testGetAndSetContent()
	{
		ContentObject co = new ContentObject();
		co.setFormat(ContentFormat.RAW);
		co.getProperties().setProperty("customTestField", true);
		String content = "<p>test get/set content</p>";
		String overrideContent = "<p>test override content</p>";
		String finalContent = "<p>This is what should come out yo.</p>";

		TestSetContentHandlers setHandlers = new TestSetContentHandlers(this.editor, co.getFormat(), content, overrideContent, finalContent);
		HandlerRegistration setReg = this.editor.addSetContentHandler(setHandlers);
		HandlerRegistration beforeSetReg = this.editor.addBeforeSetContentHandler(setHandlers);

		// Let's start with a set content that the handlers SHOULDN'T see.
		co.setNoEvents(true);
		this.editor.setContent("<p>NOBODY SHOULD SEE THIS.</p>", co);

		// Make sure handlers didn't get fired.
		assertFalse(setHandlers.beforeSetCalled);
		assertFalse(setHandlers.setCalled);

		// Now let's try running a set content that handlers can get in on.
		co.setNoEvents(false);
		String setContentResult = this.editor.setContent(content, co);
		
		assertTrue(setHandlers.beforeSetCalled);
		assertTrue(setHandlers.setCalled);
		assertEquals(overrideContent, this.editor.getContent());
		assertEquals(finalContent, setContentResult);

		setReg.removeHandler();
		beforeSetReg.removeHandler();
		
		// Alright that's the set handlers done. Let's try get handlers now.
		TestGetContentHandlers getHandlers = new TestGetContentHandlers(this.editor, co.getFormat(), content, overrideContent, finalContent);
		HandlerRegistration getReg = this.editor.addGetContentHandler(getHandlers);
		HandlerRegistration beforeGetReg = this.editor.addBeforeGetContentHandler(getHandlers);

		co.setNoEvents(true);
		getHandlers.expectedContent = this.editor.getContent(co);

		assertFalse(getHandlers.beforeGetCalled);
		assertFalse(getHandlers.getCalled);

		co.setNoEvents(false);
		String getContentResult = this.editor.getContent(co);
		
		assertTrue(getHandlers.beforeGetCalled);
		assertTrue(getHandlers.getCalled);
		assertEquals(getContentResult, finalContent);
		
		getReg.removeHandler();
		beforeGetReg.removeHandler();
	}

	public void testUndoManager()
	{
		UndoManager um = this.editor.getUndoManager();

		// Let's start with a clean slate.
		ContentObject options = new ContentObject(true, ContentFormat.RAW);
		this.editor.setContent("<p><b>Test content!</b></p>", options);
		um.clear();

		// After a clear there should be no undo or redo available.
		assertFalse(um.hasRedo());
		assertFalse(um.hasUndo());

		// Let's try setting some new content and making sure undo/redo works as intended.
		String originalContent = this.editor.getContent(options);
		String newContent = "<p><b>TEST.</b></p>";
		
		// Before we set the new content, we take a snapshot of current content.
		UndoLevel originalLevel = um.add();
		
		// Now we set the content on the editor.
		this.editor.setContent(newContent, options);

		// ... And create another undo level. This one will class as the "current" undo level.
		UndoLevel newLevel = um.add();

		// This pure test scenario should definitely mean we now have two different undo levels to play with.
		assertNotNull(originalLevel);
		assertNotNull(newLevel);

		// Old undo level should be equal to original editor content.
		assertEquals(originalContent, originalLevel.getContent());

		// New (and current) undo level should be equal to new content we just set.
		assertEquals(newContent, newLevel.getContent());

		// There should now be an undo level available, but no redo available (yet).
		assertTrue(um.hasUndo());
		assertFalse(um.hasRedo());

		// Now let's try undoing.
		UndoLevel level = um.undo();
		
		// The undo level that just came back should be equal to the original undo level we created.
		assertEquals(originalLevel, level);
		
		// The content of the editor should now be equal to the original content.
		assertEquals(originalContent, this.editor.getContent(options));

		// There should no longer be an undo level available, but a redo should now be available.
		assertFalse(um.hasUndo());
		assertTrue(um.hasRedo());

		// Now let's try redoing.
		level = um.redo();

		// The level returned from redo should be the new level we created after we changed the content originally.
		assertEquals(newLevel, level);
		
		// The content of editor should be equal to new content we made before.
		assertEquals(newContent, this.editor.getContent(new ContentObject(true, ContentFormat.RAW)));

		// Undo should be available again, and no redo.
		assertTrue(um.hasUndo());
		assertFalse(um.hasRedo());

		// Clear undo manager again, recheck state just for good measure.
		um.clear();
		assertFalse(um.hasRedo());
		assertFalse(um.hasUndo());
	}

	public void testUndoManagerHandlers()
	{
		UndoManager um = this.editor.getUndoManager();
		TestUndoRedoHandlers handlers = new TestUndoRedoHandlers(this.editor, um);

		HandlerRegistration undoReg = this.editor.addUndoHandler(handlers);
		HandlerRegistration redoReg = this.editor.addRedoHandler(handlers);

		um.add();
		HandlerRegistration changeReg = this.editor.addChangeHandler(handlers);
		this.editor.setContent("<p><b>undo handlers test</b></p>");
		UndoLevel newLevel = um.add();
		UndoLevel undoLevel = um.undo();
		UndoLevel redoLevel = um.redo();

		assertTrue(handlers.undoCalled);
		assertTrue(handlers.redoCalled);
		assertTrue(handlers.changeCalled);

		assertEquals(undoLevel, handlers.undoLevel);
		assertEquals(redoLevel, handlers.redoLevel);
		assertEquals(newLevel, handlers.changeLevel);

		undoReg.removeHandler();
		redoReg.removeHandler();
		changeReg.removeHandler();
	}

	public void testFocusAndBlur()
	{
		// Gonna need a second editor for this little test.
		Document doc = Document.get();
		final DivElement newElement = doc.createDivElement();
		newElement.setId("focustestingsecondeditor");
		newElement.setInnerHTML("NEW EDITOR FOR FOCUS TESTING.");
		doc.getBody().appendChild(newElement);

		final Editor newEditor = new Editor(newElement.getId(), new EditorSettings());

		final TestFocusHandlers handlers = new TestFocusHandlers(this.editor, newEditor);

		// Since we need to wait for the new editor to fire up, we'll perform the rest of this test case logic inside the init handler of the new editor.
		newEditor.addInitHandler(new InitHandler()
		{
			@Override
			public void onInit(Editor sender)
			{
				// We'll start with the new editor having focus.
				newEditor.focus();

				// Now we'll add the handlers to our main editor instance.
				HandlerRegistration activateReg = editor.addActivateHandler(handlers);
				HandlerRegistration deactivateReg = editor.addDeactivateHandler(handlers);

				// Now we'll run the test scenario.
				
				// Focus our main editor. This should fire activate handlers.
				editor.focus();

				// Now focus the new editor again, this should fire deactivate handlers.
				newEditor.focus();

				assertTrue(handlers.activateCalled);
				assertTrue(handlers.deactivateCalled);

				activateReg.removeHandler();
				deactivateReg.removeHandler();

				editor.focus();
				newEditor.remove();
				newElement.removeFromParent();

				finishTest();
			}
		});

		newEditor.render();
		delayTestFinish(3000);
	}

	private void dispatchEditorBodyEvent(NativeEvent ev) {
		EditorTest.this.editor.getDoc().getBody().dispatchEvent(ev);
	}

	public void testClickEventHandlers()
	{
		final TestClickHandlers handlers = new TestClickHandlers(this.editor);
		final HandlerRegistration clickReg = this.editor.addClickHandler(handlers);
		final HandlerRegistration downReg = this.editor.addMouseDownHandler(handlers);
		final HandlerRegistration upReg = this.editor.addMouseUpHandler(handlers);

		this.editor.focus();
		delayTestFinish(30 * 1000);

		Document editorDoc = this.editor.getDoc();
		this.dispatchEditorBodyEvent(editorDoc.createMouseDownEvent(0, 0, 0, 0, 0, false, false, false, false, NativeEvent.BUTTON_LEFT));
		this.dispatchEditorBodyEvent(editorDoc.createMouseUpEvent(0, 0, 0, 0, 0, false, false, false, false, NativeEvent.BUTTON_LEFT));
		this.dispatchEditorBodyEvent(editorDoc.createClickEvent(0, 0, 0, 0, 0, false, false, false, false));				

		new Timer()
		{
			@Override
			public void run()
			{
				if(handlers.clickCalled && handlers.downCalled && handlers.upCalled)
				{
					clickReg.removeHandler();
					downReg.removeHandler();
					upReg.removeHandler();
					this.cancel();
					finishTest();
				}
			}
		}.scheduleRepeating(50);
	}

	public void testDoubleClickHandler()
	{
		final TestDblClickHandler handler = new TestDblClickHandler(this.editor);
		final HandlerRegistration reg = this.editor.addDblClickHandler(handler);

		this.editor.focus();
		delayTestFinish(30 * 1000);

		Document editorDoc = this.editor.getDoc();
		this.dispatchEditorBodyEvent(editorDoc.createDblClickEvent(0, 0, 0, 0, 0, false, false, false, false));				

		new Timer()
		{
			@Override
			public void run()
			{
				if(handler.called)
				{
					reg.removeHandler();
					this.cancel();
					finishTest();		
				}
			}
		}.scheduleRepeating(50);
	}

	public void testContextMenuEventHandler()
	{
		final TestContextMenuHandler handler = new TestContextMenuHandler(this.editor);
		final HandlerRegistration reg = this.editor.addContextMenuHandler(handler);
		
		this.editor.focus();
		delayTestFinish(30 * 1000);

		Document editorDoc = this.editor.getDoc();
		this.dispatchEditorBodyEvent(editorDoc.createContextMenuEvent());	

		new Timer()
		{
			@Override
			public void run()
			{
				if(handler.called)
				{
					reg.removeHandler();
					this.cancel();
					finishTest();
				}
			}
		}.scheduleRepeating(50);		
	}

	public void testKeyEventHandlers()
	{
		final TestKeyHandlers handlers = new TestKeyHandlers(this.editor);
		final HandlerRegistration downReg = this.editor.addKeyDownHandler(handlers);
		final HandlerRegistration pressReg = this.editor.addKeyPressHandler(handlers);
		final HandlerRegistration upReg = this.editor.addKeyUpHandler(handlers);

		// Same deal as the click handlers test. Prompt user to simulate test for us.
		this.editor.focus();
		delayTestFinish(30 * 1000);

		Document editorDoc = this.editor.getDoc();
		this.dispatchEditorBodyEvent(editorDoc.createKeyDownEvent(false, false, false, false, 32));
		this.dispatchEditorBodyEvent(editorDoc.createKeyUpEvent(false, false, false, false, 32));
		this.dispatchEditorBodyEvent(editorDoc.createKeyPressEvent(false, false, false, false, 32));

		new Timer()
		{
			@Override
			public void run()
			{
				if(handlers.pressCalled && handlers.downCalled && handlers.upCalled)
				{
					pressReg.removeHandler();
					downReg.removeHandler();
					upReg.removeHandler();
					this.cancel();
					finishTest();
				}
			}
		}.scheduleRepeating(50);
	}
	
	public void testSetProgressState()
	{
		String testObj = "TEST";

		TestSetProgressStateHandler handler = new TestSetProgressStateHandler(this.editor, true, 666.666f, testObj);
		HandlerRegistration reg = this.editor.addSetProgressStateHandler(handler);
		
		this.editor.setProgressState(true, 666.666f, testObj);
		
		assertTrue(handler.called);
		reg.removeHandler();
	}

	private class TestCustomCommandCallback
		implements CustomEditorCommandCallback
	{
		private String expectedCommand;
		private boolean expectedUi;
		private Object expectedVal;
		private boolean called;
		private Editor editor;

		public TestCustomCommandCallback(Editor editor, String expectedCommand,
				boolean expectedUi, Object expectedVal)
		{
			this.editor = editor;
			this.expectedCommand = expectedCommand;
			this.expectedUi = expectedUi;
			this.expectedVal = expectedVal;
			this.called = false;
		}

		@Override
		public boolean onCommand(Editor editor, String commandName, boolean ui, Object val)
		{
			assertFalse(this.called);
			assertEquals(this.editor, editor);
			assertEquals(this.expectedCommand, commandName);
			assertEquals(this.expectedUi, ui);
			assertEquals(this.expectedVal, val);
			this.called = true;

			return false;
		}
	}

	private class TestExecCommandHandlers
		implements BeforeExecCommandHandler, ExecCommandHandler
	{
		private String expectedCommand;
		private boolean expectedUi;
		private boolean beforeCalled, called;
		private Editor editor;

		public TestExecCommandHandlers(Editor editor, String expectedCommand,
				boolean expectedUi)
		{
			this.editor = editor;
			this.expectedCommand = expectedCommand;
			this.expectedUi = expectedUi;
			this.beforeCalled = false;
			this.called = false;
		}

		@Override
		public void onBeforeExecCommand(Editor sender, String command,
				boolean ui, ExecCommandContextObject context)
		{
			assertEquals(this.editor, editor);
			assertFalse(this.beforeCalled);
			assertEquals(this.expectedCommand, command);
			assertEquals(this.expectedUi, ui);
			this.beforeCalled = true;
		}

		@Override
		public void onExecCommand(Editor sender, String command, boolean ui)
		{
			assertEquals(this.editor, editor);
			assertFalse(this.called);
			assertEquals(this.expectedCommand, command);
			assertEquals(this.expectedUi, ui);
			this.called = true;
		}
	}
	
	private class TestInitHandlers
	implements InitHandler, BeforeRenderUIHandler, PostRenderHandler
	{
		private boolean initHandlerCalled, beforeRenderUIHandlerCalled, postRenderHandlerCalled;
		private Editor editor;

		public TestInitHandlers(Editor editor)
		{
			this.editor = editor;
		}

		@Override
		public void onInit(Editor editor)
		{
			assertEquals(this.editor, editor);
			this.initHandlerCalled = true;
		}

		@Override
		public void onBeforeRenderUI(Editor ed)
		{
			assertEquals(this.editor, editor);
			this.beforeRenderUIHandlerCalled = true;
		}
		
		@Override
		public void onPostRender(Editor ed)
		{
			assertEquals(this.editor, editor);
			this.postRenderHandlerCalled = true;
		}		
	}
	
	private class TestUndoRedoHandlers
		implements UndoHandler, RedoHandler, ChangeHandler
	{
		private boolean undoCalled, redoCalled, changeCalled;
		private Editor editor;
		private UndoManager um;

		private UndoLevel undoLevel, redoLevel, changeLevel;

		public TestUndoRedoHandlers(Editor editor, UndoManager um)
		{
			this.editor = editor;
			this.um = um;
		}

		@Override
		public void onRedo(Editor sender, UndoLevel level, UndoManager um)
		{
			assertFalse(this.redoCalled);
			assertEquals(this.editor, sender);
			assertEquals(this.um, um);
			
			this.redoLevel = level;
			this.redoCalled = true;
		}

		@Override
		public void onUndo(Editor sender, UndoLevel level, UndoManager um)
		{
			assertFalse(this.undoCalled);
			assertEquals(this.editor, sender);
			assertEquals(this.um, um);
			
			this.undoLevel = level; 
			this.undoCalled = true;
		}
		
		@Override
		public void onChange(Editor sender, UndoLevel level, UndoManager um)
		{
			assertFalse(this.changeCalled);
			assertEquals(this.editor, sender);
			assertEquals(this.um, um);
			
			this.changeLevel = level;
			this.changeCalled = true;
		}
	}
	
	private class TestFocusHandlers
		implements ActivateHandler, DeactivateHandler
	{
		private Editor newEditor;
		private Editor oldEditor;
		private boolean activateCalled, deactivateCalled;

		public TestFocusHandlers(Editor newEditor, Editor oldEditor)
		{
			this.newEditor = newEditor;
			this.oldEditor = oldEditor;
		}

		@Override
		public void onActivate(Editor editor, Editor previous)
		{
			assertFalse(this.activateCalled);
			assertEquals(this.newEditor, editor);
			assertEquals(this.oldEditor, previous);

			this.activateCalled = true;
		}
		
		@Override
		public void onDeactivate(Editor ed, Editor newEd)
		{
			assertFalse(this.deactivateCalled);
			assertEquals(this.newEditor, ed);
			assertEquals(this.oldEditor, newEd);
			
			this.deactivateCalled = true;
		}
	}
	
	private class TestSetContentHandlers
		implements BeforeSetContentHandler, SetContentHandler
	{
		private Editor editor;
		private String expectedContent, overrideContent, finalContent;
		private ContentFormat expectedFormat;
		private boolean beforeSetCalled, setCalled;

		public TestSetContentHandlers(Editor editor, ContentFormat expectedFormat, String expectedContent, String overrideContent, String finalContent)
		{
			this.editor = editor;
			this.expectedContent = expectedContent;
			this.overrideContent = overrideContent;
			this.expectedFormat = expectedFormat;
			this.finalContent = finalContent;
		}

		@Override
		public void onBeforeSetContent(Editor ed, ContentObject o)
		{
			// Handler should only be fired once.
			assertFalse(this.beforeSetCalled);

			// Editor should match the one we used to trigger the event.
			assertEquals(this.editor, ed);

			// Content that comes in should match what we set initially.
			assertEquals(this.expectedContent, o.getContent());

			// Format of content should match what we provided initially.
			assertEquals(this.expectedFormat, o.getFormat());

			// Make sure the custom property we set on content object carreid through to the handlers.
			assertTrue(o.getProperties().getProperty("customTestField", false));

			// Now, we're going to change the content to the override content we were provided. This is to check and ensure editor is picking it up correctly.
			o.setContent(this.overrideContent);
			
			this.beforeSetCalled = true;
		}
		
		@Override
		public void onSetContent(Editor sender, ContentObject co)
		{
			assertFalse(this.setCalled);
			assertEquals(this.editor, sender);
			assertEquals(this.overrideContent, co.getContent());
			assertEquals(this.expectedFormat, co.getFormat());
			assertTrue(co.getProperties().getProperty("customTestField", false));

			co.setContent(this.finalContent);

			this.setCalled = true;
		}
	}
	
	private class TestGetContentHandlers
		implements BeforeGetContentHandler, GetContentHandler
	{
		private Editor editor;
		private String expectedContent, overrideContent, finalContent;
		private boolean beforeGetCalled, getCalled;
		private ContentFormat expectedFormat;

		public TestGetContentHandlers(Editor editor, ContentFormat expectedFormat, String expectedContent, String overrideContent, String finalContent)
		{
			this.editor = editor;
			this.expectedContent = expectedContent;
			this.overrideContent = overrideContent;
			this.finalContent = finalContent;
			this.expectedFormat = expectedFormat;
		}

		@Override
		public void onBeforeGetContent(Editor ed, ContentObject o)
		{
			assertFalse(this.beforeGetCalled);
			assertEquals(this.editor, ed);
			assertEquals(this.expectedFormat, o.getFormat());
			assertEquals(this.expectedContent, o.getContent());
			assertTrue(o.getProperties().getProperty("customTestField", false));
			
			o.setContent(this.overrideContent);

			this.beforeGetCalled = true;
		}

		@Override
		public void onGetContent(Editor ed, ContentObject co)
		{
			assertFalse(this.getCalled);
			assertEquals(this.editor, ed);
			assertEquals(this.expectedFormat, co.getFormat());
			assertEquals(this.overrideContent, co.getContent());
			assertTrue(co.getProperties().getProperty("customTestField", false));
			
			co.setContent(this.finalContent);
			
			this.getCalled = true;
		}
	}
	
	private class TestClickHandlers
		implements ClickHandler, MouseDownHandler, MouseUpHandler
	{
		private Editor editor;
		private boolean clickCalled, downCalled, upCalled;

		public TestClickHandlers(Editor editor)
		{
			this.editor = editor;
		}

		@Override
		public void onClick(Editor ed, NativeEvent event)
		{
			assertFalse(this.clickCalled);
			assertEquals(this.editor, ed);
			assertEquals("click", event.getType());
			
			this.clickCalled = true;
		}

		@Override
		public void onMouseDown(Editor editor, NativeEvent ev)
		{
			assertFalse(this.downCalled);
			assertEquals(this.editor, editor);
			assertEquals("mousedown", ev.getType());
			
			this.downCalled = true;
		}

		@Override
		public void onMouseUp(Editor sender, NativeEvent ev)
		{
			assertFalse(this.upCalled);
			assertEquals(this.editor, editor);
			assertEquals("mouseup", ev.getType());
			
			this.upCalled = true;
		}
	}
	
	private class TestDblClickHandler
		implements DblClickHandler
	{
		private Editor editor;
		private boolean called;

		public TestDblClickHandler(Editor editor)
		{
			this.editor = editor;
		}

		@Override
		public void onDblClick(Editor sender, NativeEvent ev)
		{
			assertFalse(this.called);
			assertEquals(this.editor, sender);
			assertEquals("dblclick", ev.getType());

			this.called = true;
		}
	}
	
	private class TestKeyHandlers
		implements KeyDownHandler, KeyPressHandler, KeyUpHandler
	{
		private Editor editor;
		private boolean downCalled, pressCalled, upCalled;

		public TestKeyHandlers(Editor editor)
		{
			this.editor = editor;
		}

		@Override
		public void onKeyDown(Editor sender, NativeEvent ev)
		{
			assertFalse(this.downCalled);
			assertEquals(this.editor, editor);
			assertEquals("keydown", ev.getType());

			this.downCalled = true;
		}

		@Override
		public void onKeyPress(Editor sender, NativeEvent ev)
		{
			assertFalse(this.pressCalled);
			assertEquals(this.editor, editor);
			assertEquals("keypress", ev.getType());
			
			this.pressCalled = true;
		}

		@Override
		public void onKeyUp(Editor sender, NativeEvent ev)
		{
			assertFalse(this.upCalled);
			assertEquals(this.editor, sender);
			assertEquals("keyup", ev.getType());

			this.upCalled = true;
		}
	}

	private class TestContextMenuHandler
		implements ContextMenuHandler
	{
		private Editor editor;
		private boolean called;

		public TestContextMenuHandler(Editor editor)
		{
			this.editor = editor;
		}

		@Override
		public void onContextMenu(Editor sender, NativeEvent ev)
		{
			assertFalse(this.called);
			assertEquals(this.editor, sender);
			assertEquals("contextmenu", ev.getType());

			ev.preventDefault();
			ev.stopPropagation();
			this.called = true;
		}
	}
	
	private class TestSetProgressStateHandler
		implements SetProgressStateHandler
	{
		private Editor editor;
		private boolean called;
		private boolean enabled;
		private float timeout;
		private Object o;

		public TestSetProgressStateHandler(Editor editor, boolean enabled, float timeout, Object o)
		{
			this.editor = editor;
			this.enabled = enabled;
			this.timeout = timeout;
			this.o = o;
		}

		@Override
		public void onSetProgressState(Editor sender, boolean enabled,
				float timeout, Object o)
		{
			assertFalse(this.called);
			assertEquals(this.editor, sender);
			assertEquals(this.enabled, enabled);
			assertEquals(this.timeout, timeout);
			assertEquals(this.o, o);

			this.called = true;
		}
	}
}
