package com.kamaz.payload.request;

import javax.validation.constraints.NotBlank;

public class UserPositionRequest {

	@NotBlank
	private Long userid;

	@NotBlank
	private Long paramid;

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getParamid() {
		return paramid;
	}

	public void setParamid(Long paramid) {
		this.paramid = paramid;
	}

}
