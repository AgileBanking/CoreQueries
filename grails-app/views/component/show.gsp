
<%@ page import="entities.Component" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'component.label', default: 'Component')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-component" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-component" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list component">
			
				<g:if test="${componentInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="component.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${componentInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${componentInstance?.appVersion}">
				<li class="fieldcontain">
					<span id="appVersion-label" class="property-label"><g:message code="component.appVersion.label" default="App Version" /></span>
					
						<span class="property-value" aria-labelledby="appVersion-label"><g:fieldValue bean="${componentInstance}" field="appVersion"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${componentInstance?.supportAdmin}">
				<li class="fieldcontain">
					<span id="supportAdmin-label" class="property-label"><g:message code="component.supportAdmin.label" default="Support Admin" /></span>
					
						<span class="property-value" aria-labelledby="supportAdmin-label"><g:formatBoolean boolean="${componentInstance?.supportAdmin}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${componentInstance?.baseURL}">
				<li class="fieldcontain">
					<span id="baseURL-label" class="property-label"><g:message code="component.baseURL.label" default="Base URL" /></span>
					
						<span class="property-value" aria-labelledby="baseURL-label"><g:fieldValue bean="${componentInstance}" field="baseURL"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${componentInstance?.isActive}">
				<li class="fieldcontain">
					<span id="isActive-label" class="property-label"><g:message code="component.isActive.label" default="Is Active" /></span>
					
						<span class="property-value" aria-labelledby="isActive-label"><g:formatBoolean boolean="${componentInstance?.isActive}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${componentInstance?.notes}">
				<li class="fieldcontain">
					<span id="notes-label" class="property-label"><g:message code="component.notes.label" default="Notes" /></span>
					
						<span class="property-value" aria-labelledby="notes-label"><g:fieldValue bean="${componentInstance}" field="notes"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${componentInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="component.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${componentInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${componentInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="component.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${componentInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${componentInstance?.id}" />
					<g:link class="edit" action="edit" id="${componentInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
