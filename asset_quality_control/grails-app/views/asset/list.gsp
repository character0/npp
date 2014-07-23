

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'asset.label', default: 'Asset')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <!--<span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>-->
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div>
            <g:form>
               Asset Central ID:
               <input type="text" id='assetid' name='assetid' maxlength="30" />
              <g:actionSubmit action="asset_find_asset_id" value="Find" />
            </g:form>
          </div>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <th>QC Actions</th>
                            <g:sortableColumn property="id" title="${message(code: 'asset.id.label', default: 'Id')}" />
                            <g:sortableColumn property="centralId" title="${message(code: 'asset.centralId.label', default: 'Central Id')}" />
                            <g:sortableColumn property="mediaId" title="${message(code: 'asset.mediaId.label', default: 'Media Id')}" />
                            <g:sortableColumn property="processor" title="${message(code: 'asset.processor.label', default: 'Processor')}" />
                            <g:sortableColumn property="createDate" title="${message(code: 'asset.createDate.label', default: 'Create Date')}" />
                            <g:sortableColumn property="stagedDate" title="${message(code: 'asset.stagedDate.label', default: 'Staged Date')}" />
                            <g:sortableColumn property="transferStartDate" title="${message(code: 'asset.transferStartDate.label', default: 'Transfer Start Date')}" />
                            <g:sortableColumn property="assetState" title="${message(code: 'asset.assetState.label', default: 'Asset State')}" />
                            <g:sortableColumn property="ingestSourceId" title="${message(code: 'asset.ingestSourceId.label', default: 'Ingest Source Id')}" />
                            <g:sortableColumn property="qcType" title="${message(code: 'asset.qcType.label', default: 'QC Type')}" />
                            <g:sortableColumn property="stagedDirectoryName" title="${message(code: 'asset.stagedDirectoryName.label', default: 'Staged Directory Name')}" />
                            <g:sortableColumn property="name" title="${message(code: 'asset.name.label', default: 'Name')}" />
                            <g:sortableColumn property="episode" title="${message(code: 'asset.episode.label', default: 'Episode')}" />
                            <g:sortableColumn property="assetId" title="${message(code: 'asset.assetId.label', default: 'Asset Id')}" />
                            <g:sortableColumn property="startDate" title="${message(code: 'asset.startDate.label', default: 'Start Date')}" />
                            <g:sortableColumn property="endDate" title="${message(code: 'asset.endDate.label', default: 'End Date')}" />
                            <g:sortableColumn property="rating" title="${message(code: 'asset.rating.label', default: 'Rating')}" />
                            <g:sortableColumn property="advisory" title="${message(code: 'asset.advisory.label', default: 'Advisory')}" />
                            <g:sortableColumn property="runtime" title="${message(code: 'asset.runtime.label', default: 'Runtime')}" />
                            <g:sortableColumn property="title" title="${message(code: 'asset.title.label', default: 'Publish Title')}" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${assetInstanceList}" status="i" var="assetInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>
                              <div>
                                <g:form>
                                    <g:hiddenField name="id" value="${assetInstance?.id}" />
                                    <g:actionSubmit controller="asset" class="save" action="qcNew" value="QC New Content" /></br>
                                    <g:actionSubmit controller="asset" class="save" action="reQC" value="Re-QC Asset" onclick="return confirm('${message(code: 'default.button.reject.confirm.message', default: 'Are you sure?')}');"/></br>
                                    <g:actionSubmit controller="asset" class="save" action="approve" value="${message(code: 'default.button.approve.label', default: 'Approve')}" /></br>
                                    <g:actionSubmit controller="asset" class="save" action="reject" value="${message(code: 'default.button.reject.label', default: 'Reject')}" onclick="return confirm('${message(code: 'default.button.reject.confirm.message', default: 'Are you sure?')}');" />
                                </g:form>
                                </div>
                                <!--<a href="/clquality/asset/qcNewContents/${assetInstance.id}">
                                    <img alt="QC New Contents" title="QC New Contents" src="/clquality/images/skin/information.png">
                                </a>
                                <br>
                                <a href="/clquality/asset/qcAllContent/${assetInstance.id}">
                                    <img alt="Re-QC" title="Re-QC" src="/clquality/images/skin/exclamation.png">
                                </a>-->
                            </td>
                            <td>
                                <g:link class= action="show" id="${assetInstance.id}">${assetInstance.id}</g:link>
                            </td>
                            <td>${assetInstance.centralId}</td>
                            <td>${fieldValue(bean: assetInstance, field: "mediaId")}</td>
                            <td>${fieldValue(bean: assetInstance, field: "processor")}</td>
                            <td>${fieldValue(bean: assetInstance, field: "createDate")}</td>
                            <td>${fieldValue(bean: assetInstance, field: "stagedDate")}</td>
                            <td>${fieldValue(bean: assetInstance, field: "transferStartDate")}</td>
                            <td>${fieldValue(bean: assetInstance, field: "assetState")}</td>
                            <td>${assetInstance.ingestSourceId}</td>
                            <td>${fieldValue(bean: assetInstance, field: "qcType")}</td>
                            <td>${fieldValue(bean: assetInstance, field: "stagedDirectoryName")}</td>
                            <td>${fieldValue(bean: assetInstance, field: "name")}</td>
                            <td>${fieldValue(bean: assetInstance, field: "episode")}</td>
                            <td>${fieldValue(bean: assetInstance, field: "assetId")}</td>
                            <td>${fieldValue(bean: assetInstance, field: "startDate")}</td>
                            <td>${fieldValue(bean: assetInstance, field: "endDate")}</td>
                            <td>${fieldValue(bean: assetInstance, field: "rating")}</td>
                            <td>${fieldValue(bean: assetInstance, field: "advisory")}</td>
                            <td>${assetInstance.runtime}</td>
                            <td>${fieldValue(bean: assetInstance, field: "title")}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${assetInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
