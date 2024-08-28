package jp.co.internous.team2406.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.internous.team2406.model.domain.MstUser;
import jp.co.internous.team2406.model.form.UserForm;
import jp.co.internous.team2406.model.mapper.MstUserMapper;
import jp.co.internous.team2406.model.session.LoginSession;

/**
 * ユーザー登録に関する処理を行うコントローラー
 * @author インターノウス
 *
 */
@Controller
@RequestMapping("/team2406/user")
public class UserController {
		
	
	@Autowired
	private MstUserMapper userMapper;
	
	@Autowired
	private LoginSession loginSession;
	
	
	/**
	 * 新規ユーザー登録画面を初期表示する。
	 * @param m 画面表示用オブジェクト
	 * @return 新規ユーザー登録画面
	 */
	@RequestMapping("/")
	public String index(Model m) {
		m.addAttribute("loginSession",loginSession);
		return "register_user";
	}
	
	/**
	 * ユーザー名重複チェックを行う
	 * @param f ユーザーフォーム
	 * @return true:重複あり、false:重複なし
	 */
	@PostMapping("/duplicatedUserName")
	@ResponseBody
	public boolean duplicatedUserName(@RequestBody UserForm f) {
		int count = userMapper.findCountByUserName(f.getUserName()); 
		return count > 0; 
	}
	
	/**
	 * ユーザー情報登録を行う
	 * @param UserForm ユーザーフォーム
	 * @return true:登録成功、false:登録失敗
	 */
	@PostMapping("/register")
	@ResponseBody
	public boolean register(@RequestBody UserForm f) {		
		
		MstUser user = new MstUser();
		user.setUserName(f.getUserName());
		user.setPassword(f.getPassword());
		user.setFamilyName(f.getFamilyName());
		user.setFirstName(f.getFirstName());
		user.setFamilyNameKana(f.getFamilyNameKana());
		user.setFirstNameKana(f.getFirstNameKana());
		user.setGender(f.getGender());
		
		int insertCount = userMapper.insert(user);
		return insertCount > 0;
	}
}
