<#assign className = table.className>
<#assign classNameFirstLower = className?uncap_first>   
<#assign classNameLowerCase = className?lower_case>
package ${basepackage}.controller;

import ${basepackage}.service.${className}Service;
import ${basepackage}.domain.entity.${className};
import ${basepackage}.core.Result;
import ${basepackage}.core.ResultGenerator;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

<#include "/java_copyright.include">
@RestController
@RequestMapping("/${classNameLowerCase}")
public class ${className}Controller {

    @Resource
	private ${className}Service ${classNameFirstLower}Service;

	@GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<${className}> list = ${classNameFirstLower}Service.selectAll();
        PageInfo<${className}> pageInfo = new PageInfo<>(list);
        return ResultGenerator.success(pageInfo);
	}

	@PostMapping
    public Result add(@RequestBody ${className} ${classNameFirstLower}) {
        ${classNameFirstLower}Service.save(${classNameFirstLower});
        return ResultGenerator.success();
	}

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        ${classNameFirstLower}Service.deleteById(id);
        return ResultGenerator.success();
	}

    @PutMapping
    public Result update(@RequestBody ${className} ${classNameFirstLower}) {
        ${classNameFirstLower}Service.updateById(${classNameFirstLower});
        return ResultGenerator.success();
	}

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        ${className} ${classNameFirstLower} = ${classNameFirstLower}Service.selectById(id);
        return ResultGenerator.success(${classNameFirstLower});
	}
}

