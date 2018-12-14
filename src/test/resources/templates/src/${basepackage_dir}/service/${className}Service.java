<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.service;

import ${basepackage}.mapper.${className}Mapper;
import ${basepackage}.domain.entity.${className};
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import ${basepackage}.core.AbstractService;

<#include "/java_copyright.include">
@Service
public class ${className}Service extends AbstractService<${className}> {

	@Resource
	private ${className}Mapper ${classNameLower}Mapper;

}
