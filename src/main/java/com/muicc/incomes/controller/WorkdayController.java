package com.muicc.incomes.controller;


import com.muicc.incomes.dao.CreatedateDao;
import com.muicc.incomes.dao.EmployerDao;
import com.muicc.incomes.dao.WorkdayDao;
import com.muicc.incomes.model.RequestWorkday;
import com.muicc.incomes.pojo.Createdate;
import com.muicc.incomes.pojo.Employer;
import com.muicc.incomes.pojo.Workday;
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
            map.put("absence", workday.getAbsence());//请假
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
            map.put("leaveral", workday.getLeaveral());//早退
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
            map.put("absence", workday.getAbsence());//请假
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
            map.put("leaveral", workday.getLeaveral());//早退
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
        int absence = requestWorkday.getAbsence();
        double overtimen = requestWorkday.getOvertimen();
        double overtimed = requestWorkday.getOvertimed();
        double absenteeism = requestWorkday.getAbsenteeism();
        double late = requestWorkday.getLate();
        double leaveral = requestWorkday.getLeaveral();
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
            int i = workdayDao.addWorkday(employer.getId(),createdate.getId(),absence,overtimen,overtimed,absenteeism,late,leaveral);
            if(i==0){
                message = String.format("添加失败！");
                return ResultFactory.buildFailResult(message);
            }else{
                return ResultFactory.buildSuccessResult(message,i);
            }
        }
    }

    //按员工姓名和时间修改出勤记录
    @CrossOrigin
    @PutMapping("incomes/updateWorkday")
    @ResponseBody
    public Result updateWorkday(@RequestBody RequestWorkday requestWorkday) {
        int id = requestWorkday.getId();
        String time = requestWorkday.getTime();
        int absence = requestWorkday.getAbsence();
        double overtimen = requestWorkday.getOvertimen();
        double overtimed = requestWorkday.getOvertimed();
        double absenteeism = requestWorkday.getAbsenteeism();
        double late = requestWorkday.getLate();
        double leaveral = requestWorkday.getLeaveral();
         String message = String.format("修改成功！");
        Createdate createdate = createdateDao.getCreatedateByTime(time);
        if(null==createdate){
            message = String.format("员工姓名或时间不正确！");
            return ResultFactory.buildFailResult(message);
        }else {
            int i = workdayDao.updateWorkday(id, absence, overtimen, overtimed, absenteeism, late, leaveral,createdate.getId());
            if (i == 0) {
                message = String.format("修改失败！");
                return ResultFactory.buildFailResult(message);
            } else {
                return ResultFactory.buildSuccessResult(message, i);
            }
        }
     }

    //按员工姓名和时间删除出勤记录
    @CrossOrigin
    @DeleteMapping("incomes/deleteWorkday")
    @ResponseBody
    public Result deleteWorkday(@RequestBody RequestWorkday requestWorkday) {
        String message = String.format("删除成功！");
        Integer id = requestWorkday.getId();
        if (0 == id ) {
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }
            int i = workdayDao.deleteWorkday(id);
            if(i==0){
                message = String.format("删除失败！");
                return ResultFactory.buildFailResult(message);
            }else{
                return ResultFactory.buildSuccessResult(message,i);
            }
    }

}
