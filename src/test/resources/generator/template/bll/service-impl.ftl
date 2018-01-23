<#--package ${basePackage}.service.impl;-->

<#--import ${basePackage}.dao.${modelNameUpperCamel}Mapper;-->
<#--import ${basePackage}.model.${modelNameUpperCamel};-->
<#--import ${basePackage}.service.${modelNameUpperCamel}Service;-->
<#--import ${basePackage}.core.AbstractService;-->
package ${serviceImplPackage};

import javax.annotation.Resource;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ${mapperPackage}.${modelNameUpperCamel}Mapper;
import ${DBModelPackage}.${modelNameUpperCamel};
import ${servicePackage}.I${serviceModelName}Service;


/**
 * Created by ${author} on ${date}.
 */
@Service
@Transactional
public class ${serviceModelName}ServiceImpl extends AbstractSimpleService<${modelNameUpperCamel}> implements I${serviceModelName}Service {
    @Resource
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

    public List<${modelNameUpperCamel}> selectPage(int pageNum, int pageSize, PageInfo<${modelNameUpperCamel}> pageInfo) {
        PageHelper.startPage(pageNum,pageSize);
        PageHelper.orderBy("date desc,remark asc");

        List<${modelNameUpperCamel}> list= ${modelNameLowerCamel}Mapper.selectAll();
        PageInfo<${modelNameUpperCamel}> page = new PageInfo<>(list);
        BeanUtils.copyProperties(page,pageInfo);

        return list;
    }

}
