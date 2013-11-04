
<%@ page import="entities.Repo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'repo.label', default: 'Repo')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-repo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-repo" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="repo.domainApp.label" default="Domain App" /></th>
					
						<g:sortableColumn property="title" title="${message(code: 'repo.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="tags" title="${message(code: 'repo.tags.label', default: 'Tags')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'repo.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="serviceURL" title="${message(code: 'repo.serviceURL.label', default: 'Service URL')}" />
					
						<g:sortableColumn property="target" title="${message(code: 'repo.target.label', default: 'Target')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${repoInstanceList}" status="i" var="repoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${repoInstance.id}">${fieldValue(bean: repoInstance, field: "domainApp")}</g:link></td>
					
						<td>${fieldValue(bean: repoInstance, field: "title")}</td>
					
						<td>${fieldValue(bean: repoInstance, field: "tags")}</td>
					
						<td>${fieldValue(bean: repoInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: repoInstance, field: "serviceURL")}</td>
					
						<td>${fieldValue(bean: repoInstance, field: "target")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${repoInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
