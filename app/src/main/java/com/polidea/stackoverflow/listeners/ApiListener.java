package com.polidea.stackoverflow.listeners;

public interface ApiListener extends ConnectionListener {
	public void onError(String errorCode, String errorMsg);
}
