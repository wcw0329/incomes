package com.muicc.incomes.controller;


import com.muicc.incomes.dao.CutDao;
import com.muicc.incomes.dao.CutcategoryDao;
import com.muicc.incomes.dao.CreatedateDao;
import com.muicc.incomes.dao.EmployerDao;
import com.muicc.incomes.model.RequestCut;
import com.muicc.incomes.pojo.Createdate;
import com.muicc.incomes.pojo.Cut;
import com.muicc.incomes.pojo.Employer;
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
public class CutController {

    @Autowired
    CutDao cutDao;

    @Autowired
    EmployerDao employerDao;

    @Autowired
    CreatedateDao createdateDao;

    @Autowired
    CutcategoryDao cutcategoryDao;

    //查找所有月扣款金额信息
    @CrossOrigin
    @GetMapping(value = "incomes/getAllCut")
    @ResponseBody
    public List getAllCut() {
        List<Cut> cutList = cutDao.getAllCut();
        List list = new ArrayList<>();
        for(int i = 0; i < cutList.size(); i++){
            Map map = new HashMap();
            Cut cut = cutList.get(i);
            map.put("num", i+1);//序号
            map.put("id", cut.getId());//ID
            Employer employerById = employerDao.getEmployerById(cut.getEid());
            if(null==employerById){
                continue;
            }
            map.put("ename",employerById.getName());//员工姓名
            String nameById = cutcategoryDao.getNameById(cut.getCcid());
            map.put("name", nameById);//扣款种类
            map.put("cut", cut.getCut());//扣款金额
            Createdate createdateByCdid = createdateDao.getCreatedateByCdid(cut.getCdid());
            map.put("time", createdateByCdid.getName());//发放时间
            String status = null;
            if(cut.getStatus()==0){
                status = "已提交";
            }else{
                status = "未提交";
            }
            map.put("status", status);//状态
            list.add(map);
        }
        return list;
    }

    //按ID添加月扣款金额信息
    @CrossOrigin
    @PostMapping("incomes/addCut")
    @ResponseBody
    @Transactional
    public Result addCut(@RequestBody RequestCut requestCut) {
        String message = String.format("添加成功！");
        String ename = requestCut.getEname();//员工姓名
        if(null==ename||ename.equals("")){
            message = String.format("员工姓名不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        Employer employerByName = employerDao.getEmployerByName(ename);
        int eid =employerByName.getId();
        String acname = requestCut.getCategory();//扣款种类
        if(null==acname||acname.equals("")){
            message = String.format("扣款种类不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        int ccid = cutcategoryDao.getIdByName(acname);
        double cut = requestCut.getCut();//扣款金额
        if(0.0==cut){
            message = String.format("扣款金额不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        String time = requestCut.getTime();//发放时间
        Createdate createdateByTime = createdateDao.getCreatedateByTime(time);
        if(null==createdateByTime||createdateByTime.equals("")){
            message = String.format("扣款时间不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        int cdid =createdateByTime.getId();
        Cut cutByEidAndCcidAndCdid= cutDao.getCutByEidAndCcidAndCdid(eid, ccid,cdid);
        if(cutByEidAndCcidAndCdid!=null){
            message = String.format("该员工已存在该月份该扣款种类记录！");
            return ResultFactory.buildFailResult(message);
        }
        int i = cutDao.addCut(ccid,cut,eid,cdid);
        if(i==0){
            message = String.format("添加失败！错误代码441");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message,i);
    }

    //按ID修改月扣款金额信息
    @CrossOrigin
    @PostMapping("incomes/updateCut")
    @ResponseBody
    @Transactional
    public Result updateCut(@RequestBody RequestCut requestCut) {
        String message = String.format("修改成功！");
        int id = requestCut.getId();//ID
        String ename = requestCut.getEname();//员工姓名
        if(null==ename||ename.equals("")){
            message = String.format("员工姓名不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        Employer employerByName = employerDao.getEmployerByName(ename);
        int eid =employerByName.getId();
        String acname = requestCut.getCategory();//扣款种类
        if(null==acname||acname.equals("")){
            message = String.format("扣款种类不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        int ccid = cutcategoryDao.getIdByName(acname);
        double cut = requestCut.getCut();//扣款金额
        if(0.0==cut){
            message = String.format("扣款金额不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        String time = requestCut.getTime();//发放时间
        Createdate createdateByTime = createdateDao.getCreatedateByTime(time);
        if(null==createdateByTime||createdateByTime.equals("")){
            message = String.format("扣款时间不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        int cdid =createdateByTime.getId();
        int i = cutDao.updateCut(id,ccid,cut,eid,cdid);
        if(i==0){
            message = String.format("修改失败！错误代码441");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message,i);
    }


    //按ID删除月扣款金额信息
    @CrossOrigin
    @PostMapping("incomes/deleteCut")
    @ResponseBody
    @Transactional
    public Result deleteCut(@RequestBody RequestCut requestCut) {
        String message = String.format("删除成功！");
        int id = requestCut.getId();//ID
        int i = cutDao.deleteCut(id);
        if(i==0){
            message = String.format("删除失败！错误代码441");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message,i);
    }
}
