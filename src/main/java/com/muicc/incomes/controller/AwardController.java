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
            map.put("ename",employerById.getName());//员工姓名
            String nameById = awardcategoryDao.getNameById(award.getAcid());
            map.put("name", nameById);//奖金种类
            map.put("award", award.getAward());//奖金金额
            Createdate createdateByCdid = createdateDao.getCreatedateByCdid(award.getCdid());
            map.put("time", createdateByCdid.getName());//发放时间
            list.add(map);
        }
        return list;
    }

    //按ID添加月奖金金额信息
    @CrossOrigin
    @PostMapping("incomes/addAward")
    @ResponseBody
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

        Award awardByEidAndAcidAndCdid= awardDao.getAwardByEidAndAcidAndCdid(eid, acid,cdid);
        if(awardByEidAndAcidAndCdid!=null){
            message = String.format("该员工已存在该月份该奖金种类记录！");
            return ResultFactory.buidResult(100,message,awardByEidAndAcidAndCdid);
        }

        int i = awardDao.addAward(acid,award,eid,cdid);
        if(i==0){
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }

    //按ID修改月奖金金额信息
    @CrossOrigin
    @PostMapping("incomes/updateAward")
    @ResponseBody
    public Result updateAward(@RequestBody RequestAward requestAward) {
        String message = String.format("修改成功！");
        int id = requestAward.getId();//ID
        String ename = requestAward.getEname();//员工姓名
        Employer employerByName = employerDao.getEmployerByName(ename);
        int eid =employerByName.getId();
        String acname = requestAward.getAcname();//奖金种类
        int acid = awardcategoryDao.getIdByName(acname);
        double award = requestAward.getAward();//奖金金额
        String time = requestAward.getTime();//发放时间
        Createdate createdateByTime = createdateDao.getCreatedateByTime(time);
        int cdid =createdateByTime.getId();

        int i = awardDao.updateAward(id,acid,award,eid,cdid);
        if(i==0){
            message = String.format("修改失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }


    //按ID删除月奖金金额信息
    @CrossOrigin
    @PostMapping("incomes/deleteAward")
    @ResponseBody
    public Result deleteAward(@RequestBody RequestAward requestAward) {
        String message = String.format("删除成功！");
        int id = requestAward.getId();//ID
        int i = awardDao.deleteAward(id);
        if(i==0){
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }
}
