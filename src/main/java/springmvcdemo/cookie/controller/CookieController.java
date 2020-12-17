package springmvcdemo.cookie.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import springmvcdemo.common.domain.Result;

@Controller
public class CookieController {

	
	@RequestMapping(value = "/cookie/add", method = RequestMethod.GET)
	@ResponseBody
	public Result AddCookie(HttpServletResponse response){
		Result result = new Result(true, "", null);
		try {
			Cookie cookie=new Cookie("name","hyman");//创建新cookie
			cookie.setMaxAge(5*60);// 设置存在时间为5分钟
			cookie.setPath("/");//设置作用域
			response.addCookie(cookie);//将cookie添加到response的cookie数组中返回给客户端
			result.setData(cookie);
		} catch (Exception ex) {
			result.setSucceed(false);
			result.setMsg(ex.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/cookie/get", method = RequestMethod.GET)
	@ResponseBody
	public Result getCookie(HttpServletRequest request){
		Result result = new Result(true, "", null);
		try {
			Cookie[] cookies = request.getCookies();//根据请求数据，找到cookie数组

	        if (null==cookies) {//如果没有cookie数组
	        	result.setData("没有cookie");
	        } else {
	        	result.setData(cookies);
	            for(Cookie cookie : cookies){
	                System.out.println("cookieName:"+cookie.getName()+",cookieValue:"+ cookie.getValue());
	            }
	        }
		} catch (Exception ex) {
			result.setSucceed(false);
			result.setMsg(ex.getMessage());
		}
		return result;
	}
}
