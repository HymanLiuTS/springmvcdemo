package springmvcdemo.common.domain;

/**
 * 
 * 项目名称：springmvcdemo 类名称：Result 类描述：控制器统一的返回的结果 创建人：Hyman 创建时间：2019年3月28日
 * 上午10:56:37
 * 
 * @version
 */
public class Result {
	private Boolean succeed;
	private String msg;
	private Object data;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Boolean getSucceed() {
		return succeed;
	}

	public void setSucceed(Boolean succeed) {
		this.succeed = succeed;
	}

	public Result(Boolean succeed, String msg, Object data) {
		this.succeed = succeed;
		this.msg = msg;
		this.data = data;
	}
}
