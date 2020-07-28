package com.muicc.incomes.controller;


import com.muicc.incomes.dao.*;
import com.muicc.incomes.model.RequestWorkday;
import com.muicc.incomes.pojo.*;
import com.muicc.incomes.result.Result;
import com.muicc.incomes.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
            Employer employerById = employerDao.getEmployerById(workday.getEid());
            if(null==employerById){
                continue;
            }
            map.put("name", employerById.getName());//员工姓名
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
    @Transactional
    public Result addWorkday(@RequestBody RequestWorkday requestWorkday) {
        String message = String.format("添加成功！");
        String name = requestWorkday.getName();
        if (null == name ||name.equals("")) {
            message = String.format("员工姓名不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        String time = requestWorkday.getTime();
        if (null == time ||time.equals("")) {
            message = String.format("时间不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        double absence = requestWorkday.getAbsence();
        double paidLeave = requestWorkday.getPaidLeave();
        double overtimen = requestWorkday.getOvertimen();
        double overtimed = requestWorkday.getOvertimed();
        double absenteeism = requestWorkday.getAbsenteeism();
        double late = requestWorkday.getLate();
        int lateTimes = requestWorkday.getLateTimes();
        double leaveral = requestWorkday.getLeaveral();
        int leaveralTimes = requestWorkday.getLeaveralTimes();
        Employer employer = employerDao.getEmployerByName(name);
        Createdate createdate = createdateDao.getCreatedateByTime(time);
        Workday workdayByEidAndCdid = workdayDao.getWorkdayByEidAndCdid(employer.getId(), createdate.getId());
        if(workdayByEidAndCdid!=null){
            message = String.format("该员工已存在该月份下的记录！");
            return ResultFactory.buildFailResult(message);
        }
        List<Stay> allStay = stayDao.getAllStay();
        Userdefault1 userdefault1ByEid = userdefault1Dao.getUserdefault1ByEid(employer.getId());
        Double t = allStay.get(0).getTime();//迟到早退时间限制
        Double bei = allStay.get(0).getBei();//旷工扣款倍数
        double regularpay = userdefault1ByEid.getRegularpay();//基本工资
        if(absenteeism!=0&&late+leaveral>=t){
            double cutmoney = regularpay / 21.75 / 8 * bei *(absenteeism+late+leaveral);
            int j = awaysDao.addAways(1, cutmoney, employer.getId(), createdate.getId(), 1);
            if (j == 0) {
                message = String.format("添加失败！错误代码441");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
        }
        if(absenteeism!=0&&late+leaveral<t){
            double cutmoney = regularpay / 21.75 / 8 * bei * absenteeism;
            int j = awaysDao.addAways(1,cutmoney,employer.getId(),createdate.getId(),1);
            if (j == 0) {
                message = String.format("添加失败！错误代码442");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
        }
        int i = workdayDao.addWorkday(employer.getId(), createdate.getId(), paidLeave, absence, overtimen, overtimed, absenteeism, late, lateTimes, leaveral, leaveralTimes);
        if (i == 0) {
            message = String.format("添加失败！错误代码440");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message, i);
    }

    //按ID修改出勤记录
    @CrossOrigin
    @PostMapping("incomes/updateWorkday")
    @ResponseBody
    @Transactional
    public Result updateWorkday(@RequestBody RequestWorkday requestWorkday) {
        String message = String.format("修改成功！");
        int id = requestWorkday.getId();
        String name = requestWorkday.getName();
        if (null == name ||name.equals("")) {
            message = String.format("员工姓名不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        String time = requestWorkday.getTime();
        if (null == time ||time.equals("")) {
            message = String.format("时间不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        double absence = requestWorkday.getAbsence();
        double paidLeave = requestWorkday.getPaidLeave();
        double overtimen = requestWorkday.getOvertimen();
        double overtimed = requestWorkday.getOvertimed();
        double absenteeism = requestWorkday.getAbsenteeism();
        double late = requestWorkday.getLate();
        int lateTimes = requestWorkday.getLateTimes();
        double leaveral = requestWorkday.getLeaveral();
        int leaveralTimes = requestWorkday.getLeaveralTimes();
        Createdate createdate = createdateDao.getCreatedateByTime(time);
        Employer employer = employerDao.getEmployerByName(name);
        Workday workdayByEidAndCdid = workdayDao.getWorkdayByEidAndCdid(employer.getId(), createdate.getId());
        if(workdayByEidAndCdid!=null&&workdayByEidAndCdid.getId()!=id){
            message = String.format("该员工已存在该月份下的记录！");
            return ResultFactory.buildFailResult(message);
        }
        Workday workdayById = workdayDao.getWorkdayById(id);
        Aways awaysByEidAndCdid = awaysDao.getByEidAndCdid(workdayById.getEid(), workdayById.getCdid());
        if(awaysByEidAndCdid!=null){
            int k = awaysDao.deleteAways(workdayById.getEid(), workdayById.getCdid());
            if (k == 0) {
                message = String.format("修改失败！错误代码440");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
        }
        List<Stay> allStay = stayDao.getAllStay();
        Userdefault1 userdefault1ByEid = userdefault1Dao.getUserdefault1ByEid(workdayById.getEid());
        Double t = allStay.get(0).getTime();//迟到早退时间限制
        Double bei = allStay.get(0).getBei();//旷工扣款倍数
        double regularpay = userdefault1ByEid.getRegularpay();//基本工资
        if(absenteeism!=0&&late+leaveral>=t){
            double cutmoney = regularpay / 21.75 / 8 * bei *(absenteeism+late+leaveral);
            int j = awaysDao.addAways(1,cutmoney,workdayById.getEid(),createdate.getId(),1);
            if (j == 0) {
                message = String.format("修改失败！错误代码441");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
        }
        if(absenteeism!=0&&late+leaveral<t){
            double cutmoney = regularpay / 21.75 / 8 * bei * absenteeism;
            int j = awaysDao.addAways(1,cutmoney,workdayById.getEid(),createdate.getId(),1);
            if (j == 0) {
                message = String.format("修改失败！错误代码442");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
        }
        int i = workdayDao.updateWorkday(id,paidLeave, absence, overtimen, overtimed, absenteeism, late,lateTimes, leaveral,leaveralTimes,createdate.getId());
        if (i == 0) {
            message = String.format("修改失败！错误代码443");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message, i);
     }

    //按ID删除出勤记录
    @CrossOrigin
    @PostMapping("incomes/deleteWorkday")
    @ResponseBody
    @Transactional
    public Result deleteWorkday(@RequestBody RequestWorkday requestWorkday) {
        String message = String.format("删除成功！");
        Integer id = requestWorkday.getId();
        if (0 == id ) {
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }
        Workday workdayById = workdayDao.getWorkdayById(id);
        Aways byEidAndCdid = awaysDao.getByEidAndCdid(workdayById.getEid(), workdayById.getCdid());
        if(byEidAndCdid!=null){
            int i = awaysDao.deleteAways(workdayById.getEid(), workdayById.getCdid());
            if(i==0){
                message = String.format("删除失败！错误代码440");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
        }
        int j = workdayDao.deleteWorkday(id);
        if(j==0){
            message = String.format("删除失败！错误代码441");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message,j);
    }

}
