package com.muicc.incomes.controller;


import com.muicc.incomes.dao.*;
import com.muicc.incomes.model.RequestWorkday;
import com.muicc.incomes.pojo.*;
import com.muicc.incomes.result.Result;
import com.muicc.incomes.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WorkdayController {

    @Autowired
    WorkdayDao workdayDao;
    @Autowired
    EmployerDao employerDao;
    @Autowired
    CreatedateDao createdateDao;
    @Autowired
    AwaysDao awaysDao;
    @Autowired
    StayDao stayDao;
    @Autowired
    Userdefault1Dao userdefault1Dao;

    //按员工号查询出勤记录
    @CrossOrigin
    @GetMapping(value = "incomes/getWorkdayByEid")
    @ResponseBody
    public List getWorkdayByEid(int eid) {

        List<Workday> workdayList = workdayDao.getWorkdayByEid(eid);
        List list = new ArrayList<>();
        for(int i = 0; i < workdayList.size(); i++){
            Map map = new HashMap();
            Workday workday = workdayList.get(i);
            map.put("id", workday.getId());//ID
            map.put("eid", workday.getEid());//员工号
            map.put("cdid", workday.getCdid());
            map.put("time", createdateDao.getCreatedateByCdid(workday.getCdid()).getName());//时间
            map.put("name", employerDao.getEmployerById(workday.getEid()).getName());//员工姓名
            map.put("absence", workday.getAbsence());//无薪请假
            map.put("paidLeave", workday.getPaidLeave());//带薪请假
            map.put("overtimen", workday.getOvertimen());//普通加班
            map.put("overtimed", workday.getOvertimed());//假日加班
            String status = null;
            if(workday.getStatus()==0){
                status = "已提交";
            }else{
                status = "未提交";
            }
            map.put("status", status);//状态

            map.put("absenteeism", workday.getAbsenteeism());//旷工
            map.put("late", workday.getLate());//迟到
            map.put("lateTimes", workday.getLateTimes());//迟到次数
            map.put("leaveral", workday.getLeaveral());//早退
            map.put("leaveralTimes", workday.getLeaveralTimes());//早退次数
            list.add(map);
        }
        return list;
    }

    //查询所有出勤记录
    @CrossOrigin
    @GetMapping(value = "incomes/getAllWorkday")
    @ResponseBody
    public List getAllWorkday() {
        List<Workday> workdayList = workdayDao.getAllWorkday();
        List list = new ArrayList<>();
        for(int i = 0; i < workdayList.size(); i++){
            Map map = new HashMap();
            Workday workday = workdayList.get(i);
            map.put("num", i+1);//序号
            map.put("id", workday.getId());//ID
            map.put("eid", workday.getEid());//员工号
            map.put("cdid", workday.getCdid());
            map.put("time", createdateDao.getCreatedateByCdid(workday.getCdid()).getName());//时间
            map.put("name", employerDao.getEmployerById(workday.getEid()).getName());//员工姓名
            map.put("absence", workday.getAbsence());//无薪请假
            map.put("paidLeave", workday.getPaidLeave());//带薪请假
            map.put("overtimen", workday.getOvertimen());//普通加班
            map.put("overtimed", workday.getOvertimed());//假日加班
            String status = null;
            if(workday.getStatus()==0){
                status = "已提交";
            }else{
                status = "未提交";
            }
            map.put("status", status);//状态
            map.put("absenteeism", workday.getAbsenteeism());//旷工
            map.put("late", workday.getLate());//迟到
            map.put("lateTimes", workday.getLateTimes());//迟到次数
            map.put("leaveral", workday.getLeaveral());//早退
            map.put("leaveralTimes", workday.getLeaveralTimes());//早退次数
            list.add(map);
        }
        return list;
    }


    //添加出勤记录
    @CrossOrigin
    @PostMapping("incomes/addWorkday")
    @ResponseBody
    public Result addWorkday(@RequestBody RequestWorkday requestWorkday) {
        String name = requestWorkday.getName();
        String time = requestWorkday.getTime();
        double absence = requestWorkday.getAbsence();
        double paidLeave = requestWorkday.getPaidLeave();
        double overtimen = requestWorkday.getOvertimen();
        double overtimed = requestWorkday.getOvertimed();
        double absenteeism = requestWorkday.getAbsenteeism();
        double late = requestWorkday.getLate();
        int lateTimes = requestWorkday.getLateTimes();
        double leaveral = requestWorkday.getLeaveral();
        int leaveralTimes = requestWorkday.getLeaveralTimes();
        String message = String.format("添加成功！");
        if (null == name || null == time) {
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        }
        Employer employer = employerDao.getEmployerByName(name);
        Createdate createdate = createdateDao.getCreatedateByTime(time);
        if(null==employer||null==createdate){
            message = String.format("员工姓名或时间不正确！");
            return ResultFactory.buildFailResult(message);
        }else{
            Workday workdayByEidAndCdid = workdayDao.getWorkdayByEidAndCdid(employer.getId(), createdate.getId());
            if(workdayByEidAndCdid!=null){
                message = String.format("该员工已存在该月份下的记录！");
                return ResultFactory.buidResult(100,message,workdayByEidAndCdid);
            }else {
                List<Stay> allStay = stayDao.getAllStay();
                Userdefault1 userdefault1ByEid = userdefault1Dao.getUserdefault1ByEid(employer.getId());
                Double t = allStay.get(0).getTime();//迟到早退时间限制
                Double bei = allStay.get(0).getBei();//旷工扣款倍数
                double regularpay = userdefault1ByEid.getRegularpay();//基本工资
                Aways byEidAndCdid = awaysDao.getByEidAndCdid(employer.getId(), createdate.getId());
                if(null==byEidAndCdid){
                    if(absenteeism!=0&&late+leaveral>=t){
                        double cutmoney = regularpay / 21.75 / 8 * bei *(absenteeism+late+leaveral);
                        awaysDao.addAways(1,cutmoney,employer.getId(),createdate.getId(),1);
                    }
                    if(absenteeism!=0&&late+leaveral<t){
                        double cutmoney = regularpay / 21.75 / 8 * bei * absenteeism;
                        awaysDao.addAways(1,cutmoney,employer.getId(),createdate.getId(),1);
                    }
                }else{
                    if(absenteeism!=0&&late+leaveral>=t){
                        double cutmoney = regularpay / 21.75 / 8 * bei *(absenteeism+late+leaveral);
                        awaysDao.updateAways(cutmoney,employer.getId(),createdate.getId());
                    }
                    if(absenteeism!=0&&late+leaveral<t){
                        double cutmoney = regularpay / 21.75 / 8 * bei * absenteeism;
                        awaysDao.updateAways(cutmoney,employer.getId(),createdate.getId());
                    }
                }
                int i = workdayDao.addWorkday(employer.getId(), createdate.getId(), paidLeave, absence, overtimen, overtimed, absenteeism, late, lateTimes, leaveral, leaveralTimes);
                if (i == 0) {
                    message = String.format("添加失败！");
                    return ResultFactory.buildFailResult(message);
                } else {
                    return ResultFactory.buildSuccessResult(message, i);
                }

            }
        }
    }

    //按ID修改出勤记录
    @CrossOrigin
    @PostMapping("incomes/updateWorkday")
    @ResponseBody
    public Result updateWorkday(@RequestBody RequestWorkday requestWorkday) {
        int id = requestWorkday.getId();
        String time = requestWorkday.getTime();
        double absence = requestWorkday.getAbsence();
        double paidLeave = requestWorkday.getPaidLeave();
        double overtimen = requestWorkday.getOvertimen();
        double overtimed = requestWorkday.getOvertimed();
        double absenteeism = requestWorkday.getAbsenteeism();
        double late = requestWorkday.getLate();
        int lateTimes = requestWorkday.getLateTimes();
        double leaveral = requestWorkday.getLeaveral();
        int leaveralTimes = requestWorkday.getLeaveralTimes();
        String message = String.format("修改成功！");
        Createdate createdate = createdateDao.getCreatedateByTime(time);
        if(null==createdate){
            message = String.format("员工姓名或时间不正确！");
            return ResultFactory.buildFailResult(message);
        }else {
            int i = workdayDao.updateWorkday(id,paidLeave, absence, overtimen, overtimed, absenteeism, late,lateTimes, leaveral,leaveralTimes,createdate.getId());
            if (i == 0) {
                message = String.format("修改失败！");
                return ResultFactory.buildFailResult(message);
            } else {
                Workday workdayById = workdayDao.getWorkdayById(id);
                List<Stay> allStay = stayDao.getAllStay();
                Userdefault1 userdefault1ByEid = userdefault1Dao.getUserdefault1ByEid(workdayById.getEid());
                Double t = allStay.get(0).getTime();//迟到早退时间限制
                Double bei = allStay.get(0).getBei();//旷工扣款倍数
                double regularpay = userdefault1ByEid.getRegularpay();//基本工资
                Aways byEidAndCdid = awaysDao.getByEidAndCdid(workdayById.getEid(), createdate.getId());
                if(null==byEidAndCdid){
                    if(absenteeism!=0&&late+leaveral>=t){
                        double cutmoney = regularpay / 21.75 / 8 * bei *(absenteeism+late+leaveral);
                        awaysDao.addAways(1,cutmoney,workdayById.getEid(),createdate.getId(),1);
                    }
                    if(absenteeism!=0&&late+leaveral<t){
                        double cutmoney = regularpay / 21.75 / 8 * bei * absenteeism;
                        awaysDao.addAways(1,cutmoney,workdayById.getEid(),createdate.getId(),1);
                    }
                }else{
                    if(absenteeism!=0&&late+leaveral>=t){
                        double cutmoney = regularpay / 21.75 / 8 * bei *(absenteeism+late+leaveral);
                        awaysDao.updateAways(cutmoney,workdayById.getEid(),createdate.getId());
                    }
                    if(absenteeism!=0&&late+leaveral<t){
                        double cutmoney = regularpay / 21.75 / 8 * bei * absenteeism;
                        awaysDao.updateAways(cutmoney,workdayById.getEid(),createdate.getId());
                    }
                }
                return ResultFactory.buildSuccessResult(message, i);
            }
        }
     }

    //按ID删除出勤记录
    @CrossOrigin
    @PostMapping("incomes/deleteWorkday")
    @ResponseBody
    public Result deleteWorkday(@RequestBody RequestWorkday requestWorkday) {
        String message = String.format("删除成功！");
        Integer id = requestWorkday.getId();
        if (0 == id ) {
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }
            Workday workdayById = workdayDao.getWorkdayById(id);
            int i = awaysDao.deleteAways(workdayById.getEid(), workdayById.getCdid());
            int j = workdayDao.deleteWorkday(id);
            if(i==0||j==0){
                message = String.format("删除失败！");
                return ResultFactory.buildFailResult(message);
            }else{
                return ResultFactory.buildSuccessResult(message,i);
            }
    }

}
