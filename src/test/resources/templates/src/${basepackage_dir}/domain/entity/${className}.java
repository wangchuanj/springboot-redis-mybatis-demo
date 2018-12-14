<#include "/macro.include"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.domain.entity;
import lombok.Data;
import javax.persistence.Id;
<#include "/java_copyright.include">
@Data
public class ${className} implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	
	<#list table.columns as column>
	<#if column_index==0>
	@Id
	</#if>
	private ${column.javaType} ${column.columnNameLower};
	</#list>

}
