package com.muicc.incomes.controller;


import com.muicc.incomes.dao.AwaysDao;
import com.muicc.incomes.dao.CreatedateDao;
import com.muicc.incomes.dao.EmployerDao;
import com.muicc.incomes.dao.WorkdayDao;
import com.muicc.incomes.pojo.Aways;
import com.muicc.incomes.pojo.Workday;
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
public class AwaysController {

    @Autowired
    AwaysDao awaysDao;
    @Autowired
    WorkdayDao workdayDao;
    @Autowired
    EmployerDao employerDao;
    @Autowired
    CreatedateDao createdateDao;

    //查询所有旷工扣款金额记录
    @CrossOrigin
    @GetMapping(value = "incomes/getAllAways")
    @ResponseBody
    public List getAllAways() {
        List<Aways> awaysList = awaysDao.getAllAways();
        List list = new ArrayList<>();
        for(int i = 0; i < awaysList.size(); i++){
            Map map = new HashMap();
            Aways aways = awaysList.get(i);
            map.put("num", i+1);//序号
            map.put("cutmoney",aways.getCutmoney());//旷工扣款金额
            int workdayId = aways.getWorkdayid();
            Workday workday = workdayDao.getWorkdayById(workdayId);
            if(workday!=null){
                map.put("ename",employerDao.getEmployerById(workday.getEid()).getName());//员工姓名
                map.put("time", createdateDao.getCreatedateByCdid(workday.getCdid()).getName());//时间
                String status = null;
                if(aways.getShown()==1){
                    status = "已提交";
                }else{
                    status = "未提交";
                }
                map.put("status", status);//状态
                list.add(map);
            }else{
                continue;
            }
        }
        return list;
    }
}
