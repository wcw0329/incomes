package com.muicc.incomes.controller;


import com.muicc.incomes.dao.EmployerDao;
import com.muicc.incomes.dao.InsuranceDao;
import com.muicc.incomes.dao.InsurancerateDao;
import com.muicc.incomes.dao.Userdefault1Dao;
import com.muicc.incomes.model.RequestInsurancerate;
import com.muicc.incomes.pojo.Insurancerate;
import com.muicc.incomes.pojo.Userdefault1;
import com.muicc.incomes.result.Result;
import com.muicc.incomes.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class InsurancerateController {

    @Autowired
    InsurancerateDao insurancerateDao;

    @Autowired
    InsuranceDao insuranceDao;

    @Autowired
    EmployerDao employerDao;

    @Autowired
    Userdefault1Dao userdefault1Dao;

    //查找所有的社保信息
    @CrossOrigin
    @GetMapping(value = "incomes/getAllInsurancerate")
    @ResponseBody
    public List getAllInsurancerate() {

        List<Insurancerate> insurancerateList = insurancerateDao.getAllInsurancerate();
        List list = new ArrayList<>();
        for(int i = 0; i < insurancerateList.size(); i++){
            Map map = new HashMap();
            Insurancerate insurancerate = insurancerateList.get(i);
            map.put("num", i+1);//序号
            map.put("id", insurancerate.getId());//序号
            map.put("updatedate",insurancerate.getUpdatedate());//更新时间
            map.put("ename",employerDao.getEmployerById(insurancerate.getEid()).getName());//员工姓名
            map.put("insurancerate", insurancerate.getInsurancerate());//社保费率
            map.put("fixedfee", insurancerate.getFixedfee());//固定社保费额
            list.add(map);
        }
        return list;
    }

    //添加社保信息
    @CrossOrigin
    @PostMapping("incomes/addInsurancerate")
    @ResponseBody
    public Result addInsurancerate(@RequestBody RequestInsurancerate requestInsurancerate) throws ParseException {
        String ename = requestInsurancerate.getEname();//员工姓名
        double insurancerate = requestInsurancerate.getInsurancerate();//社保费率
        double fixedfee = requestInsurancerate.getFixedfee();//固定社保费额
        String updatedate = requestInsurancerate.getUpdatedate();//更新时间

        String message = String.format("添加成功！");
        if (null == ename ||null== updatedate) {
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        }
        int eid =employerDao.getEmployerByName(ename).getId();
        List<Insurancerate> insurancerateList = insurancerateDao.getAllInsurancerate();
        for(int i = 0; i < insurancerateList.size(); i++){
            if(insurancerateList.get(i).getEid()==eid){
                message = String.format("该员工已有记录，请勿重复添加！");
                return ResultFactory.buidResult(100,message,eid);
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int i = insurancerateDao.addInsurancerate(eid,insurancerate,fixedfee,simpleDateFormat.parse(updatedate));
        Insurancerate insurancerateByEid = insurancerateDao.getInsurancerateByEid(eid);
        Integer lastId = userdefault1Dao.getLastId(eid);
        Userdefault1 userdefault1ByUdid = userdefault1Dao.getUserdefault1ByUdid(lastId);
        double insurance =userdefault1ByUdid.getInsurancebase()*insurancerate+fixedfee;
        int j = insuranceDao.addInsurance(insurance,insurancerateByEid.getId());
        if(i==0||j==0){
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }

    //按社保记录ID修改社保信息
    @CrossOrigin
    @PutMapping("incomes/updateInsurancerate")
    @ResponseBody
    public Result updateInsurancerate(@RequestBody RequestInsurancerate requestInsurancerate) throws ParseException {
        String ename = requestInsurancerate.getEname();//员工姓名
        double insurancerate = requestInsurancerate.getInsurancerate();//社保费率
        double fixedfee = requestInsurancerate.getFixedfee();//固定社保费额
        String updatedate = requestInsurancerate.getUpdatedate();//更新时间

        String message = String.format("修改成功！");
        if (null== updatedate) {
            message = String.format("修改失败！");
            return ResultFactory.buildFailResult(message);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int eid =employerDao.getEmployerByName(ename).getId();
        int i = insurancerateDao.updateInsurancerate(eid,insurancerate,fixedfee,simpleDateFormat.parse(updatedate));
        Insurancerate insurancerateByEid = insurancerateDao.getInsurancerateByEid(eid);
        Integer lastId = userdefault1Dao.getLastId(eid);
        Userdefault1 userdefault1ByUdid = userdefault1Dao.getUserdefault1ByUdid(lastId);
        double insurance =userdefault1ByUdid.getInsurancebase()*insurancerateByEid.getInsurancerate()+insurancerateByEid.getFixedfee();
        int j = insuranceDao.updateInsurance(insurance,insurancerateByEid.getId());
        if(i==0||j==0){
            message = String.format("修改失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }

    //按社保记录ID删除社保信息（假删除）
    @CrossOrigin
    @PutMapping("incomes/deleteInsurancerate")
    @ResponseBody
    public Result deleteInsurancerate(@RequestBody RequestInsurancerate requestInsurancerate) {
        int id =requestInsurancerate.getId();//ID
        String message = String.format("删除成功！");

        int i = insurancerateDao.deleteInsurancerate(id);
        int j = insuranceDao.deleteInsurance(id);
        if(i==0||j==0){
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }

}
