// import static com.company.project.core.ProjectConstant.*;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.google.common.base.CaseFormat;

import freemarker.template.TemplateExceptionHandler;

/**
 * 代码生成器，根据数据表名称生成对应的Model、Mapper、ISimpleService、Controller简化开发。
 */
public class CodeGenerator {

    /**
     * 项目常量
     */

    private static final String CORE_PACKAGE = "com.company.project.core"; // 此项将来会删除

    private static final String APP_NAME = "DirectPayment";

    private static final String BASE_PACKAGE = "com.wahaha.iqunxing";// 项目基础包名称，根据自己公司的项目修改

    private static final String DAL_PACKAGE = BASE_PACKAGE + ".dal";// dal所在包

    private static final String DB_MODEL_PACKAGE = DAL_PACKAGE + ".po";// Model所在包

    private static final String MAPPER_PACKAGE = DAL_PACKAGE + ".dao";// 各个业务Mapper所在包

    private static final String BASE_MAPPER_NAME = "SimpleBaseMapper";// 各个业务Mapper的基类（通用mapper）名称

    private static final String MAPPER_INTERFACE_REFERENCE = DAL_PACKAGE + "."+BASE_MAPPER_NAME;// 各个业务Mapper的基类（通用mapper）的完全限定名

    private static final String SERVICE_PACKAGE = BASE_PACKAGE + ".bll.service";// Service所在包

    private static final String SERVICE_IMPL_PACKAGE = BASE_PACKAGE + ".bll.impl";// ServiceImpl所在包

    private static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";// Controller所在包

    private static final String CONFIGURER_PACKAGE = BASE_PACKAGE + ".configurer";// Configurer所在包

    // JDBC配置，请修改为你项目的实际配置
    private static final String JDBC_URL = "jdbc:mysql://t4.dcfservice.com:3306/dcf_payment_interior";

    private static final String JDBC_USERNAME = "changling.zhou";

    private static final String JDBC_PASSWORD = "YTAxM2Y2YWY";

    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    // user.dir是jvm的系统属性，表示当前的工作路径。只有在项目路径下（也就是工作路径）执行以下代码时获取到的user.dir才是项目路径
    private static final String PROJECT_PATH = System.getProperty("user.dir");// 项目在硬盘上的基础路径

    private static final String TEMPLATE_FILE_PATH = PROJECT_PATH + "/src/test/resources/generator/template";// 模板位置

    private static final String JAVA_PATH = "/src/main/java"; // java文件路径

    private static final String RESOURCES_PATH = "/src/main/resources";// 资源文件路径

    private static final String PACKAGE_PATH_BASE = packageConvertPath(BASE_PACKAGE);// 生成的app存放路径

    private static final String PACKAGE_PATH_DAL = packageConvertPath(DAL_PACKAGE);// dal路径

    private static final String PACKAGE_PATH_SERVICE = packageConvertPath(SERVICE_PACKAGE);// 生成的Service存放路径

    private static final String PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(SERVICE_IMPL_PACKAGE);// 生成的Service实现存放路径

    private static final String PACKAGE_PATH_CONTROLLER = packageConvertPath(CONTROLLER_PACKAGE);// 生成的Controller存放路径

    private static final String PACKAGE_PATH_CONFIGURER = packageConvertPath(CONFIGURER_PACKAGE);// 生成的Configurer存放路径

    private static final String AUTHOR = "CodeGenerator";// @author

    private static final String DATE = new SimpleDateFormat("yyyy/MM/dd").format(new Date());// @date

    private static final String[] TABLES = {
            "t_holiday", "t_bank_error_code", "t_payment_account_dtl"
    };

    public static void main(String[] args) {
        genCode(TABLES);
        // genCodeByCustomModelName("输入表名","输入自定义Model名称");
    }

    /**
     * 通过数据表名称生成代码，Model 名称通过解析数据表名称获得，下划线转大驼峰的形式。 如输入表名称 "t_user_detail" 将生成
     * TUserDetail、TUserDetailMapper、TUserDetailService ...
     * 
     * @param tableNames
     *            数据表名称...
     */
    public static void genCode(String... tableNames) {
        for (String tableName : tableNames) {
            genCodeByCustomModelName(tableName, null);
        }
    }

    /**
     * 通过数据表名称，和自定义的 Model 名称生成代码 如输入表名称 "t_user_detail" 和自定义的 Model 名称 "User"
     * 将生成 User、UserMapper、UserService ...
     * 
     * @param tableName
     *            数据表名称
     * @param modelName
     *            自定义的 Model 名称
     */
    public static void genCodeByCustomModelName(String tableName, String modelName) {
        genApplication();
        genBaseMapper();
        genModelAndMapper(tableName, modelName);
        genBaseService();
        genService(tableName, modelName);
        genController(tableName, modelName);
        genConfigurer();
        genMetaInfo();
    }

    public static void genConfigurer() {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            data.put("configurerPackage", CONFIGURER_PACKAGE);
            data.put("DBModelPackage", DB_MODEL_PACKAGE);
            data.put("mapperPackage", MAPPER_PACKAGE);
            data.put("mapperInterfaceReference", MAPPER_INTERFACE_REFERENCE);

            File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CONFIGURER + "MybatisConfigurer.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("configurer/MybatisConfigurer.ftl").process(data, new FileWriter(file));

            File file1 = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CONFIGURER + "WebMvcConfigurer.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("configurer/WebMvcConfigurer.ftl").process(data, new FileWriter(file1));
            System.out.println(file.getName() + "," + file1.getName() + "生成成功");

        } catch (Exception e) {
            throw new RuntimeException("Configurer生成失败", e);
        }
    }

    public static void genApplication() {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            data.put("appNameUpperCamel", APP_NAME);
            data.put("basePackage", BASE_PACKAGE);

            File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_BASE + APP_NAME + "Application.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("application/application.ftl").process(data, new FileWriter(file));
            System.out.println(APP_NAME + "Application.java 生成成功");

        } catch (Exception e) {
            throw new RuntimeException(APP_NAME + "Application.java 生成失败", e);
        }
    }

    public static void genBaseMapper() {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            data.put("dalPackage", DAL_PACKAGE);
            data.put("baseMapperName", BASE_MAPPER_NAME);


            File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_DAL + BASE_MAPPER_NAME+".java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("dal/BaseMapper.ftl").process(data, new FileWriter(file));
            System.out.println("Mapper.java 生成成功");

        } catch (Exception e) {
            throw new RuntimeException("生成Mapper.java失败", e);
        }
    }

    public static void genModelAndMapper(String tableName, String modelName) {
        Context context = new Context(ModelType.FLAT);
        context.setId("Mysql");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", MAPPER_INTERFACE_REFERENCE);
        context.addPluginConfiguration(pluginConfiguration);

        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(DB_MODEL_PACKAGE);
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        // 生成Mapper接口对应的XML文件
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(PROJECT_PATH + RESOURCES_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage("mapper");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
        javaClientGeneratorConfiguration.setTargetPackage(MAPPER_PACKAGE);//各个业务mapper文件会存在此目录下
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        if (isNotEmpty(modelName))
            tableConfiguration.setDomainObjectName(modelName);
        // generatedKey意味着所有的表都有一个id自增的主键
        tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", true, null));
        context.addTableConfiguration(tableConfiguration);

        List<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();

            boolean overwrite = true;
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            warnings = new ArrayList<String>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }

        if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            throw new RuntimeException("生成Model和Mapper失败：" + warnings);
        }
        if (isEmpty(modelName))
            modelName = tableNameConvertUpperCamel(tableName);
        System.out.println(modelName + ".java 生成成功");
        System.out.println(modelName + "Mapper.xml 生成成功");
    }

    public static void genBaseService() {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            data.put("servicePackage", SERVICE_PACKAGE);
            data.put("serviceImplPackage", SERVICE_IMPL_PACKAGE);
            data.put("mapperPackage", MAPPER_PACKAGE);
            data.put("corePackage", CORE_PACKAGE);
            data.put("mapperInterfaceReference", MAPPER_INTERFACE_REFERENCE);
            data.put("baseMapperName", BASE_MAPPER_NAME);

            File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE + "ISimpleService.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("bll/ISimpleService.ftl").process(data, new FileWriter(file));
            System.out.println("ISimpleService.java 生成成功");

            File file1 = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE_IMPL + "AbstractSimpleService.java");
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            cfg.getTemplate("bll/AbstractSimpleService.ftl").process(data, new FileWriter(file1));
            System.out.println("AbstractSimpleService.java 生成成功");

        } catch (Exception e) {
            throw new RuntimeException("生成BaseService失败", e);
        }
    }

    public static void genService(String tableName, String modelName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String serviceModelName = isEmpty(modelName) ? tableNameConvertModelName(tableName) : modelName;
            String modelNameUpperCamel = isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", tableNameConvertLowerCamel(tableName));
            data.put("basePackage", BASE_PACKAGE);
            data.put("servicePackage", SERVICE_PACKAGE);
            data.put("DBModelPackage", DB_MODEL_PACKAGE);
            data.put("serviceImplPackage", SERVICE_IMPL_PACKAGE);
            data.put("mapperPackage", MAPPER_PACKAGE);
            // data.put("corePackage", CORE_PACKAGE);
            data.put("serviceModelName", serviceModelName);

            File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE + "I" + serviceModelName + "Service.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            cfg.getTemplate("bll/service.ftl").process(data, new FileWriter(file));
            System.out.println("I" + modelNameUpperCamel + "SimpleService.java 生成成功");

            File file1 = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE_IMPL + serviceModelName + "ServiceImpl.java");
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            cfg.getTemplate("bll/service-impl.ftl").process(data, new FileWriter(file1));

            new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE + "/dto").mkdir();
            new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE_IMPL + "/bo").mkdir();
            System.out.println(modelNameUpperCamel + "ServiceImpl.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成" + tableName + "的Service失败", e);
        }
    }

    public static void genController(String tableName, String modelName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String serviceModelName = isEmpty(modelName) ? tableNameConvertModelName(tableName) : modelName;
            String modelNameUpperCamel = isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("baseRequestMapping", modelNameConvertMappingPath(modelNameUpperCamel));
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", BASE_PACKAGE);
            data.put("controllerPackage", CONTROLLER_PACKAGE);
            data.put("servicePackage", SERVICE_PACKAGE);
            data.put("DBModelPackage", DB_MODEL_PACKAGE);
            data.put("corePackage", CORE_PACKAGE);
            data.put("serviceModelName", serviceModelName);

            File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CONTROLLER + serviceModelName + "Controller.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            // cfg.getTemplate("controller-restful.ftl").process(data, new
            // FileWriter(file));
            cfg.getTemplate("controller/controller.ftl").process(data, new FileWriter(file));

            System.out.println(modelNameUpperCamel + "Controller.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成" + tableName + "的Controller失败", e);
        }

    }

    public static void genMetaInfo() {
        genMetaInfoFile(null, "spring-devtools.properties");
        genMetaInfoFile("services", "com.athaydes.spockframework.report.IReportCreator.properties");
    }

    private static void genMetaInfoFile(String targetSubDir, String metaTemplateFileName) {
        try {
            StringBuilder fileFullName = new StringBuilder(PROJECT_PATH + RESOURCES_PATH + "/META-INF/");
            if (!isEmpty(targetSubDir)) {
                fileFullName.append(targetSubDir + "/" + metaTemplateFileName);
            } else {
                fileFullName.append(metaTemplateFileName);
            }
            File file = new File(fileFullName.toString());
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            freemarker.template.Configuration cfg = getConfiguration();
            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);

            cfg.getTemplate("meta-info/" + metaTemplateFileName + ".ftl").process(data, new FileWriter(file));

        } catch (Exception e) {
            throw new RuntimeException("生成META-INF文件失败", e);
        }
    }

    private static freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(
                freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    private static String tableNameConvertLowerCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
    }

    private static String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());

    }

    private static String tableNameConvertModelName(String tableName) {
        String tModelName = tableNameConvertUpperCamel(tableName);

        if (tableName.startsWith("t_") || tableName.startsWith("T_")) {
            return tModelName.substring(1);// 去掉tModelName中开头的"T"
        } else {
            return tModelName;
        }
    }

    private static String tableNameConvertMappingPath(String tableName) {
        tableName = tableName.toLowerCase();// 兼容使用大写的表名
        return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
    }

    private static String modelNameConvertMappingPath(String modelName) {
        String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelName);
        return tableNameConvertMappingPath(tableName);
    }

    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

}
