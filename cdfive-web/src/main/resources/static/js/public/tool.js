var tool={
	isFunction:function(fun){
		if(fun){
			 return typeof fun == 'function';
		}
		return false;
	},
	isString:function(str){
		if(arguments.length==0){
			return false;
		}
		return (typeof str=='string')&&str.constructor==String; 
	},
	isArray:function (obj){
		if(obj==null||obj==undefined) return false;
		return (typeof obj=='object')&&obj.constructor==Array;
	},
	isNumber:function (num){
		return typeof num=='number';
	},
/*
 * isInteger(obj):判断传入的参数obj是否为整数
 * 对传入的数字取整取整后的数组与原数字相等表示为整数
 * 成功避免了空数组，布尔值，空对象
 * Math.floor([])===0
 * Math.floor(true)===1
 * Math.floor({})===NaN
*/
	isInteger:function(obj) {
		return Math.floor(obj) === obj
	},
	//	inRange:数字在什么区间
	inRange:function(minNum,maxNum,num){
		var _maxNum=parseInt(maxNum);
		var _minNum=parseInt(minNum);
		var _num=parseInt(num);
		if(tool.isNumber(_maxNum)||tool.isNumber(_minNum)||tool.isNumber(_num)){
			return false;
		}
		if(_num>maxNum||_num<minNum){
			return false
		}
		return true;
	},
	//传入两个数输出百分比，第一个是除数，第二个是被除数;主要用于制作进度条,第三个参数是保留多少位小数
	toPercent:function(num1,num2){
		if(str.isEmpty(num1)||str.isEmpty(num2)){
			return 0;
		}
		var _floatLen=arguments[2]?(parseInt(arguments[2])):2;
		_floatLen=tool.inRange(0,10,_floatLen)?2:_floatLen;//判断传入的数字是否在区间类，不在区间类默认显示两位
		var _divisor =parseFloat(num1);//除数
		var _dividend=parseFloat(num2)==0?1:parseFloat(num2);//被除数
		var _res=(_divisor/_dividend).toFixed(_floatLen+2).split('.')[1];
		var _resLen=_res.length;
		return parseInt(_res.slice(0,_resLen-_floatLen))+'.'+_res.slice(_resLen-_floatLen,_resLen)+'%';
	},
	//为大型整数添加 分位符
	formatNum:function(num){
		if(arguments.length==0){
			return 0;
		}
		if(!tool.isString(num)&&!tool.isNumber(num)){
			return 0
		}
		var _num=parseInt(num).toString();
		var _len=_num.length
		if(_len<=3){
			return _num;
		}
		var _r=_len%3;
		if(_r>0){
			return _num.slice(0,_r)+","+_num.slice(_r,_len).match(/\d{3}/g).join(",")
		}else{
			return _num.slice(_r,_len).match(/\d{3}/g).join(",");
		}
	},
//	将字符串分割成若干个指定长度的字符串数据，最后一个字符串成都小于指定长度
	toStrArr:function(str,len){
		var _len=str.length;
    	var _resArr=[];
    	for(var i=0,l=_len;i<l;){
    		var _start=i;
    		var _end=(i+len)>l?l:(i+len);
    		_resArr.push(str.slice(_start,_end));
    		i=_end;
    	}
    	return _resArr;
	},
	strIsOver:function(str,len){ //判断字符串长度是否已经超过限定的长度 
		var strLen=this.getLenth(str);
		if(strLen>len){
			return true;
		}else{
			return false;
		}
	},
	getStrLen:function(str) {  //字符串常用方法
	    ///<summary>获得字符串实际长度，中文2，英文1</summary>  
	    ///<param name="str">要获得长度的字符串</param>  
	    var realLength = 0, len = str.length, charCode = -1;  
	    for (var i = 0; i < len; i++) {  
	        charCode = str.charCodeAt(i);  
	        if (charCode >= 0 && charCode <= 128) realLength += 1;  
	        else realLength += 2;  
	    }  
	    return realLength;  
	},
	strIsEmpty:function(str){
		if (str === null || str === undefined || str === '' || str === 'null') {
			return true;
		} 
		return false;
	},
	arrHasRepeat:function(arr){
		if(a instanceof Array){
			var ary = arr;
			var nary=ary.sort();
			for(var i=0;i<ary.length;i++){
				if (nary[i]==nary[i+1]){
					return true;
				}
			} 
			return false;
		}
	},
	arrHasEmpty:function(arr){
		if(a instanceof Array){
			for(var i=0;i<arr.length;i++) {
				var _arr=arr[i].replace(/\s/g, "")
				if(!_arr){
					return true;
				}
			} 
			return false;
		}
	},
	cleanArrRepeat:function(arr){
		if(this.isArray(arr)){
			var ary = arr;
			var _hash={},_arr=[];
			for(var i=0,_len=arr.length;i<_len;i++){
				if(!_hash[arr[i]]){
					_hash[arr[i]]=true;
					_arr.push(arr[i]);
				}
			}
			return _arr;
		}
	}
}

