package com.aidoudong.properties;

public class Authentication {
	// application.yml 没配置取默认值
	private String loginPage = "/login/page";
	private String imageCodeUrl = "/code/image";
	private String mobilePageUrl = "/mobile/page";
	private String mobileImageCodeUrl = "/code/mobile";
	private String loginProcessingUrl = "/login/form";
	private String mobileLoginProcessingUrl = "/mobie/form";
	private String usernameParameter = "name";
	private String passwordParameter = "pwd";
	private String[] staticPaths = {"/dist/**", "/modules/**", "/plugins/**"};
	private Integer tokenValiditySeconds = 604800;
	
	/**
	 * 认证响应的类型： JSON/REDIRECT
	 */
	private LoginResponseType loginType = LoginResponseType.REDIRECT;
	
	public String getLoginPage() {
		return loginPage;
	}
	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}
	public String getLoginProcessingUrl() {
		return loginProcessingUrl;
	}
	public void setLoginProcessingUrl(String loginProcessingUrl) {
		this.loginProcessingUrl = loginProcessingUrl;
	}
	public String getUsernameParameter() {
		return usernameParameter;
	}
	public void setUsernameParameter(String usernameParameter) {
		this.usernameParameter = usernameParameter;
	}
	public String getPasswordParameter() {
		return passwordParameter;
	}
	public void setPasswordParameter(String passwordParameter) {
		this.passwordParameter = passwordParameter;
	}
	public String[] getStaticPaths() {
		return staticPaths;
	}
	public void setStaticPaths(String[] staticPaths) {
		this.staticPaths = staticPaths;
	}
	public LoginResponseType getLoginType() {
		return loginType;
	}
	public void setLoginType(LoginResponseType loginType) {
		this.loginType = loginType;
	}
	public String getImageCodeUrl() {
		return imageCodeUrl;
	}
	public void setImageCodeUrl(String imageCodeUrl) {
		this.imageCodeUrl = imageCodeUrl;
	}
	public String getMobilePageUrl() {
		return mobilePageUrl;
	}
	public void setMobilePageUrl(String mobilePageUrl) {
		this.mobilePageUrl = mobilePageUrl;
	}
	public String getMobileImageCodeUrl() {
		return mobileImageCodeUrl;
	}
	public void setMobileImageCodeUrl(String mobileImageCodeUrl) {
		this.mobileImageCodeUrl = mobileImageCodeUrl;
	}
	public String getMobileLoginProcessingUrl() {
		return mobileLoginProcessingUrl;
	}
	public void setMobileLoginProcessingUrl(String mobileLoginProcessingUrl) {
		this.mobileLoginProcessingUrl = mobileLoginProcessingUrl;
	}
	public Integer getTokenValiditySeconds() {
		return tokenValiditySeconds;
	}
	public void setTokenValiditySeconds(Integer tokenValiditySeconds) {
		this.tokenValiditySeconds = tokenValiditySeconds;
	}
	
}
