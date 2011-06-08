package com.site2go.gwtmce.client.util;

/**
 * Some types don't convert well in JSNI bridge when we treat them just as an
 * object. This class creates some JS functions to ensures Java primitives that
 * have been wrapped in their class equivalents get marshaled correctly and vice
 * versa.
 */
public class TypeMarshaller
{
	private static boolean hasInit = false;

	public static final void init()
	{
		if(hasInit) return;
		hasInit = true;

		initImpl();
	}

	private static final native void initImpl()
	/*-{
		$wnd._gwtMarshalTypeFromJava = function(val)
		{
			if(@com.site2go.gwt.util.client.TypeMarshaller::isBoolean(Ljava/lang/Object;)(val))
			{
				val = @com.site2go.gwt.util.client.TypeMarshaller::convertToBoolean(Ljava/lang/Boolean;)(val);
			}

			return val;
		};

		$wnd._gwtMarshalTypeFromJS = function(val)
		{
			if(typeof(val) == "undefined") return null;

			var valType = typeof(val);
			if(valType == "boolean")
				val = @java.lang.Boolean::new(Z)(val);
			else if(valType == "number")
			{
				if(parseInt(val) == val)
					val = @java.lang.Integer::new(I)(val);
				else
					val = @java.lang.Double::new(D)(val);
			}

			return val;
		};

	}-*/;

	private static final boolean isBoolean(Object obj)
	{
		return (obj instanceof Boolean);
	}

	private static final boolean convertToBoolean(Boolean obj)
	{
		return obj.booleanValue();
	}
}
