
	<input value="<#if functionality??><#if functionality.functionalityId??>${functionality.functionalityId?c}</#if></#if>" id="functionalityId" name="functionalityId" type="hidden" />
	<input value="<#if functionality??><#if functionality.parentId??>${functionality.parentId?c}</#if></#if>" id="parentId" name="parentId" type="hidden" />
	<input value="<#if functionality??><#if functionality.level??>${functionality.level}</#if></#if>" id="level" name="level" type="hidden" />
	<input value="<#if functionality??><#if functionality.path??>${functionality.path}</#if></#if>" id="path" name="path" type="hidden" />
	<input value="<#if functionality??>${functionality.leaf?string('true','false')}</#if>" id="leaf" name="leaf" type="hidden" />
	<tr>	
		<td class="tdright" style="width:100px">
			<span class="required">*</span>权限名称:
		</td>		
		<td>
		<input value="<#if functionality??><#if functionality.name??>${functionality.name}</#if></#if>" id="name" name="name" class="finput {required:true}" maxlength="255"/>
		</td>
	</tr>	
	
	<tr>	
		<td class="field">
			Url Pattern:
		</td>		
		<td>
		<input value="<#if functionality??><#if functionality.urlPattern??>${functionality.urlPattern}</#if></#if>" id="urlPattern" style="width:260px" name="urlPattern" class="finput " maxlength="1024"/>
		<br> <span>注释--关于url pattern: 此项是给后台开发者使用，具有url拦截功能，设置之后没有此权限的用户即使直接输入url也没权限访问。 可以为空(比如没有具体功能入口的父权限节点)，空值不参与url拦截。多个url之间用分号隔开。样例：<br>/functionality/showTree.go <br> /functionality/**  <br> /functionality/pattern?.go <br> /functionality/**/edit.go;/usercms/add.go</span>
		</td>
	</tr>
