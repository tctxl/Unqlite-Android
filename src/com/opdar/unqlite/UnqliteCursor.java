package com.opdar.unqlite;

public class UnqliteCursor {
	public UnqliteCursor() {
	}
	
	public String getKey(){
		int length = keyLength();
		byte[] keybuf = new byte[length];
		key(keybuf, length);
		return new String(keybuf);
	}
	
	public String getValue(){
		int length = dataLength();
		byte[] valuebuf = new byte[length];
		value(valuebuf, length);
		return new String(valuebuf);
	}
	public boolean isVaild(){
		int rc = valid();
		return rc != 0;
	}
	public native int moveToFrist();
	public native int moveToLast();
	public native int next();
	public native int previous();
	public native int delete();
	public native int valid();
	public native int keyLength();
	public native int dataLength();
	public native byte[] key(byte[] keybuf,int length);
	public native byte[] value(byte[] valuebuf,int length);
	public native int close();
}
