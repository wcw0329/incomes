package com.muicc.incomes.controller;


import com.muicc.incomes.dao.AwardDao;
import com.muicc.incomes.dao.AwardcategoryDao;
import com.muicc.incomes.dao.CreatedateDao;
import com.muicc.incomes.dao.EmployerDao;
import com.muicc.incomes.model.RequestAward;
import com.muicc.incomes.pojo.Award;
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
public class AwardController {

    @Autowired
    AwardDao awardDao;

    @Autowired
    EmployerDao employerDao;

    @Autowired
    CreatedateDao createdateDao;

    @Autowired
    AwardcategoryDao awardcategoryDao;


    //查找所有月奖金金额信息
    @CrossOrigin
    @GetMapping(value = "incomes/getAllAward")
    @ResponseBody
    public List getAllAward() {
        List<Award> awardList = awardDao.getAllAward();
        List list = new ArrayList<>();
        for(int i = 0; i < awardList.size(); i++){
            Map map = new HashMap();
            Award award = awardList.get(i);
            map.put("num", i+1);//序号
            map.put("id", award.getId());//ID
            Employer employerById = employerDao.getEmployerById(award.getEid());
            if(null==employerById){
                continue;
            }
            map.put("ename",employerById.getName());//员工姓名
            String nameById = awardcategoryDao.getNameById(award.getAcid());
            map.put("name", nameById);//奖金种类
            map.put("award", award.getAward());//奖金金额
            Createdate createdateByCdid = createdateDao.getCreatedateByCdid(award.getCdid());
            map.put("time", createdateByCdid.getName());//发放时间
            String status = null;
            if(award.getStatus()==0){
                status = "已提交";
            }else{
                status = "未提交";
            }
            map.put("status", status);//状态
            list.add(map);
        }
        return list;
    }

    //按ID添加月奖金金额信息
    @CrossOrigin
    @PostMapping("incomes/addAward")
    @ResponseBody
    @Transactional
    public Result addAward(@RequestBody RequestAward requestAward) {
        String message = String.format("添加成功！");
        String ename = requestAward.getEname();//员工姓名
        Employer employerByName = employerDao.getEmployerByName(ename);
        int eid =employerByName.getId();
        String acname = requestAward.getAcname();//奖金种类
        int acid = awardcategoryDao.getIdByName(acname);
        double award = requestAward.getAward();//奖金金额
        String time = requestAward.getTime();//发放时间
        Createdate createdateByTime = createdateDao.getCreatedateByTime(time);
        int cdid =createdateByTime.getId();
        if(null==ename||ename.equals("")||null==acname||acname.equals("")||0.0==award||null==createdateByTime){
            message = String.format("信息填写不正确！");
            return ResultFactory.buildFailResult(message);
        }
        Award awardByEidAndAcidAndCdid= awardDao.getAwardByEidAndAcidAndCdid(eid, acid,cdid);
        if(awardByEidAndAcidAndCdid!=null){
            message = String.format("该员工已存在该月份该奖金种类记录！");
            return ResultFactory.buildFailResult(message);
        }
        int i = awardDao.addAward(acid,award,eid,cdid);
        if(i==0){
            message = String.format("添加失败！错误代码441");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message,i);
    }

    //按ID修改月奖金金额信息
    @CrossOrigin
    @PostMapping("incomes/updateAward")
    @ResponseBody
    @Transactional
    public Result updateAward(@RequestBody RequestAward requestAward) {
        String message = String.format("修改成功！");
        int id = requestAward.getId();//ID
        String ename = requestAward.getEname();//员工姓名
        if(null==ename||ename.equals("")){
            message = String.format("员工姓名不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        Employer employerByName = employerDao.getEmployerByName(ename);
        int eid =employerByName.getId();
        String acname = requestAward.getAcname();//奖金种类
        if(null==acname||acname.equals("")){
            message = String.format("奖金种类不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        int acid = awardcategoryDao.getIdByName(acname);
        double award = requestAward.getAward();//奖金金额
        if(0.0==award){
            message = String.format("奖金金额不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        String time = requestAward.getTime();//发放时间
        Createdate createdateByTime = createdateDao.getCreatedateByTime(time);
        if(null==createdateByTime||createdateByTime.equals("")){
            message = String.format("发放时间不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        int cdid =createdateByTime.getId();
        int i = awardDao.updateAward(id,acid,award,eid,cdid);
        if(i==0){
            message = String.format("修改失败！错误代码441");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message,i);
    }


    //按ID删除月奖金金额信息
    @CrossOrigin
    @PostMapping("incomes/deleteAward")
    @ResponseBody
    @Transactional
    public Result deleteAward(@RequestBody RequestAward requestAward) {
        String message = String.format("删除成功！");
        int id = requestAward.getId();//ID
        int i = awardDao.deleteAward(id);
        if(i==0){
            message = String.format("删除失败！错误代码441");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message,i);
    }
}
