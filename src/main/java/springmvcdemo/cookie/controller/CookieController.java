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
			Cookie cookie=new Cookie("name","hyman");//������cookie
			cookie.setMaxAge(5*60);// ���ô���ʱ��Ϊ5����
			cookie.setPath("/");//����������
			response.addCookie(cookie);//��cookie��ӵ�response��cookie�����з��ظ��ͻ���
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
			Cookie[] cookies = request.getCookies();//�����������ݣ��ҵ�cookie����

	        if (null==cookies) {//���û��cookie����
	        	result.setData("û��cookie");
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
