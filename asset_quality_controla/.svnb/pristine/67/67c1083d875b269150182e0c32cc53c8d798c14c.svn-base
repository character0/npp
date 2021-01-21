

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'asset.label', default: 'Asset')}" />
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
                            <td valign="top" class="name"><g:message code="asset.id.label" default="ID" /></td>
                            <td valign="top" class="value">${assetInstance.id}</td>
                        </tr>
                         <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.centralId.label" default="Central ID" /></td>
                            <td valign="top" class="value">${assetInstance.centralId}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.mediaId.label" default="Media ID" /></td>
                            <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "mediaId")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.assetState.label" default="Asset State" /></td>
                            <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "assetState")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.ingestSourceId.label" default="Ingest Source ID" /></td>
                            <td valign="top" class="value">${assetInstance.ingestSourceId}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.stagedDirectoryName.label" default="Staged Directory Name" /></td>
                            <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "stagedDirectoryName")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.name.label" default="Name" /></td>
                            <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "name")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.episode.label" default="Episode" /></td>
                            <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "episode")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.assetId.label" default="Asset ID" /></td>
                            <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "assetId")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.createDate.label" default="Create Date" /></td>
                            <td valign="top" class="value"><g:formatDate date="${assetInstance?.createDate}" /></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.startDate.label" default="Start Date" /></td>
                            <td valign="top" class="value"><g:formatDate date="${assetInstance?.startDate}" /></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.endDate.label" default="End Date" /></td>
                            <td valign="top" class="value"><g:formatDate date="${assetInstance?.endDate}" /></td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.rating.label" default="Rating" /></td>
                            <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "rating")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.advisory.label" default="Advisory" /></td>
                            <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "advisory")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.runtime.label" default="Runtime" /></td>
                            <td valign="top" class="value">${assetInstance.runtime}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.qcType.label" default="QC Type" /></td>
                            <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "qcType")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.title.label" default="Title" /></td>
                            <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "title")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.shortTitle.label" default="Short Title" /></td>
                            <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "shortTitle")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.description.label" default="Description" /></td>
                            <td valign="top" class="value">${fieldValue(bean: assetInstance, field: "description")}</td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.hasPreviews.label" default="Has Previews" /></td>
                            <td valign="top" class="value"><g:formatBoolean boolean="${assetInstance?.hasPreviews}" /></td>
                        </tr>
                         <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.adixml.label" default="ADI.XML" /></td>
                            <td valign="top" class="value">
                                <g:clDisplayAdiXmlLink asset="${assetInstance}"/>
                            </td>
                        </tr>
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="asset.contents.label" default="Contents" /></td>
                            <td valign="top" class="table">
                                <table border="0">
                                    <tr>
                                        <td>
                                            <span>Name</span>
                                        </td>
                                        <td>
                                            <span>Original</span>
                                        </td>
                                        <td>
                                            <span>Profile</span>
                                        </td>
                                        <td>
                                            <span>Content Type</span>
                                        </td>
                                        <td>
                                            <span>QC Date</span>
                                        </td>
                                        <td>
                                            <span>QC Result</span>
                                        </td>
                                        <td>
                                            <span>Manzanita Result</span>
                                        </td>
                                    </tr>

                                    <g:each in="${sortedContentsByType}" var="c">
                                        <tr>
                                            <g:set var="rowClass" value="${rowClass == 'odd' ? 'even' : 'odd'}" />
                                            <td class="${rowClass}"><g:link controller="content" action="show" id="${c.id}">${c?.name?.encodeAsHTML()}</g:link></td>
                                            <td class="${rowClass}">${c?.original?.encodeAsHTML()}</td>
                                            <td class="${rowClass}">${c?.profile?.encodeAsHTML()}</td>
                                            <td class="${rowClass}">${c?.contentType?.encodeAsHTML()}</td>

                                            <td class="${rowClass}">${c?.qcDate?.encodeAsHTML()}</td>
                                            <td class="${rowClass}">${c?.qcResult?.encodeAsHTML()}</td>

                                            <td class="${rowClass}">${c?.manzanitaResult?.encodeAsHTML()}</td>
                                        </tr>
                                    </g:each>

                                 </table>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${assetInstance?.id}" />
                    <span class="button"><g:actionSubmit controller="asset" class="save" action="approve" value="${message(code: 'default.button.approve.label', default: 'Approve')}" /></span>
                    <span class="button"><g:actionSubmit controller="asset" class="delete" action="reject" value="${message(code: 'default.button.reject.label', default: 'Reject')}" onclick="return confirm('${message(code: 'default.button.reject.confirm.message', default: 'Are you sure?')}');" /></span>
                    <span class="button"><g:actionSubmit controller="asset" class="edit" action="qcNew" value="QC New Content" /></span>
                    <span class="button"><g:actionSubmit controller="asset" class="edit" action="reQC" value="Re-QC Asset" onclick="return confirm('${message(code: 'default.button.reject.confirm.message', default: 'Are you sure?')}');"/></span>
                </g:form>
            </div>
    </body>
</html>
