


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'asset.label', default: 'Asset')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${assetInstance}">
            <div class="errors">
                <g:renderErrors bean="${assetInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${assetInstance?.id}" />
                <g:hiddenField name="version" value="${assetInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="mediaId"><g:message code="asset.mediaId.label" default="Media Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'mediaId', 'errors')}">
                                    <g:textField name="mediaId" value="${assetInstance?.mediaId}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="qcType"><g:message code="asset.qcType.label" default="Qc Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'qcType', 'errors')}">
                                    <g:textField name="qcType" value="${assetInstance?.qcType}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="asset.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${assetInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="assetId"><g:message code="asset.assetId.label" default="Asset Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'assetId', 'errors')}">
                                    <g:textField name="assetId" value="${assetInstance?.assetId}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="assetState"><g:message code="asset.assetState.label" default="Asset State" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'assetState', 'errors')}">
                                    <g:textField name="assetState" value="${assetInstance?.assetState}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="stagedDirectoryName"><g:message code="asset.stagedDirectoryName.label" default="Staged Directory Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'stagedDirectoryName', 'errors')}">
                                    <g:textField name="stagedDirectoryName" value="${assetInstance?.stagedDirectoryName}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="rating"><g:message code="asset.rating.label" default="Rating" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'rating', 'errors')}">
                                    <g:textField name="rating" value="${assetInstance?.rating}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="advisory"><g:message code="asset.advisory.label" default="Advisory" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'advisory', 'errors')}">
                                    <g:textField name="advisory" value="${assetInstance?.advisory}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="title"><g:message code="asset.title.label" default="Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'title', 'errors')}">
                                    <g:textField name="title" value="${assetInstance?.title}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="shortTitle"><g:message code="asset.shortTitle.label" default="Short Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'shortTitle', 'errors')}">
                                    <g:textField name="shortTitle" value="${assetInstance?.shortTitle}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="episode"><g:message code="asset.episode.label" default="Episode" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'episode', 'errors')}">
                                    <g:textField name="episode" value="${assetInstance?.episode}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="adixml"><g:message code="asset.adixml.label" default="Adixml" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'adixml', 'errors')}">
                                    <g:textField name="adixml" value="${assetInstance?.adixml}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="description"><g:message code="asset.description.label" default="Description" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'description', 'errors')}">
                                    <g:textField name="description" value="${assetInstance?.description}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="createDate"><g:message code="asset.createDate.label" default="Create Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'createDate', 'errors')}">
                                    <g:datePicker name="createDate" precision="day" value="${assetInstance?.createDate}" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="runtime"><g:message code="asset.runtime.label" default="Runtime" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'runtime', 'errors')}">
                                    <g:textField name="runtime" value="${fieldValue(bean: assetInstance, field: 'runtime')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="ingestSourceId"><g:message code="asset.ingestSourceId.label" default="Ingest Source Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'ingestSourceId', 'errors')}">
                                    <g:textField name="ingestSourceId" value="${fieldValue(bean: assetInstance, field: 'ingestSourceId')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="hasPreviews"><g:message code="asset.hasPreviews.label" default="Has Previews" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'hasPreviews', 'errors')}">
                                    <g:checkBox name="hasPreviews" value="${assetInstance?.hasPreviews}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="centralId"><g:message code="asset.centralId.label" default="Central Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'centralId', 'errors')}">
                                    <g:textField name="centralId" value="${fieldValue(bean: assetInstance, field: 'centralId')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="contents"><g:message code="asset.contents.label" default="Contents" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'contents', 'errors')}">
                                    
<ul>
<g:each in="${assetInstance?.contents?}" var="c">
    <li><g:link controller="content" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="content" action="create" params="['asset.id': assetInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'content.label', default: 'Content')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="endDate"><g:message code="asset.endDate.label" default="End Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'endDate', 'errors')}">
                                    <g:datePicker name="endDate" precision="day" value="${assetInstance?.endDate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="startDate"><g:message code="asset.startDate.label" default="Start Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: assetInstance, field: 'startDate', 'errors')}">
                                    <g:datePicker name="startDate" precision="day" value="${assetInstance?.startDate}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
