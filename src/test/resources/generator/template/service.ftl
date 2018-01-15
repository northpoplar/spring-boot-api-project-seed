<#--package ${basePackage}.service;-->
<#--import ${basePackage}.model.${modelNameUpperCamel};-->
<#--import ${basePackage}.core.Service;-->

package ${servicePackage};

import ${DBModelPackage}.${modelNameUpperCamel};
import ${corePackage}.ISimpleService;

/**
 * Created by ${author} on ${date}.
 */
public interface I${serviceModelName}Service extends ISimpleService<${modelNameUpperCamel}> {

}
