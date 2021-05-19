//纠正
$(".restrictnumber").keyup(function() {
	// alert("val="+$(this).val());
	var str = $(this).val();
	var strt = "";
	var reg = new RegExp("^[0-9.]*$");
	for (var i = 0; i < str.length; i++) {
		if (reg.test(str[i])) {
			strt += str[i];
		}
		// 替换全角的数字为半角的数字
		if (str[i] == '．') {
			strt += '.';
		}
		if (str[i] == '０') {
			strt += '0';
		}
		if (str[i] == '１') {
			strt += '1';
		}
		if (str[i] == '２') {
			strt += '2';
		}
		if (str[i] == '３') {
			strt += '3';
		}
		if (str[i] == '４') {
			strt += '4';
		}
		if (str[i] == '５') {
			strt += '5';
		}
		if (str[i] == '６') {
			strt += '6';
		}
		if (str[i] == '７') {
			strt += '7';
		}
		if (str[i] == '８') {
			strt += '8';
		}
		if (str[i] == '９') {
			strt += '9';
		}
	}
	$(this).val(strt)
});