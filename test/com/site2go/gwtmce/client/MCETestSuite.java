package com.site2go.gwtmce.client;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.google.gwt.junit.tools.GWTTestSuite;

public class MCETestSuite
	extends GWTTestSuite
{
	public static Test suite()
	{
		TestSuite suite = new TestSuite("GWTMCE Test Suite");

		suite.addTestSuite(TinyMCETest.class);
		suite.addTestSuite(EditorTest.class);
		suite.addTestSuite(ThemeTest.class);
		suite.addTestSuite(PluginTest.class);

		return suite;
	}
}
