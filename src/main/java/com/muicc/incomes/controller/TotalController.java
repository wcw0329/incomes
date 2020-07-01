package com.muicc.incomes.controller;


import com.muicc.incomes.dao.*;
import com.muicc.incomes.model.RequestTotal;
import com.muicc.incomes.pojo.*;
import com.muicc.incomes.result.Result;
import com.muicc.incomes.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
public class TotalController {
    @Autowired
    TotalDao totalDao;
    @Autowired
    CreatedateDao createdateDao;
    @Autowired
    UserobjectDao userobjectDao;
    @Autowired
    Userdefault1Dao userdefault1Dao;
    @Autowired
    EmployerDao employerDao;
    @Autowired
    UpdatedateDao updatedateDao;
    @Autowired
    AwardsDao awardsDao;
    @Autowired
    AwardDao awardDao;
    @Autowired
    StayDao stayDao;
    @Autowired
    AwardcategoryDao awardcategoryDao;
    @Autowired
    TotalwageDao totalwageDao;
    @Autowired
    TaxrateDao taxrateDao;
    @Autowired
    TaxDao taxDao;
    @Autowired
    InsuranceDao insuranceDao;
    @Autowired
    InsurancerateDao insurancerateDao;
    @Autowired
    WorkdayDao workdayDao;
    @Autowired
    AwaysDao awaysDao;
    @Autowired
    UserDao userDao;
    @Autowired
    AllowancecategoryDao allowancecategoryDao;
    @Autowired
    AllowanceDao allowanceDao;
    @Autowired
    CutcategoryDao cutcategoryDao;
    @Autowired
    CutDao cutDao;


    //查找所有的工资管理信息
    @CrossOrigin
    @GetMapping(value = "incomes/getAllTotal")
    @ResponseBody
    public List getAllTotal() {

        List<Total> totalList = totalDao.getAllTotal();
        List list = new ArrayList<>();
        for(int i = 0; i < totalList.size(); i++){
            Map map = new HashMap();
            Total total = totalList.get(i);
            map.put("num", i+1);//序号
            map.put("id", total.getId());//ID
            map.put("time", createdateDao.getCreatedateByCdid(total.getCdid()).getName());//时间
            map.put("sum",total.getTotalsum());//工资发放总额
            list.add(map);
        }
        return list;
    }


    //添加工资管理信息
    @CrossOrigin
    @PostMapping("incomes/addTotal")
    @ResponseBody
    public Result addTotal(@RequestBody RequestTotal requestTotal){
        String message = String.format("添加成功！");
        String saltime = requestTotal.getSaltime();
        int udid = 0 ;//Udid
        int twid = 0 ;//Twid
        int txid = 0 ;//Txid
        int isid = 0 ;//Isid
        int cdid = 0 ;//Cdid
        int wdids = 0 ;//Wdids
        //获取时间cdid
        Createdate createdateByTime = createdateDao.getCreatedateByTime(saltime);
        cdid = createdateByTime.getId();
        //判断是否存在
        Total total = totalDao.getTotal(cdid);
        if(total!=null){
            return ResultFactory.buidResult(300,message, total);
        }
        //获取所有员工信息
        List<Employer> allEmployer = employerDao.getAllEmployer();
        for (int i = 0; i < allEmployer.size(); i++) {
            Employer employer = allEmployer.get(i);
            //获取单个员工工号
            int empId = employer.getId();
            /**
             * userdefault1员工基本信息表
             */
            double regularpay = 0;//基本工资
            double insurancebase = 0;//社保
            double allowanceids = 0;//补贴
            double cutids = 0;//扣款
            List<Userdefault1> userdefault1ByEid = userdefault1Dao.getUserdefault1ByEid(empId);
            if (userdefault1ByEid.isEmpty()) {
                userdefault1Dao.addUserdefault1(empId, 0.0, 0.0, 0, 0, null, null);
            } else {
                Integer lastId = userdefault1Dao.getLastId(empId);
                //获取该员工基本信息
                Userdefault1 userdefault1 = userdefault1Dao.getUserdefault1ByUdid(lastId);
                //基本工资
                regularpay = userdefault1.getRegularpay();
                //社保
                insurancebase = userdefault1.getInsurancebase();
                //Udid
                udid = userdefault1.getId();
                //补贴
                allowanceids = userdefault1.getAllowanceids();
                //扣款
                cutids = userdefault1.getCutids();
            }
            /**
             * insurancerate社保率表
             */
            int insurancerateId = 0;
            double insurancerate = 0;//税率
            double fixedfee = 0;//固定税率
            Insurancerate insurancerateByEid = insurancerateDao.getInsurancerateByEid(empId);
            if (null == insurancerateByEid) {
                insurancerateDao.addInsurancerate1(empId, 0, 0);
            } else {
                //税率
                insurancerate = insurancerateByEid.getInsurancerate();
                //固定税率
                fixedfee = insurancerateByEid.getFixedfee();
                //ID
                insurancerateId = insurancerateByEid.getId();
            }
            /**
             * insurance社保表
             */
            List<Insurance> insuranceByIrid = insuranceDao.getInsuranceByIrid(insurancerateId);
            if (insuranceByIrid.isEmpty()) {
                insuranceDao.addInsurance(insurancebase * insurancerate + fixedfee, insurancerateId);
                insuranceByIrid = insuranceDao.getInsuranceByIrid(insurancerateId);
            }
            isid = insuranceByIrid.get(insuranceByIrid.size()-1).getId();
            /**
             * workday表
             */
            int absence = 0;//请假
            double overtimed = 0;//节假日加班
            double overtimen = 0;//加班
            double late = 0;//迟到
            double leaveral = 0;//早退
            double absenteeism = 0;//旷工
            double work = 0;//加班奖金
            double work1 = 0;//请假扣款
            double awards = 0;//奖金
            Workday workdayByEidAndCdid = workdayDao.getWorkdayByEidAndCdid(empId, cdid);
            if (null != workdayByEidAndCdid) {
                wdids = workdayByEidAndCdid.getId();
                absence = workdayByEidAndCdid.getAbsence();//请假
                overtimed = workdayByEidAndCdid.getOvertimed();//节假日加班
                overtimen = workdayByEidAndCdid.getOvertimen();//普通加班
                late = workdayByEidAndCdid.getLate();//迟到
                leaveral = workdayByEidAndCdid.getLeaveral();//早退
                absenteeism = workdayByEidAndCdid.getAbsenteeism();//旷工
                //计算请假加班扣费
                if (overtimen >= absence) {
                    overtimen = overtimen - absence;
                    //加班
                    work = overtimen * regularpay * 1.5 / 8 / 21.75 + overtimed * 2 * regularpay / 21.75 / 8;
                    //请假扣款
                    work1=0;
                } else if (absence > overtimen && absence < (overtimen + overtimed)) {
                    overtimed = overtimed + overtimen - absence;
                    overtimen = 0;
                    absence = 0;
                    //加班
                    work = overtimen * regularpay * 1.5 / 8 / 21.75 + overtimed * 2 * regularpay / 21.75 / 8;
                    //请假扣款
                    work1=0;
                } else if (absence >= (overtimen + overtimed)) {
                    double a1 = absence - (overtimen + overtimed);
                    //加班
                    work =0;
                    //请假扣款
                    work1 = (a1 * regularpay / 21.75 / 8);
                }
            } else {
                return ResultFactory.buidResult(100,message, workdayByEidAndCdid);
            }
            //总价=基本工资+补贴-社保+加班-请假
            double zongJia1 = regularpay + allowanceids - (insurancebase * insurancerate + fixedfee) + work - work1;
            /**
             * awards表
             */
            double time = 0;//时间
            List<Awards> awardsByEid = awardsDao.getAwardsByEid(empId);
            if (awardsByEid != null) {
                //根据员工工号获取时间与奖金,计算奖金
                time = awardsByEid.get(0).getTime();//时间限制
                if (absenteeism == 0 && leaveral == 0) {
                    if (time > late) {
                        awards = awardsByEid.get(0).getMoney();//奖金总额
                    } else {
                        awards = 0;
                    }
                } else {
                    awards = 0;
                }
            }
            double zongJia2 = zongJia1 + awards;
            /**
             * aways表
             */
            double cutmoney = 0;
            Aways awaysByWorkdayId = awaysDao.getAwaysByWorkdayId(wdids);
            if (awaysByWorkdayId != null) {
                cutmoney = awaysByWorkdayId.getCutmoney();//旷工扣款
            } else {
                cutmoney = 0;
                Integer stayId = stayDao.getLastId();
                List<Awards> allAwards = awardsDao.getAllAwards();
                int awardsId = allAwards.get(i).getId();
                awaysDao.addAways(1,0,wdids,stayId,awardsId);
            }
            //总价=基本工资+奖金+补贴-社保+加班-请假-旷工扣款
            double zongJia = zongJia2 - cutmoney;
            /**
             * taxrate
             */
            Integer lastId = updatedateDao.getLastId();
            double taxx = 0;
            if (lastId == 0) {
                taxx = 0;
            } else {
                //根据最大时间编号查询税率信息
                List<Taxrate> taxrateByUpdid = taxrateDao.getTaxrateByUpdid(lastId);
                for (int j = 0; j < taxrateByUpdid.size(); j++) {
                    Taxrate taxrate = taxrateByUpdid.get(i);
                    double taxrate1 = taxrate.getTaxrate();//税率
                    double threshold = taxrate.getThreshold();//底薪
                    double min = taxrate.getMin();
                    double max = taxrate.getMax();
                    double tt = zongJia - threshold;
                    if (tt > min && tt <= max) {
                        taxx = taxx + (tt - min) * taxrate1;
                    }
                    if (tt > max) {
                        taxx = taxx + (max - min) * taxrate1;
                    }
                }
            }
            taxx = (double) Math.round(taxx * 100) / 100;
            taxDao.addTax(taxx, lastId);
            txid = taxDao.getTxid(taxx, lastId);
            double sumpayable1 = zongJia - taxx;
            /**
             * totalwage表,员工工资总价表
             */
            double initialsum = regularpay + allowanceids + awards + work - work1 - cutmoney;//zongjia
            double secondsum = regularpay + allowanceids + awards + work - work1 - cutmoney - (insurancebase * insurancerate + fixedfee);
            double sumpayable = sumpayable1 - cutids;
            initialsum =(double) Math.round(initialsum * 100) / 100;
            secondsum =(double) Math.round(secondsum * 100) / 100;
            sumpayable =(double) Math.round(sumpayable * 100) / 100;
            int k = totalwageDao.addTotalwage(initialsum, secondsum, sumpayable);
            if (k!= 0) {
                twid = totalwageDao.getTwid(initialsum, secondsum, sumpayable);
            }
            /**
             * userobject表
             */
            int UI = userobjectDao.addUserobject(udid, twid, txid, isid, cdid, wdids);
            Integer userobjectId = userobjectDao.getUserobjectId(udid, twid, txid, isid, cdid, wdids);
            userDao.addUser("基本工资",regularpay,1,userobjectId);//存入基本工资
            work = (double) Math.round(work * 100) / 100;
            userDao.addUser("加班",work,1,userobjectId);//存入加班
            work1 = (double) Math.round(work1 * 100) / 100;
            userDao.addUser("请假",work1,1,userobjectId);//存入请假
            //存入补贴
            /**
             * allowancecategory表、allowance表
             */
            double allowanceMoney =0;
            List<Allowancecategory> allowancecategoryByShown = allowancecategoryDao.getAllowancecategoryByShown(1);
            if (!allowancecategoryByShown.isEmpty()) {
                for (int ak = 0; ak < allowancecategoryByShown.size(); ak++) {
                    Allowancecategory allowancecategory = allowancecategoryByShown.get(ak);
                    int allowancecategoryId = allowancecategory.getId();//编号
                    String allowancecategoryName = allowancecategory.getName();//名称
                    List<Allowance> allowanceByEidAndAlcid = allowanceDao.getAllowanceByEidAndAlcid(allowancecategoryId, udid);
                    if (!allowanceByEidAndAlcid.isEmpty()) {
                        Allowance allowance = allowanceByEidAndAlcid.get(0);
                        allowanceMoney = allowance.getAllowance();//补贴金额
                    } else {
                        allowanceMoney = 0;
                    }
                    userDao.addUser(allowancecategoryName,allowanceMoney,1,userobjectId);//存入补贴
                }
            }
            List<Allowancecategory> allowancecategoryByShown2 = allowancecategoryDao.getAllowancecategoryByShown(0);
            if (!allowancecategoryByShown2.isEmpty()) {
                for (int ak = 0; ak < allowancecategoryByShown2.size(); ak++) {
                    Allowancecategory allowancecategory = allowancecategoryByShown2.get(ak);
                    int allowancecategoryId = allowancecategory.getId();//编号
                    String allowancecategoryName = allowancecategory.getName();//名称
                    List<Allowance> allowanceByEidAndAlcid = allowanceDao.getAllowanceByEidAndAlcid(allowancecategoryId, udid);
                    if (!allowanceByEidAndAlcid.isEmpty()) {
                        Allowance allowance = allowanceByEidAndAlcid.get(0);
                        allowanceMoney = allowance.getAllowance();//补贴金额
                    } else {
                        allowanceMoney = 0;
                    }
                    userDao.addUser(allowancecategoryName,allowanceMoney,0,userobjectId);//存入补贴
                }
            }
            //存入奖金
            /**
             * awardcategory表、awards表
             */
            double awardMoney =0;
            List<Awards> awardsByEid1 = awardsDao.getAwardsByEid(empId);
            int awardsid = awardsByEid1.get(0).getId();
            List<Awardcategory> awardcategoryByShown = awardcategoryDao.getAwardcategoryByShown(1);
            if (!awardcategoryByShown.isEmpty()) {
                for (int ak = 0; ak < awardcategoryByShown.size(); ak++) {
                    Awardcategory awardcategory = awardcategoryByShown.get(ak);
                    int awardcategoryId = awardcategory.getId();//编号
                    String awardcategoryName = awardcategory.getName();//名称
                    Award awardByEidAndAwardsid = awardDao.getAwardByAcidAndAwardsid(awardsid,awardcategoryId);
                    if (awardByEidAndAwardsid!=null) {
                        awardMoney = awardByEidAndAwardsid.getAward();//奖金金额
                    } else {
                        awardMoney = 0;
                    }
                    userDao.addUser(awardcategoryName,awardMoney,1,userobjectId);//存入奖金
                }
            }
            List<Awardcategory> awardcategoryByShown2 = awardcategoryDao.getAwardcategoryByShown(0);
            if (!awardcategoryByShown2.isEmpty()) {
                for (int ak = 0; ak < awardcategoryByShown2.size(); ak++) {
                    Awardcategory awardcategory = awardcategoryByShown2.get(ak);
                    int awardcategoryId = awardcategory.getId();//编号
                    String awardcategoryName = awardcategory.getName();//名称
                    Award awardByEidAndAwardsid = awardDao.getAwardByAcidAndAwardsid(awardsid,awardcategoryId);
                    if (awardByEidAndAwardsid!=null) {
                        awardMoney = awardByEidAndAwardsid.getAward();//奖金金额
                    } else {
                        awardMoney = 0;
                    }
                    userDao.addUser(awardcategoryName,awardMoney,0,userobjectId);//存入奖金
                }
            }
            //存入代扣社保,根据员工编号查询代扣社保,获取insurancerate的id
            /**
             * insurancerate表、insurance表
             */
            double insurancerateMoney =0;
            Insurancerate insurancerateByEid1 = insurancerateDao.getInsurancerateByEid(empId);
            int irid =insurancerateByEid1.getId();
            List<Insurance>  insuranceByIrid1 = insuranceDao.getInsuranceByIrid(irid);
            Insurance insuranceq = insuranceByIrid1.get(insuranceByIrid1.size() - 1);
            insurancerateMoney = insuranceq.getInsurance();
            userDao.addUser("代扣社保",insurancerateMoney,1,userobjectId); //存入代扣社保

            //存入扣款
            /**
             * cutcategory表、cut表
             */
            double cutMoney =0;
            List<Cutcategory> cutcategoryByShown = cutcategoryDao.getCutcategoryByShown(1);
            if (!cutcategoryByShown.isEmpty()) {
                for (int ck = 0; ck < cutcategoryByShown.size(); ck++) {
                    Cutcategory cutcategory = cutcategoryByShown.get(ck);
                    int cutcategoryId = cutcategory.getId();//编号
                    String cutcategoryName = cutcategory.getName();//名称
                    List<Cut> cutByEidAndCcid = cutDao.getCutByEidAndCcid(cutcategoryId, udid);
                    if (!cutByEidAndCcid.isEmpty()) {
                        Cut cut = cutByEidAndCcid.get(0);
                        cutMoney = cut.getCut();//扣款金额
                    } else {
                        cutMoney = 0;
                    }
                    userDao.addUser(cutcategoryName,cutMoney,1,userobjectId);//存入扣款
                }
            }
            List<Cutcategory> cutcategoryByShown2 = cutcategoryDao.getCutcategoryByShown(0);
            if (!cutcategoryByShown2.isEmpty()) {
                for (int ck = 0; ck < cutcategoryByShown2.size(); ck++) {
                    Cutcategory cutcategory = cutcategoryByShown2.get(ck);
                    int cutcategoryId = cutcategory.getId();//编号
                    String cutcategoryName = cutcategory.getName();//名称
                    List<Cut> cutByEidAndCcid = cutDao.getCutByEidAndCcid(cutcategoryId, udid);
                    if (!cutByEidAndCcid.isEmpty()) {
                        Cut cut = cutByEidAndCcid.get(0);
                        cutMoney = cut.getCut();//扣款金额
                    } else {
                        cutMoney = 0;
                    }
                    userDao.addUser(cutcategoryName,cutMoney,0,userobjectId);//存入扣款
                }
            }
            //存入旷工扣款
            userDao.addUser("旷工扣款",cutmoney,1,userobjectId);
            //存入代扣个税
            userDao.addUser("代扣个税",taxx,1,userobjectId);
            //存入工资合计
            userDao.addUser("工资合计",initialsum,1,userobjectId);
            //存入应付金额
            userDao.addUser("工资合计",secondsum,1,userobjectId);
            //存入实发金额
            userDao.addUser("实发金额",sumpayable,1,userobjectId);
        }
        //根据时间获取总价
        List<Userobject> userobjectByCdid = userobjectDao.getUserobjectByCdid(cdid);
        double sum = 0;
        double sumpayable =0;
        for (int u = 0; u < userobjectByCdid.size(); u++) {
            Userobject userobject = userobjectByCdid.get(u);
            int twid2 = userobject.getTwid();
            Totalwage totalwageByid = totalwageDao.getTotalwageByid(twid2);
            sumpayable = totalwageByid.getSumpayable();
            sum = sum + sumpayable;
        }
        sum = (double) Math.round(sum * 100) / 100;
        int i1 =totalDao.addTotal(sum,cdid);

        /**
         * 将时间status改为0
         */
        int i2 = createdateDao.updateCreatedate(0,saltime);

        if (i1 == 0||i2==0) {
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        } else {
            return ResultFactory.buildSuccessResult(message, i1);
        }
    }


    //按ID删除工资管理信息
    @CrossOrigin
    @DeleteMapping("incomes/deleteTotal")
    @ResponseBody
    public Result deleteTotal(@RequestBody Total requestTotal){
        String message = String.format("删除成功！");
        int id = requestTotal.getId();//ID
        int cdid = totalDao.getTotalById(id).getCdid();//cdid
        int a =0;int b =0;int c =0;int d =0;
        List<Userobject> userobjectByCdid = userobjectDao.getUserobjectByCdid(cdid);
        for(int u = 0; u < userobjectByCdid.size(); u++){
            Userobject userobject = userobjectByCdid.get(u);
            int userobjectId = userobject.getId();
            int twid = userobject.getTwid();
            int txid = userobject.getTxid();
            //删除user表里相关数据
            a = userDao.deleteUser(userobjectId);
            //删除tax表里相关数据
            b = taxDao.deleteTax(txid);
            //删除totalwage表里相关数据
            c = totalwageDao.deleteTotalwage(twid);
        }
        //删除userobject表里相关数据
        d = userobjectDao.deleteUserobject(cdid);
        //删除total表里相关数据
        int i = totalDao.deleteTotal(id);
        /**
         * 将时间status改回1
         */
        createdateDao.updateCreatedate(1,createdateDao.getCreatedateByCdid(cdid).getName());
        if(i==0||a==0||b==0||c==0||d==0){
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }

}
