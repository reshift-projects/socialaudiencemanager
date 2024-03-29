<%@ include file="/WEB-INF/jsp/main/page-a.inc" %>
<%@ include file="/WEB-INF/jsp/main/menu.inc" %>
<%@ include file="/WEB-INF/jsp/main/scheduler/submenu.inc" %>
<style>
#modify-scheduler-service {
	width: 100%;
	margin-right: 10px;
}
#modify-scheduler .label {
	margin-top: 2px;
	margin-bottom: 2px;
}
#configPropsText {
	font-size: 1.5em;
	height: 2.0em;
	font-height: 1.2em;
	margin-top: 2px;
	margin-bottom: 2px;
}
#configPropsText {
	height: 20.0em;
	width: 100%;
}
#submit {
	font-size: 1.5em;
}
</style>
<h1>Modify Scheduler Config</h1>

<div class="warning">
Modifying config properties will shutdown the current scheduler, re-initialize, and auto restart if it's set!
</div>

<div id="modify-scheduler-service">
<form method="post" action="modify-action">

<div>
<label class="label">Quartz Scheduler Config Properties</label>
<textarea id="configPropsText" name="configPropsText">${ data.configPropsText }</textarea>
</div>

<input type="hidden" name="configId" value="${ data.configId }"/>
<input id="submit" type="submit" value="Modify Scheduler"/>

</form>
</div><!-- modify-scheduler-service -->

<%@ include file="/WEB-INF/jsp/main/page-b.inc" %>