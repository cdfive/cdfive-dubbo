/*这个js文件，书写template.helper*/

var speratingStateStr={
	 '1':'正常营业 '
	,'2':'关门（无法观测店铺里面的状态） '
	,'3':'关门（可观察到店铺里面陈列了大量货物）,'
	,'4':'店铺空置（店铺关门但可观察到店铺里面有很少或基本没有货物）'
	,'5':'铺面转让 '
	,'6':'正在装修 '
	,'7':'其他'
}
//将index转成对应序号的小写字母
template.helper('indexToLetter', function (value) {
	var leters='a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z';
	var lowercaseArr=leters.split(',');
	return lowercaseArr[value];
});

template.helper('formatOperatingState', function (value) {
	if(!speratingStateStr[value]){
		return speratingStateStr['7'];
	}
	return speratingStateStr[value];
});
//将商户的类型转换成文字
var typeImgObj={
		'1':'/res/imgs/common/street.png'
		,'2':'/res/imgs/common/market.png'
		,'3':'/res/imgs/common/bazaar.png'
}
//将商户的类型转换成文字
var typeObj={
		'1':'街道'
		,'2':'商场'
		,'3':'市场'
}
template.helper('formatTypeState', function (value) {
	return typeObj[value]
});
template.helper('formatTypeImgSrc', function (value) {
	return typeImgObj[value]
});

