 /*
 * Copyright(C) 2007 Agree Tech, All rights reserved.
 * 
 * Created on 2006-3-22   by c
 */

package com.agree.framework.communication.natp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Set;

 /**
 * <DL><DT>
 * �����ļ��s���.
 * </DT><p><DD>
 * ��ϸ����
 * </DD></DL><p>
 * 
 * <DL><DT><B>ʹ�÷���</B></DT><p><DD>
 * ʹ�÷���˵��
 * </DD></DL><p>
 * 
 * @author Xu Haibo
 * @author ��ͬ�Ƽ�
 * @version $Revision: 1.1 $ $Date: 2011/03/16 01:53:03 $ 
 */
@SuppressWarnings("rawtypes")
public class ProfileReader {

	
	private Hashtable sectionTable = new Hashtable();

	private static final String keyValueSeparators = "=: \t\r\n\f";

	private static final String strictKeyValueSeparators = "=:";

	private static final String whiteSpaceChars = " \t\r\n\f";

	@SuppressWarnings("unchecked")
	public synchronized void load(InputStream inStream) throws IOException {

		String sectionName="";
		BufferedReader in = new BufferedReader(new InputStreamReader(inStream,
				"8859_1"));
		while (true) {
			// Get next line
			String line = in.readLine();
			if (line == null)
				return;
			
			if (line.length() > 0) {
				// Find start of key
				int len = line.length();
				int keyStart;
				for (keyStart = 0; keyStart < len; keyStart++)
					if (whiteSpaceChars.indexOf(line.charAt(keyStart)) == -1)
						break;

				// Blank lines are ignored
				if (keyStart == len)
					continue;

				// Continue lines that end in slashes if they are not comments
				char firstChar = line.charAt(keyStart);
				
				//����һ���ַ���[�Ļ�,������һ���ַ���]�Ļ�(��ȥwhitespacechars)����ʾ������.
				if(firstChar=='['){
					int index = line.length() - 1;
					while ((index >= 0) && whiteSpaceChars.indexOf(line.charAt(index))!=-1){
						index--;
					}
					if(line.charAt(index)==']'){
						Hashtable sectionHashtable=new Hashtable();
						sectionName=line.substring(keyStart+1,index);
						sectionTable.put(sectionName, sectionHashtable);
						continue;
					}
					
				}
				
				/**
				 * ����Ĵ���ȡ��java.util.Properties#load()
				 */
				if ((firstChar != '#') && (firstChar != '!')) {
					while (continueLine(line)) {
						String nextLine = in.readLine();
						if (nextLine == null)
							nextLine = "";
						String loppedLine = line.substring(0, len - 1);
						// Advance beyond whitespace on new line
						int startIndex;
						for (startIndex = 0; startIndex < nextLine.length(); startIndex++)
							if (whiteSpaceChars.indexOf(nextLine
									.charAt(startIndex)) == -1)
								break;
						nextLine = nextLine.substring(startIndex, nextLine
								.length());
						line = new String(loppedLine + nextLine);
						len = line.length();
					}
					// Find separation between key and value
					int separatorIndex;
					for (separatorIndex = keyStart; separatorIndex < len; separatorIndex++) {
						char currentChar = line.charAt(separatorIndex);
						if (currentChar == '\\'){
							separatorIndex++;
						}
						else if (keyValueSeparators.indexOf(currentChar) != -1){
							break;
						}
							
					}

					// Skip over whitespace after key if any
					int valueIndex;
					for (valueIndex = separatorIndex; valueIndex < len; valueIndex++)
						if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1)
							break;

					// Skip over one non whitespace key value separators if any
					if (valueIndex < len)
						if (strictKeyValueSeparators.indexOf(line
								.charAt(valueIndex)) != -1)
							valueIndex++;

					// Skip over white space after other separators if any
					while (valueIndex < len) {
						if (whiteSpaceChars.indexOf(line.charAt(valueIndex)) == -1)
							break;
						valueIndex++;
					}
					String key = line.substring(keyStart, separatorIndex);
					
					
					int tmpIndex;
					for (tmpIndex= len-1;tmpIndex>separatorIndex;tmpIndex--){
						if (whiteSpaceChars.indexOf(line.charAt(tmpIndex)) == -1){
								break;	
						}
					}
					//trip whitespace after value
					String value = (separatorIndex < len) ? line.substring(
							valueIndex, tmpIndex+1) : "";	
					// Convert then store key and value
					
					key = loadConvert(key);
					value = loadConvert(value);
					Hashtable currentHashtable=(Hashtable)sectionTable.get(sectionName);
					currentHashtable.put(key, value);
					
					
				}
			}
		}
	}

	/*
	 * Returns true if the given line is a line that must be appended to the
	 * next line
	 */
	private boolean continueLine(String line) {
		int slashCount = 0;
		int index = line.length() - 1;
		while ((index >= 0) && (line.charAt(index--) == '\\'))
			slashCount++;
		return (slashCount % 2 == 1);
	}

	/*
	 * Converts encoded &#92;uxxxx to unicode chars and changes special saved
	 * chars to their original forms
	 */
	private String loadConvert(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);

		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed \\uxxxx encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}

	public String getProperty(String section, String key) {
		if (!sectionTable.containsKey(section)) {
			return null;
		}
		Hashtable table = (Hashtable) sectionTable.get(section);
		Object oval = table.get(key);
		String sval = (oval instanceof String) ? (String) oval : null;
		return sval;
	}

	public String getProperty(String section, String key, String defaultValue) {
		String val = getProperty(section, key);
		return (val == null) ? defaultValue : val;
	}
	
	
	public Hashtable getSectionAllProperty(String section){
		
		return (Hashtable)sectionTable.get(section);
		
	}
	
	public Hashtable getSectionAllProperty(String section,Hashtable defaultValue){
		Hashtable tmpTable=getSectionAllProperty(section);
		return (tmpTable==null?defaultValue:tmpTable);
	}
	
	public boolean isEmpty(){
		return this.sectionTable.isEmpty();
	}
	
	public void clear(){
		this.sectionTable.clear();
	}
	
	public Set getAllGroup(){
		
		return this.sectionTable.keySet();
	}
	
	public String toString()
	{
		return sectionTable.toString();
	}

}