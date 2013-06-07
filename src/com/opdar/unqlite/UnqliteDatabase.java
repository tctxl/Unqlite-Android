package com.opdar.unqlite;

import com.opdar.unqlite.exception.UnqliteDatabaseException;


public class UnqliteDatabase {

	public static final int OPEN_READONLY = 0x00000001;
	public static final int OPEN_READWRITE = 0x00000002;
	public static final int OPEN_CREATE = 0x00000004;
	public static final int OPEN_EXCLUSIVE = 0x00000008;
	public static final int OPEN_TEMP_DB = 0x00000010;
	public static final int OPEN_NOMUTEX = 0x00000020;
	public static final int OPEN_OMIT_JOURNALING = 0x00000040;
	public static final int OPEN_IN_MEMORY = 0x00000080;
	public static final int OPEN_MMAP = 0x00000100;

	public static final int OK = 0;
	public static final int DONE = -28;

	public enum Match {
		EXACT(1), LE(2), GE(3);
		int id;

		private Match(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}

	public boolean onCreateDatabase(String dbName, int iMode) {
		int result = createDb(dbName, iMode, "1");
		return result == OK;
	}

	public boolean replaceValue(String key, String value) {
		int result = replace(key, value);
		return result == OK;
	}

	public boolean appendValue(String key, String value) {
		int rc = append(key, value);
		System.out.println("rc : "+rc);
		return rc == OK;
	}

	public boolean closeDatabase() {
		int result = close();
		return result == OK;
	}
	
	public UnqliteCursor query() throws UnqliteDatabaseException {
		int rc = queryinit();
		if(rc == OK){
			UnqliteCursor cursor = new UnqliteCursor();
			cursor.moveToFrist();
			return cursor;
		}else{
			throw new UnqliteDatabaseException(rc);
		}
	}

	public UnqliteCursor query(String key) throws UnqliteDatabaseException {
		return query(key, Match.EXACT);
	}

	public UnqliteCursor query(String key, Match match) throws UnqliteDatabaseException {
		int result = queryforMatch(key, match.getId()); 
		if(result == OK){
			UnqliteCursor cursor = new UnqliteCursor();
			return cursor;
		}else{
			throw new UnqliteDatabaseException(result);
		}
	}

	public UnqliteDatabase() {
		
	}
	
	static {
		System.loadLibrary("unqlite");
	}
	public native String stringFromJNI();

	private native int queryinit();;
	
	private native int queryforMatch(String key, int match);;

	private native int createDb(String dbName, int iMode, String version);

	public native int open(String dbName, int iMode);

	private native int append(String key ,String value);

	private native int replace(String key, String value);
	
	public native int close();
}
