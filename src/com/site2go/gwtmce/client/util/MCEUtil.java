package com.site2go.gwtmce.client.util;

public class MCEUtil
{
	public static final String implode(Object[] objs, String delim)
	{
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		
		for(Object obj : objs)
		{
			if(first) first = false;
			else sb.append(delim);
			
			sb.append(obj.toString());
		}

		return sb.toString();
	}
}
