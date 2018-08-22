package com.agree.framework.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.agree.util.CommonData;




public class LoadKeyWord {
	@SuppressWarnings("rawtypes")
	private  List _elements;
	public static final String GROUP_FLAG = "[组名]";
	private static final Log logger=LogFactory.getLog(LoadKeyWord.class);
	@SuppressWarnings("rawtypes")
	public LoadKeyWord() {
		
		_elements = new Vector();
		
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void load(String realpath){
		realpath=realpath+"WEB-INF\\classes\\resources\\messages\\selectCodes.properties";
		try{
			FileInputStream fis=new FileInputStream(new File(realpath));
			BufferedReader in = new BufferedReader(new InputStreamReader(fis,"ISO-8859-1"));
			_elements = new Vector();
			//pr.load(in);
			do {
				int len;
				int keyStart;
				char firstChar;
				String line;
				do {
					do {
						do {
							line = in.readLine();
							if (line == null)
								return;
						} while (line.length() <= 0);
						len = line.length();
						for (keyStart = 0; keyStart < len; keyStart++)
							if (" \t\r\n\f".indexOf(line.charAt(keyStart)) == -1)
								break;
		
					} while (keyStart == len);
					firstChar = line.charAt(keyStart);
				} while (firstChar == '#' || firstChar == '!');
				while (continueLine(line)) {
					String nextLine = in.readLine();
					if (nextLine == null)
						nextLine = "";
					String loppedLine = line.substring(0, len - 1);
					int startIndex;
					for (startIndex = 0; startIndex < nextLine.length(); startIndex++)
						if (" \t\r\n\f".indexOf(nextLine.charAt(startIndex)) == -1)
							break;
		
					nextLine = nextLine.substring(startIndex, nextLine.length());
					line = new String(loppedLine + nextLine);
					len = line.length();
				}
				int separatorIndex;
				for (separatorIndex = keyStart; separatorIndex < len; separatorIndex++) {
					char currentChar = line.charAt(separatorIndex);
					if (currentChar == '\\') {
						separatorIndex++;
						continue;
					}
					if ("=: \t\r\n\f".indexOf(currentChar) != -1)
						break;
				}
		
				int valueIndex;
				for (valueIndex = separatorIndex; valueIndex < len; valueIndex++)
					if (" \t\r\n\f".indexOf(line.charAt(valueIndex)) == -1)
						break;
		
				if (valueIndex < len && "=:".indexOf(line.charAt(valueIndex)) != -1)
					valueIndex++;
				for (; valueIndex < len; valueIndex++)
					if (" \t\r\n\f".indexOf(line.charAt(valueIndex)) == -1)
						break;
		
				String key = line.substring(keyStart, separatorIndex);
				String value = separatorIndex >= len ? "" : line.substring(
						valueIndex, len);
				key = loadConvert(key);
				value = loadConvert(value);
				_elements.add(new String[] { key, value });
			}while(true);
		}catch(Exception e){
				logger.error(e.getMessage(),e);
		}
	}
	@SuppressWarnings("rawtypes")
	public List get_elements() {
		return _elements;
	}
	@SuppressWarnings("rawtypes")
	public void set_elements(List elements) {
		_elements = elements;
	}
	private  boolean continueLine(String line) {
		int slashCount = 0;
		for (int index = line.length() - 1; index >= 0
				&& line.charAt(index--) == '\\';)
			slashCount++;

		return slashCount % 2 == 1;
	}
	private  String loadConvert(String theString) {
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			char aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case 48: // '0'
						case 49: // '1'
						case 50: // '2'
						case 51: // '3'
						case 52: // '4'
						case 53: // '5'
						case 54: // '6'
						case 55: // '7'
						case 56: // '8'
						case 57: // '9'
							value = ((value << 4) + aChar) - 48;
							break;

						case 97: // 'a'
						case 98: // 'b'
						case 99: // 'c'
						case 100: // 'd'
						case 101: // 'e'
						case 102: // 'f'
							value = ((value << 4) + 10 + aChar) - 97;
							break;

						case 65: // 'A'
						case 66: // 'B'
						case 67: // 'C'
						case 68: // 'D'
						case 69: // 'E'
						case 70: // 'F'
							value = ((value << 4) + 10 + aChar) - 65;
							break;

						case 58: // ':'
						case 59: // ';'
						case 60: // '<'
						case 61: // '='
						case 62: // '>'
						case 63: // '?'
						case 64: // '@'
						case 71: // 'G'
						case 72: // 'H'
						case 73: // 'I'
						case 74: // 'J'
						case 75: // 'K'
						case 76: // 'L'
						case 77: // 'M'
						case 78: // 'N'
						case 79: // 'O'
						case 80: // 'P'
						case 81: // 'Q'
						case 82: // 'R'
						case 83: // 'S'
						case 84: // 'T'
						case 85: // 'U'
						case 86: // 'V'
						case 87: // 'W'
						case 88: // 'X'
						case 89: // 'Y'
						case 90: // 'Z'
						case 91: // '['
						case 92: // '\\'
						case 93: // ']'
						case 94: // '^'
						case 95: // '_'
						case 96: // '`'
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
			} else {
				outBuffer.append(aChar);
			}
		}

		return outBuffer.toString();
	}
	@SuppressWarnings("rawtypes")
	public Map<String,List<CommonData>> getSelectCodes(){
		HashMap<String,List<CommonData>> map=new HashMap<String,List<CommonData>>();
		String groupName = null; // 当前组的名称
        List<CommonData> groupData = null; // 组数据
        int groupAmount = 0; // 组数
		List data = this.get_elements();
        for (int i = 0; i < data.size(); ++i)
        {
            String[] row = (String[]) data.get(i);
            String key = row[0].trim();
            String value = row[1];

            if (key.startsWith(GROUP_FLAG))
            {
                // 当前行为组标题行
                if (groupName != null)
                {
                    // 已经存在当前组，否则为第一组
                	map.put(groupName, groupData);
                    groupAmount++; // 累加组数
                }

                // 获取组名
                groupName = value;
                groupData = new ArrayList<CommonData>();
            } else
            {
                if (groupData == null)
                    continue;

                groupData.add(new CommonData(key, value));
            }
        }
        if (groupName != null)
        	map.put(groupName, groupData);

        logger.info("加载下拉框数据成功，共加载 " + groupAmount + " 组数据");
		return map;
	}
}
