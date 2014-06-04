package com.example.megaalarmclock;

import java.util.Random;

public class AlarmKeyGenerator
{
	/*public String Generate(int length)
	{
		char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++)
		{
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		String output = sb.toString();
		return output;
	}*/
	
	public String Generate()
	{
		int min = 1;
		int max = 99999999;
		
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    
	    return String.valueOf(randomNum);
	}
}
