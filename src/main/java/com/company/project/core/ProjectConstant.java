package com.company.project.core;

/**
 * 项目常量
 */
public final class ProjectConstant {
    public static final String APP_NAME="directPayment";
    public static final String BASE_PACKAGE = "com.wahaha.iqunxing";//项目基础包名称，根据自己公司的项目修改

    public static final String DB_MODEL_PACKAGE = BASE_PACKAGE + ".dal.po";//Model所在包
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".dal.dao";//Mapper所在包
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".bll.service";//Service所在包
    public static final String SERVICE_IMPL_PACKAGE = BASE_PACKAGE + ".bll.impl";//ServiceImpl所在包
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";//Controller所在包

    public static final String CORE_PACKAGE = "com.company.project.core";
    public static final String MAPPER_INTERFACE_REFERENCE = CORE_PACKAGE + ".Mapper";//Mapper插件基础接口的完全限定名
}
