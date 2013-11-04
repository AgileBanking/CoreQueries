
<%@ page import="entities.Repo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'repo.label', default: 'Repo')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-repo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-repo" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list repo">
			
				<g:if test="${repoInstance?.domainApp}">
				<li class="fieldcontain">
					<span id="domainApp-label" class="property-label"><g:message code="repo.domainApp.label" default="Domain App" /></span>
					
						<span class="property-value" aria-labelledby="domainApp-label"><g:link controller="component" action="show" id="${repoInstance?.domainApp?.id}">${repoInstance?.domainApp?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${repoInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="repo.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${repoInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${repoInstance?.tags}">
				<li class="fieldcontain">
					<span id="tags-label" class="property-label"><g:message code="repo.tags.label" default="Tags" /></span>
					
						<span class="property-value" aria-labelledby="tags-label"><g:fieldValue bean="${repoInstance}" field="tags"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${repoInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="repo.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${repoInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${repoInstance?.serviceURL}">
				<li class="fieldcontain">
					<span id="serviceURL-label" class="property-label"><g:message code="repo.serviceURL.label" default="Service URL" /></span>
					
						<span class="property-value" aria-labelledby="serviceURL-label"><g:fieldValue bean="${repoInstance}" field="serviceURL"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${repoInstance?.target}">
				<li class="fieldcontain">
					<span id="target-label" class="property-label"><g:message code="repo.target.label" default="Target" /></span>
					
						<span class="property-value" aria-labelledby="target-label"><g:fieldValue bean="${repoInstance}" field="target"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${repoInstance?.body}">
				<li class="fieldcontain">
					<span id="body-label" class="property-label"><g:message code="repo.body.label" default="Body" /></span>
					
						<span class="property-value" aria-labelledby="body-label"><g:fieldValue bean="${repoInstance}" field="body"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${repoInstance?.example}">
				<li class="fieldcontain">
					<span id="example-label" class="property-label"><g:message code="repo.example.label" default="Example" /></span>
					
						<span class="property-value" aria-labelledby="example-label"><g:fieldValue bean="${repoInstance}" field="example"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${repoInstance?.id}" />
					<g:link class="edit" action="edit" id="${repoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
