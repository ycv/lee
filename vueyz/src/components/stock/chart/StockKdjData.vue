<template>
  <div class="stock-chart-box">
    <div class="proCharts" ref='wrCharts'></div>
  </div>
</template>

<script>
//白色(天蓝)线代表K线，黄色线代表D线，紫色线代表J线。
//KD的取值范围都是0～100  J值的取值范围可以超过100和低于0
//如果K、D、J值都大于50时，为多头市场，后市看涨;如果K、D、J值都小于50时，为空头市场，后市看空。
//
// K、D、J这三值在20以下为超卖区,是买入信号;K、D、J这三值在80以上为超买区,是卖出信号；
//当J大于K、K大于D时，即3条指标曲线呈多头排列，显示当前为多头市场;当3条指标出现黄金交叉时，指标发出买入信号。

// 当紫线上穿黄白两线的时候，KDJ形成金叉。  当紫线下穿黄白两线的时候，KDJ形成死叉。
//当k<d，k线向上突破d线时，是买进信号，进入上升趋势；
// KDJ低位金叉，行情上涨的几率大。KDJ高位死叉，行情下跌的几率大。
// 一般而言， K线由下转上为买入信号，由上转下为卖出信号。　

import {isEmptyObj} from "@/utils/common/validate";
import * as echarts from "echarts";

export default {
  name: "StockKdjData",
  data() {
    return {
      echartsOption: {
        // 全图默认背景
        backgroundColor: 'rgba(154,216,246,0.7)',
        // 默认色板 K线  D线  J线  日线cci  成交金额
        color: ['rgb(171,230,241)', 'rgb(236,217,10)', 'rgb(154,7,224)', 'rgb(2,97,245)', 'rgb(232,10,128)'],
        // 图表标题
        title: {
          // 水平安放位置，默认为左对齐，可选为：// 'center' | 'left' | 'right'| {number}（x坐标，单位px）
          x: 'left',
          // 垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）
          y: 'top',
          textAlign: null,        // 水平对齐方式，默认根据x设置自动调整
          backgroundColor: 'rgba(0,0,0,0)',
          borderColor: '#ccc',       // 标题边框颜色
          borderWidth: 0,            // 标题边框线宽，单位px，默认为0（无边框）
          padding: 5,                // 标题内边距，单位px，默认各方向内边距为5，
          itemGap: 10,               // 主副标题纵向间隔，单位px，默认为10，
          textStyle: {
            fontSize: 18,
            fontWeight: 'bolder', textShadowOffsetY: '100%',
            color: '#333'          // 主标题文字颜色
          },
          subtextStyle: {
            color: 'rgba(40,37,37,0.96)'          // 副标题文字颜色
          },
          subtext: '-',             //副标题
          text: '-'                 //主标题
        },
        //提示框
        tooltip: {
          //当trigger为’item’时只会显示该点的数据，为’axis’时显示该列下所有坐标轴所对应的数据。
          trigger: 'axis',           // 触发类型，默认数据触发，见下图，可选为：'item' | 'axis'
          showDelay: 50,             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
          hideDelay: 100,            // 隐藏延迟，单位ms
          transitionDuration: 0.4,  // 动画变换时间，单位s
          backgroundColor: 'rgba(0,0,0,0.7)',     // 提示背景颜色，默认为透明度为0.7的黑色
          borderColor: '#333',       // 提示边框颜色
          borderRadius: 4,           // 提示边框圆角，单位px，默认为4
          borderWidth: 0,            // 提示边框线宽，单位px，默认为0（无边框）
          padding: 5,                // 提示内边距，单位px，默认各方向内边距为5，
          axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'line',         // 默认为直线，可选为：'line' | 'shadow'
            lineStyle: {          // 直线指示器样式设置
              color: '#48b',
              width: 2,
              type: 'solid'
            },
            shadowStyle: {                       // 阴影指示器样式设置
              width: 'auto',                    // 阴影大小
              color: 'rgba(150,150,150,0.3)'    // 阴影颜色
            }
          },
          textStyle: {
            color: '#fff'
          }
        },
        // 区域缩放控制器
        dataZoom: {
          // 布局方式，默认为水平布局，可选为：'horizontal' | 'vertical'
          orient: 'horizontal',
          // 水平安放位置，默认为根据grid参数适配，可选为：{number}（x坐标，单位px）
          // x: {number},
          // 垂直安放位置，默认为根据grid参数适配，可选为：{number}（y坐标，单位px）
          // y: {number},
          // width: {number},        // 指定宽度，横向布局时默认为根据grid参数适配
          // height: {number},       // 指定高度，纵向布局时默认为根据grid参数适配
          backgroundColor: 'rgba(0,0,0,0)',       // 背景颜色
          dataBackgroundColor: '#eee',            // 数据背景颜色
          fillerColor: 'rgba(144,197,237,0.2)',   // 填充颜色
          handleColor: 'rgba(70,130,180,0.8)'     // 手柄颜色
        },
        //图例的类型
        legend: {
          // 布局方式，默认为水平布局，可选为：'horizontal' | 'vertical'
          orient: 'horizontal',
          // 水平安放位置，默认为全图居中，可选为：'center' | 'left' | 'right'| {number}（x坐标，单位px）
          x: 'center',
          // 垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）
          y: 'top',
          backgroundColor: 'rgba(0,0,0,0)',
          borderColor: '#ccc',       // 图例边框颜色
          borderWidth: 0,            // 图例边框线宽，单位px，默认为0（无边框）
          padding: 5,                // 图例内边距，单位px，默认各方向内边距为5，
          itemGap: 10,               // 各个item之间的间隔，单位px，默认为10，横向布局时为水平间隔，纵向布局时为纵向间隔
          itemWidth: 20,             // 图例图形宽度
          itemHeight: 14,            // 图例图形高度
          textStyle: {
            fontFamily: "sans-serif", // 文字的字体系列。
            fontWeight: "bold", // 文字字体的粗细。 'normal' 'bold'  'bolder' 'lighter'
            color: '#333'             // 图例文字颜色
          },
          align: "auto",          // 图例标记和文本的对齐
          //图例icon图标
          icon: 'roundRect',          //circle  roundRect
          data: [
            {
              name: "k",
              textStyle: {
                color: '#fff'
              }

            }, {
              name: "d",
              textStyle: {
                color: '#fff'
              }
            }, {
              name: "j",
              textStyle: {
                color: '#fff'
              }
            }, {
              name: "cci",
              textStyle: {
                color: '#fff'
              }
            }, {
              name: "成交金额",
              textStyle: {
                color: '#fff'
              }
            }
          ]
        },
        // 值域
        dataRange: {
          // 布局方式，默认为垂直布局，可选为：'horizontal' | 'vertical'
          orient: 'vertical',
          // 水平安放位置，默认为全图左对齐，可选为：'center' | 'left' | 'right'| {number}（x坐标，单位px）
          x: 'left',
          // 垂直安放位置，默认为全图底部，可选为：top' | 'bottom' | 'center'| {number}（y坐标，单位px）
          y: 'bottom',
          backgroundColor: 'rgba(0,0,0,0)',
          borderColor: '#ccc',       // 值域边框颜色
          borderWidth: 0,            // 值域边框线宽，单位px，默认为0（无边框）
          padding: 5,                // 值域内边距，单位px，默认各方向内边距为5，
          itemGap: 10,               // 各个item之间的间隔，单位px，默认为10，横向布局时为水平间隔，纵向布局时为纵向间隔
          itemWidth: 10,             // 值域图形宽度，线性渐变水平布局宽度为该值 * 10
          itemHeight: 100,            // 值域图形高度，线性渐变垂直布局高度为该值 * 10
          //splitNumber: 5,            // 分割段数，默认为5，为0时为线性渐变
          // color: ['#1e90ff', '#f0ffff'],//颜色
          text: ['高', '低'],         // 文本，默认为数值文本
          textStyle: {
            color: '#333'          // 值域文字颜色
          }
        },
        toolbox: {
          // 布局方式，默认为水平布局，可选为：'horizontal' | 'vertical'
          orient: 'horizontal',
          // 水平安放位置，默认为全图右对齐，可选为： 'center' | 'left' | 'right'| {number}（x坐标，单位px）
          x: 'right',
          // 垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center'| {number}（y坐标，单位px）
          y: 'top',
          color: ['#1e90ff', '#22bb22', '#4b0082', '#d2691e'],
          backgroundColor: 'rgba(0,0,0,0)', // 工具箱背景颜色
          borderWidth: 0,            // 工具箱边框线宽，单位px，默认为0（无边框）
          padding: 5,                // 工具箱内边距，单位px，默认各方向内边距为5，
          itemGap: 10,               // 各个item之间的间隔，单位px，默认为10，横向布局时为水平间隔，纵向布局时为纵向间隔
          itemSize: 16,              // 工具箱图形宽度
          featureImageIcon: {},     // 自定义图片icon
          featureTitle: {
            mark: '辅助线开关',
            markUndo: '删除辅助线',
            markClear: '清空辅助线',
            dataZoom: '区域缩放',
            dataZoomReset: '区域缩放后退',
            dataView: '数据视图',
            lineChart: '折线图切换',
            barChart: '柱形图切换',
            restore: '还原',
            saveAsImage: '保存为图片'
          }
        },
        // 网格
        grid: {
          left: '5%',
          // right: '4%',
          bottom: '2%',
          top: '15%',
          backgroundColor: 'rgba(0,0,0,0)',
          borderWidth: 2,
          borderColor: '#ccc',
          width: '90%',
          // height: '80%',
          containLabel: true //grid区域是否包含坐标轴的刻度标签 true
        },

        // 类目轴
        categoryAxis: {
          position: 'bottom',    // 位置
          nameLocation: 'end',   // 坐标轴名字位置，支持'start' | 'end'
          boundaryGap: true,     // 类目起始和结束两端空白策略
          axisLine: {            // 坐标轴线
            show: true,        // 默认显示，属性show控制显示与否
            lineStyle: {       // 属性lineStyle控制线条样式
              color: '#48b',
              width: 2,
              type: 'solid'
            }
          },
          axisTick: {            // 坐标轴小标记
            show: true,       // 属性show控制显示与否，默认不显示
            interval: 'auto',
            // onGap: null,
            inside: false,    // 控制小标记是否在grid里
            length: 5,         // 属性length控制线长
            lineStyle: {       // 属性lineStyle控制线条样式
              color: '#333',
              width: 1
            }
          },
          axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
            show: true,
            interval: 'auto',
            rotate: 0,
            margin: 8,
            // formatter: null,
            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
              color: '#333'
            }
          },
          splitLine: {           // 分隔线
            show: true,        // 默认显示，属性show控制显示与否
            // onGap: null,
            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
              color: ['#ccc'],
              width: 1,
              type: 'solid'
            }
          },
          splitArea: {           // 分隔区域
            show: false,       // 默认不显示，属性show控制显示与否
            // onGap: null,
            areaStyle: {       // 属性areaStyle（详见areaStyle）控制区域样式
              color: ['rgba(250,250,250,0.3)', 'rgba(200,200,200,0.3)']
            }
          }
        },


        // 数值型坐标轴默认参数
        valueAxis: {
          position: 'left',      // 位置
          nameLocation: 'end',   // 坐标轴名字位置，支持'start' | 'end'
          nameTextStyle: {},     // 坐标轴文字样式，默认取全局样式
          boundaryGap: [0, 0],   // 数值起始和结束两端空白策略
          splitNumber: 5,        // 分割段数，默认为5
          axisLine: {            // 坐标轴线
            show: true,        // 默认显示，属性show控制显示与否
            lineStyle: {       // 属性lineStyle控制线条样式
              color: '#48b',
              width: 2,
              type: 'solid'
            }
          },
          axisTick: {            // 坐标轴小标记
            show: false,       // 属性show控制显示与否，默认不显示
            inside: false,    // 控制小标记是否在grid里
            length: 5,         // 属性length控制线长
            lineStyle: {       // 属性lineStyle控制线条样式
              color: '#333',
              width: 1
            }
          },
          axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
            show: true,
            rotate: 0,
            margin: 8,
            // formatter: null,
            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
              color: '#333'
            }
          },
          splitLine: {           // 分隔线
            show: true,        // 默认显示，属性show控制显示与否
            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
              color: ['#ccc'],
              width: 1,
              type: 'solid'
            }
          },
          splitArea: {           // 分隔区域
            show: false,       // 默认不显示，属性show控制显示与否
            areaStyle: {       // 属性areaStyle（详见areaStyle）控制区域样式
              color: ['rgba(250,250,250,0.3)', 'rgba(200,200,200,0.3)']
            }
          }
        },

        // 折线图默认参数
        line: {
          itemStyle: {
            normal: {
              // color: 各异,
              label: {
                show: false,            //false true
                //  默认自适应，水平布局为'top'，垂直布局为'right'，可选为'inside'|'left'|'right'|'top'|'bottom'
                // position: 'right'
                // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
              },
              lineStyle: {
                width: 2,
                type: 'solid',
                shadowColor: 'rgba(0,0,0,0)', //默认透明
                shadowBlur: 5,
                shadowOffsetX: 3,
                shadowOffsetY: 3
              }
            },
            emphasis: {
              // color: 各异,
              label: {
                show: false
                // 默认自适应，水平布局为'top'，垂直布局为'right'，可选为 'inside'|'left'|'right'|'top'|'bottom'
                // position: 'right'
                // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
              }
            }
          },
          //smooth : false,
          //symbol: null,         // 拐点图形类型
          symbolSize: 5,          // 拐点图形大小
          //symbolRotate : null,  // 拐点图形旋转控制
          showAllSymbol: false    // 标志图形默认只有主轴显示（随主轴标签间隔隐藏策略）
        },
        textStyle: {
          decoration: 'none',
          fontFamily: 'Arial, Verdana, sans-serif',
          fontFamily2: '微软雅黑',    // IE8- 字体模糊并且不支持不同字体混排，额外指定一份
          fontSize: 12,
          fontStyle: 'normal',
          fontWeight: 'normal'
        },
        // 默认标志图形类型列表
        symbolList: [
          'circle', 'rectangle', 'triangle', 'diamond',
          'emptyCircle', 'emptyRectangle', 'emptyTriangle', 'emptyDiamond'
        ],
        loadingText: 'Loading...',
        // 可计算特性配置，提示颜色
        calculable: false,              // 默认关闭可计算特性
        calculableColor: 'rgba(205,11,47,0.6)',       // 拖拽提示边框颜色
        calculableHolderColor: '#ccc', // 可计算占位提示颜色
        nameConnector: ' & ',
        valueConnector: ' : ',
        animation: true,
        animationThreshold: 2500,       // 动画元素阀值，产生的图形原素超过2500不出动画
        addDataAnimation: true,         // 动态数据接口是否开启动画效果
        animationDuration: 2000,
        animationEasing: 'ExponentialOut',    //BounceOut

        xAxis: {
          type: 'category', //坐标轴类型。
          boundaryGap: true, //坐标轴两边留白策略 false true
          data: [],
          splitLine: {
            show: false,//  false true
            lineStyle: {
              color: '#7E85AB',
              type: 'dashed',
            },
          },
          axisLabel: {//坐标轴刻度标签的相关设置
            interval: 0,
            textStyle: {
              color: '#fff',
              fontSize: 10
            },
          },
          axisLine: {//坐标轴轴线相关设置
            show: true,//true false
            lineStyle: {
              width: '1',
              color: 'rgb(51,102,158)',
              type: 'dashed',
            }
          },
          axisTick: {
            //坐标轴刻度相关设置。
            show: false,//true false
          }
        },
        yAxis: [
          {
            type: 'value',
            axisLabel: { //x轴的坐标文字
              show: true,
              textStyle: {
                color: '#fff' //文字的颜色
              },

            },
            min: -20,//最小值 -20
            max: 120,//最大值 120
            axisLine: {//坐标轴轴线相关设置
              show: true,
              //  onZero: true,//表示 X 轴或者 Y 轴的轴线是否在另一个轴的 0 刻度上，只有在另一个轴为数值轴且包含 0 刻度时有效。
              lineStyle: {
                type: 'dashed', width: '1',
                color: 'rgba(253,220,2,0.94)'
              }
            },
            axisTick: { //坐标轴刻度相关设置。
              show: true,//false  true
            },
            splitLine: { //坐标在grid区域的分割线
              show: true,//false  true
              lineStyle: { //设置分割线的样式(图表横线颜色)
                color: ['#153a8a'], type: 'dashed',
              }
            }
          },
          {
            type: 'value',
            name: '（亿元）',
            min: -2,
            max: 12,//最大值 120
            nameTextStyle: {
              padding: [0, 0, 0, 0], // 上右下左与原位置距离
            },
            // interval: 5, //间隔
            axisLabel: {
              formatter: '{value}',
            },
            axisLine: {
              show: true,
              lineStyle: {
                color: '#d208e8',
                width: '1',
                type: 'dashed',
              },
            },
            axisTick: {
              show: false,
            },
            splitLine: {
              //表中是否显示网格线
              show: true,
              lineStyle: {
                color: '#7E85AB',
                type: 'dashed',
              },
            },
          }
        ],
        series: [
          {
            name: 'k', smooth: 0.2, //折线的弧度 0-1
            type: 'line',
            itemStyle: {
              normal: {
                color: 'rgb(171,230,241)',
                lineStyle: {
                  color: 'rgb(171,230,241)' //白色线代表K线
                },
              },
            },
            data: [], yAxisIndex: 0
          },
          {
            name: 'd', smooth: 0.2, //折线的弧度 0-1
            type: 'line',
            data: [], yAxisIndex: 0,
            itemStyle: {
              normal: {
                color: 'rgb(236,217,10)',
                lineStyle: {
                  color: 'rgb(236,217,10)' //黄色线代表D线
                },
              },
            }
          },
          {
            name: 'j', smooth: 0.2, //折线的弧度 0-1
            type: 'line',
            data: [], yAxisIndex: 0,
            itemStyle: {
              normal: {
                color: 'rgb(154,7,224)',
                lineStyle: {
                  color: 'rgb(154,7,224)' //紫色线代表J线
                },
              },
            }
          },
          {
            name: 'cci', smooth: 0.2, //折线的弧度 0-1
            type: 'line',
            data: [], yAxisIndex: 0,
            itemStyle: {
              normal: {
                color: 'rgb(2,97,245)',
                lineStyle: {
                  color: 'rgb(2,97,245)' //紫色线代表J线
                },
              },
            }
          },
          {
            name: '成交金额',
            type: 'bar',
            barWidth: 10,//设置柱状图大小
            itemStyle: {
              //item设置小圆点的颜色
              normal: {
                color: 'rgb(232,10,128)',
                // color: function (params) {
                //   //注意，如果颜色太少的话，后面颜色不会自动循环，最好多定义几个颜色
                //   var colorList = ['#c23531', '#2f4554', '#61a0a8', '#d48265', '#91c7ae', '#749f83', '#ca8622'];
                //   return '#c23531';
                // }
                lineStyle: {
                  //line设置线的颜色
                  color: 'rgb(232,10,128)',
                },
              },
            },
            // symbol: 'none',
            smooth: 0.5,//折线的弧度 0-1
            // stack: 'Total',
            yAxisIndex: 1,
            data: [],
          }
        ]
      },
    }
  },
  props: {
    kdjParam: {
      type: Object,
      default: function () {
        return {k: [], d: [], j: [], cci: [], dateList: [], tradeAmount: []};
      },
      required: true
    }
  },
  watch: {
    kdjParam: {
      deep: true,
      handler: function () {
        // 监听有值后才会调用
        console.log("StockkdjParam 5656");
        console.log(this.kdjParam);
        this.setStockKdjData(this.kdjParam);
      }
    }
  },
  created() {
    this.$nextTick(() => {
      if (!isEmptyObj(this.kdjParam)) {
        console.log("StockkdjParam--created 666119991126");
      }
    });
  },
  methods: {
    setStockKdjData(kdjParam) {
      console.log(kdjParam);

      this.echartsOption.title.subtext = "成交金额(亿元)";
      this.echartsOption.title.text = "日线kdj指标";

      this.echartsOption.xAxis.data = kdjParam.dateList;
      for (let j = 0; j < this.echartsOption.series.length; j++) {
        let seriesName = this.echartsOption.series[j].name;
        if ("成交金额" == seriesName) {
          seriesName = "tradeAmount";
        }
        if (!isEmptyObj(kdjParam[seriesName])) {
          this.echartsOption.series[j].data = kdjParam[seriesName];
        }
      }
      this.$nextTick(() => {
        this.myCharts();
      })
    },
    myCharts() {
      let myChart = echarts.init(this.$refs.wrCharts, "macarons");
      myChart.clear();
      myChart.setOption(this.echartsOption);
      //图表自适应 随着屏幕大小调节图表
      window.addEventListener("resize", function () {
        myChart.resize(); // myChart 是实例对象
      });
    }
  }
}
</script>


<style scoped>
.stock-chart-box {
  width: 90%;
  margin-left: 5%;
  border: 0px saddlebrown solid;
  /*margin-bottom: 10px;*/
  height: 460px;
  border-radius: 4px;
  background-color: #e8ebf1;
  text-align: center;
  color: #fff;
  /*padding: 40px 20px;*/
  box-sizing: border-box;
  /*margin-right: 20px;*/
}

.proCharts {
  width: 100%;
  height: 460px;
  background: rgb(14, 51, 129);
}
</style>