package com.liferay.staging.taglib.exception;

public class ExportImportEntityException extends Exception {

	public static final int TYPE_INVALID_COMMAND = 1;

	public ExportImportEntityException(int type) {
		_type = type;
	}

	public int getType() {
		return _type;
	}

	private int _type;
}
