//# sourceURL=dashboard.js

var tips = (function() {
	var marqueeContent = new Array();
	marqueeContent[0] = '工欲善其事,必先利其器。——孔子'
	marqueeContent[1] = '如烟往事俱忘却，心底无私天地宽。——陶铸'
	marqueeContent[2] = '莫等闲，白了少年头，空悲切。——岳飞'
	marqueeContent[3] = '不论你在什么时候开始，重要的是开始之后就不要停止。'
	marqueeContent[4] = '世上无难事，只要肯登攀。'
	marqueeContent[5] = '一分耕耘，一分收获。 一艺之成，当尽毕生之力。'
	marqueeContent[6] = '敏而好学，不耻下问。 ——孔子'
	marqueeContent[7] = '只要努力，你就能成为你想成为的人。'
	marqueeContent[8] = '志当存高远。 —— 诸葛亮'
	marqueeContent[9] = '做好手中事，珍惜眼前人。'
	marqueeContent[10] = '但愿每次回忆，对生活都不感到负疚 —— 郭小川'
	marqueeContent[11] = '知之为知之，不知为不知，是知也。——《论语》'
	marqueeContent[12] = '风声、雨声、读书声、声声入耳；家事、国事、天下事、事事关心。——顾宪成'
	marqueeContent[13] = '敏而好学，不耻下问。——孔子'
	marqueeContent[14] = '与有肝胆人共事，从无字句处读书。——周恩来'
	marqueeContent[15] = '学而不思则罔，思而不学则殆。——孔子'
	marqueeContent[16] = '你不能左右天气，但可以改变心情。你不能改变容貌，但可以掌握自己。你不能预见明天，但可以珍惜今天。'
	marqueeContent[17] = '冰冻三尺，非一日之寒。'
	marqueeContent[18] = '世上只有想不通的人，没有走不通的路。'
	marqueeContent[19] = '生当作人杰，死亦为鬼雄，至今思项羽，不肯过江东。 —— 李清照'
	marqueeContent[20] = '勿以恶小而为之，勿以善小而不为。'
	marqueeContent[21] = '因为年轻我们一无所有，也正因为年轻我们将拥有一切。'
	marqueeContent[22] = '少壮不努力，老大徒伤悲。——《长歌行》'
	marqueeContent[23] = '先相信你自己，然后别人才会相信你。 —— 屠格涅夫'
	marqueeContent[24] = '聪明在于勤奋，天才在于积累。——华罗庚'
	marqueeContent[25] = '如果耐不住寂寞，你就看不到繁华。'
	marqueeContent[26] = '人总是珍惜未得到的，而遗忘了所拥有的。'
	marqueeContent[27] = '非学无以广才，非志无以成学。——诸葛亮'
	marqueeContent[28] = '逆风的方向更适合飞翔，我不怕千万人阻挡只怕自己投降。'
	marqueeContent[29] = '三人行，必有我师也。择其善者而从之，其不善者而改之。——孔子'
	marqueeContent[30] = '一个人可以被毁灭，但不能被打败。——《老人与海》'
	marqueeContent[31] = '博学之，审问之，慎思之，明辨之，笃行之。——礼记'
	marqueeContent[32] = '业精于勤，荒于嬉；行成于思，毁于随。——韩愈'
	marqueeContent[33] = '只要功夫深，铁杵磨成针。'
	marqueeContent[34] = '学而不思则罔，思而不学则殆。'
	marqueeContent[35] = '燕雀安知鸿鹄之志哉！ —— 陈胜'
	marqueeContent[36] = '得之坦然，失之淡然，顺其自然，争其必然。'
	marqueeContent[37] = '书籍的使命是帮助人们认识生活，而不是代替思想对生活的认识。——科尔查克'
	marqueeContent[38] = '读书是在别人思想的帮助下，建立起自己的思想。——鲁巴金'
	marqueeContent[39] = '不戚戚于贫贱，不汲汲于富贵。 —— 陶渊明'
	marqueeContent[40] = '人不怕走在黑夜里，就怕心中没有阳光。'
	marqueeContent[41] = '好读书，不求甚解。每有会意，便欣然忘食。——陶渊明'
	marqueeContent[42] = '读书破万卷，下笔如有神。——杜甫'
	marqueeContent[43] = '自己选择的路，跪着也要把它走完。'
	marqueeContent[44] = '老骥伏枥，志在千里；烈士暮年，壮心不已。 —— 曹操'
	marqueeContent[45] = '不念居安思危，戒奢以俭；斯以伐根而求木茂，塞源而欲流长也。 —— 魏徵'
	marqueeContent[46] = '知之者不如好之者，好之者不如乐之者。——孔子'
	marqueeContent[47] = '冬天天已经到来，春天还会远吗？ —— 雪莱'
	marqueeContent[48] = '社会犹如一条船，每个人都要有掌舵的准备。 —— 易卜生'
	marqueeContent[49] = '书籍是人类进步的阶梯。——高尔基'
	marqueeContent[50] = '抓住自己最有兴趣的东西，由浅入深，循序渐进地学。——华罗庚'
	marqueeContent[51] = '冬天天已经到来，春天还会远吗？ —— 雪莱'
	var marqueeDelay = 5000;
	var scrollDelay = 20;
	var marqueeHeight = 17;
	var intervalMarquee;
	var intervalScorll;

	function initMarquee(jqId) {
		var marqueeId = Math.floor(Math.random() * (marqueeContent.length - 1));
		var str = marqueeContent[marqueeId];
		$(jqId)
				.html(
						'<div id=marqueeBox style="float:left;font-size:12px;height:'
								+ marqueeHeight
								+ 'px;line-height:'
								+ marqueeHeight
								+ 'px;overflow:hidden;" onmouseover="tips.stopMarquee()" onmouseout="intervalMarquee=tips.initMarquee()"><div>'
								+ str + '</div></div>');
		intervalMarquee = setInterval(function() {
			startMarquee();
		}, marqueeDelay);
	}

	function startMarquee() {
		var marqueeId = Math.floor(Math.random() * (marqueeContent.length - 1));
		var str = marqueeContent[marqueeId];
		if (marqueeBox.childNodes.length == 1) {
			var nextLine = document.createElement('div');
			nextLine.innerHTML = str;
			marqueeBox.appendChild(nextLine);
		} else {
			marqueeBox.childNodes[0].innerHTML = str;
			marqueeBox.appendChild(marqueeBox.childNodes[0]);
			marqueeBox.scrollTop = 0;
		}
		clearInterval(intervalScorll);
		intervalScorll = setInterval(function() {
			scrollMarquee();
		}, scrollDelay);
	}

	function scrollMarquee() {
		marqueeBox.scrollTop++;
		if (marqueeBox.scrollTop % marqueeHeight == (marqueeHeight - 1)) {
			clearInterval(intervalScorll);
		}
	}

	function stopMarquee() {
		clearInterval(intervalScorll);
		clearInterval(intervalMarquee);
	}

	return {
		initMarquee : initMarquee,
		stopMarquee : stopMarquee
	};
})();

$(function() {

	sendAjax(STAT_COUNT, {}, function(data) {
		$("#js_div_mp3Count").html(data["mp3Count"]);
		$("#js_div_catalogCount").html(data["catalogCount"]);
		$("#js_div_articleCount").html(data["articleCount"]);
		$("#js_div_userCount").html(data["userCount"]);
	});

	sendAjax(API_LOG_TOP_LIST, {
		num : 8
	}, function(data) {
		templateHtml('tpl_api_log_list', '#js_tbody_api_log', {
			'list' : data
		});
	});

	sendAjax(ARTICLE_TOP_LIST, {
		num : 8
	}, function(data) {
		templateHtml('tpl_article_list', '#js_div_article', {
			'list' : data
		});
	});

	sendAjax(MP3_TOP_LIST, {
		num : 8
	}, function(data) {
		templateHtml('tpl_mp3_list', '#js_tbody_mp3', {
			'list' : data
		});
	});

	$('.js_c_quick_jump').click(function() {
		var name = $(this).attr('name');
		$('#js_ul_siderbarNav a').each(function() {
			if ($(this).prop("href") != '' && $(this).text().trim() == name) {
				$(this).click();
				$(this).parent().parent().show();
				return false;
			}
		});
	});

	var $js_btn_return = $("#js_btn_return");
	var requestType = 1;
	var date;
	var ip;

	var $js_div_request = $("#js_div_request");
	var $js_div_requestDate = $("#js_div_requestDate");
	var $js_div_requestDateIp = $("#js_div_requestDateIp");

	$js_btn_return.click(function() {
		if (requestType == 2) {
			requestType = 1;
			$js_div_request.show();
			$js_div_requestDate.hide();
			$js_div_requestDateIp.hide();
		} else if (requestType == 3) {
			requestType = 2;
			$js_div_request.hide();
			$js_div_requestDate.show();
			$js_div_requestDateIp.hide();
		}
	});

	// 基于准备好的dom，初始化echarts实例
	var chartRequest = echarts.init($js_div_request[0]);
	var chartRequestDate = echarts.init($js_div_requestDate[0]);
	var chartRequestDateIp = echarts.init($js_div_requestDateIp[0]);

	function chartRequestDateClickFunc(params) {
		$js_div_requestDate.hide();
		$js_div_requestDateIp.show();
		requestType = 3;

		ip = params["name"];

		sendAjax(STAT_REQUEST, {
			date : date,
			ip : ip
		}, function(data) {
			var xData = [];
			var yData = [];
			for (var i = 0; i < data.length; i++) {
				var tmpData = data[i];
				xData.push(tmpData["name"]);
				yData.push(tmpData["count"]);
			}

			var optionRequestDateIp = {
				title : {
					text : date + '[' + ip + ']访问统计',
					left : 'center'
				},
				tooltip : {
					trigger : 'axis'
				// ,formatter:function(param) {
				// debugger;
				// }
				},
				legend : {
					data : [ '日期' ]
				},
				xAxis : {

				},
				yAxis : {
					data : xData,
					inverse : true,
					axisLabel : {
						textStyle : {
							fontSize : '10px'
						},
						interval : 0,// 标签设置为全部显示
					}
				},
				series : [ {
					name : '数量',
					type : 'bar',
					data : yData,
					itemStyle : {
						normal : {
							borderWidth : '2'
						}
					}
				} ]
			};

			chartRequestDateIp.setOption(optionRequestDateIp);
		});
	}

	function chartRequestClickFunc(params) {
		$js_div_request.hide();
		$js_div_requestDate.show();
		requestType = 2;

		date = params["name"];
		sendAjax(STAT_REQUEST, {
			date : date
		}, function(data) {
			var xData = [];
			var yData = [];
			for (var i = 0; i < data.length; i++) {
				var tmpData = data[i];
				xData.push(tmpData["name"]);
				yData.push(tmpData["count"]);
			}

			var optionRequestDate = {
				title : {
					text : date + '访问统计',
					left : 'center'
				},
				tooltip : {
					trigger : 'axis'
				// ,formatter:function(param) {
				// debugger;
				// }
				},
				legend : {
					data : [ '日期' ]
				},
				xAxis : {
					data : xData,
					axisLabel : {
						textStyle : {
							fontSize : '10px'
						},
						interval : 0,// 标签设置为全部显示
					}
				},
				yAxis : {},
				series : [ {
					name : '数量',
					type : 'bar',
					data : yData,
					itemStyle : {
						normal : {
							borderWidth : '2'
						}
					}
				} ]
			};

			chartRequestDate.setOption(optionRequestDate);
			chartRequestDate.on('click', chartRequestDateClickFunc);
		});
	}

	sendAjax(STAT_REQUEST, {}, function(data) {
		var xData = [];
		var yData = [];
		for (var i = 0; i < data.length; i++) {
			var tmpData = data[i];
			xData.push(tmpData["name"]);
			yData.push(tmpData["count"]);
		}
		var optionRequest = {
			title : {
				text : '访问统计',
				left : 'center'
			},
			tooltip : {
				trigger : 'axis'
			// ,formatter:function(param) {
			// debugger;
			// }
			},
			legend : {
				data : [ '日期' ]
			},
			xAxis : {
				data : xData,
				axisLabel : {
					textStyle : {
						fontSize : '10px'
					},
					interval : 0,// 标签设置为全部显示
				}
			},
			yAxis : {},
			series : [ {
				name : '数量',
				type : 'line',
				data : yData,
				itemStyle : {
					normal : {
						borderWidth : '2'
					}
				}
			} ]
		};

		chartRequest.setOption(optionRequest);
		chartRequest.on('click', chartRequestClickFunc);
	});

	tips.initMarquee('#js_span_tips');
});
