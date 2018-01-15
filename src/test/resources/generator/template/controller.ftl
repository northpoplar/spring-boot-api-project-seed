<#--package ${basePackage}.web;-->
<#--import ${basePackage}.core.Result;-->
<#--import ${basePackage}.core.ResultGenerator;-->
<#--import ${basePackage}.model.${modelNameUpperCamel};-->
<#--import ${basePackage}.service.${modelNameUpperCamel}Service;-->
<#--import com.github.pagehelper.PageHelper;-->
<#--import com.github.pagehelper.PageInfo;-->
<#--import org.springframework.web.bind.annotation.PostMapping;-->
<#--import org.springframework.web.bind.annotation.RequestMapping;-->
<#--import org.springframework.web.bind.annotation.RequestParam;-->
<#--import org.springframework.web.bind.annotation.RestController;-->

package ${controllerPackage};

import ${servicePackage}.I${serviceModelName}Service;
import ${DBModelPackage}.${modelNameUpperCamel};
import ${corePackage}.Result;
import ${corePackage}.ResultGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by ${author} on ${date}.
*/
@RestController
@RequestMapping("${baseRequestMapping}")
public class ${serviceModelName}Controller {
    @Resource
    private I${serviceModelName}Service ${serviceModelName}Service;

    @PostMapping("/add")
    public Result add(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${serviceModelName}Service.save(${modelNameLowerCamel});
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        ${serviceModelName}Service.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${serviceModelName}Service.update(${modelNameLowerCamel});
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        ${modelNameUpperCamel} ${modelNameLowerCamel} = ${serviceModelName}Service.findById(id);
        return ResultGenerator.genSuccessResult(${modelNameLowerCamel});
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<${modelNameUpperCamel}> list = ${serviceModelName}Service.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
