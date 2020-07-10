package com.muicc.incomes.controller;


import com.muicc.incomes.dao.*;
import com.muicc.incomes.model.RequestTotal;
import com.muicc.incomes.pojo.*;
import com.muicc.incomes.result.Result;
import com.muicc.incomes.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
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
    AttendanceDao attendanceDao;
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

        //获取时间cdid
        Createdate createdateByTime = createdateDao.getCreatedateByTime(saltime);
        int cdid = createdateByTime.getId();
        //判断某月的工资记录是否存在
        Total total = totalDao.getTotal(cdid);
        if(total!=null){
            message = String.format("该月记录已存在！");
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
            double insurancebase = 0;//社保基数

            Userdefault1 userdefault1ByEid = userdefault1Dao.getUserdefault1ByEid(empId);
            if (null == userdefault1ByEid) {
                message = String.format("该员工未添加基本信息！");
                return ResultFactory.buidResult(400, message, userdefault1ByEid);
            } else {
                //获取该员工基本信息
                //基本工资
                regularpay = userdefault1ByEid.getRegularpay();
                //社保基数
                insurancebase = userdefault1ByEid.getInsurancebase();
            }
            /**
             * insurancerate社保率表
             */
            double insurancerate = 0;//社保率
            double fixedfee = 0;//固定社保费
            double cutmoney =0;//旷工扣款
            Insurancerate insurancerateByEid = insurancerateDao.getInsurancerateByEid(empId);
            if (null == insurancerateByEid) {
                message = String.format("该员工未添加社保信息！");
                return ResultFactory.buidResult(500, message, userdefault1ByEid);
            } else {
                //获取该员工社保信息
                //税率
                insurancerate = insurancerateByEid.getInsurancerate();
                //固定税率
                fixedfee = insurancerateByEid.getFixedfee();
            }
            /**
             * workday请假加班表
             */
            double paidLeave = 0;//带薪请假
            double absence = 0;//无薪请假
            double overtimed = 0;//节假日加班
            double overtimen = 0;//普通加班
            double late = 0;//迟到
            int lateTimes = 0;//迟到次数
            double leaveral = 0;//早退
            int leaveralTimes = 0;//早退次数
            double absenteeism = 0;//旷工
            double work = 0;//加班奖金
            double work1 = 0;//请假扣款
            double awards = 0;//奖金
            double zongJia3 = 0;
            double attenceAwards = 0;//全勤奖
            Workday workdayByEidAndCdid = workdayDao.getWorkdayByEidAndCdid(empId, cdid);
            if (null == workdayByEidAndCdid) {
                message = String.format("该员工未找到请假加班信息！");
                return ResultFactory.buidResult(100, message, userdefault1ByEid);
            } else {
                paidLeave = workdayByEidAndCdid.getPaidLeave();//带薪请假
                absence = workdayByEidAndCdid.getAbsence();//无薪请假
                overtimed = workdayByEidAndCdid.getOvertimed();//节假日加班
                overtimen = workdayByEidAndCdid.getOvertimen();//普通加班
                late = workdayByEidAndCdid.getLate();//迟到
                lateTimes = workdayByEidAndCdid.getLateTimes();//迟到次数
                leaveral = workdayByEidAndCdid.getLeaveral();//早退
                leaveralTimes = workdayByEidAndCdid.getLeaveralTimes();//早退次数
                absenteeism = workdayByEidAndCdid.getAbsenteeism();//旷工
                //计算请假加班扣费
                if (overtimen >= absence) {
                    overtimen = overtimen - absence;
                    //加班奖金
                    work = overtimen * regularpay * 1.5 / 8 / 21.75 + overtimed * 2 * regularpay / 21.75 / 8;
                    //请假扣款
                    work1 = 0;
                } else if (absence > overtimen && absence < (overtimen + overtimed)) {
                    overtimed = overtimed + overtimen - absence;
                    //加班奖金
                    work = overtimed * 2 * regularpay / 21.75 / 8;
                    //请假扣款
                    work1 = 0;
                } else if (absence >= (overtimen + overtimed)) {
                    double absence1 = absence - (overtimen + overtimed);
                    //加班奖金
                    work = 0;
                    //请假扣款
                    work1 = (absence1 * regularpay / 21.75 / 8);
                }
                //总价=基本工资-社保+加班-请假
                double zongJia1 = regularpay - (insurancebase * insurancerate + fixedfee) + work - work1;

                /**
                 * attendance考勤表
                 */
                List<Attendance> allAttendance = attendanceDao.getAllAttendance();
                Attendance attendance = allAttendance.get(0);
                double atime = attendance.getTime();//迟到早退时间限制
                int atimes = attendance.getTimes();//迟到早退次数限制
                double rate = attendance.getRate();//全勤奖率
                double fixedAward = attendance.getFixedAward();//全勤奖固定金额

                if (paidLeave + absence + absenteeism == 0 && late + leaveral <= atime && lateTimes + leaveralTimes <= atimes) {
                    attenceAwards = regularpay * rate + fixedAward;
                } else {
                    attenceAwards = 0;
                }
                //总价=基本工资-社保+加班-请假+全勤奖
                double zongJia2 = zongJia1 + attenceAwards;
                /**
                 * aways旷工扣款表
                 */
                cutmoney = 0;
                Aways AwaysbyEidAndCdid = awaysDao.getByEidAndCdid(empId, cdid);
                if (AwaysbyEidAndCdid != null) {
                    cutmoney = awaysDao.getAwaysByEidAndCdid(empId, cdid);
                }
                //总价=基本工资-社保+加班-请假+全勤奖-旷工扣款
                zongJia3 = zongJia2 - cutmoney;
            }


            /**
             * award奖金表
             */
            double awardByEidAndCdid = 0;
            Award AwardbyEidAndCdid = awardDao.getByEidAndCdid(empId, cdid);
            if (AwardbyEidAndCdid != null) {
                awardByEidAndCdid = awardDao.getAwardByEidAndCdid(empId, cdid);
            }
            //总价=基本工资-社保+加班-请假+全勤奖-旷工扣款+奖金
            double zongJia4 = zongJia3 + awardByEidAndCdid;
            /**
             * allowance补贴表
             */
            double allowanceByEidAndCdid = 0;
            Allowance AllowancebyEidAndCdid = allowanceDao.getByEidAndCdid(empId, cdid);
            if (AllowancebyEidAndCdid != null) {
                allowanceByEidAndCdid = allowanceDao.getAllowanceByEidAndCdid(empId, cdid);
            }
            //总价=基本工资-社保+加班-请假+全勤奖-旷工扣款+奖金+补贴
            double zongJia5 = zongJia4 + allowanceByEidAndCdid;

            /**
             * cut扣款表
             */
            double cutByEidAndCdid = 0;
            Cut CutbyEidAndCdid = cutDao.getByEidAndCdid(empId, cdid);
            if (CutbyEidAndCdid != null) {
                cutByEidAndCdid = cutDao.getCutByEidAndCdid(empId, cdid);
            }
            //总价=基本工资-社保+加班-请假+全勤奖-旷工扣款+奖金+补贴-其他扣款
            double zongJia = zongJia5 - cutByEidAndCdid;
            /**
             * taxrate扣税表
             */
            Integer lastId = updatedateDao.getLastId();
            double taxx = 0;
            if (lastId == 0) {
                taxx = 0;
            } else {
                //根据最大时间编号查询税率信息
                List<Taxrate> taxrateByUpdid = taxrateDao.getTaxrateByUpdid(lastId);
                for (int j = 0; j < taxrateByUpdid.size(); j++) {
                    Taxrate taxrate = taxrateByUpdid.get(j);
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
            //总价=基本工资-社保+加班-请假+全勤奖-旷工扣款+奖金+补贴-其他扣款-个税
            double sumpayable = zongJia - taxx;
            sumpayable = Double.valueOf(String.format("%.2f", sumpayable ));
            /**
             * total月工资总和表
             */
            //添加total工资总和
            Total total1 = totalDao.getTotal(cdid);
            if(null==total1){
                int i1 = totalDao.addTotal(sumpayable, cdid);
                if(i1==0){
                    message = String.format("添加失败！");
                    return ResultFactory.buildFailResult(message);
                }
            }else{
                sumpayable=sumpayable+total1.getTotalsum();
                int i2 = totalDao.updateTotal(sumpayable,cdid);
                if(i2==0){
                    message = String.format("添加失败！");
                    return ResultFactory.buildFailResult(message);
                }
            }
            /**
             * userobject月工资明细表
             */
            //添加userobject数据

            double insurance =insurancebase * insurancerate + fixedfee;
            double a  = Double.valueOf(String.format("%.2f",zongJia5 + insurance));
            double b  = Double.valueOf(String.format("%.2f", zongJia5 ));
            double c  = Double.valueOf(String.format("%.2f", zongJia-taxx ));
            int i1 = userobjectDao.addUserobject(cdid, empId, regularpay, overtimed + overtimen, paidLeave + absence, allowanceByEidAndCdid, awardByEidAndCdid + attenceAwards
                    , absenteeism, a, insurance, b, cutByEidAndCdid+cutmoney, taxx,c , null, null);
            if(i1==0){
                message = String.format("添加月工资明细失败！");
                return ResultFactory.buildFailResult(message);
            }
            /**
             * user月员工工资明细表
             */
            int userobjectId = userobjectDao.getIdByEidAndCdid(empId, cdid);
            int aa =userDao.addUser("基本工资",regularpay,1,userobjectId);
            int bb =userDao.addUser("普通加班",overtimen,1,userobjectId);
            int cc =userDao.addUser("假期加班",overtimed,1,userobjectId);
            int dd =userDao.addUser("无薪请假",absence,1,userobjectId);
            int ee =userDao.addUser("带薪请假",paidLeave,1,userobjectId);
            int ff =userDao.addUser("补贴",allowanceByEidAndCdid,1,userobjectId);
            int gg =userDao.addUser("全勤奖",attenceAwards,1,userobjectId);
            int hh =userDao.addUser("其他奖金",awardByEidAndCdid,1,userobjectId);
            int ii =userDao.addUser("社保",insurance,1,userobjectId);
            int jj =userDao.addUser("旷工扣款",cutmoney,1,userobjectId);
            int kk =userDao.addUser("其他扣款",cutByEidAndCdid,1,userobjectId);
            int ll =userDao.addUser("代扣个税",taxx,1,userobjectId);
            int mm =userDao.addUser("工资合计",sumpayable,1,userobjectId);
            if (aa == 0||bb==0||cc==0||dd==0||ee==0||ff==0||gg==0||hh==0||ii==0||jj==0||kk==0||ll==0||mm==0) {
                message = String.format("月员工工资明细表!");
                return ResultFactory.buildFailResult(message);
            }
        }

        /**
         * 将请假加班管理status改为0
         */
        int i = workdayDao.updateWorkdayStatus(0, cdid);


        /**
         * 将旷工扣款status改为0
         */
        int j = awaysDao.updateAwaysStatus(0, cdid);

        if (i == 0||j==0) {
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        } else {
            return ResultFactory.buildSuccessResult(message, i);
        }
    }


    //按ID删除工资管理信息
    @CrossOrigin
    @PostMapping("incomes/deleteTotal")
    @ResponseBody
    public Result deleteTotal(@RequestBody Total requestTotal){
        String message = String.format("删除成功！");
        int id = requestTotal.getId();//ID
        int cdid = totalDao.getTotalById(id).getCdid();
        /**
         * 将total里数据删除
         */
        int i = totalDao.deleteTotal(cdid);
        /**
         * 将user里数据删除
         */
        List<Userobject> userobjectByCdid = userobjectDao.getUserobjectByCdid(cdid);
        for(int a=0; a<userobjectByCdid.size();a++){
            Userobject userobject = userobjectByCdid.get(a);
            int idByEidAndCdid = userobjectDao.getIdByEidAndCdid(userobject.getEid(), cdid);
            int b = userDao.deleteUser(idByEidAndCdid);
            if(b==0){
                message = String.format("删除失败！");
                return ResultFactory.buildFailResult(message);
            }
        }
        /**
         * 将userobject里数据删除
         */
        int j = userobjectDao.deleteUserobject(cdid);
        /**
         * 将旷工扣款status改回1
         */
        int k = awaysDao.updateAwaysStatus(1, cdid);
        /**
         * 将请假加班管理status改回1
         */
        int l = workdayDao.updateWorkdayStatus(1, cdid);
        if(i==0||j==0||k==0||l==0){
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }

}
