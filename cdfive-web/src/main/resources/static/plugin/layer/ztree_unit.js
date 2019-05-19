/**
 * Created by wjm on 2016/7/26.
 */
/*
 * 本文件是基于ztree的进一步封装，目的是方面大家的阅读和使用，并且将所有关于ztree的操作集中在此文件中，便于大家的阅读和使用
 * 提供 如下方法
 * init：
 * 使用方法:$(ele).ztreeUnit('init',param);
 * 参数：param:{config:{},data:data};
 * 目的：使用 param中的配置config(非必须)，和数据data(必须)初始化ztree
 * 返回值:null
 * getCheckNodes:
 * 使用方法:$(ele).ztreeUnit('getCheckNodes');
 * 目的：获取$(ele)这个dom元素中包含的ztree中已经选中的节点的nodes数据
 * 返回值:格式数组，选中节点的node数据
 * setChecked：
 * 使用方法:$(ele).ztreeUnit('setChecked',params);
 * 参数：param:{key:[]};
 * 目的：将param中的key作为属性值将该属性值与param.key相同的节点选中
 * 返回值:null
 * getNodesByParam：
 * 使用方法:$(ele).ztreeUnit('getNodesByParam',params);
 * 参数：param:{key:[]};
 * 目的：获取param中的key作为属性值将该属性值与param.key相同的节点
 * 返回值:null
 * refreshTreeByParam：
 * 使用方法:$(ele).ztreeUnit('refreshTreeByParam',params);
 * 参数：param:{key:[]};
 * 目的：属性param中的key作为属性值将该属性值与param.key相同的节点
 * 返回值:null
 * getSelectedNodes：
 * 使用方法:$(ele).ztreeUnit('getSelectedNodes');
 * 目的：获取当前选中的节点
 * 返回值:格式数组，选中节点的node数据
 * */
(function(){
	var ZtreeUnit=function(ele){
		this.ztreeId='js-ztree-'+new Date().getTime();
		this.$Container=ele;
		this.jqueryMap={};
		this.configMap = {
				data : {
					simpleData : {
						enable : true
					}
				},
				view : {
					showLine : false
				}
			};
		this.ztreeObj;
	}
	ZtreeUnit.prototype.setHtml=function(){
		this.$Container.html('<ul id="'+this.ztreeId+'" class="ztree"></ul>');
	}
	ZtreeUnit.prototype.setJqueryMap=function(){
		this.jqueryMap={
				 $Container:this.$Container,
				 $ztree:this.$Container.find('#'+this.ztreeId)
		 }
	}
	ZtreeUnit.prototype.setConfigMap=function(inputMap){
		this.configMap=$.extend({}, this.configMap, inputMap)
	}
	
	ZtreeUnit.prototype.getCheckNodes=function(){
		var $this=this;
		if($this.configMap.check.enable==false){
			console.log('当前树无法进行选中操作');
			return;
		}
		  var _nodes =$this.ztreeObj.getCheckedNodes(true);
		  return _nodes;
	}
	ZtreeUnit.prototype.getSelectedNodes=function(){
		var $this=this;
	  	var _nodes =$this.ztreeObj.getSelectedNodes();
	  	return _nodes;
	}
	ZtreeUnit.prototype.getNodesByParam=function(params){
		debugger;
		var $this=this;
		var _ztreeObj=$this.ztreeObj;
		var nodes=[];
		for(var key in params){
			var _params=params[key];
			for(var i=0,l=_params.length;i<l;i++){
				var _nodes=_ztreeObj.getNodesByParam(key,_params[i],null)
				nodes.push.apply( nodes, _nodes );
			}
		}
		return nodes;
	}
	ZtreeUnit.prototype.setChecked=function(params){
		var $this=this;
		var _ztreeObj=$this.ztreeObj;
		_ztreeObj.expandAll(true);
		_ztreeObj.checkAllNodes(false);
		for(var key in params){
			var _params=params[key];
			for(var i=0,l=_params.length;i<l;i++){
				var _nodes = _ztreeObj.getNodesByParam(key, _params[i], null);
				var _len = _nodes.length
				if (_len == 0)
					continue;
				for (var j = 0; j < _len; j++) {
					_ztreeObj.checkNode(_nodes[j], true, true);
				}
			}
		}
	}
	//refreshTreeByParam 通过属性刷新节点
	ZtreeUnit.prototype.refreshTreeByParam=function(params){
		var $this=this;
		var _ztreeObj=$this.ztreeObj;
		if(!params){
			_ztreeObj.reAsyncChildNodes(null, "refresh");
			return;
		}
		for(var key in params){
			var _params=params[key];
			for(var i=0,l=_params.length;i<l;i++){
				var _nodes = _ztreeObj.getNodesByParam(key, _params[i], null);
				var _len = _nodes.length
				if (_len == 0)
					continue;
				for (var j = 0; j < _len; j++) {
					if(nodes[i].isParent==false){
						nodes[i].isParent=true;//解决无子节点树 不刷新问题
					}
					ztreeObj.reAsyncChildNodes(nodes[i], "refresh");
				}
			}
		}
	}
	ZtreeUnit.prototype.init=function(data){
		this.setJqueryMap();
		this.setHtml();
		if(data.config){
			this.setConfigMap(data.config);
		}
		this.ztreeObj=$.fn.zTree.init($("#"+this.ztreeId),this.configMap,data.data);
	}
	/* 获取选中数据 */
    $.fn.extend({
        "ztreeUnit": function (option1,option2) {
        	var $this=$(this); 
        	var data=$this.data("adas.ZtreeUnit");
        	if(!data){
    			$this.data("adas.ZtreeUnit",(data = new ZtreeUnit($this)));
    		}
        	if(option1=='init'){
        		data.init(option2);
        	}else if(option1=='getCheckNodes'){
        		return data.getCheckNodes();
        	}else if(option1=='setChecked'){
        		return data.setChecked(option2);
        	}else if(option1=='getNodesByParam'){
        		return data.getNodesByParam(option2);
        	}else if(option1=='refreshTreeByParam'){
        		return data.refreshTreeByParam(option2);
        	}else if(option1=='getSelectedNodes'){
        		return data.getSelectedNodes();
        	}
        }
    });
})()
