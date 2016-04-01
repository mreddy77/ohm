var app = angular.module('iohm.filters', []);

app.filter('range', function() {
    return function(input, total) {
        total = parseInt(total);
        for (var i = 0; i < total; i++)
            input.push(i);
        return input;
    };
});

app.filter('zeropad', function() {
	return function(input, n) {
		if(input === undefined)
			input = ""
		if(input.length >= n)
			return input
		var zeros = "0".repeat(n);
		return (zeros + input).slice(-1 * n)
	};
});