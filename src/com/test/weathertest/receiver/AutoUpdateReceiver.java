package com.test.weathertest.receiver;

import com.test.weathertest.service.AutoUpdateService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoUpdateReceiver extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO �Զ����ɵķ������
		Intent i = new Intent(context,AutoUpdateService.class);
		context.startService(i);
	}
}