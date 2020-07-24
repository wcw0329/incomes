package com.muicc.incomes.controller;

import com.muicc.incomes.dao.*;
import com.muicc.incomes.model.RequestTax;
import com.muicc.incomes.model.RequestTaxrate;
import com.muicc.incomes.pojo.Taxrate;
import com.muicc.incomes.pojo.Updatedate;
import com.muicc.incomes.result.Result;
import com.muicc.incomes.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TaxrateController {

    @Autowired
    TaxrateDao taxrateDao;

    @Autowired
    TaxDao taxDao;

    @Autowired
    UpdatedateDao updatedateDao;

    @Autowired
    UserobjectDao userobjectDao;

    @Autowired
    EmployerDao employerDao;

    @Autowired
    CreatedateDao createdateDao;

    //查找所有的税率信息
    @CrossOrigin
    @GetMapping(value = "incomes/getAllTaxrate")
    @ResponseBody
    public List getAllTaxrate() {

        List<Taxrate> taxrateList = taxrateDao.getAllTaxrate();
        List list = new ArrayList<>();
        for(int i = 0; i < taxrateList.size(); i++){
            Map map = new HashMap();
            Map map2 = new HashMap();
            List list2 = new ArrayList<>();
            Taxrate taxrate = taxrateList.get(i);
            map.put("num", i+1);//序号
            map.put("id", taxrate.getId());//ID
            map.put("min",taxrate.getMin());//工资下限
            map.put("max",taxrate.getMax());//工资上限
            map.put("taxrate",taxrate.getTaxrate());//对应税率
            map.put("threshold", taxrate.getThreshold());//征税基数
            Updatedate updatedate= updatedateDao.getUpdatedateById(taxrate.getUpdid());
            map.put("updatedate", updatedate.getUpdatedate());//更新时间
            list.add(map);
        }
        return list;
    }

    //添加税率信息
    @CrossOrigin
    @PostMapping("incomes/addTaxrate")
    @ResponseBody
    @Transactional
    public Result addTaxrate(@RequestBody RequestTaxrate requestTaxrate) throws ParseException {
        String message = String.format("编辑成功！");
        double min =requestTaxrate.getMin();//工资下限
        double max =requestTaxrate.getMax();//工资上限
        double taxrate =requestTaxrate.getTaxrate();//对应税率
        int threshold =requestTaxrate.getThreshold();//起征税点
        String updatedate =requestTaxrate.getUpdatedate();//更新时间
        if(null==updatedate||updatedate.equals("")){
            message = String.format("时间不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Updatedate updatedateByDate = updatedateDao.getUpdatedateByDate(simpleDateFormat.parse(updatedate));
        if(null==updatedateByDate){
            int i = updatedateDao.updateUpdatedate(simpleDateFormat.parse(updatedate));
            if (i == 0) {
                message = String.format("添加失败！错误代码448");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
            Updatedate updatedateByDate2 = updatedateDao.getUpdatedateByDate(simpleDateFormat.parse(updatedate));
            int j = taxrateDao.addTaxrate(updatedateByDate2.getId(), min, max, taxrate, threshold);
            if (j == 0) {
                message = String.format("添加失败！错误代码442");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
        } else {
            int j = taxrateDao.addTaxrate(updatedateByDate.getId(), min, max, taxrate, threshold);
            if (j == 0) {
                message = String.format("添加失败！错误代码443");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
        }
        return ResultFactory.buildSuccessResult(message, updatedateByDate);
    }

    //删除所有的税率信息
    @CrossOrigin
    @PostMapping(value = "incomes/deleteTaxrate")
    @ResponseBody
    @Transactional
    public Result deleteTaxrate() {
        String message = String.format("删除成功！");
        int i = taxrateDao.deleteTaxrate();
        if(i==0){
            message = String.format("删除失败！错误代码441");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message,i);
    }

    //自动生成个税
    @CrossOrigin
    @PostMapping(value = "incomes/generateTaxrate")
    @ResponseBody
    public Result generateTaxrate(@RequestBody RequestTax requestTax) {
        String message = String.format("生成成功！");
        String name = requestTax.getName();
        String time = requestTax.getTime();
        double sum = requestTax.getSum();
        if(name==null||time==null||sum==0.0){
            message = String.format("时间或姓名为填写！");
            return ResultFactory.buildFailResult(message);
        }
        int m =Integer.parseInt(time.substring(5,7));
        int empId = employerDao.getEmployerByName(name).getId();
        int cdid = createdateDao.getCreatedateByTime(time).getId();
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
                    double tt = sum - threshold;
                    if (tt > min && tt <= max) {
                        taxx = taxx + (tt - min) * taxrate1;
                    }
                    if (tt > max) {
                        taxx = taxx + (max - min) * taxrate1;
                    }
                }else{
                    int sumpayByCdid = userobjectDao.getSumpayByCdid(empId, cdid1, cdid2);//本年度累计应交税所得额
                    double tt = sumpayByCdid + sum -threshold*m;
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
        return ResultFactory.buildSuccessResult(message,taxx);
    }

}
