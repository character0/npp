Generate Reconciliation Report of Staged QC Assets to Submitted MXP Assets.
<div style="float: right; margin-right: 10px;">
    <g:link action="${exportAction ?: 'export'}" params="${[exportFormat:'csv'] + (exportParams ?: [:])}">
      <img src="${resource(dir:'images',file:'csv.png')}" title="CSV" alt="CSV"/> CSV
    </g:link>
    &nbsp;&nbsp;&nbsp;
    <g:link action="${exportAction ?: 'export'}" params="${[exportFormat:'excel'] + (exportParams ?: [:])}">
      <img src="${resource(dir:'images',file:'excel.png')}" title="Excel" alt="Excel"/> Excel
    </g:link>
    &nbsp;&nbsp;&nbsp;
    <g:link action="${exportAction ?: 'export'}" params="${[exportFormat:'pdf'] + (exportParams ?: [:])}">
      <img src="${resource(dir:'images',file:'pdf.png')}" title="PDF" alt="PDF"/> PDF
    </g:link>
</div>