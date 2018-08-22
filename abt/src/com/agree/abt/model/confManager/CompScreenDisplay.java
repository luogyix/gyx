package com.agree.abt.model.confManager;

import java.io.Serializable;

public class CompScreenDisplay implements Serializable{
	private static final long serialVersionUID = 6284196585197059220L;
	private String comp_screen_id;
	private String branch;
	private String default_flag;
	private String free_style;
	private String free_content;
	private String free_speed;
	private String call_content;
	private String call_style;
	private String call_speed;
	private String pause_content;
	private String pause_style;
	private String pause_speed;
	private String serve_content;
	private String serve_style;
	private String serve_speed;
	
	
	public String getServe_content() {
		return serve_content;
	}


	public void setServe_content(String serve_content) {
		this.serve_content = serve_content;
	}


	public String getServe_style() {
		return serve_style;
	}


	public void setServe_style(String serve_style) {
		this.serve_style = serve_style;
	}


	public String getServe_speed() {
		return serve_speed;
	}


	public void setServe_speed(String serve_speed) {
		this.serve_speed = serve_speed;
	}


	
	public String getComp_screen_id() {
		return comp_screen_id;
	}


	public void setComp_screen_id(String comp_screen_id) {
		this.comp_screen_id = comp_screen_id;
	}


	public String getBranch() {
		return branch;
	}


	public void setBranch(String branch) {
		this.branch = branch;
	}


	public String getDefault_flag() {
		return default_flag;
	}


	public void setDefault_flag(String default_flag) {
		this.default_flag = default_flag;
	}


	public String getFree_style() {
		return free_style;
	}


	public void setFree_style(String free_style) {
		this.free_style = free_style;
	}


	public String getFree_content() {
		return free_content;
	}


	public void setFree_content(String free_content) {
		this.free_content = free_content;
	}


	public String getFree_speed() {
		return free_speed;
	}


	public void setFree_speed(String free_speed) {
		this.free_speed = free_speed;
	}


	public String getCall_content() {
		return call_content;
	}


	public void setCall_content(String call_content) {
		this.call_content = call_content;
	}


	public String getCall_style() {
		return call_style;
	}


	public void setCall_style(String call_style) {
		this.call_style = call_style;
	}


	public String getCall_speed() {
		return call_speed;
	}


	public void setCall_speed(String call_speed) {
		this.call_speed = call_speed;
	}


	public String getPause_content() {
		return pause_content;
	}


	public void setPause_content(String pause_content) {
		this.pause_content = pause_content;
	}


	public String getPause_style() {
		return pause_style;
	}


	public void setPause_style(String pause_style) {
		this.pause_style = pause_style;
	}


	public String getPause_speed() {
		return pause_speed;
	}


	public void setPause_speed(String pause_speed) {
		this.pause_speed = pause_speed;
	}

	public CompScreenDisplay() {
		super();
	}
	
	
	public CompScreenDisplay(String comp_screen_id,String branch,String default_flag, String free_style,
 String free_content, String free_speed, String call_content, String call_style, String call_speed,
 String pause_content, String pause_style, String pause_speed, String serve_content, String serve_style, String serve_speed) {
		super();
		this.branch=branch;
		this.comp_screen_id=comp_screen_id;
		this.default_flag=default_flag;
		this.call_content=call_content;
		this.call_speed=call_speed;
		this.call_style=call_style;
		this.free_content=free_content;
		this.free_speed=free_speed;
		this.free_style=free_style;
		this.pause_content=pause_content;
		this.pause_speed=pause_speed;
		this.pause_style=pause_style;
		this.serve_content=serve_content;
		this.serve_speed=serve_speed;
		this.serve_style=serve_style;
		
		
	}
	

}
