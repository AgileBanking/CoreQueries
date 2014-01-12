
<%@ page import="entities.Component" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'component.label', default: 'Component')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-component" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-component" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'component.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="appVersion" title="${message(code: 'component.appVersion.label', default: 'App Version')}" />
					
						<g:sortableColumn property="supportAdmin" title="${message(code: 'component.supportAdmin.label', default: 'Support Admin')}" />
					
						<g:sortableColumn property="baseURL" title="${message(code: 'component.baseURL.label', default: 'Base URL')}" />
					
						<g:sortableColumn property="isActive" title="${message(code: 'component.isActive.label', default: 'Is Active')}" />
					
						<g:sortableColumn property="notes" title="${message(code: 'component.notes.label', default: 'Notes')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${componentInstanceList}" status="i" var="componentInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${componentInstance.id}">${fieldValue(bean: componentInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: componentInstance, field: "appVersion")}</td>
					
						<td><g:formatBoolean boolean="${componentInstance.supportAdmin}" /></td>
					
						<td>${fieldValue(bean: componentInstance, field: "baseURL")}</td>
					
						<td><g:formatBoolean boolean="${componentInstance.isActive}" /></td>
					
						<td>${fieldValue(bean: componentInstance, field: "notes")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${componentInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
