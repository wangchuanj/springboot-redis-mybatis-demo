<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.mapper;

import ${basepackage}.domain.entity.${className};
import ${basepackage}.core.Mapper;

<#include "/java_copyright.include">
public interface  ${className}Mapper extends Mapper<${className}>{

}
