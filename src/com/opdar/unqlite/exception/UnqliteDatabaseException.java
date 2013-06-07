package com.opdar.unqlite.exception;

public class UnqliteDatabaseException extends Exception{
	private static final long serialVersionUID = 4071899576932015466L;

	public static final int IO_ERR = -2;
	public static final int ITEM_NOT_FOUND = -6;
	public static final int CORRUPT = -24;
	public static final int READ_ONLY = -75;
	
	public UnqliteDatabaseException(int err) {
		super("err code : "+err+"  "+getErrorMessage(err));
		
	}
	
	private static String getErrorMessage(int err){
		switch (err) {
		case CORRUPT:
			return "中断操作！";
		case IO_ERR:
			return "数据库可能未被创建！请先创建数据库!";
		case ITEM_NOT_FOUND:
			return "未找到该字段数据！";
		case READ_ONLY:
			return "当前数据库处于只读模式！";
		default:
			return "";
		}
	}
	
}
