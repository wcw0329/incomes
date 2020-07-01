package com.muicc.incomes.controller;


import com.muicc.incomes.dao.AwardDao;
import com.muicc.incomes.dao.AwardcategoryDao;
import com.muicc.incomes.dao.AwardsDao;
import com.muicc.incomes.dao.EmployerDao;
import com.muicc.incomes.model.RequestAwards;
import com.muicc.incomes.pojo.Award;
import com.muicc.incomes.pojo.Awards;
import com.muicc.incomes.result.Result;
import com.muicc.incomes.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AwardsController {

    @Autowired
    AwardsDao awardsDao;

    @Autowired
    EmployerDao employerDao;

    @Autowired
    AwardDao awardDao;

    @Autowired
    AwardcategoryDao awardcategoryDao;


    //查找所有的奖金规则信息
    @CrossOrigin
    @GetMapping(value = "incomes/getAllAwards")
    @ResponseBody
    public List getAllAwards() {

        List<Awards> awardsList = awardsDao.getAllAwards();
        List list = new ArrayList<>();
        for (int i = 0; i < awardsList.size(); i++) {
            Map map = new HashMap();
            Awards awards = awardsList.get(i);
            List<Award> awardByAwardsid = awardDao.getAwardByAwardsid(awards.getId());
            List list2 = new ArrayList<>();
            for (int j = 0; j < awardByAwardsid.size(); j++) {
                Map map2 = new HashMap();
                Award award = awardByAwardsid.get(j);
                map2.put("award", award.getAward());
                map2.put("category", awardcategoryDao.getNameById(award.getAcid()));
                list2.add(map2);
            }
            map.put("num", i + 1);//序号
            map.put("id", awards.getId());//ID
            map.put("updatedate", awards.getUpdatedate());//更新时间
            map.put("ename", employerDao.getEmployerById(awards.getEid()).getName());//员工姓名
            map.put("time", awards.getTime());//每月迟到时间限制（小时）
            map.put("money", awards.getMoney());//奖励金额
            map.put("award", list2);
            list.add(map);
        }
        return list;
    }

    //添加奖金规则信息
    @CrossOrigin
    @PostMapping("incomes/addAwards")
    @ResponseBody
    public Result addAwards(@RequestBody RequestAwards requestAwards) throws ParseException {
        String message = String.format("添加成功！");
        double time = requestAwards.getTime();//每月迟到时间限制（小时）
        double money = requestAwards.getMoney();//奖励金额
        String category = requestAwards.getCategory();//奖励种类
        String updatedate = requestAwards.getUpdatedate();//更新时间
        String ename = requestAwards.getEname();//员工姓名
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int eid = employerDao.getEmployerByName(ename).getId();
        int acid = awardcategoryDao.getIdByName(category);
        List<Awards> awardsByEid = awardsDao.getAwardsByEid(eid);//null
        if (!awardsByEid.isEmpty()) {
            Award awardByAcidAndAwardsid = awardDao.getAwardByAcidAndAwardsid(awardsByEid.get(0).getId(), acid);
            if (awardByAcidAndAwardsid != null) {
                message = String.format("该员工已存在该奖励种类，请勿重复添加！");
                return ResultFactory.buidResult(100, message, eid);
            } else {
                int j = awardDao.addAward(acid, money, awardsByEid.get(0).getId());
                int sumMoney = awardDao.getSumMoney(awardsByEid.get(0).getId());
                int k = awardsDao.updateAwards(eid, time, sumMoney, simpleDateFormat.parse(updatedate));
                if (j == 0 || k == 0) {
                    message = String.format("添加失败！");
                    return ResultFactory.buildFailResult(message);
                } else {
                    return ResultFactory.buildSuccessResult(message, k);
                }
            }
        } else {
            int i = awardsDao.addAwards(eid, time, 0, simpleDateFormat.parse(updatedate));
            List<Awards> awardsByEid2 = awardsDao.getAwardsByEid(eid);
            for (int m = 0; m < awardsByEid2.size() - 1; m++) {
                int a = awardsDao.deleteAwards(awardsDao.getLastId());
                if (a == 0) {
                    message = String.format("添加失败！");
                    return ResultFactory.buildFailResult(message);
                }
            }
            int j = awardDao.addAward(acid, money, awardsByEid2.get(0).getId());
            int sumMoney = awardDao.getSumMoney(awardsByEid2.get(0).getId());
            int k = awardsDao.updateAwards(eid, time, sumMoney, simpleDateFormat.parse(updatedate));
            if (i == 0 || j == 0 || k == 0) {
                message = String.format("添加失败！");
                return ResultFactory.buildFailResult(message);
            } else {
                return ResultFactory.buildSuccessResult(message, j);
            }
        }
    }

    //修改奖金规则信息
//    @CrossOrigin
//    @PutMapping("incomes/updateAwards")
//    @ResponseBody
//    public Result updateAwards(@RequestBody RequestAwards requestAwards) throws ParseException {
//        String message = String.format("修改成功！");
//        double time = requestAwards.getTime();//每月迟到时间限制（小时）
//        double money = requestAwards.getMoney();//奖励金额
//        String category = requestAwards.getCategory();//奖励种类
//        String updatedate = requestAwards.getUpdatedate();//更新时间
//        String ename = requestAwards.getEname();//员工姓名
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        int eid = employerDao.getEmployerByName(ename).getId();
//        int acid = awardcategoryDao.getIdByName(category);
//        List<Awards> awardsByEid = awardsDao.getAwardsByEid(eid);
//        int awardsid = awardsByEid.get(0).getId();
//        int a = awardsDao.deleteAwards(awardsid);
//        int b = awardDao.deleteAward(awardsid);
//
//        int i = awardsDao.addAwards(eid, time, 0, simpleDateFormat.parse(updatedate));
//        List<Awards> awardsByEid2 = awardsDao.getAwardsByEid(eid);
//        for (int m = 0; m < awardsByEid2.size() - 1; m++) {
//                int n = awardsDao.deleteAwards(awardsDao.getLastId());
//                if (n == 0) {
//                    message = String.format("修改失败！");
//                    return ResultFactory.buildFailResult(message);
//                }
//        }
//        int j = awardDao.addAward(acid, money, awardsByEid2.get(0).getId());
//        int sumMoney = awardDao.getSumMoney(awardsByEid2.get(0).getId());
//        int k = awardsDao.updateAwards(eid, time, sumMoney, simpleDateFormat.parse(updatedate));
//        if (i == 0 || j == 0 || k == 0) {
//                message = String.format("修改失败！");
//                return ResultFactory.buildFailResult(message);
//        } else {
//                return ResultFactory.buildSuccessResult(message, j);
//        }
//    }

    //按管理员账号删除奖金规则信息
    @CrossOrigin
    @DeleteMapping("incomes/deleteAwards")
    @ResponseBody
    public Result deleteAwards(@RequestBody RequestAwards requestAwards) {
        String message = String.format("删除成功！");
        int id = requestAwards.getId();//ID
        if (0 == id) {
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }
        int i = awardsDao.deleteAwards(id);
        int j = awardDao.deleteAward(id);
        if(i==0||j==0){
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }

}
