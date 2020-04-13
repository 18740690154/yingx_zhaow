package com.baizhi.zw.controller;

import com.baizhi.zw.vo.CityVo;
import com.baizhi.zw.vo.MondelVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("eCharts")
public class EChartsController {

    @RequestMapping("queryUserNum")
    public Map<String,Object> queryUsersNum(){
        HashMap<String, Object> map = new HashMap<>();

        map.put("month", Arrays.asList("1月","2月","3月","4月","5月","6月"));
        map.put("boys",Arrays.asList(15, 10, 35, 15, 20, 30));
        map.put("girls",Arrays.asList(5, 20, 30, 10, 5, 15));

        System.out.println("map = " + map);
        return map;
    }

    @RequestMapping("queryUserDistributionNum")
    public List<Object> queryUsersDistributionNum(){
        ArrayList<Object> list = new ArrayList<>();

        ArrayList<CityVo> boyCityVos = new ArrayList<>();
        
        boyCityVos.add( new CityVo("北京","34"));
        boyCityVos.add( new CityVo("天津","45"));
        boyCityVos.add( new CityVo( "上海","32"));
        boyCityVos.add( new CityVo("重庆", "22"));
        boyCityVos.add( new CityVo("河北", "34"));
        boyCityVos.add( new CityVo( "河南", "56"));
        boyCityVos.add( new CityVo( "云南", "23"));
        boyCityVos.add( new CityVo("辽宁", "34"));
        boyCityVos.add( new CityVo( "湖南", "23"));
        boyCityVos.add( new CityVo( "安徽", "24"));
        boyCityVos.add( new CityVo( "内蒙古", "24"));
        boyCityVos.add( new CityVo( "黑龙江", "24"));
        boyCityVos.add( new CityVo("山东", "23"));
        boyCityVos.add( new CityVo( "新疆", "34"));
        boyCityVos.add( new CityVo( "江苏", "32"));
        boyCityVos.add( new CityVo( "浙江", "54"));
        boyCityVos.add( new CityVo( "江西", "23"));
        boyCityVos.add( new CityVo( "浙江", "54"));
        boyCityVos.add( new CityVo( "湖北", "32"));
        boyCityVos.add( new CityVo( "浙江", "54"));
        boyCityVos.add( new CityVo(  "甘肃", "12"));
        boyCityVos.add( new CityVo( "山西", "33"));
        boyCityVos.add( new CityVo( "陕西", "23"));
        boyCityVos.add( new CityVo( "吉林", "34"));
        boyCityVos.add( new CityVo(  "福建", "54"));
        boyCityVos.add( new CityVo(  "贵州", "23"));
        boyCityVos.add( new CityVo(  "广东", "45"));
        boyCityVos.add( new CityVo(  "青海", "23"));
        boyCityVos.add( new CityVo(  "西藏", "35"));
        boyCityVos.add( new CityVo(  "四川", "45"));
        boyCityVos.add( new CityVo(  "宁夏", "32"));
        boyCityVos.add( new CityVo(  "台湾", "54"));
        boyCityVos.add( new CityVo(   "海南", "43"));
        boyCityVos.add( new CityVo(   "香港", "42"));

        ArrayList<CityVo> girlCityVos = new ArrayList<>();

        girlCityVos.add( new CityVo("北京","114"));
        girlCityVos.add( new CityVo("天津","145"));
        girlCityVos.add( new CityVo( "上海","132"));
        girlCityVos.add( new CityVo("重庆", "122"));
        girlCityVos.add( new CityVo("河北", "134"));
        girlCityVos.add( new CityVo( "河南", "256"));
        girlCityVos.add( new CityVo( "云南", "123"));
        girlCityVos.add( new CityVo("辽宁", "134"));
        girlCityVos.add( new CityVo( "湖南", "123"));
        girlCityVos.add( new CityVo( "安徽", "214"));
        girlCityVos.add( new CityVo( "内蒙古", "24"));
        girlCityVos.add( new CityVo( "黑龙江", "24"));
        girlCityVos.add( new CityVo("山东", "123"));
        girlCityVos.add( new CityVo( "新疆", "234"));
        girlCityVos.add( new CityVo( "江苏", "232"));
        girlCityVos.add( new CityVo( "浙江", "154"));
        girlCityVos.add( new CityVo( "江西", "103"));
        girlCityVos.add( new CityVo( "浙江", "54"));
        girlCityVos.add( new CityVo( "湖北", "32"));
        girlCityVos.add( new CityVo( "浙江", "54"));
        girlCityVos.add( new CityVo(  "甘肃", "12"));
        girlCityVos.add( new CityVo( "山西", "33"));
        girlCityVos.add( new CityVo( "陕西", "23"));
        girlCityVos.add( new CityVo( "吉林", "34"));
        girlCityVos.add( new CityVo(  "福建", "54"));
        girlCityVos.add( new CityVo(  "贵州", "123"));
        girlCityVos.add( new CityVo(  "广东", "45"));
        girlCityVos.add( new CityVo(  "青海", "23"));
        girlCityVos.add( new CityVo(  "西藏", "135"));
        girlCityVos.add( new CityVo(  "四川", "45"));
        girlCityVos.add( new CityVo(  "宁夏", "32"));
        girlCityVos.add( new CityVo(  "台湾", "54"));
        girlCityVos.add( new CityVo(   "海南", "163"));
        girlCityVos.add( new CityVo(   "香港", "142"));
        MondelVo boys = new MondelVo("男",boyCityVos);
        MondelVo girls = new MondelVo("女",girlCityVos);

        list.add(boys);
        list.add(girls);

        System.out.println("list = " + list);
        return list;
    }

}
