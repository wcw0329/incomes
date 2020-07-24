package com.muicc.incomes.controller;


import com.muicc.incomes.dao.CreatedateDao;
import com.muicc.incomes.dao.TempoDao;
import com.muicc.incomes.pojo.Createdate;
import com.muicc.incomes.pojo.Tempo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TempoController {

    @Autowired
    TempoDao tempoDao;

    @Autowired
    CreatedateDao createdateDao;

    //查找所有临时工资明细
    @CrossOrigin
    @GetMapping(value = "incomes/getTempoByCdid")
    @ResponseBody
    public List getTempoByCdid(String time) {
        if(null==time){
            return null;
        }
        Createdate createdateByTime = createdateDao.getCreatedateByTime(time);
        List<Tempo> tempoList = tempoDao.getTempoByCdid(createdateByTime.getId());
        List list = new ArrayList<>();
        for(int i = 0; i < tempoList.size(); i++){
            Map map = new HashMap();
            Tempo tempo = tempoList.get(i);
            map.put("time", time);//日期
            map.put("name", tempo.getName());//名称
            map.put("secondsum",tempo.getSecondsum());//工资合计
            map.put("tax", tempo.getTax());//代扣个税
            map.put("sumpayable", tempo.getSumpayable());//实发奖金
            map.put("qianzhang","---");//签章
            if(null==tempo.getNote()){
                map.put("note","---");//备注
            }else{
                map.put("note",tempo.getNote());//备注
            }
            list.add(map);
        }
        return list;
    }

}
