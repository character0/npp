

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'content.label', default: 'Content')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <!--<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>-->
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.id.label" default="ID" /></td>
                            <td valign="top" class="value">${contentInstance.id}</td>
                        </tr>
                         <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.asset.label" default="Asset" /></td>
                            <td valign="top" class="value"><g:link controller="asset" action="show" id="${contentInstance?.asset?.id}">${contentInstance?.asset?.id?.encodeAsHTML()}</g:link></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.name.label" default="Name" /></td>
                            <td valign="top" class="value">${fieldValue(bean: contentInstance, field: "name")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.stagedDate.label" default="Staged Date" /></td>
                            <td valign="top" class="value">${fieldValue(bean: contentInstance, field: "stagedDate")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.original.label" default="Original" /></td>
                            <td valign="top" class="value"><g:formatBoolean boolean="${contentInstance?.original}" /></td>
                        </tr>
                         <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.profile.label" default="Profile" /></td>
                            <td valign="top" class="value">${fieldValue(bean: contentInstance, field: "profile")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.contentType.label" default="Content Type" /></td>
                            <td valign="top" class="value">${fieldValue(bean: contentInstance, field: "contentType")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.contentState.label" default="Content State" /></td>
                            <td valign="top" class="value">${fieldValue(bean: contentInstance, field: "contentState")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.checksum.label" default="Checksum" /></td>
                            <td valign="top" class="value">${fieldValue(bean: contentInstance, field: "checksum")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.size.label" default="Size" /></td>
                            <td valign="top" class="value">${contentInstance.size}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.dataServiceXml.label" default="Data Service XML" /></td>
                            <td valign="top" class="value"><g:clDisplayDataServiceXmlLink content="${contentInstance}"/></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.audioType.label" default="Audio Type" /></td>
                            <td valign="top" class="value">${fieldValue(bean: contentInstance, field: "audioType")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.runtime.label" default="Runtime" /></td>
                            <td valign="top" class="value">${contentInstance.runtime}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.manzanitaResult.label" default="Manzanita Result" /></td>
                            <td valign="top" class="value"><g:clDisplayManzanitaXmlLink content="${contentInstance}"/></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.manzanitaRuntime.label" default="Manzanita Runtime" /></td>
                            <td valign="top" class="value">${contentInstance.manzanitaRuntime}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.manzanitaVChip.label" default="Manzanita V Chip" /></td>
                            <td valign="top" class="value">${fieldValue(bean: contentInstance, field: "manzanitaVChip")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.manzanitaCGMS.label" default="Manzanita CGMS" /></td>
                            <td valign="top" class="value">${fieldValue(bean: contentInstance, field: "manzanitaCGMS")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.qcComplete.label" default="QC Complete" /></td>
                            <td valign="top" class="value">${fieldValue(bean: contentInstance, field: "qcComplete")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.qcDate.label" default="QC Date" /></td>
                            <td valign="top" class="value">${fieldValue(bean: contentInstance, field: "qcDate")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.qcResult.label" default="QC Result" /></td>
                            <td valign="top" class="value">${fieldValue(bean: contentInstance, field: "qcResult")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.centralId.label" default="Central ID" /></td>
                            <td valign="top" class="value">${contentInstance.centralId}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="content.uri.label" default="URI" /></td>
                            <td valign="top" class="value">${fieldValue(bean: contentInstance, field: "uri")}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                 <g:form>
                    <g:hiddenField name="id" value="${contentInstance?.id}" />
                    <span class="button"><g:actionSubmit controller="content" class="save" action="pass" value="${message(code: 'default.button.pass.label', default: 'Pass')}" /></span>
                    <span class="button"><g:actionSubmit controller="content" class="delete" action="fail" value="${message(code: 'default.button.fail.label', default: 'Fail')}" onclick="return confirm('${message(code: 'default.button.reject.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
