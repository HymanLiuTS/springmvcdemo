package springmvcdemo.courseselection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import springmvcdemo.authentication.model.User;
import springmvcdemo.authentication.service.UserService;
import springmvcdemo.business.model.Student;
import springmvcdemo.common.domain.Result;
import springmvcdemo.courseselection.service.CourseSelectionService;

@Controller
public class CourseSelectionController {

	@Autowired
	@Qualifier("courseSelectionService")
	private CourseSelectionService courseSelectionService;
	
	@RequestMapping(value = "/student", method = RequestMethod.POST)
	@ResponseBody
	public Result addStudent(@RequestBody Student student) {
		Result result = new Result(true, "", null);
		try {
			student = courseSelectionService.saveStudent(student);
			result.setData(student);
		} catch (Exception ex) {
			result.setSucceed(false);
			result.setMsg(ex.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/student", method = RequestMethod.GET)
	@ResponseBody
	public Result addStudent(Long id) {
		Result result = new Result(true, "", null);
		try {
			Student student = courseSelectionService.findById(id);
			result.setData(student);
		} catch (Exception ex) {
			result.setSucceed(false);
			result.setMsg(ex.getMessage());
		}
		return result;
	}
}
