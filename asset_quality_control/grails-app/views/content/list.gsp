

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'content.label', default: 'Content')}" />
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
            <div class="list">
                <table>
                    <thead>
                        <tr>
                            <g:sortableColumn property="id" title="${message(code: 'content.id.label', default: 'Id')}" />
                            <g:sortableColumn property="asset" title="${message(code: 'content.asset.label', default: 'Asset')}" />
                            <g:sortableColumn property="name" title="${message(code: 'content.name.label', default: 'Name')}" />
                            <g:sortableColumn property="stagedDate" title="${message(code: 'content.stagedDate.label', default: 'Staged Date')}" />
                            <g:sortableColumn property="original" title="${message(code: 'content.original.label', default: 'Original')}" />
                            <g:sortableColumn property="profile" title="${message(code: 'content.profile.label', default: 'Profile')}" />
                            <g:sortableColumn property="contentType" title="${message(code: 'content.contentType.label', default: 'Content Type')}" />
                            <g:sortableColumn property="contentState" title="${message(code: 'content.contentState.label', default: 'Content State')}" />
                            <g:sortableColumn property="checksum" title="${message(code: 'content.checksum.label', default: 'Checksum')}" />
                            <g:sortableColumn property="size" title="${message(code: 'content.size.label', default: 'Size')}" />
                            <g:sortableColumn property="dataServiceXml" title="${message(code: 'content.dataServiceXml.label', default: 'Nielsen Xml')}" />
                            <g:sortableColumn property="audioType" title="${message(code: 'content.audioType.label', default: 'Audio Type')}" />
                            <g:sortableColumn property="runtime" title="${message(code: 'content.runtime.label', default: 'Runtime')}" />
                            <g:sortableColumn property="manzanitaResult" title="${message(code: 'content.manzanitaResult.label', default: 'Manzanita Result')}" />
                            <g:sortableColumn property="manzanitaRuntime" title="${message(code: 'content.manzanitaRuntime.label', default: 'Manzanita Runtime')}" />
                            <g:sortableColumn property="manzanitaVChip" title="${message(code: 'content.manzanitaVChip.label', default: 'Manzanita V Chip')}" />
                            <g:sortableColumn property="manzanitaCGMS" title="${message(code: 'content.manzanitaCGMS.label', default: 'Manzanita CGMS')}" />
                            <g:sortableColumn property="qcComplete" title="${message(code: 'content.qcComplete.label', default: 'QC Complete')}" />
                            <g:sortableColumn property="qcDate" title="${message(code: 'content.qcDate.label', default: 'QC Date')}" />
                            <g:sortableColumn property="qcResult" title="${message(code: 'content.qcResult.label', default: 'QC Result')}" />
                            <g:sortableColumn property="centralId" title="${message(code: 'content.centralId.label', default: 'Central Id')}" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${contentInstanceList}" status="i" var="contentInstance">
                    <!--TODO || contentInstance.dataServiceXml?.length() < 1-->
                        <tr style="background: ${(contentInstance.manzanitaResult == 'FAILED') ? 'red' : 'white'}" class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td><g:link action="show" id="${contentInstance.id}">${contentInstance.id}</g:link></td>
                            <td><g:link controller="asset" action="show" id="${contentInstance.asset.id}">${contentInstance.asset.id}</g:link></td>
                            <td>${fieldValue(bean: contentInstance, field: "name")}</td>
                            <td>${fieldValue(bean: contentInstance, field: "stagedDate")}</td>
                            <td>${fieldValue(bean: contentInstance, field: "original")}</td>
                            <td>${fieldValue(bean: contentInstance, field: "profile")}</td>
                            <td>${fieldValue(bean: contentInstance, field: "contentType")}</td>
                            <td>${fieldValue(bean: contentInstance, field: "contentState")}</td>
                            <td>${fieldValue(bean: contentInstance, field: "checksum")}</td>
                            <td>${contentInstance.size}</td>
                            <td><g:clDisplayDataServiceXmlLink content="${contentInstance}"/></td>
                            <td>${fieldValue(bean: contentInstance, field: "audioType")}</td>
                            <td>${contentInstance.runtime}</td>
                            <td><g:clDisplayManzanitaXmlLink content="${contentInstance}"/></td>
                            <td>${contentInstance.manzanitaRuntime}</td>
                            <td>${fieldValue(bean: contentInstance, field: "manzanitaVChip")}</td>
                            <td>${fieldValue(bean: contentInstance, field: "manzanitaCGMS")}</td>
                            <td>${fieldValue(bean: contentInstance, field: "qcComplete")}</td>
                            <td>${fieldValue(bean: contentInstance, field: "qcDate")}</td>
                            <td>${fieldValue(bean: contentInstance, field: "qcResult")}</td>
                            <td>${contentInstance.centralId}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${contentInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
