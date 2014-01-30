function guess() {
	var sudoku_id = $("#guess-div").children(".sudoku_table")[0].id;
	var guesses = [];

	for (var ind = 0; ind < $("#guess-div input:text").length; ind++) {
		var input = $("#guess-div input:text")[ind];
		if (input.value.length > 0) {
			if (input.value != $("#" + input.id).attr("oldValue")) {
				var guess = {};
				guess.x = input.id.split("_")[0];
				guess.y = input.id.split("_")[1];
				guess.value = input.value;
				guesses.push(guess);
			}
		}
	}

	doPost("/sudoku-runner/guess", {
		"data" : guesses,
		"sudoku_id" : sudoku_id
	});
}

function doPost(url, data) {
	$.ajax({
		url : url,
		type : 'POST',
		data : {
			"sudoku_id" : data.sudoku_id,
			"data" : JSON.stringify(data.data)
		},
		dataType : 'json',
		complete : function() {
			reloadUrl("sudoku_id=" + data.sudoku_id);
		}
	});
}

function doGet(url, data) {
	$.ajax({
		url : url,
		type : 'GET',
		data : data
	});
}

function reloadUrl(params) {
	var url = window.location.href;
	if (url.indexOf('?') > -1) {
		url = url.substring(0, url.indexOf('?'));
	}
	url += '?';
	window.location.href = url + params;
}