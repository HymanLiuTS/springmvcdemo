package springmvcdemo.session.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import springmvcdemo.authentication.model.User;
import springmvcdemo.common.domain.Result;

@Controller
public class SessionController {

	@Autowired
	RedisTemplate redisTemplate;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/session/add", method = RequestMethod.GET)
	@ResponseBody
	public Result mylogin(HttpSession httpSession) {
		Result result = new Result(true, "", null);
		try {
			httpSession.setAttribute("openid", 123456);
			redisTemplate.opsForValue().set(httpSession.getId().toString(), 123456);
			result.setData(httpSession.getId());
		} catch (Exception ex) {
			result.setSucceed(false);
			result.setMsg(ex.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/session/get", method = RequestMethod.GET)
	@ResponseBody
	public Result mylogout(HttpSession httpSession) {
		Result result = new Result(true, "", null);
		try {
			Integer openid1 = (Integer) httpSession.getAttribute("openid");
			Integer openid2 = (Integer) redisTemplate.opsForValue().get(httpSession.getId().toString());
			result.setData(String.format("openid1:%d,openid2:%d", openid1, openid2));
		} catch (Exception ex) {
			result.setSucceed(false);
			result.setMsg(ex.getMessage());
		}
		return result;
	}

}
