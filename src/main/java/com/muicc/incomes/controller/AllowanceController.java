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
            map.put("ename",employerById.getName());//员工姓名
            String nameById = allowancecategoryDao.getNameById(allowance.getAlcid());
            map.put("name", nameById);//补贴种类
            map.put("allowance", allowance.getAllowance());//补贴金额
            Createdate createdateByCdid = createdateDao.getCreatedateByCdid(allowance.getCdid());
            map.put("time", createdateByCdid.getName());//发放时间
            list.add(map);
        }
        return list;
    }

    //按ID添加月补贴金额信息
    @CrossOrigin
    @PostMapping("incomes/addAllowance")
    @ResponseBody
    public Result addAllowance(@RequestBody RequestAllowance requestAllowance) {
        String message = String.format("添加成功！");
        String ename = requestAllowance.getEname();//员工姓名
        Employer employerByName = employerDao.getEmployerByName(ename);
        int eid =employerByName.getId();
        String acname = requestAllowance.getCategory();//补贴种类
        int alcid = allowancecategoryDao.getIdByName(acname);
        double allowance = requestAllowance.getAllowance();//补贴金额
        String time = requestAllowance.getTime();//发放时间
        Createdate createdateByTime = createdateDao.getCreatedateByTime(time);
        int cdid =createdateByTime.getId();

        Allowance allowanceByEidAndAcidAndCdid= allowanceDao.getAllowanceByEidAndAlcidAndCdid(eid, alcid,cdid);
        if(allowanceByEidAndAcidAndCdid!=null){
            message = String.format("该员工已存在该月份该补贴种类记录！");
            return ResultFactory.buidResult(100,message,allowanceByEidAndAcidAndCdid);
        }

        int i = allowanceDao.addAllowance(alcid,allowance,eid,cdid);
        if(i==0){
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }

    //按ID修改月补贴金额信息
    @CrossOrigin
    @PostMapping("incomes/updateAllowance")
    @ResponseBody
    public Result updateAllowance(@RequestBody RequestAllowance requestAllowance) {
        String message = String.format("修改成功！");
        int id = requestAllowance.getId();//ID
        String ename = requestAllowance.getEname();//员工姓名
        Employer employerByName = employerDao.getEmployerByName(ename);
        int eid =employerByName.getId();
        String acname = requestAllowance.getCategory();//补贴种类
        int alcid = allowancecategoryDao.getIdByName(acname);
        double allowance = requestAllowance.getAllowance();//补贴金额
        String time = requestAllowance.getTime();//发放时间
        Createdate createdateByTime = createdateDao.getCreatedateByTime(time);
        int cdid =createdateByTime.getId();

        int i = allowanceDao.updateAllowance(id,alcid,allowance,eid,cdid);
        if(i==0){
            message = String.format("修改失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }


    //按ID删除月补贴金额信息
    @CrossOrigin
    @PostMapping("incomes/deleteAllowance")
    @ResponseBody
    public Result deleteAllowance(@RequestBody RequestAllowance requestAllowance) {
        String message = String.format("删除成功！");
        int id = requestAllowance.getId();//ID
        int i = allowanceDao.deleteAllowance(id);
        if(i==0){
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }
}
