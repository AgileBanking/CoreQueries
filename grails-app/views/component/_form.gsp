<%@ page import="entities.Component" %>



<div class="fieldcontain ${hasErrors(bean: componentInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="component.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${componentInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: componentInstance, field: 'appVersion', 'error')} ">
	<label for="appVersion">
		<g:message code="component.appVersion.label" default="App Version" />
		
	</label>
	<g:textField name="appVersion" value="${componentInstance?.appVersion}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: componentInstance, field: 'supportAdmin', 'error')} ">
	<label for="supportAdmin">
		<g:message code="component.supportAdmin.label" default="Support Admin" />
		
	</label>
	<g:checkBox name="supportAdmin" value="${componentInstance?.supportAdmin}" />
</div>

<div class="fieldcontain ${hasErrors(bean: componentInstance, field: 'baseURL', 'error')} ">
	<label for="baseURL">
		<g:message code="component.baseURL.label" default="Base URL" />
		
	</label>
	<g:textField name="baseURL" value="${componentInstance?.baseURL}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: componentInstance, field: 'isActive', 'error')} ">
	<label for="isActive">
		<g:message code="component.isActive.label" default="Is Active" />
		
	</label>
	<g:checkBox name="isActive" value="${componentInstance?.isActive}" />
</div>

<div class="fieldcontain ${hasErrors(bean: componentInstance, field: 'notes', 'error')} ">
	<label for="notes">
		<g:message code="component.notes.label" default="Notes" />
		
	</label>
	<g:textArea name="notes" cols="40" rows="5" maxlength="10240" value="${componentInstance?.notes}"/>
</div>

