package com.site2go.gwtmce.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;
import com.site2go.gwtmce.client.editor.Editor;
import com.site2go.gwtmce.client.editor.EditorSettings;
import com.site2go.gwtmce.client.editor.events.HandlerRegistration;
import com.site2go.gwtmce.client.util.TinyMCE;
import com.site2go.gwtmce.client.util.events.AddEditorHandler;
import com.site2go.gwtmce.client.util.events.RemoveEditorHandler;

/**
 * This test case covers testing of various aspects of the global {@link TinyMCE} instance.
 */
public class TinyMCETest
	extends GWTTestCase
{
	@Override
	public String getModuleName()
	{
		return "com.site2go.gwtmce.GWTMCETest";
	}

	/**
	 * Test the add/remove editor event handlers.
	 */
	public void testEventHandlers()
	{
		TinyMCE tinymce = TinyMCE.get();
		Document doc = Document.get();

		// Create a node to be cannibalized by editor.
		Element el = doc.createDivElement();
		el.setInnerHTML("<b>Test content!</b>");
		el.setId("mceTestEl");
		doc.getBody().appendChild(el);

		final TestAddRemoveEditorHandlers handlers = new TestAddRemoveEditorHandlers();

		final HandlerRegistration addReg = tinymce.addAddEditorHandler(handlers);
		final HandlerRegistration removeReg = tinymce.addRemoveEditorHandler(handlers);
		
		EditorSettings settings = new EditorSettings();
		settings.setTheme("simple");
		final Editor editor = new Editor(el.getId(), settings);

		final Timer checkRemoveTimer = new Timer()
		{
			@Override
			public void run()
			{
				if(handlers.removeCalled)
				{
					assertSame(editor, handlers.removeEditor);
					this.cancel();
					addReg.removeHandler();
					removeReg.removeHandler();

					new Timer()
					{
						@Override
						public void run()
						{
							finishTest();
						}
					}.schedule(500);
				}
			}
		};

		final Timer checkAddTimer = new Timer()
		{
			@Override
			public void run()
			{
				if(handlers.addCalled)
				{
					assertSame(editor, handlers.addEditor);
					
					// Give the editor some time to breathe before we destroy iy.
					new Timer()
					{
						@Override
						public void run()
						{
							editor.remove();
							checkRemoveTimer.scheduleRepeating(10);
						}
					}.schedule(500);
					this.cancel();
				}
			}
		};

		editor.render();
		checkAddTimer.scheduleRepeating(10);

		delayTestFinish(3000);
	}

	private class TestAddRemoveEditorHandlers
		implements AddEditorHandler, RemoveEditorHandler
	{
		private Editor addEditor, removeEditor;
		private boolean addCalled, removeCalled;

		public TestAddRemoveEditorHandlers()
		{
		}

		@Override
		public void onAddEditor(Editor editor)
		{
			assertFalse(this.addCalled);

			this.addEditor = editor;
			this.addCalled = true;
		}

		@Override
		public void onRemoveEditor(Editor editor)
		{
			assertFalse(this.removeCalled);

			this.removeEditor = editor;
			this.removeCalled = true;
		}
	}
}
