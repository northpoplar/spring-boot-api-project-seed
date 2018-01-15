<#--package ${basePackage}.service.impl;-->

<#--import ${basePackage}.dao.${modelNameUpperCamel}Mapper;-->
<#--import ${basePackage}.model.${modelNameUpperCamel};-->
<#--import ${basePackage}.service.${modelNameUpperCamel}Service;-->
<#--import ${basePackage}.core.AbstractService;-->
package ${serviceImplPackage};

import ${mapperPackage}.${modelNameUpperCamel}Mapper;
import ${DBModelPackage}.${modelNameUpperCamel};
import ${servicePackage}.I${serviceModelName}Service;
import ${corePackage}.AbstractSimpleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by ${author} on ${date}.
 */
@Service
@Transactional
public class ${serviceModelName}ServiceImpl extends AbstractSimpleService<${modelNameUpperCamel}> implements I${serviceModelName}Service {
    @Resource
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

}
