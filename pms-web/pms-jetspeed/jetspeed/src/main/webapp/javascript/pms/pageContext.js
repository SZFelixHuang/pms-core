var pageContext = {

	initValueFieldLength : 0,

	initEnterableFieldLength : 0
};

$(document).ready(function() {
	
	pageContext.initValueFieldLength = $("*[initValue]").length;

	pageContext.initEnterableFieldLength = $("input").length + $("select").length + $("textarea").length;
});