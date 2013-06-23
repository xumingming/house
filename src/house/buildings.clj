(ns house.buildings)

(def ^{:doc ""} dibiao
  [{:name "黄龙时代广场"
    :geo [120.13115 30.278699]}
   {:name "武林广场"
    :geo [120.169824 30.276804]}])

(def buildings
  {"wangke-qixianjun"
   {:id "wangke-qixianjun"
    :name "万科七贤郡"
    :loc "万科良渚文化村内（近东西大道）"
    :geo [120.02866 30.378027]
    :touzishan "浙江万科南都房地产有限公司"
    :kaifashan "杭州良渚文化村开发有限公司"
    :wuye ""
    :wuyefei 0
    :price 10000
    :edu "良渚职业高中"
    :jiaotong "313路,348路"
    :kaipan-date "2012-10"
    :jiaofu-date "2014"
    :lvhualv 0.3
    :rongjilv 1.30
    :defanglv 0
    :zhuangxiu "精装修"
    :hushu 1140
    :cheweishu 0
    :homepage "http://hz.fang.anjuke.com/loupan/246961.html"
    :note ""
    }

   "lvcheng-feicuicheng"
   {:id "lvcheng-feicuicheng"
    :name "绿城翡翠城"
    :loc "余杭高教路和五常大道交汇处"
    :geo [120.03051 30.244214]
    :touzishan "绿城房地产集团有限公司"
    :kaifashan "杭州翡翠城房地产开发有限公司"
    :wuye "浙江绿城物业管理公司"
    :wuyefei 2.3
    :price 16000
    :edu "浙江科技学院,浙江工业大学屏峰校区"
    :jiaotong "3号线藕花洲大街站,346路,367路"
    :kaipan-date "2013-07-05"
    :jiaofu-date "2015-12-31"
    :lvhualv 0.4
    :rongjilv 1.30
    :defanglv 0.76
    :zhuangxiu "毛坯"
    :hushu 8500
    :cheweishu 2125
    :homepage "http://hz.fang.anjuke.com/loupan/239002.html"
    :note "不能用公积金贷款"
    }

   "dahua-haipaifengfan"
   {:id "dahua-haipaifengfan"
    :name "大华海派风范"
    :loc "文一西路北、同城印象东侧"
    :geo [119.956511 30.274644]
    :touzishan "浙江大华房地产开发有限公司"
    :kaifashan "浙江大华房地产开发有限公司"
    :wuye "彩虹物业有限公司"
    :wuyefei 1.5
    :price 9200
    :edu "余杭中学(太炎路),余杭镇第一中学教育集团镇中校区"
    :jiaotong "5号线余杭塘路站"
    :kaipan-date "2013-04-13"
    :jiaofu-date "2015-03-30"
    :lvhualv 0.316
    :rongjilv 2.30
    :defanglv 0
    :zhuangxiu "毛坯"
    :hushu 1385
    :cheweishu 1386
    :homepage "http://hz.fang.anjuke.com/loupan/240423.html"
    :note ""
    }
   
   "rongke-ailishan"
   {:id "rongke-ailishan"
    :name "融科瑷骊山"
    :loc "之江国家旅游度假区象山路西"
    :geo [120.071227 30.149159]
    :touzishan ""
    :kaifashan "浙江融科智地房地产开发有限公司"
    :wuye "第一太平融科物业管理（北京）有限公司杭州分公司"
    :wuyefei 2.25
    :price "待定"
    :edu "小学"
    :jiaotong ""
    :kaipan-date "2013-05"
    :jiaofu-date ""
    :lvhualv 0.00
    :rongjilv 1.86
    :defanglv 0
    :zhuangxiu "毛坯"
    :hushu 835
    :cheweishu 0
    :homepage "http://hz.fang.anjuke.com/loupan/248282.html"
    :note ""
    }

   "zhongan-baimashanzhuang"
   {:id "zhongan-baimashanzhuang"
    :name "众安-白马山庄"
    :loc "余杭区闲林镇留和路浙江科技学院西北侧	"
    :geo [120.023559 30.223168]
    :touzishan "众安房产"
    :kaifashan ""
    :wuye ""
    :wuyefei 100
    :price 9500
    :edu "余杭镇第一幼儿园沈家店分园"
    :jiaotong "483路,484路,481路,482路"
    :kaipan-date "2012-09-23"
    :jiaofu-date "2014"
    :lvhualv 0.3
    :rongjilv 1.3
    :defanglv 0
    :zhuangxiu "毛坯 精装"
    :hushu 992
    :cheweishu 99999
    :homepage "http://hz.fang.anjuke.com/loupan/246950.html"
    :note ""
    }

   "xixi-haixiaohai"
   {:id "xixi-haixiaohai"
    :name "西溪海小海"
    :loc "天目山西路北侧，闲富北路与上和路交叉口西南角"
    :geo [119.985697 30.247459]
    :touzishan ""
    :kaifashan ""
    :wuye "杭州金成物业公司"
    :wuyefei 1.8
    :price 9800
    :edu "小学"
    :jiaotong ""
    :kaipan-date "2013-04"
    :jiaofu-date "2014-12"
    :lvhualv 0.3395
    :rongjilv 2.41
    :defanglv 0
    :zhuangxiu "毛坯"
    :hushu 900
    :cheweishu 871
    :homepage "http://hz.fang.anjuke.com/loupan/246960.html"
    :note ""
    }
   
   "xinhu-guolin"
   {:id "xinhu-guolin"
    :name "新湖果岭"
    :loc ""
    :geo [119.983511 30.263306]
    :touzishan "浙江新兰得置业有限公司"
    :kaifashan "浙江新兰得置业有限公司"
    :wuye "浙江新湖物业管理有限公司"
    :wuyefei 2
    :price 10000
    :edu     ["杭州英特外国语学校"]
    #_["杭州英特外国语学校"
       "余杭镇第一中学教育集团镇中校区"
       "余杭镇第一幼儿园"
       "龙文教育"
       "余杭中学"
       "国泰·西园幼儿园"
       "杭州波达塑业有限公司研究开发中心"
       "阳光宝贝方汇幼儿园"
       "杭州绿城育华桃花源学校"
       "国泰·西园儿童之家"]
    :jiaotong "5号线工教路站"
    :kaipan-date "2011-10"
    :jiaofu-date "2014-06"
    :lvhualv 0.36
    :rongjilv 1.2
    :defanglv 0
    :zhuangxiu "毛坯"
    :hushu 2330
    :cheweishu 2302
    :homepage "http://hz.fang.anjuke.com/loupan/238636.html"
    :note ""
    }}
  )