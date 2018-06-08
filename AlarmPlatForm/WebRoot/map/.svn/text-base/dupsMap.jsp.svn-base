<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="ECharts">
    <meta name="author" content="kener.linfeng@gmail.com">
	
<title>运行监控地图</title>

	<script>
		function getUrl(pro){
		  var urls = location.host ;
		  urls = urls.substring(0,urls.indexOf(':'));
		  urls="http://"+urls+":9080/"+pro;
		  window.location.href  = urls; 
		}
	</script>
</head>
<body>

 <div id="mainMap" style="height:800px;border:1px solid #ccc;padding:10px;"></div>
    <script src="<%=request.getContextPath() %>/map/dist/echarts.js"></script>
	<script src="<%=request.getContextPath() %>/map/dist/echarts-all.js.js"></script>
	<script src="<%=request.getContextPath() %>/map/dist/jquery.min.js"></script>
	<script src="<%=request.getContextPath() %>/map/echarts.js"></script>
	<script src="<%=request.getContextPath() %>/map/asset/js/codemirror.js"></script>
    <script src="<%=request.getContextPath() %>/map/asset/js/javascript.js"></script>

    <script type="text/javascript">
    require.config({
        paths: {
            echarts: './dist'
        }
    });
    require(
        [
            'echarts',
            'echarts/chart/bar',
            'echarts/chart/line',
            'echarts/chart/map'
        ],
        function (ec) {
            var myChart = ec.init(document.getElementById('mainMap'));
			
            var option = {
                title: {
					text : '全国34个省市自治区',
					subtext : 'china （滚轮或点击切换）'
				},
				tooltip : {
					trigger: 'item',
					formatter: '{b}'
				},
  
				dataRange: {//左下角的图例
				    //show:true,
					//precision : 1,
					splitNumber:3,
					min : 11,
					max : 13,
					calculable : false,
					text:['高','低'],           // 文本，默认为数值文本
					color: ['red','yellow','lightgreen']
				},
				roamController: {
					show: true,
					zlevel:1,
					x: 'right',
					z:1,
		            handleColor:'#1e90ff',
					mapTypeControl: {
						'china': true
					}
				},
                series : [{
						name: '中国',
						type: 'map',
						mapType: 'china',
						
						selectedMode : 'single',//选中模式
						hoverable: false,
						roam:false,//滚轮
                        scaleLimit:{max:2, min:0.5},//滚轮放大缩小极限
						data:[
							{name:'广东',selected:false}
						],
						markPoint : {
							symbolSize: 5,       // 标注大小，半宽（半径）参数，当图形为方向或菱形则总宽度为symbolSize * 2
							itemStyle: {
								normal: {
									borderColor: 'yellow',
									borderWidth: 1,  // 标注边线线宽，单位px，默认为1
									label: {
										show: false
									}
								},
								emphasis: {//轮廓
									borderColor: '#1e90ff',
									borderWidth: 5,
									label: {
										show: true
									}
								}
							},
							data : [
								{name:"新平", value:1},
								{name:"弥勒", value:1},
								{name:"日土", value:1},
								{name:"噶尔", value:1},
								{name:"仲巴", value:1},
								{name:"昂仁", value:1},
								{name:"那曲", value:1},
								{name:"昌都", value:1},
								{name:"察隅", value:1},
								{name:"勉县", value:1},
								{name:"安康", value:1},
								{name:"嘉峪关", value:1},
								{name:"玛曲", value:1},
								{name:"松潘", value:1},
								{name:"蓟县", value:1},
								{name:"阿勒泰", value:1},
								{name:"库尔勒", value:1},
								{name:"温州", value:1},
								{name:"中甸", value:1},
								{name:"于田", value:1},
								{name:"上海", value:1},
								{name:"长春", value:1},
								{name:"梧州", value:1},
								{name:"张家口", value:1},
								{name:"淄博", value:1},
								{name:"武汉", value:1},
								{name:"乌鲁木齐", value:1},
								{name:"湛江", value:1},
								{name:"河池", value:1},
								{name:"吉安", value:1},
								{name:"万州", value:1},
								{name:"恩施", value:1},
								{name:"襄樊", value:1},
								{name:"溧水", value:1},
								{name:"金州", value:1},
								{name:"海拉尔", value:1},
								{name:"包头", value:1},
								{name:"盐池", value:1},
								{name:"银川", value:1},
								{name:"十三陵", value:1},
								{name:"房山", value:1},
								{name:"延庆", value:1},
								{name:"高台", value:1},
								{name:"广州", value:1},
								{name:"汕头", value:1},
								{name:"北海", value:1},
								{name:"桂林", value:1},
								{name:"凤冈", value:1},
								{name:"郑州", value:1},
								{name:"麻阳", value:1},
								{name:"格尔木", value:1},
								{name:"玉树", value:1},
								{name:"荣成", value:1},
								{name:"甘孜", value:1},
								{name:"泸州", value:1},
								{name:"下关", value:1},
								{name:"泰安", value:1},
								{name:"乌什", value:1},
								{name:"古北口", value:1},
								{name:"宝坻", value:1},
								{name:"南通", value:1},
								{name:"蚌埠", value:1},
								{name:"平潭", value:1},
								{name:"嘉祥", value:1},
								{name:"昌邑", value:1},
								{name:"济源", value:1},
								{name:"长寿", value:1},
								{name:"攀枝花", value:1},
								{name:"小金", value:1},
								{name:"喜德", value:1},
								{name:"宁南", value:1},
								{name:"天全", value:1},
								{name:"马边", value:1},
								{name:"筠连", value:1},
								{name:"施甸", value:1},
								{name:"临沧", value:1},
								{name:"永胜", value:1},
								{name:"思茅", value:1},
								{name:"独山子", value:1},
								{name:"石河子", value:1},
								{name:"富蕴", value:1},
								{name:"芨芨台", value:1},
								{name:"德令哈", value:1},
								{name:"木垒", value:1},
								{name:"二连浩特", value:1},
								{name:"伊宁", value:1},
								{name:"武夷山", value:1},
								{name:"拉萨", value:1},
								{name:"厦门", value:1},
								{name:"隆尧", value:1},
								{name:"琼中", value:1},
								{name:"鹤岗", value:1},
								{name:"百色", value:1},
								{name:"改则", value:1},
								{name:"朗勃拉邦", value:1},
								{name:"茫崖", value:1},
								{name:"南山", value:1},
								{name:"密支那", value:1},
								{name:"腊肖", value:1},
								{name:"尼玛", value:1},
								{name:"双湖", value:1},
								{name:"正蓝旗", value:1},
								{name:"秭归", value:1},
								{name:"茂县", value:1},
								{name:"耿马", value:1},
								{name:"延安", value:1},
								{name:"天水", value:1},
								{name:"平凉", value:1},
								{name:"冷湖", value:1},
								{name:"和田", value:1},
								{name:"榆次", value:1},
								{name:"阿鲁科尔沁", value:1},
								{name:"抚远", value:1},
								{name:"丽江", value:1},
								{name:"乌拉斯台", value:1},
								{name:"巴音布鲁克", value:1},
								{name:"额济纳旗", value:1},
								{name:"长岭", value:1},
								{name:"珠峰", value:1},
								{name:"古县", value:1},
								{name:"阿拉善右旗", value:1},
								{name:"乌拉特后旗", value:1},
								{name:"东乌珠穆沁旗", value:1},
								{name:"鄂伦春旗", value:1},
								{name:"丹东", value:1},
								{name:"漠河", value:1},
								{name:"绥阳", value:1},
								{name:"鼎新", value:1},
								{name:"武清", value:1},
								{name:"阿拉善左旗", value:1},
								{name:"葫芦岛", value:1},
								{name:"舟山", value:1},
								{name:"三亚", value:1},
								{name:"理塘", value:1},
								{name:"冕宁", value:1},
								{name:"勐海", value:1},
								{name:"楚雄", value:1},
								{name:"青岛", value:1},
								{name:"若羌", value:1},
								{name:"塔什库尔干", value:1},
								{name:"腾冲", value:1},
								{name:"昆明", value:1},
								{name:"水城", value:1},
								{name:"烟台", value:1},
								{name:"珠海", value:1},
								{name:"道孚", value:1},
								{name:"九龙", value:1},
								{name:"盐源", value:1},
								{name:"石棉", value:1},
								{name:"遂宁", value:1},
								{name:"广元", value:1},
								{name:"南充", value:1},
								{name:"瑞丽", value:1},
								{name:"墨江", value:1},
								{name:"通海", value:1},
								{name:"金平", value:1},
								{name:"会泽", value:1},
								{name:"太白", value:1},
								{name:"敦煌", value:1},
								{name:"民勤", value:1},
								{name:"陇西", value:1},
								{name:"沱沱河", value:1},
								{name:"都兰", value:1},
								{name:"班玛", value:1},
								{name:"中卫", value:1},
								{name:"塔中", value:1},
								{name:"大姚", value:1},
								{name:"西宁", value:1},
								{name:"大同", value:1},
								{name:"托克托", value:1},
								{name:"营口", value:1},
								{name:"延吉", value:1},
								{name:"连云港", value:1},
								{name:"安庆", value:1},
								{name:"浏阳", value:1},
								{name:"南宁", value:1},
								{name:"炉霍", value:1},
								{name:"木里", value:1},
								{name:"贵阳", value:1},
								{name:"澜沧", value:1},
								{name:"景东", value:1},
								{name:"文山", value:1},
								{name:"日喀则", value:1},
								{name:"叶城", value:1},
								{name:"且末", value:1},
								{name:"布尔津", value:1},
								{name:"鄯善", value:1},
								{name:"克拉玛依", value:1},
								{name:"滨海", value:1},
								{name:"阳原", value:1},
								{name:"承德", value:1},
								{name:"唐山", value:1},
								{name:"鹿泉", value:1},
								{name:"临汾", value:1},
								{name:"长治", value:1},
								{name:"灵丘", value:1},
								{name:"乌海", value:1},
								{name:"乌加河", value:1},
								{name:"乌兰浩特", value:1},
								{name:"阿古拉", value:1},
								{name:"长白山", value:1},
								{name:"五大连池", value:1},
								{name:"盐城", value:1},
								{name:"海原", value:1},
								{name:"布伦口", value:1},
								{name:"乌恰", value:1},
								{name:"巴楚", value:1},
								{name:"昭苏", value:1},
								{name:"温泉", value:1},
								{name:"沧县", value:1},
								{name:"赤城", value:1},
								{name:"荆门", value:1},
								{name:"玛多", value:1},
								{name:"临沂", value:1},
								{name:"夏县", value:1},
								{name:"太原", value:1},
								{name:"越西", value:1},
								{name:"当雄", value:1},
								{name:"新源", value:1},
								{name:"云龙", value:1},
								{name:"东川", value:1},
								{name:"建德", value:1},
								{name:"蒙自", value:1},
								{name:"亚东", value:1},
								{name:"华阴", value:1},
								{name:"安西", value:1},
								{name:"兰州", value:1},
								{name:"刚察", value:1},
								{name:"塔城", value:1},
								{name:"库车", value:1},
								{name:"青河", value:1},
								{name:"永暑礁", value:1},
								{name:"永兴岛", value:1},
								{name:"岢岚", value:1},
								{name:"霞浦", value:1},
								{name:"湖口", value:1},
								{name:"海口", value:1},
								{name:"乡城", value:1},
								{name:"旬邑", value:1},
								{name:"西安", value:1},
								{name:"鹤壁", value:1},
								{name:"哈尔滨", value:1},
								{name:"确山", value:1},
								{name:"沈阳", value:1},
								{name:"巴中", value:1},
								{name:"巴嘎", value:1},
								{name:"韶关", value:1},
								{name:"定西", value:1},
								{name:"民乐", value:1},
								{name:"元谋", value:1},
								{name:"古浪", value:1},
								{name:"景泰", value:1},
								{name:"岷县", value:1},
								{name:"武都", value:1},
								{name:"静宁", value:1},
								{name:"清水", value:1},
								{name:"祁连", value:1},
								{name:"玛沁", value:1},
								{name:"门源", value:1},
								{name:"同仁", value:1}
							]
						},
						geoCoord: {
							"新平":[101.91,24.10],
							"弥勒":[103.38,24.40],
							"日土":[79.72,33.39],
							"噶尔":[80.11,32.52],
							"仲巴":[84.16,29.68],
							"昂仁":[87.18,29.27],
							"那曲":[92.11,31.49],
							"昌都":[97.17,31.14],
							"察隅":[97.47,28.66],
							"勉县":[106.69,33.13],
							"安康":[108.77,32.79],
							"嘉峪关":[98.22,39.81],
							"玛曲":[102.06,34.02],
							"松潘":[103.58,32.65],
							"蓟县":[117.53,40.08],
							"阿勒泰":[88.14,47.86],
							"库尔勒":[86.19,41.79],
							"温州":[120.76,27.93],
							"中甸":[99.70,27.82],
							"于田":[81.97,36.43],
							"上海":[121.2,30.9],
							"长春":[125.43,43.78],
							"梧州":[111.23,23.48],
							"张家口":[114.90,40.83],
							"淄博":[117.99,36.81],
							"武汉":[114.36,30.53],
							"乌鲁木齐":[87.60,43.81],
							"湛江":[110.30,21.16],
							"河池":[108.04,24.70],
							"吉安":[115.03,26.44],
							"万州":[108.49,30.77],
							"恩施":[109.49,30.28],
							"襄樊":[112.04,32.00],
							"溧水":[119.42,31.35],
							"金州":[121.74,39.09],
							"海拉尔":[119.74,49.27],
							"包头":[110.02,40.60],
							"盐池":[107.44,37.78],
							"银川":[106.27,38.49],
							"十三陵":[116.22,40.25],
							"房山":[115.89,39.61],
							"延庆":[115.97,40.37],
							"高台":[99.81,39.41],
							"广州":[113.34,23.19],
							"汕头":[116.60,23.42],
							"北海":[109.21,21.65],
							"桂林":[110.31,25.19],
							"凤冈":[107.73,27.98],
							"郑州":[113.10,34.52],
							"麻阳":[109.80,27.88],
							"格尔木":[94.77,36.15],
							"玉树":[97.02,33.01],
							"荣成":[122.42,37.17],
							"甘孜":[100.02,31.61],
							"泸州":[105.41,28.87],
							"下关":[100.25,25.61],
							"泰安":[117.12,36.21],
							"乌什":[79.2,41.2],
							"古北口":[119.16,40.69],
							"宝坻":[117.40,39.70],
							"南通":[120.89,31.95],
							"蚌埠":[117.3,32.91],
							"平潭":[119.77,25.50],
							"嘉祥":[116.35,35.43],
							"昌邑":[119.46,36.75],
							"济源":[112.45,35.16],
							"长寿":[107.23,29.91],
							"攀枝花":[101.74,26.50],
							"小金":[102.37,31.00],
							"喜德":[102.44,28.30],
							"宁南":[102.72,27.06],
							"天全":[102.77,30.07],
							"马边":[103.53,28.84],
							"筠连":[104.52,28.18],
							"施甸":[99.19,24.71],
							"临沧":[100.08,23.87],
							"永胜":[100.75,26.68],
							"思茅":[101.05,22.74],
							"独山子":[84.89,44.31],
							"石河子":[86.11,44.21],
							"富蕴":[89.54,47.00],
							"芨芨台":[94.34,42.85],
							"德令哈":[97.38,37.38],
							"木垒":[90.30,43.81],
							"二连浩特":[111.94,43.64],
							"伊宁":[81.53,43.97],
							"武夷山":[117.99,27.62],
							"拉萨":[91.10,29.66],
							"厦门":[118.08,24.45],
							"隆尧":[114.71,37.40],
							"琼中":[109.85,19.03],
							"鹤岗":[130.24,47.35],
							"百色":[106.67,23.92],
							"改则":[84.07,32.29],
							"朗勃拉邦":[102.15,19.77],
							"茫崖":[91.45,37.90],
							"南山":[97.77,20.88],
							"密支那":[97.45,25.43],
							"腊肖":[104.97,18.18],
							"尼玛":[87.23,31.80],
							"双湖":[88.83,33.20],
							"正蓝旗":[115.98,42.23],
							"秭归":[110.97,30.84],
							"茂县":[103.85,31.67],
							"耿马":[99.39,23.55],
							"延安":[109.46,36.62],
							"天水":[105.91,34.49],
							"平凉":[106.59,35.55],
							"冷湖":[93.20,38.44],
							"和田":[79.05,37.16],
							"榆次":[112.89,37.63],
							"阿鲁科尔沁":[120.11,43.86],
							"抚远":[134.28,48.37],
							"丽江":[100.03,26.70],
							"乌拉斯台":[86.72,42.92],
							"巴音布鲁克":[83.72,42.83],
							"额济纳旗":[101.06,41.96],
							"长岭":[123.52,43.79],
							"珠峰":[86.55,27.59],
							"古县":[111.90,36.25],
							"阿拉善右旗":[101.68,39.21],
							"乌拉特后旗":[107.06,41.08],
							"东乌珠穆沁旗":[116.96,45.51],
							"鄂伦春旗":[123.73,50.58],
							"丹东":[124.33,40.03],
							"漠河":[122.51,52.98],
							"绥阳":[130.91,44.43],
							"鼎新":[100.20,40.98],
							"武清":[117.10,39.47],
							"阿拉善左旗":[105.67,38.81],
							"葫芦岛":[120.85,40.68],
							"舟山":[121.99,30.07],
							"三亚":[109.53,18.24],
							"理塘":[100.22,29.99],
							"冕宁":[102.17,28.33],
							"勐海":[100.45,21.93],
							"楚雄":[101.48,25.05],
							"青岛":[120.30,36.08],
							"若羌":[88.17,39.02],
							"塔什库尔干":[75.23,37.76],
							"腾冲":[98.44,24.96],
							"昆明":[102.79,25.02],
							"水城":[104.86,26.59],
							"烟台":[121.44,37.48],
							"珠海":[113.57,22.28],
							"道孚":[101.12,30.98],
							"九龙":[101.5,29.01],
							"盐源":[101.51,27.43],
							"石棉":[102.35,29.23],
							"遂宁":[105.56,30.51],
							"广元":[105.85,32.44],
							"南充":[105.88,30.98],
							"瑞丽":[97.85,24.00],
							"墨江":[101.67,23.42],
							"通海":[102.75,24.12],
							"金平":[103.23,22.79],
							"会泽":[103.29,26.41],
							"太白":[107.32,34.06],
							"敦煌":[94.69,40.14],
							"民勤":[103.09,38.63],
							"陇西":[104.65,35.00],
							"沱沱河":[92.44,34.22],
							"都兰":[98.10,36.30],
							"班玛":[100.74,32.93],
							"中卫":[105.24,37.59],
							"塔中":[83.66,38.97],
							"大姚":[101.33,25.72],
							"西宁":[101.77,36.60],
							"大同":[113.39,40.12],
							"托克托":[111.25,40.26],
							"营口":[122.60,40.68],
							"延吉":[129.50,42.87],
							"连云港":[119.47,34.72],
							"安庆":[116.99,30.62],
							"浏阳":[113.04,28.16],
							"南宁":[108.15,22.57],
							"炉霍":[100.67,31.39],
							"木里":[101.28,27.93],
							"贵阳":[106.67,26.47],
							"澜沧":[99.95,22.56],
							"景东":[100.88,24.44],
							"文山":[104.25,23.41],
							"日喀则":[88.87,29.25],
							"叶城":[77.45,37.85],
							"且末":[85.53,38.12],
							"布尔津":[86.86,47.70],
							"鄯善":[90.26,42.89],
							"克拉玛依":[84.91,45.61],
							"滨海":[117.69,39.08],
							"阳原":[114.16,40.13],
							"承德":[117.92,41.02],
							"唐山":[118.30,39.74],
							"鹿泉":[114.31,38.25],
							"临汾":[111.37,36.08],
							"长治":[113.18,36.23],
							"灵丘":[114.02,39.38],
							"乌海":[106.84,39.66],
							"乌加河":[108.07,41.30],
							"乌兰浩特":[122.03,46.04],
							"阿古拉":[122.63,43.30],
							"长白山":[128.11,42.41],
							"五大连池":[126.14,48.67],
							"盐城":[120.02,33.38],
							"海原":[105.65,36.55],
							"布伦口":[74.97,38.65],
							"乌恰":[75.24,39.74],
							"巴楚":[78.77,39.81],
							"昭苏":[80.36,42.68],
							"温泉":[81.00,44.96],
							"沧县":[116.93,38.47],
							"赤城":[115.84,40.88],
							"荆门":[112.17,31.12],
							"玛多":[98.21,34.92],
							"临沂":[118.29,35.00],
							"夏县":[111.23,35.11],
							"太原":[112.43,37.71],
							"越西":[102.51,28.65],
							"当雄":[91.10,30.49],
							"新源":[83.26,43.40],
							"云龙":[99.37,25.89],
							"东川":[103.18,26.11],
							"建德":[119.27,29.48],
							"蒙自":[103.4,23.35],
							"亚东":[88.91,27.44],
							"华阴":[110.08,34.48],
							"安西":[95.76,40.52],
							"兰州":[103.67,36.08],
							"刚察":[100.13,37.33],
							"塔城":[82.87,46.82],
							"库车":[83.05,41.88],
							"青河":[90.37,46.65],
							"永暑礁":[112.88,9.55],
							"永兴岛":[112.17,16.08],
							"岢岚":[111.58,38.82],
							"霞浦":[120.02,26.88],
							"湖口":[116.24,29.73],
							"海口":[110.25,19.99],
							"乡城":[99.80,28.94],
							"旬邑":[108.39,35.17],
							"西安":[108.99,34.18],
							"鹤壁":[114.52,35.66],
							"哈尔滨":[126.62,45.70],
							"确山":[114.03,32.85],
							"沈阳":[123.58,41.83],
							"巴中":[106.74,31.84],
							"巴嘎":[81.43,30.84],
							"韶关":[113.59,24.85],
							"定西":[104.60,35.55],
							"民乐":[100.82,38.44],
							"元谋":[101.86,25.69],
							"古浪":[102.89,37.46],
							"景泰":[104.06,37.18],
							"岷县":[104.02,34.43],
							"武都":[104.82,33.42],
							"静宁":[105.76,35.53],
							"清水":[106.21,34.75],
							"祁连":[100.24,38.19],
							"玛沁":[100.25,34.48],
							"门源":[101.40,37.47],
							"同仁":[102.01,35.52]
						}
				}]
            };
			
			var ecConfig = require('echarts/config');
			var curIndx = 0;
			var mapType = [
				'china',
				'广东', '青海', '四川', '海南', '陕西', 
				'甘肃', '云南', '湖南', '湖北', '黑龙江',
				'贵州', '山东', '江西', '河南', '河北',
				'山西', '安徽', '福建', '浙江', '江苏', 
				'吉林', '辽宁', '台湾',
				'新疆', '广西', '宁夏', '内蒙古', '西藏', 
				'北京', '天津', '上海', '重庆',
				'香港', '澳门'
			];

			myChart.on(ecConfig.EVENT.MAP_SELECTED, function (param){
				var len = mapType.length;
				var mt = mapType[curIndx % len];
				if (mt == 'china') {
					// 全国选择时指定到选中的省份
					var selected = param.selected;
					for (var i in selected) {
						if (selected[i]) {
							mt = i;
							while (len--) {
								if (mapType[len] == mt) {
									curIndx = len;
								}
							}
							break;
						}
					}
					option.tooltip.formatter = '滚轮切换省份或点击返回全国<br/>{b}';
				}
				else {
					curIndx = 0;
					mt = 'china';
					option.tooltip.formatter = '滚轮切换或点击进入该省<br/>{b}';
				}
				option.series[0].mapType = mt;
				option.title.subtext = mt + ' （滚轮或点击切换）';
				myChart.setOption(option, true);
			});
            myChart.setOption(option);
           
		    window.setInterval(hello,10000); 
			function hello(){
			   //option.series[0].markPoint.data.push({ name: "青岛",value:Math.floor(Math.random()*1000)},{ name: "泰安",value:Math.floor(Math.random()*1000) });
               //myChart.setOption(option,true);
			   getlist();
			}
			
			function getlist(){
			    //alert('1');
				$.ajax({
					type:"get",
					url:"/AlarmPlatForm/alarmSendSearch/getMapDupsState.action",
					data:"",
					dataType:"json",
					success:function(msg){
						var len = msg.length;
						for(i=0;i<len;i++){ 
							option.series[0].markPoint.data.push({ name: msg[i].name,value:msg[i].siteState});
						}
						myChart.setOption(option,true);
						//alert(2);
					},
					error:function(e){
						console.log(e);	
					}

				});
			}
			
        }
    );
	
	
	

	
    </script>
</body>

</html>