package com.muicc.incomes.controller;


import com.muicc.incomes.dao.AllowanceDao;
import com.muicc.incomes.dao.AllowancecategoryDao;
import com.muicc.incomes.dao.CreatedateDao;
import com.muicc.incomes.dao.EmployerDao;
import com.muicc.incomes.model.RequestAllowance;
import com.muicc.incomes.pojo.Allowance;
import com.muicc.incomes.pojo.Createdate;
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
public class AllowanceController {
    @Autowired
    AllowanceDao allowanceDao;

    @Autowired
    EmployerDao employerDao;

    @Autowired
    CreatedateDao createdateDao;

    @Autowired
    AllowancecategoryDao allowancecategoryDao;

    //查找所有月补贴金额信息
    @CrossOrigin
    @GetMapping(value = "incomes/getAllAllowance")
    @ResponseBody
    public List getAllAllowance() {
        List<Allowance> allowanceList = allowanceDao.getAllAllowance();
        List list = new ArrayList<>();
        for(int i = 0; i < allowanceList.size(); i++){
            Map map = new HashMap();
            Allowance allowance = allowanceList.get(i);
            map.put("num", i+1);//序号
            map.put("id", allowance.getId());//ID
            Employer employerById = employerDao.getEmployerById(allowance.getEid());
            if(null==employerById){
                continue;
            }
            map.put("ename",employerById.getName());//员工姓名
            String nameById = allowancecategoryDao.getNameById(allowance.getAlcid());
            map.put("name", nameById);//补贴种类
            map.put("allowance", allowance.getAllowance());//补贴金额
            Createdate createdateByCdid = createdateDao.getCreatedateByCdid(allowance.getCdid());
            map.put("time", createdateByCdid.getName());//发放时间
            String status = null;
            if(allowance.getStatus()==0){
                status = "已提交";
            }else{
                status = "未提交";
            }
            map.put("status", status);//状态
            list.add(map);
        }
        return list;
    }

    //按ID添加月补贴金额信息
    @CrossOrigin
    @PostMapping("incomes/addAllowance")
    @ResponseBody
    @Transactional
    public Result addAllowance(@RequestBody RequestAllowance requestAllowance) {
        String message = String.format("添加成功！");
        String ename = requestAllowance.getEname();//员工姓名
        if(null==ename||ename.equals("")){
            message = String.format("员工姓名不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        Employer employerByName = employerDao.getEmployerByName(ename);
        int eid =employerByName.getId();
        String acname = requestAllowance.getCategory();//补贴种类
        if(null==acname||acname.equals("")){
            message = String.format("补贴种类不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        int alcid = allowancecategoryDao.getIdByName(acname);
        double allowance = requestAllowance.getAllowance();//补贴金额
        if(0.0==allowance){
            message = String.format("补贴金额不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        String time = requestAllowance.getTime();//发放时间
        Createdate createdateByTime = createdateDao.getCreatedateByTime(time);
        if(null==createdateByTime||createdateByTime.equals("")){
            message = String.format("发放时间不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        int cdid =createdateByTime.getId();
        Allowance allowanceByEidAndAcidAndCdid= allowanceDao.getAllowanceByEidAndAlcidAndCdid(eid, alcid,cdid);
        if(allowanceByEidAndAcidAndCdid!=null){
            message = String.format("该员工已存在该月份该补贴种类记录！");
            return ResultFactory.buildFailResult(message);
        }
        int i = allowanceDao.addAllowance(alcid,allowance,eid,cdid);
        if(i==0){
            message = String.format("添加失败！错误代码441");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message,i);
    }

    //按ID修改月补贴金额信息
    @CrossOrigin
    @PostMapping("incomes/updateAllowance")
    @ResponseBody
    @Transactional
    public Result updateAllowance(@RequestBody RequestAllowance requestAllowance) {
        String message = String.format("修改成功！");
        int id = requestAllowance.getId();//ID
        String ename = requestAllowance.getEname();//员工姓名
        if(null==ename||ename.equals("")){
            message = String.format("员工姓名不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        Employer employerByName = employerDao.getEmployerByName(ename);
        int eid =employerByName.getId();
        String acname = requestAllowance.getCategory();//补贴种类
        if(null==acname||acname.equals("")){
            message = String.format("补贴种类不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        int alcid = allowancecategoryDao.getIdByName(acname);
        double allowance = requestAllowance.getAllowance();//补贴金额
        if(0.0==allowance){
            message = String.format("补贴金额不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        String time = requestAllowance.getTime();//发放时间
        Createdate createdateByTime = createdateDao.getCreatedateByTime(time);
        if(null==createdateByTime||createdateByTime.equals("")){
            message = String.format("发放时间不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        int cdid =createdateByTime.getId();
        int i = allowanceDao.updateAllowance(id,alcid,allowance,eid,cdid);
        if(i==0){
            message = String.format("修改失败！错误代码441");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message,i);
    }


    //按ID删除月补贴金额信息
    @CrossOrigin
    @PostMapping("incomes/deleteAllowance")
    @ResponseBody
    @Transactional
    public Result deleteAllowance(@RequestBody RequestAllowance requestAllowance) {
        String message = String.format("删除成功！");
        int id = requestAllowance.getId();//ID
        int i = allowanceDao.deleteAllowance(id);
        if(i==0){
            message = String.format("删除失败！错误代码441");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message,i);
    }
}
