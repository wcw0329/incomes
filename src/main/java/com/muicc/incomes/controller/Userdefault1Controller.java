package com.muicc.incomes.controller;


import com.muicc.incomes.dao.*;
import com.muicc.incomes.model.RequestAllowance;
import com.muicc.incomes.model.RequestCut;
import com.muicc.incomes.model.RequestUserdefault1;
import com.muicc.incomes.pojo.Employer;
import com.muicc.incomes.pojo.Userdefault1;
import com.muicc.incomes.result.Result;
import com.muicc.incomes.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Userdefault1Controller {

    @Autowired
    Userdefault1Dao userdefault1Dao;

    @Autowired
    EmployerDao employerDao;

    @Autowired
    AllowanceDao allowanceDao;

    @Autowired
    CutDao cutDao;

    @Autowired
    AllowancecategoryDao allowancecategoryDao;

    @Autowired
    CutcategoryDao cutcategoryDao;

    //按员工ID查找员工工资信息
    @CrossOrigin
    @GetMapping(value = "incomes/getUserdefault1ByEid")
    @ResponseBody
    public List getUserdefault1ByEid(int eid) {

        List<Userdefault1> Userdefault1ByEid = userdefault1Dao.getUserdefault1ByEid(eid);
        List list = new ArrayList<>();
        for(int i = 0; i < Userdefault1ByEid.size(); i++){
            Map map = new HashMap();
            Userdefault1 userdefault1 = Userdefault1ByEid.get(i);
            Employer employerById = employerDao.getEmployerById(eid);
            map.put("id", userdefault1.getId());//ID
            map.put("ename", employerById.getName());//姓名
            map.put("eid", userdefault1.getEid());//员工工号
            map.put("regularpay", userdefault1.getRegularpay());//基本工资
            map.put("allowanceids",userdefault1.getAllowanceids());//补贴
            map.put("cutids", userdefault1.getCutids());//扣款
            map.put("insurancebase", userdefault1.getInsurancebase());//社保基数
            map.put("updatedate", userdefault1.getUpdatedate());//更新时间
            map.put("note", userdefault1.getNote());//备注
            list.add(map);
        }
        return list;
    }

    //添加员工工资信息
    @CrossOrigin
    @PostMapping(value = "incomes/addUserdefault1")
    @ResponseBody
    public Result addUserdefault1(@RequestBody RequestUserdefault1 requestUserdefault1) throws ParseException {
        String message = String.format("添加成功！");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String ename = requestUserdefault1.getEname();//员工姓名
        Integer eid = employerDao.getEmployerByName(ename).getId();//员工ID
        double insurancebase = requestUserdefault1.getInsurancebase();//社保基数
        double regularpay = requestUserdefault1.getRegularpay();//基本工资
        String updatedate = requestUserdefault1.getUpdatedate();//更新时间
        String note = requestUserdefault1.getNote();//备注
        int i =userdefault1Dao.addUserdefault2(eid,0,0,regularpay,insurancebase,simpleDateFormat.parse(updatedate),note);
        if(i==0){
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }

    }

    //添加补贴
    @CrossOrigin
    @PostMapping(value = "incomes/addAllowance")
    @ResponseBody
    public Result addAllowance(@RequestBody RequestAllowance requestAllowance) {
        String message = String.format("添加成功！");
        String category = requestAllowance.getCategory();//补贴种类
        double allowance = requestAllowance.getAllowance();//补贴金额
        int idByName = allowancecategoryDao.getIdByName(category);//补贴种类ID
        Integer eid = requestAllowance.getEid();//员工ID
        int id = userdefault1Dao.getLastId(eid);
        int i = allowanceDao.addAllowance(idByName,allowance,id);
        if(i==0){
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }

    }

    //添加扣款
    @CrossOrigin
    @PostMapping(value = "incomes/addCut")
    @ResponseBody
    public Result addCut(@RequestBody RequestCut requestCut) {
        String message = String.format("添加成功！");
        String category = requestCut.getCategory();//扣款种类
        double cut = requestCut.getCut();//扣款金额
        int idByName = cutcategoryDao.getIdByName(category);//扣款种类ID
        Integer eid = requestCut.getEid();//员工ID
        int id = userdefault1Dao.getLastId(eid);
        int i = cutDao.addCut(idByName,cut,id);
        if(i==0){
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }

    }

    //更新员工工资信息
    @CrossOrigin
    @PutMapping(value = "incomes/updateUserdefault1")
    @ResponseBody
    public Result updateUserdefault1(@RequestBody RequestUserdefault1 requestUserdefault1) {
        String message = String.format("添加成功！");
        Integer eid = requestUserdefault1.getEid();//员工ID
        int userdefault1Id = userdefault1Dao.getUserdefault1ByEid(eid).get(0).getId();
        double allowanceids =allowanceDao.getSumAllowance(userdefault1Id);
        double cutids =cutDao.getSumCut(userdefault1Id);
        int i =userdefault1Dao.updateUserdefault1(eid,allowanceids,cutids);
        if(i==0){
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }

    }

    //按ID删除员工工资信息
    @CrossOrigin
    @PutMapping(value = "incomes/deleteUserdefault1")
    @ResponseBody
    public Result deleteUserdefault1(@RequestBody Userdefault1 requestUserdefault1) {
        String message = String.format("删除成功！");
        Integer id = requestUserdefault1.getId();//ID
        int i = userdefault1Dao.deleteUserdefault1(id);
        int j = allowanceDao.deleteAllowance(id);
        int k = cutDao.deleteCut(id);
        if(i==0||j==0||k==0){
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }

    }
}
