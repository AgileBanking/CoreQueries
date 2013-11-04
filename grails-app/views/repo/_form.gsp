<%@ page import="entities.Repo" %>



<div class="fieldcontain ${hasErrors(bean: repoInstance, field: 'domainApp', 'error')} required">
	<label for="domainApp">
		<g:message code="repo.domainApp.label" default="Domain App" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="domainApp" name="domainApp.id" from="${entities.Component.list()}" optionKey="id" required="" value="${repoInstance?.domainApp?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: repoInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="repo.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${repoInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: repoInstance, field: 'tags', 'error')} ">
	<label for="tags">
		<g:message code="repo.tags.label" default="Tags" />
		
	</label>
	<g:textField name="tags" value="${repoInstance?.tags}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: repoInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="repo.description.label" default="Description" />
		
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="1024" value="${repoInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: repoInstance, field: 'serviceURL', 'error')} ">
	<label for="serviceURL">
		<g:message code="repo.serviceURL.label" default="Service URL" />
		
	</label>
	<g:textField name="serviceURL" value="${repoInstance?.serviceURL}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: repoInstance, field: 'target', 'error')} ">
	<label for="target">
		<g:message code="repo.target.label" default="Target" />
		
	</label>
	<g:select name="target" from="${repoInstance.constraints.target.inList}" value="${repoInstance?.target}" valueMessagePrefix="repo.target" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: repoInstance, field: 'body', 'error')} ">
	<label for="body">
		<g:message code="repo.body.label" default="Body" />
		
	</label>
	<g:textArea name="body" cols="40" rows="5" maxlength="1024" value="${repoInstance?.body}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: repoInstance, field: 'example', 'error')} ">
	<label for="example">
		<g:message code="repo.example.label" default="Example" />
		
	</label>
	<g:textArea name="example" cols="40" rows="5" maxlength="1024" value="${repoInstance?.example}"/>
</div>

