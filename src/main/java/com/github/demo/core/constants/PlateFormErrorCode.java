package com.github.demo.core.constants;

public enum PlateFormErrorCode {
	SYS_UNKOWN_ERROR("ERR_SYSTEM_000001", "操作失败，请稍后重试！"),
	SYS_PARAMETER_IS_NULL("ERR_SYSTEM_000002", "参数不能为空或缺少参数！"),
	SYS_PARAMETER_IS_ERROR("ERR_SYSTEM_000003", "参数错误！"),
	
	
	SERVICE_AUTH_PASSWORD_IS_NULL("SERVICE_ERR_000001","用户名或密码为空！"),
	SERVICE_AUTH_PASSWORD_IS_ERROR("SERVICE_ERR_000002","用户账号密码错误！"),
	
	SERVICE_AUTH_NOT_EXSITS_IS_ERROR("SERVICE_ERR_000003","资源不存在！不能继续操作！"),
	SERVICE_AUTH_STATUS_IS_ERROR("SERVICE_ERR_000004","资源状态错误！不能继续操作！"),
	SERVICE_ROLE_IS_USE_ERROR("SERVICE_ERR_000005","该角色不能删除，已被占用！"),

	AUTH_PWD_ERROR("SERVICE_ERR_000013","登录账号密码错误"),
	AUTH_LOGIN_FAILURE("SERVICE_ERR_000014","登录失败，请重新登录");
	private String key;
	private String value;

	private PlateFormErrorCode(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public static PlateFormErrorCode get(String key) {
		for (PlateFormErrorCode pair : values()) {
			if (pair.key.equals(key)) {
				return pair;
			}
		}
		return null;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
