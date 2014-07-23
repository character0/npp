<%@ page import="states.AssetStates" %>
<html>
    <head>
        <title>Clearleap QC</title>
		<meta name="layout" content="main" />
		<style type="text/css" media="screen">

			#nav {
				margin-top:20px;
				margin-left:30px;
				width:228px;
				float:left;

			}
			.homePagePanel * {
				margin:0px;
			}
			.homePagePanel .panelBody ul {
				list-style-type:none;
				margin-bottom:10px;
			}
			.homePagePanel .panelBody h1 {
				text-transform:uppercase;
				font-size:1.1em;
				margin-bottom:10px;
			}
			.homePagePanel .panelBody {
			    background: url(images/leftnav_midstretch.png) repeat-y top;
				margin:0px;
				padding:15px;
			}
			.homePagePanel .panelBtm {
			    background: url(images/leftnav_btm.png) no-repeat top;
				height:20px;
				margin:0px;
			}

			.homePagePanel .panelTop {
			    background: url(images/leftnav_top.png) no-repeat top;
				height:11px;
				margin:0px;
			}
			h2 {
				margin-top:15px;
				margin-bottom:15px;
				font-size:1.2em;
			}
			#pageBody {
				margin-left:40px;
			}
		</style>
    </head>
    <body>
		<div id="pageBody">
		    <g:clQualityInfo />

	        <h1>Clearleap QC Admin</h1>

            <div>
            	<table style="width: 600px">
            		<tr>
            			<!--<td>
            				<b>Stats</b><br/>
            				Assets: <b>${assetCount}</b><br/>
            				Contents: <b>${contentCount}</b><br/>
            			</td>-->
            			<td>
							<div>Count of Assets in each <b>State</b></div>
							<table>
								<tr>
									<th>Asset State</th>
									<th>Count</th>
								</tr>
    							<tr>
									<td>${AssetStates.CREATED}</td>
									<td>${createdAssetsCount}</td>
								</tr>
                                <tr>
                                    <td>${AssetStates.PROCESSING}</td>
                                    <td>${processingAssetsCount}</td>
                                </tr>
								<tr>
									<td>${AssetStates.STAGED}</td>
									<td>${stagedAssetsCount}</td>
								</tr>
                                <tr>
                                    <td>${AssetStates.SYNCHING}</td>
                                    <td>${synchingAssetsCount}</td>
                                </tr>
                                <tr>
                                    <td>${AssetStates.SYNCHED}</td>
                                    <td>${synchedAssetsCount}</td>
                                </tr>
								<tr>
									<td>${AssetStates.SYNCH_ERROR}</td>
									<td>${synchErrorAssetsCount}</td>
								</tr>
                                <tr>
                                    <td>${AssetStates.APPROVED}</td>
                                    <td>${approvedAssetsCount}</td>
                                </tr>
								<tr>
									<td>${AssetStates.REJECTED}</td>
									<td>${rejectedAssetsCount}</td>
								</tr>
							</table>
            			</td>
            		</tr>
            		<tr>
            			<td>
							<div>Count of PRIMARY Contents with <b>Manzanita Failures</b></div>
							<table>
								<tr>
									<td>${contentsWithManzanitaFailuresCount}</td>
								</tr>
							</table>
            			</td>
            			<!--<td>
							<div><b>Top 5 content purchased</b> in last 7 days</div> 
							<table>
								<tr>
									<th>Content Id</th>
									<th>Count</th>
								</tr>
								<g:each in="${top5assetsPlayedInLast7Days}" status="i" var="content">
									<tr>
										<td>${content[0]}</td>
										<td>${content[1]}</td>
									</tr>
								</g:each>
							</table>
            			</td> -->
            		</tr>
            		<tr>
            			<td>
							<!---<div>Count of Contents <b>without</b> a </b>Data Service XML</b></div>
							<table>
								<tr>
									<td>${contentWithoutNielsenXmlCount}</td>
								</tr>
							</table>-->
                            <g:set var="exportParams" value="${[sort: params.sort, order: params.order]}" />
                            <g:render template="/shared/exportBar" model="${[exportAction: 'exportStagedAssets', exportParams: exportParams]}" />
            			</td>

            			<!--<td>
							<div><b>Top 5 devices by nav</b> in last 7 days</div> 
							<table>
								<tr>
									<th>Device Id</th>
									<th>Count</th>
								</tr>
								<g:each in="${top5devicesByNavInLast7Days}" status="i" var="device">
									<tr>
										<td>${device[0]}</td>
										<td>${device[1]}</td>
									</tr>
								</g:each>
							</table>
            			</td>-->
            		</tr>
            	</table>
            </div>
		</div>
    </body>
</html>