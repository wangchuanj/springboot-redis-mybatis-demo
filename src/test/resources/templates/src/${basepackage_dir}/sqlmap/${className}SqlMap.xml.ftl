<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${basepackage}.mapper.${className}Mapper">

    <resultMap id="${className}Map" type="${basepackage}.domain.entity.${className}">
        <#list table.columns as column>
        <result property="${column.columnNameLower}" column="${column.sqlName}"/>
		</#list>
    </resultMap>
</mapper>

