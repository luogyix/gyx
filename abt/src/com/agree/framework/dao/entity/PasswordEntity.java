package com.agree.framework.dao.entity;
/**
 * 
 * @ClassName: PasswordEntity 
 * @Description: 用户密码实体对象
 * @company agree   
 * @author haoruibing   
 * @date 2011-7-29 下午02:47:21 
 *
 */
public class PasswordEntity {
	
	private String userId;//用户id
	
	private String nowPassword;//现用密码
	
	private String updatePassword;//新密码
	
	private String reviewPassword;//新密码确认

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNowPassword() {
		return nowPassword;
	}

	public void setNowPassword(String nowPassword) {
		this.nowPassword = nowPassword;
	}

	public String getUpdatePassword() {
		return updatePassword;
	}

	public void setUpdatePassword(String updatePassword) {
		this.updatePassword = updatePassword;
	}

	public String getReviewPassword() {
		return reviewPassword;
	}

	public void setReviewPassword(String reviewPassword) {
		this.reviewPassword = reviewPassword;
	}

}
