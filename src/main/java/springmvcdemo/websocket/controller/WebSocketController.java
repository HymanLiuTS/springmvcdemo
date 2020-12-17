package springmvcdemo.websocket.controller;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import springmvcdemo.business.model.Student;
import springmvcdemo.common.domain.Result;
import springmvcdemo.courseselection.service.CourseSelectionService;
import springmvcdemo.websocket.Main;
import springmvcdemo.websocket.MyWebSocketClient;
import springmvcdemo.websocket.WebSocketServer;

@Controller
public class WebSocketController {

	@RequestMapping(value = "/websocket/{msg}", method = RequestMethod.GET)
	@ResponseBody
	public Result addStudent(@PathVariable String msg) {
		Result result = new Result(true, "", null);
		try {
			WebSocketServer.sendMessageAll(msg);
		} catch (Exception ex) {
			result.setSucceed(false);
			result.setMsg(ex.getMessage());
		}
		return result;
	}

}
