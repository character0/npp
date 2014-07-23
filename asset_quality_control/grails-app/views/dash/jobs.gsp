<html>
	<head>
	    <meta name="layout" content="main"/>
	</head>
	<body>
		<div class="body" style="margin-left: 40px;">
            <g:if test="${flash.message}">
            	<div class="message">${flash.message}</div>
            </g:if>
            
            <h1>Jobs</h1>

            <g:form name="jobs" action="executeJob" controller="dash">
				<input type="hidden" name="id" value="" />
            </g:form>

            <g:javascript>
            	function executeJob(id) {
            		if (confirm("Are you sure?")) {
	            		document.jobs.id.value = id;
    	        		document.jobs.submit();
   	        		}
            	}
			</g:javascript>

			<div>
				These are the service methods run periodically by the quartz jobs.  Click one to execute it manually now.
				<br/><br/>
				<ul style="margin-left: 10px;">
					<li><a href="#" onclick="javascript:executeJob('createAssets');">
						Create QC Assets - assetService.findNewAsset()</a></li>
					<li><a href="#" onclick="javascript:executeJob('stageAssets');">
						Stage QC Assets - assetService.processAssets()</a></li>
                    <li><a href="#" onclick="javascript:executeJob('deleteAssets');">
						Cleanup QCed Assets - assetService.cleanupQCedContent()</a></li>
                    <li><a href="#" onclick="javascript:executeJob('transferAssets');">
                        Transfer QCed Assets - transferService.processAssets()</a></li>
                    <br/>
                </ul>
			</div>
        </div>
    </body>
</html>
