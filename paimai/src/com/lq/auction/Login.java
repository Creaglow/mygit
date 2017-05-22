package com.lq.auction;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.example.paimai.R;
import com.lq.client.util.HttpUtil;

import android.R.string;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager.Query;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.DialerKeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {
	EditText etName, etPass;
	Button bnLogin, bnCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		etName = (EditText) findViewById(R.id.userEditText);
		etPass = (EditText) findViewById(R.id.pwdEditText);

		bnLogin = (Button) findViewById(R.id.bnLogin);
		bnLogin = (Button) findViewById(R.id.bnCancel);

		bnLogin.setOnClickListener(new HomeListener(this));
		bnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (validate()) {
					if (loginPro()) {
						Intent intent = new Intent(Login.this,
								AuctionClientActivity.class);
						startActivity(intent);
						finish();

					} else {
						DialogUtil.showDialog(Login.this
								, "用户名称或密码错误，请重新输入！",
								false);
					}
				}

			}

		});

	}

	private boolean loginPro() {
		//获取用户输入的用户名、密码
		String username = etName.getText().toString();
		String pwd = etPass.getText().toString();
		JSONObject jsonObject;
		try {
			jsonObject = query(username,pwd);  //查询用户名、密码是否正确
			//如果userId大于0
			if (jsonObject.getInt("userId")>0) {
				return true;
			}
		} catch (Exception e) {
			DialogUtil.showDialog(this
					, "服务器异常，请稍后再试", false);
			e.printStackTrace();
		}
		return false;
	}

	private boolean validate() {
		String username = etName.getText().toString().trim();
		if (username.equals("")) {
			DialogUtil.showDialog(this, "用户账户必填", false);
			return false;
		}
		String pwd = etPass.getText().toString().trim();  //trim():去掉前后空格
		if (pwd.equals("")) {
			DialogUtil.showDialog(this, "用户密码必填", false);
			return false;
		}
		
		return true;
	}
	
	private JSONObject query(String username, String password) 
			throws Exception{
		//使用Map封装参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("user", username);
		map.put("pass", password);
		
		//定义发送请求的URL
		String url = HttpUtil.BASE_URL + "login.jsp";
		
		return new JSONObject(HttpUtil.postReuest(url, map));
	}

}
