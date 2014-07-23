<%@ page import="clquality.Asset" %>



<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'content.label', default: 'Content')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${contentInstance}">
            <div class="errors">
                <g:renderErrors bean="${contentInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="audioType"><g:message code="content.audioType.label" default="Audio Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentInstance, field: 'audioType', 'errors')}">
                                    <g:textField name="audioType" value="${contentInstance?.audioType}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dataServiceXml"><g:message code="content.dataServiceXml.label" default="Data Service Xml" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentInstance, field: 'dataServiceXml', 'errors')}">
                                    <g:textField name="dataServiceXml" value="${contentInstance?.dataServiceXml}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="manzanitaReport"><g:message code="content.manzanitaReport.label" default="Manzanita Report" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentInstance, field: 'manzanitaReport', 'errors')}">
                                    <g:textField name="manzanitaReport" value="${contentInstance?.manzanitaReport}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="manzanitaResult"><g:message code="content.manzanitaResult.label" default="Manzanita Result" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentInstance, field: 'manzanitaResult', 'errors')}">
                                    <g:textField name="manzanitaResult" value="${contentInstance?.manzanitaResult}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="manzanitaVChip"><g:message code="content.manzanitaVChip.label" default="Manzanita VC hip" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentInstance, field: 'manzanitaVChip', 'errors')}">
                                    <g:textField name="manzanitaVChip" value="${contentInstance?.manzanitaVChip}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="manzanitaCGMS"><g:message code="content.manzanitaCGMS.label" default="Manzanita CGMS" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentInstance, field: 'manzanitaCGMS', 'errors')}">
                                    <g:textField name="manzanitaCGMS" value="${contentInstance?.manzanitaCGMS}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="profile"><g:message code="content.profile.label" default="Profile" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentInstance, field: 'profile', 'errors')}">
                                    <g:textField name="profile" value="${contentInstance?.profile}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="content.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${contentInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="uri"><g:message code="content.uri.label" default="Uri" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentInstance, field: 'uri', 'errors')}">
                                    <g:textField name="uri" value="${contentInstance?.uri}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="contentType"><g:message code="content.contentType.label" default="Content Type" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentInstance, field: 'contentType', 'errors')}">
                                    <g:textField name="contentType" value="${contentInstance?.contentType}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="checksum"><g:message code="content.checksum.label" default="Checksum" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentInstance, field: 'checksum', 'errors')}">
                                    <g:textField name="checksum" value="${contentInstance?.checksum}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="contentState"><g:message code="content.contentState.label" default="Content State" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentInstance, field: 'contentState', 'errors')}">
                                    <g:textField name="contentState" value="${contentInstance?.contentState}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="centralId"><g:message code="content.centralId.label" default="Central Id" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentInstance, field: 'centralId', 'errors')}">
                                    <g:textField name="centralId" value="${fieldValue(bean: contentInstance, field: 'centralId')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="runtime"><g:message code="content.runtime.label" default="Runtime" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentInstance, field: 'runtime', 'errors')}">
                                    <g:textField name="runtime" value="${fieldValue(bean: contentInstance, field: 'runtime')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="manzanitaRuntime"><g:message code="content.manzanitaRuntime.label" default="Manzanita Runtime" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentInstance, field: 'manzanitaRuntime', 'errors')}">
                                    <g:textField name="manzanitaRuntime" value="${fieldValue(bean: contentInstance, field: 'manzanitaRuntime')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="original"><g:message code="content.original.label" default="Original" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentInstance, field: 'original', 'errors')}">
                                    <g:checkBox name="original" value="${contentInstance?.original}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="size"><g:message code="content.size.label" default="Size" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentInstance, field: 'size', 'errors')}">
                                    <g:textField name="size" value="${fieldValue(bean: contentInstance, field: 'size')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="asset"><g:message code="content.asset.label" default="Asset" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: contentInstance, field: 'asset', 'errors')}">
                                    <g:select name="asset.id" from="${Asset.list()}" optionKey="id" value="${contentInstance?.asset?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
