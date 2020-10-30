package com.aidoudong.common.cache;

public class CacheMessage {
	
	public interface Type {
		int PUT = 10;
		int PUTIFABSENT = 11;
		int REMOVE = 20;
		int CLEAN = 30;
	}
	
	private Object key;
	private Object value;
	private int type;
	public CacheMessage() {
		super();
	}
	public CacheMessage(Object key) {
		super();
		this.key = key;
	}
	public CacheMessage(Object key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}
	public CacheMessage(Object key, Object value, int type) {
		super();
		this.key = key;
		this.value = value;
		this.type = type;
	}
	public Object getKey() {
		return key;
	}
	public void setKey(Object key) {
		this.key = key;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	
}
