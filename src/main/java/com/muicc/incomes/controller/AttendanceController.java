package com.muicc.incomes.controller;


import com.muicc.incomes.dao.AttendanceDao;
import com.muicc.incomes.model.RequestAttendance;
import com.muicc.incomes.pojo.Attendance;
import com.muicc.incomes.result.Result;
import com.muicc.incomes.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AttendanceController {

    @Autowired
    AttendanceDao attendanceDao;

    //查找所有的考勤规则信息
    @CrossOrigin
    @GetMapping(value = "incomes/getAllAttendance")
    @ResponseBody
    public List getAllAttendance(HttpSession session) {

        List<Attendance> attendanceList = attendanceDao.getAllAttendance();
        List list = new ArrayList<>();
        for(int i = 0; i < attendanceList.size(); i++){
            Map map = new HashMap();
            Attendance attendance = attendanceList.get(i);
            map.put("id", attendance.getId());//ID
            map.put("name", attendance.getName());//奖金名称
            map.put("time",attendance.getTime());//迟到早退时间限制
            map.put("times", attendance.getTimes());//迟到早退次数限制
            map.put("rate", attendance.getRate());//奖金比例
            map.put("fixedAward", attendance.getFixedAward());//固定奖金
            map.put("status", attendance.getStatus());//状态
            list.add(map);
        }
        return list;
    }

    //更改全勤奖设置
    @CrossOrigin
    @PostMapping(value = "incomes/updateAttendanceStatus")
    @ResponseBody
    @Transactional
    public Result updateAttendanceStatus(@RequestBody RequestAttendance requestAttendance) {
        String message = String.format("全勤奖设置更改成功！");
        int status = 0;
        if(requestAttendance.isValues()){
            status = 1;
        }
        int i = attendanceDao.updateAttendanceStatus(status);
        if(i==0){
            message = String.format("更改失败！错误代码440");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message,i);
    }

    //添加考勤规则信息
    @CrossOrigin
    @PostMapping("incomes/addAttendance")
    @ResponseBody
    @Transactional
    public Result addAttendance(@RequestBody Attendance requestAttendance) {
        String message = String.format("添加成功！");
        String name ="全勤奖";
        double time = requestAttendance.getTime();
        int times = requestAttendance.getTimes();
        double rate = requestAttendance.getRate();
        double fixed_award = requestAttendance.getFixedAward();
        if(0.0==time||0==times||0.0==rate||0.0==fixed_award){
            message = String.format("信息填写错误！");
            return ResultFactory.buildFailResult(message);
        }
        int i = attendanceDao.addAttendance(name,time,times,rate,fixed_award);
        if(i==0){
            message = String.format("添加失败！错误代码440");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message,i);
    }

    //修改考勤规则信息
    @CrossOrigin
    @PostMapping("incomes/updateAttendance")
    @ResponseBody
    @Transactional
    public Result updateAttendance(@RequestBody Attendance requestAttendance) {
        String message = String.format("添加成功！");
        int id = requestAttendance.getId();//ID
        double time = requestAttendance.getTime();
        int times = requestAttendance.getTimes();
        double rate = requestAttendance.getRate();
        double fixed_award = requestAttendance.getFixedAward();
        if(0.0==time||0==times||0.0==rate||0.0==fixed_award){
            message = String.format("信息填写错误！");
            return ResultFactory.buildFailResult(message);
        }
        int i = attendanceDao.updateAttendance(id,time,times,rate,fixed_award);
        if(i==0){
            message = String.format("修改失败！错误代码440");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message,i);
    }

    //按ID删除考勤规则信息
    @CrossOrigin
    @PostMapping("incomes/deleteAttendance")
    @ResponseBody
    @Transactional
    public Result deleteAttendance(@RequestBody Attendance requestAttendance) {
        String message = String.format("删除成功！");
        int id = requestAttendance.getId();//ID
        if (0 == id) {
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }
        int i = attendanceDao.deleteAttendance(id);
        if(i==0){
            message = String.format("删除失败！错误代码440");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message,i);
    }
}
