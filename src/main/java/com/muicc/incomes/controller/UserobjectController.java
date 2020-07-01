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
    AwardsDao awardsDao;
    @Autowired
    TotalwageDao totalwageDao;
    @Autowired
    TaxDao taxDao;
    @Autowired
    InsuranceDao insuranceDao;
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
        if(null==time){
            return null;
        }
        Createdate createdateByTime = createdateDao.getCreatedateByTime(time);
        List<Userobject> userobjectList = userobjectDao.getUserobjectByCdid(createdateByTime.getId());
        List list = new ArrayList<>();
        for(int i = 0; i < userobjectList.size(); i++){
            Map map = new HashMap();
            double workTime = 0;//时间
            double money = 0;//奖金金额
            double absenteeism = 0;//旷工
            double late = 0;//迟到
            double leaveral =0;//早退
            double awards = 0;//奖金
            double regularpay = 0;//基本工资
            double work = 0;//加班
            double holiday = 0;//请假
            Userobject userobject = userobjectList.get(i);
            Createdate createdateByCdid = createdateDao.getCreatedateByCdid(userobject.getCdid());
            //日期
            map.put("time", createdateByCdid.getName());
            Userdefault1 userdefault1ByUdid = userdefault1Dao.getUserdefault1ByUdid(userobject.getUdid());
                if(null!=userdefault1ByUdid){
                    Employer employerById = employerDao.getEmployerById(userdefault1ByUdid.getEid());
                    if (null!=employerById) {
                        //员工姓名
                        map.put("name", employerById.getName());
                        List<Awards> awardsByEid = awardsDao.getAwardsByEid(employerById.getId());
                        workTime = awardsByEid.get(0).getTime();//时间
                        money = awardsByEid.get(0).getMoney();//奖金金额
                    }else {
                        workTime = 0;//时间
                        money = 0;//奖金金额
                    }
                    //补贴
                    map.put("allowanceids", userdefault1ByUdid.getAllowanceids());
                    //扣借款
                    map.put("cutids", userdefault1ByUdid.getCutids());

                    regularpay = userdefault1ByUdid.getRegularpay();//基本工资
                    //基本工资
                    map.put("regularpay", regularpay);

                    //备注
//                    if(null!=userdefault1ByUdid.getNote()||userdefault1ByUdid.getNote().equals("")){
//                        map.put("note", userdefault1ByUdid.getNote());
//                    }else{
                        map.put("note", "---");
//                    }
                    //签章
//                    if(null!=userdefault1ByUdid.getQianzhang()||userdefault1ByUdid.getQianzhang().equals("")){
//                        map.put("qianzhang", userdefault1ByUdid.getQianzhang());
//                    }else{
                        map.put("qianzhang", "---");
//                    }

                }
            Totalwage totalwageByid = totalwageDao.getTotalwageByid(userobject.getTwid());
                    if (null!=totalwageByid) {
                        //工资合计
                        map.put("initialsum", totalwageByid.getInitialsum());
                        //应付金额
                        map.put("secondsum", totalwageByid.getSecondsum());
                        //实发金额
                        map.put("sumpayable", totalwageByid.getSumpayable());
                    }
            Tax taxByid = taxDao.getTaxByid(userobject.getTxid());
            if (null!=totalwageByid) {
                //个税
                map.put("tax", taxByid.getTax());
            }
            Insurance insuranceByid = insuranceDao.getInsuranceById(userobject.getIsid());
            if (null!=insuranceByid) {
                //社保
                map.put("insurance", insuranceByid.getInsurance());
            }
            Workday workdayById = workdayDao.getWorkdayById(userobject.getWdids());
            if (null!=workdayById) {
                double absence = workdayById.getAbsence();//请假
                double overtimen = workdayById.getOvertimen();//普通加班
                double overtimed = workdayById.getOvertimed();//节假日加班
                absenteeism = workdayById.getAbsenteeism();//旷工
                late = workdayById.getLate();//迟到
                leaveral = workdayById.getLeaveral();//早退
                //计算奖金金额
                if (absenteeism == 0 && leaveral == 0) {
                    if (workTime > late) {
                        awards = money;
                    } else {
                        awards = 0;
                    }
                } else {
                    awards = 0;
                }
                //奖金
                map.put("awards", awards);

                Aways awaysByWorkdayId = awaysDao.getAwaysByWorkdayId(userobject.getWdids());
                //旷工扣款
                map.put("cutmoney",awaysByWorkdayId.getCutmoney());

                //计算请假加班扣费
                if (absence >= 0 && overtimen >= absence) {
                    overtimen = overtimen - absence;
                    //加班
                    work = overtimen * regularpay * 1.5 / 8 / 21.75 + overtimed * 2 * regularpay / 21.75 / 8;
                    holiday = 0;
                    work = (double) Math.round(work * 100) / 100;
                } else if (absence > overtimen && absence < (overtimen + overtimed)) {
                    overtimed = overtimed + overtimen - absence;
                    overtimen = 0;
                    absence = 0;
                    //加班
                    work = overtimen * regularpay * 1.5 / 8 / 21.75 + overtimed * 2 * regularpay / 21.75 / 8;
                    holiday = 0;
                    work = (double) Math.round(work * 100) / 100;
                } else if (absence > (overtimen + overtimed)) {
                    double a1 = absence - (overtimen + overtimed);
                    //请假扣款
                    holiday = (a1 * regularpay / 21.75 / 8);
                    work = 0;
                    holiday = (double) Math.round(holiday * 100) / 100;
                }
                //加班
                map.put("work", work);
                //请假
                map.put("holiday", holiday);

            } else {
                map.put("work", 0);//加班
                map.put("holiday", 0);//请假
                map.put("awards", 0);//奖金
                map.put("cutmoney", 0);//旷工扣款
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
        List<Userdefault1> userdefault1ByEid = userdefault1Dao.getUserdefault1ByEid(eid);
        List list = new ArrayList<>();
        for(int i = 0; i < userdefault1ByEid.size(); i++) {
            Userdefault1 userdefault1 = userdefault1ByEid.get(i);
            Map map = new HashMap();
            double workTime = 0;//时间
            double money = 0;//奖金金额
            double absenteeism = 0;//旷工
            double late = 0;//迟到
            double leaveral = 0;//早退
            double awards = 0;//奖金
            double regularpay = 0;//基本工资
            double work = 0;//加班
            double holiday = 0;//请假

            List<Userobject> userobjectByUdid = userobjectDao.getUserobjectByUdid(userdefault1.getId());

            for (int j = 0; j < userobjectByUdid.size(); j++) {
                Userobject userobject = userobjectByUdid.get(j);

                map.put("userobjectid", userobject.getId());//Userobjectid

                List<Awards> awardsByEid = awardsDao.getAwardsByEid(eid);
                if(null!=awardsByEid){
                    workTime = awardsByEid.get(0).getTime();//时间
                    money = awardsByEid.get(0).getMoney();//奖金金额
                } else {
                    workTime = 0;//时间
                    money = 0;//奖金金额
                }
                //补贴
                map.put("allowanceids", userdefault1.getAllowanceids());
                //扣借款
                map.put("cutids", userdefault1.getCutids());

                //社保基数
                map.put("insurancebase", userdefault1.getInsurancebase());

                regularpay = userdefault1.getRegularpay();//基本工资
                //基本工资
                map.put("regularpay", regularpay);
                //备注
                map.put("note", "---");
                //签章
                map.put("qianzhang", "---");
                Totalwage totalwageByid = totalwageDao.getTotalwageByid(userobject.getTwid());
                if (null != totalwageByid) {
                    //工资合计
                    map.put("initialsum", totalwageByid.getInitialsum());
                    //应付金额
                    map.put("secondsum", totalwageByid.getSecondsum());
                    //实发金额
                    map.put("sumpayable", totalwageByid.getSumpayable());
                }
                Createdate createdateByCdid = createdateDao.getCreatedateByCdid(userobject.getCdid());
                if (null != createdateByCdid) {
                    //时间
                    map.put("time", createdateByCdid.getName());
                }
                Tax taxByid = taxDao.getTaxByid(userobject.getTxid());
                if (null != totalwageByid) {
                    //个税
                    map.put("tax", taxByid.getTax());
                }
                Insurance insuranceByid = insuranceDao.getInsuranceById(userobject.getIsid());
                if (null != insuranceByid) {
                    //社保
                    map.put("insurance", insuranceByid.getInsurance());
                }
                Workday workdayById = workdayDao.getWorkdayById(userobject.getWdids());
                if (null != workdayById) {
                    double absence = workdayById.getAbsence();//请假
                    double overtimen = workdayById.getOvertimen();//普通加班
                    double overtimed = workdayById.getOvertimed();//节假日加班
                    absenteeism = workdayById.getAbsenteeism();//旷工
                    late = workdayById.getLate();//迟到
                    leaveral = workdayById.getLeaveral();//早退
                    //计算奖金金额
                    if (absenteeism == 0 && leaveral == 0) {
                        if (workTime > late) {
                            awards = money;
                        } else {
                            awards = 0;
                        }
                    } else {
                        awards = 0;
                    }
                    //请假
                    map.put("absence", absence);
                    //加班
                    map.put("overtime", overtimen+overtimed);
                    //奖金
                    map.put("awards", awards);

                    Aways awaysByWorkdayId = awaysDao.getAwaysByWorkdayId(userobject.getWdids());
                    //旷工扣款
                    map.put("cutmoney", awaysByWorkdayId.getCutmoney());

                    //计算请假加班扣费
                    if (absence >= 0 && overtimen >= absence) {
                        overtimen = overtimen - absence;
                        //加班
                        work = overtimen * regularpay * 1.5 / 8 / 21.75 + overtimed * 2 * regularpay / 21.75 / 8;
                        holiday = 0;
                        work = (double) Math.round(work * 100) / 100;
                    } else if (absence > overtimen && absence < (overtimen + overtimed)) {
                        overtimed = overtimed + overtimen - absence;
                        overtimen = 0;
                        absence = 0;
                        //加班
                        work = overtimen * regularpay * 1.5 / 8 / 21.75 + overtimed * 2 * regularpay / 21.75 / 8;
                        holiday = 0;
                        work = (double) Math.round(work * 100) / 100;
                    } else if (absence > (overtimen + overtimed)) {
                        double a1 = absence - (overtimen + overtimed);
                        //请假扣款
                        holiday = (a1 * regularpay / 21.75 / 8);
                        work = 0;
                        holiday = (double) Math.round(holiday * 100) / 100;
                    }
                    //加班
                    map.put("work", work);
                    //请假
                    map.put("holiday", holiday);

                } else {
                    map.put("work", 0);//加班
                    map.put("holiday", 0);//请假
                    map.put("awards", 0);//奖金
                    map.put("cutmoney", 0);//旷工扣款
                }

                list.add(map);
            }
        }
        return list;
    }

}
