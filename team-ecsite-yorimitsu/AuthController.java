package jp.co.internous.wings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.wings.model.domain.MstUser;
import jp.co.internous.wings.model.form.UserForm;
import jp.co.internous.wings.model.mapper.MstUserMapper;
import jp.co.internous.wings.model.mapper.TblCartMapper;
import jp.co.internous.wings.model.session.LoginSession;

@Controller
@RequestMapping("/wings/auth")
public class AuthController {
	
	@Autowired
	private MstUserMapper mstUserMapper;
	
	@Autowired
	private TblCartMapper tblCartMapper;
	
	@Autowired
	private LoginSession loginSession;
	
	Gson gson = new Gson();
	
	@PostMapping("/login")
	@ResponseBody
	public String login(@RequestBody UserForm f, Model m) {
		MstUser user = mstUserMapper.findByUserNameAndPassword(f.getUserName(), f.getPassword());
		//認証
		if (user != null) {
			int userId = user.getId();
			int tempUserId = loginSession.getTempUserId();		
			String userName = user.getUserName();
			String password = user.getPassword();
			//カートに商品が入っている場合
			if(tempUserId != 0) {
				tblCartMapper.updateUserId(userId, tempUserId);
			}
			loginSession.setSession(userId, 0, userName, password, true);
		}
		return gson.toJson(user);
	}	
	
	@RequestMapping("/logout")
	@ResponseBody
	public String logout() {
		loginSession.setSession(0, 0, null, null, false);	
		return loginSession.getUserName();
	}	
	
	@PostMapping("/resetPassword")
	@ResponseBody
	public String resetPassword(@RequestBody UserForm f) {
		MstUser user = mstUserMapper.findByUserNameAndPassword(loginSession.getUserName(), f.getPassword());
		if (user == null) {
			return "現在のパスワードが正しくありません。";
		}
		String password = user.getPassword();
		String newPassword = f.getNewPassword();
		String newPasswordConfirm = f.getNewPasswordConfirm();
		if(password.equals(newPassword)){
			return "現在のパスワードと同一文字列が入力されました。";
		}
		if(!(newPassword.equals(newPasswordConfirm))){
			return "新パスワードと確認用パスワードが一致しません。";
		}
		mstUserMapper.updatePassword(user.getId(), f.getNewPassword());
		loginSession.setPassword(f.getNewPassword());
		return "パスワードが再設定されました。";
	}
}
