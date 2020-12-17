package springmvcdemo.common.domain;

/**
 * 
 * ��Ŀ���ƣ�springmvcdemo �����ƣ�Result ��������������ͳһ�ķ��صĽ�� �����ˣ�Hyman ����ʱ�䣺2019��3��28��
 * ����10:56:37
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
