package com.github.demo.core.constants;


public class PlatFormConstants {

    /*********系统设置***********************/
	private static final String BASE_PACKAGE = "com.zhaoonline.zhaotask";// 项目基础包名称，根据自己公司的项目修改
	public static final String MODEL_PACKAGE = BASE_PACKAGE + ".entity";// Model所在包
	public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".mapper";// Mapper所在包
	public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".core.Mapper";// Mapper插件基础接口的完全限定名
	/*********REDIS KEY*********************/
	public static final String REDIS_CLEAN_BIDDING_LOCK_KEY = "DATACLEAN:BIDDING";//历史出价数据key
	public static final String REDIS_LAST_CLEAN_TIME = "DATACLEAN:CLEANTIME";//上一次清除数据时间节点

}
