package com.muicc.incomes.controller;
import com.muicc.incomes.dao.*;
import com.muicc.incomes.pojo.*;
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
public class UserobjectController {

    @Autowired
    UserobjectDao userobjectDao;
    @Autowired
    Userdefault1Dao userdefault1Dao;
    @Autowired
    EmployerDao employerDao;
    @Autowired
    TotalwageDao totalwageDao;
    @Autowired
    TaxDao taxDao;
    @Autowired
    CreatedateDao createdateDao;
    @Autowired
    WorkdayDao workdayDao;
    @Autowired
    AwaysDao awaysDao;

    //查找所有的工资管理明细
    @CrossOrigin
    @GetMapping(value = "incomes/getAllSalary")
    @ResponseBody
    public List getAllSalary(String time) {
        Createdate createdateByTime = createdateDao.getCreatedateByTime(time);
        int cdid =createdateByTime.getId();
        List<Userobject> userobjectByCdid = userobjectDao.getUserobjectByCdid(cdid);
        List list = new ArrayList<>();
        for(int i = 0; i < userobjectByCdid.size(); i++){
            Map map = new HashMap();
            Userobject userobject = userobjectByCdid.get(i);
            map.put("num", i+1);//序号
            map.put("id", userobject.getId());//ID
            Employer employerById = employerDao.getEmployerById(userobject.getEid());
            map.put("name", employerById.getName());//姓名
            map.put("regularpay",userobject.getRegularpay());//基本工资
            map.put("work",userobject.getWork());//加班
            map.put("absence",userobject.getAbsence());//旷工
            map.put("holiday",userobject.getHoliday());//请假
            map.put("allowanceids",userobject.getAllowanceids());//补贴
            map.put("awards",userobject.getAwards());//奖金
            map.put("initialsum",userobject.getInitialsum());//工资合计
            map.put("insurance",userobject.getInsurance());//社保
            map.put("secondsum",userobject.getSecondsum());//应付金额
            map.put("cut",userobject.getCut());//扣借款
            map.put("tax",userobject.getTax());//个税
            map.put("sumpayable",userobject.getSumpayable());//实发金额
            if(null==userobject.getQianzhang()){
                map.put("qianzhang","---");//签章
            }else{
                map.put("qianzhang",userobject.getQianzhang());//签章
            }
            if(null==userobject.getNote()){
                map.put("note","---");//备注
            }else{
                map.put("note",userobject.getNote());//备注
            }
            list.add(map);
        }
        return list;
    }


    //查找员工的工资管理记录
    @CrossOrigin
    @GetMapping(value = "incomes/getSalaryByEid")
    @ResponseBody
    public List getSalaryByEid(int eid) {
        List<Userobject> userobjectByEid = userobjectDao.getUserobjectByEid(eid);
        List list = new ArrayList<>();
        for(int i = 0; i < userobjectByEid.size(); i++){
            Map map = new HashMap();
            Userobject userobject = userobjectByEid.get(i);
            map.put("num", i+1);//序号
            map.put("id", userobject.getId());//ID
            Createdate createdateByCdid = createdateDao.getCreatedateByCdid(userobject.getCdid());
            map.put("time", createdateByCdid.getName());//时间
            Employer employerById = employerDao.getEmployerById(userobject.getEid());
            map.put("name", employerById.getName());//姓名
            map.put("regularpay",userobject.getRegularpay());//基本工资
            map.put("overtime",userobject.getRegularpay());//加班
            map.put("absence",userobject.getRegularpay());//旷工
            map.put("holiday",userobject.getRegularpay());//请假
            map.put("allowanceids",userobject.getRegularpay());//补贴
            map.put("awards",userobject.getRegularpay());//奖金
            map.put("initialsum",userobject.getRegularpay());//工资合计
            map.put("insurance",userobject.getRegularpay());//社保
            map.put("secondsum",userobject.getSecondsum());//应付金额
            map.put("cut",userobject.getCut());//扣借款
            map.put("tax",userobject.getTax());//个税
            map.put("sumpayable",userobject.getSumpayable());//实发金额
            if(null==userobject.getNote()){
                map.put("note","---");//备注
            }else{
                map.put("note",userobject.getNote());//备注
            }
            list.add(map);
        }
        return list;
    }

}
