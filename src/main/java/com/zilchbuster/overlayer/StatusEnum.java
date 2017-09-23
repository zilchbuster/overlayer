package com.zilchbuster.overlayer;

public enum StatusEnum {
  FAIL {
	  public String toString() {
		  return "fail";
	  }
  },

  SUCCESS {
	  public String toString() {
		  return "success";
	  }
  }
}
