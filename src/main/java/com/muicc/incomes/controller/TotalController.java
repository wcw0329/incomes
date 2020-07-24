package com.muicc.incomes.controller;

import com.muicc.incomes.dao.*;
import com.muicc.incomes.model.RequestTotal;
import com.muicc.incomes.pojo.*;
import com.muicc.incomes.result.Result;
import com.muicc.incomes.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
    AwardDao awardDao;
    @Autowired
    StayDao stayDao;
    @Autowired
    AwardcategoryDao awardcategoryDao;
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
    @Transactional
    public Result addTotal(@RequestBody RequestTotal requestTotal)  {
        String message = String.format("添加成功！");
        String saltime = requestTotal.getSaltime();

        //获取时间cdid
        Createdate createdateByTime = createdateDao.getCreatedateByTime(saltime);
        int cdid = createdateByTime.getId();
        //判断某月的工资记录是否存在
        Total total = totalDao.getTotal(cdid);
        if(total!=null){
            message = String.format("新建失败！该月记录已存在！");
            return ResultFactory.buildFailResult(message);
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
                message = String.format("新建失败！员工未添加基本信息！");
                return ResultFactory.buildFailResult(message);
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
                message = String.format("新建失败！员工未添加社保信息！");
                return ResultFactory.buildFailResult(message);
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
            double zongJia3 = 0;
            double attenceAwards = 0;//全勤奖
            Workday workdayByEidAndCdid = workdayDao.getWorkdayByEidAndCdid(empId, cdid);
            if (null == workdayByEidAndCdid) {
                message = String.format("新建失败！未找到请假加班信息！");
                return ResultFactory.buildFailResult(message);
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
                //考勤奖存在
                if(allAttendance.size()>0){
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
            List<Award>  AwardbyEidAndCdid = awardDao.getByEidAndCdid(empId, cdid);
            if (AwardbyEidAndCdid.size()>0) {
                awardByEidAndCdid = awardDao.getAwardByEidAndCdid(empId, cdid);
            }
            //总价=基本工资-社保+加班-请假+全勤奖-旷工扣款+奖金
            double zongJia4 = zongJia3 + awardByEidAndCdid;
            /**
             * allowance补贴表
             */
            double allowanceByEidAndCdid = 0;
            List<Allowance>  AllowancebyEidAndCdid = allowanceDao.getByEidAndCdid(empId, cdid);
            if (AllowancebyEidAndCdid.size()>0) {
                allowanceByEidAndCdid = allowanceDao.getAllowanceByEidAndCdid(empId, cdid);
            }
            //总价=基本工资-社保+加班-请假+全勤奖-旷工扣款+奖金+补贴
            double zongJia5 = zongJia4 + allowanceByEidAndCdid;

            /**
             * cut扣款表
             */
            double cutByEidAndCdid = 0;
            List<Cut> CutbyEidAndCdid = cutDao.getByEidAndCdid(empId, cdid);
            if (CutbyEidAndCdid.size()>0) {
                cutByEidAndCdid = cutDao.getCutByEidAndCdid(empId, cdid);
            }
            //总价=基本工资-社保+加班-请假+全勤奖-旷工扣款+奖金+补贴-其他扣款
            double zongJia = zongJia5 - cutByEidAndCdid;
            /**
             * taxrate扣税表
             */
            int m =Integer.parseInt(saltime.substring(5,7));
            int cdid1 = cdid-(m-1);
            int cdid2 = cdid -1;
            Integer lastId = updatedateDao.getLastId();
            double taxx = 0;
            if (lastId == 0) {
                taxx = 0;
            } else {
                //根据最大时间编号查询税率信息
                List<Taxrate> taxrateByUpdid = taxrateDao.getTaxrateByUpdid(lastId);
                for (int j = 0; j < taxrateByUpdid.size(); j++) {
                    Taxrate taxrate = taxrateByUpdid.get(j);
                    double taxrate1 = taxrate.getTaxrate();//对应税率
                    double threshold = taxrate.getThreshold();//免征税额
                    double min = taxrate.getMin();
                    double max = taxrate.getMax();
                    if(m==1){
                        double tt = zongJia - threshold;
                        if (tt > min && tt <= max) {
                            taxx = taxx + (tt - min) * taxrate1;
                        }
                        if (tt > max) {
                            taxx = taxx + (max - min) * taxrate1;
                        }
                    }else{
                        int sumByCdid = userobjectDao.getSumpayByCdid(empId, cdid1, cdid2);//本年度累计应交税所得额
                        double tt = sumByCdid + zongJia -threshold*m;
                        if (tt > min && tt <= max) {
                            taxx = taxx + (tt - min) * taxrate1;
                        }
                        if (tt > max) {
                            taxx = taxx + (max - min) * taxrate1;
                        }
                    }
                }
            }
            int taxByCdid = taxDao.getTaxByCdid(empId, cdid1, cdid2);//本年度累计已扣税额
            taxx = taxx -taxByCdid;
            if(taxx<0){
                taxx=0;//上月多交个税，本月不再扣缴
            }
            taxx = (double) Math.round(taxx * 100) / 100;
            //添加扣税信息到tax表
            int t = taxDao.addTax(taxx,empId,cdid);
            if(t==0){
                message = String.format("添加失败！错误代码441");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
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
                    message = String.format("添加失败！错误代码442");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResultFactory.buildFailResult(message);
                }
            }else{
                sumpayable=sumpayable+total1.getTotalsum();
                int i2 = totalDao.updateTotal(sumpayable,cdid);
                if(i2==0){
                    message = String.format("添加失败！错误代码443");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResultFactory.buildFailResult(message);
                }
            }
            /**
             * userobject月工资明细表
             */
            //添加userobject数据

            double insurance =insurancebase * insurancerate + fixedfee;
            double a  = Double.valueOf(String.format("%.2f",zongJia5 + insurance));
            double b  = Double.valueOf(String.format("%.2f", zongJia ));
            double c  = Double.valueOf(String.format("%.2f", zongJia-taxx ));
            double w  = Double.valueOf(String.format("%.2f", work ));
            double h  = Double.valueOf(String.format("%.2f", work1 ));
            int i1 = userobjectDao.addUserobject(cdid, empId, regularpay, attenceAwards,w, h, allowanceByEidAndCdid, awardByEidAndCdid
                    , cutmoney, a, insurance, b, cutByEidAndCdid, taxx,c , null, null);
            if(i1==0){
                message = String.format("添加失败！错误代码444");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
            /**
             * user月员工工资明细表
             */
            int userobjectId = userobjectDao.getIdByEidAndCdid(empId, cdid);
            int aa =userDao.addUser("基本工资",regularpay,1,userobjectId);
            int bb =userDao.addUser("普通加班",overtimen,1,userobjectId);
            int cc =userDao.addUser("假期加班",overtimed,1,userobjectId);
            int nn =userDao.addUser("加班补贴",w,1,userobjectId);
            int dd =userDao.addUser("无薪请假",absence,1,userobjectId);
            int ee =userDao.addUser("带薪请假",paidLeave,1,userobjectId);
            int oo =userDao.addUser("请假扣款",h,1,userobjectId);
            int ff =userDao.addUser("补贴",allowanceByEidAndCdid,1,userobjectId);
            //显示在工资表的补贴
            List<Allowancecategory> allowancecategoryByShown = allowancecategoryDao.getAllowancecategoryByShown(1);
            if(allowancecategoryByShown.size()!=0) {
                for (int aaa = 0; aaa < allowancecategoryByShown.size(); aaa++) {
                    Allowancecategory allowancecategory = allowancecategoryByShown.get(aaa);
                    Allowance allowanceByEidAndAlcidAndCdid = allowanceDao.getAllowanceByEidAndAlcidAndCdid(empId, allowancecategory.getId(), cdid);
                    if(allowanceByEidAndAlcidAndCdid!=null){
                        int aaaa =userDao.addUser(allowancecategory.getName(),allowanceByEidAndAlcidAndCdid.getAllowance(),1,userobjectId);
                        if(aaaa==0){
                            message = String.format("添加失败！错误代码451");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return ResultFactory.buildFailResult(message);
                        }
                    }
                }
            }
            int gg =userDao.addUser("全勤奖",attenceAwards,1,userobjectId);
            int hh =userDao.addUser("其他奖金",awardByEidAndCdid,1,userobjectId);
            //显示在工资表的奖金
            List<Awardcategory> awardcategoryByShown = awardcategoryDao.getAwardcategoryByShown(1);
            if(awardcategoryByShown.size()!=0) {
                for (int bbb = 0; bbb < awardcategoryByShown.size(); bbb++) {
                    Awardcategory awardcategory = awardcategoryByShown.get(bbb);
                    Award awardByEidAndAcidAndCdid = awardDao.getAwardByEidAndAcidAndCdid(empId, awardcategory.getId(), cdid);
                    if(awardByEidAndAcidAndCdid!=null){
                        int bbbb =userDao.addUser(awardcategory.getName(),awardByEidAndAcidAndCdid.getAward(),1,userobjectId);
                        if(bbbb==0){
                            message = String.format("添加失败！错误代码452");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return ResultFactory.buildFailResult(message);
                        }
                    }
                }
            }
            int ii =userDao.addUser("社保",insurance,1,userobjectId);
            int jj =userDao.addUser("旷工扣款",cutmoney,1,userobjectId);
            int kk =userDao.addUser("其他扣款",cutByEidAndCdid,1,userobjectId);
            //显示在工资表的扣款
            List<Cutcategory> cutcategoryByShown = cutcategoryDao.getCutcategoryByShown(1);
            if(cutcategoryByShown.size()!=0) {
                for (int ccc = 0; ccc < cutcategoryByShown.size(); ccc++) {
                    Cutcategory cutcategory = cutcategoryByShown.get(ccc);
                    Cut cutByEidAndAcidAndCdid = cutDao.getCutByEidAndCcidAndCdid(empId, cutcategory.getId(), cdid);
                    if(cutByEidAndAcidAndCdid!=null){
                        int cccc =userDao.addUser(cutcategory.getName(),cutByEidAndAcidAndCdid.getCut(),1,userobjectId);
                        if(cccc==0){
                            message = String.format("添加失败！错误代码453");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return ResultFactory.buildFailResult(message);
                        }
                    }
                }
            }
            int ll =userDao.addUser("代扣个税",taxx,1,userobjectId);
            int mm =userDao.addUser("工资合计",c,1,userobjectId);
            if (aa == 0||bb==0||cc==0||dd==0||ee==0||ff==0||gg==0||hh==0||ii==0||jj==0||kk==0||ll==0||mm==0) {
                message = String.format("添加失败！错误代码445");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
        }

        /**
         * 将请假加班管理status改为0
         */
        int i = workdayDao.updateWorkdayStatus(0, cdid);
        if (i==0) {
            message = String.format("添加失败！错误代码446");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }

        /**
         * 将旷工扣款status改为0
         */
        int j =1;
        List<Aways> awaysByCdid = awaysDao.getAwaysByCdid(cdid);
        if(awaysByCdid.size()!=0){
            j = awaysDao.updateAwaysStatus(0, cdid);
        }
        if (j==0) {
            message = String.format("添加失败！错误代码447");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        /**
         * 将奖金表status改为0
         */
        int x = 1;
        List<Award> awardByCdid = awardDao.getAwardByCdid(cdid);
        if(awardByCdid.size()!=0){
            x =  awardDao.updateAwardStatus(0, cdid);
        }
        if (x == 0) {
            message = String.format("删除失败！错误代码448");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        /**
         * 将扣款表status改为0
         */
        int y =1;
        List<Cut> cutByCdid = cutDao.getCutByCdid(cdid);
        if(cutByCdid.size()!=0){
            y  = cutDao.updateCutStatus(0, cdid);
        }
        if (y == 0) {
            message = String.format("添加失败！错误代码449");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        /**
         * 将补贴表status改为0
         */
        int z =1;
        List<Allowance> allowanceByCdid = allowanceDao.getAllowanceByCdid(cdid);
        if(allowanceByCdid.size()!=0){
            z = allowanceDao.updateAllowanceStatus(0, cdid);
        }
        if (z == 0) {
            message = String.format("添加失败！错误代码450");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
            return ResultFactory.buildSuccessResult(message, null);
    }


    //按ID删除工资管理信息
    @CrossOrigin
    @PostMapping("incomes/deleteTotal")
    @ResponseBody
    @Transactional
    public Result deleteTotal(@RequestBody Total requestTotal){
            String message = String.format("删除成功！");
            int id = requestTotal.getId();//ID
            int cdid = totalDao.getTotalById(id).getCdid();
            /**
             * 将total里数据删除
             */
            int i = totalDao.deleteTotal(cdid);
            if (i == 0) {
                message = String.format("删除失败！错误代码441");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
            /**
             * 将user里数据删除
             */
            List<Userobject> userobjectByCdid = userobjectDao.getUserobjectByCdid(cdid);
            for (int a = 0; a < userobjectByCdid.size(); a++) {
                Userobject userobject = userobjectByCdid.get(a);
                int idByEidAndCdid = userobjectDao.getIdByEidAndCdid(userobject.getEid(), cdid);
                int b = userDao.deleteUser(idByEidAndCdid);
                if (b == 0) {
                    message = String.format("删除失败！错误代码440");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return ResultFactory.buildFailResult(message);
                }
            }
            /**
             * 将userobject里数据删除
             */
            int j = userobjectDao.deleteUserobject(cdid);
            if (j == 0) {
                message = String.format("删除失败！错误代码442");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
            /**
             * 将tax表里的数据删除
             */
            int m = taxDao.deleteTax(cdid);
            if (m == 0) {
                message = String.format("删除失败！错误代码445");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
            /**
             * 将请假加班管理status改回1
             */
            int l = workdayDao.updateWorkdayStatus(1, cdid);
            if (l == 0) {
                message = String.format("删除失败！错误代码444");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
            /**
             * 将旷工扣款status改回1
             */
            int k =1;
            List<Aways> awaysByCdid = awaysDao.getAwaysByCdid(cdid);
            if(awaysByCdid.size()!=0){
                k = awaysDao.updateAwaysStatus(1, cdid);
            }
            if (k == 0) {
                message = String.format("删除失败！错误代码443");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
            /**
             * 将补贴表status改回1
             */
            int n =1;
            List<Allowance> allowanceByCdid = allowanceDao.getAllowanceByCdid(cdid);
            if(allowanceByCdid.size()!=0){
                n = allowanceDao.updateAllowanceStatus(1, cdid);
            }
            if (n == 0) {
                message = String.format("删除失败！错误代码446");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
            /**
             * 将奖金表status改回1
             */
            int o = 1;
            List<Award> awardByCdid = awardDao.getAwardByCdid(cdid);
            if(awardByCdid.size()!=0){
                n =  awardDao.updateAwardStatus(1, cdid);
            }
            if (o == 0) {
                message = String.format("删除失败！错误代码447");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
            /**
             * 将扣款表status改回1
             */
            int p =1;
            List<Cut> cutByCdid = cutDao.getCutByCdid(cdid);
            if(cutByCdid.size()!=0){
                p  = cutDao.updateCutStatus(1, cdid);
            }
            if (p == 0) {
                message = String.format("删除失败！错误代码448");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }

            return ResultFactory.buildSuccessResult(message, null);
    }
}
