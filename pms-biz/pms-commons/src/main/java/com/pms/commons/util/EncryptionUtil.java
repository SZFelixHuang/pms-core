package com.pms.commons.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil
{
	private static MessageDigest md;
	
	static
	{
		try
		{
			md = MessageDigest.getInstance("md5");
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
	}
}