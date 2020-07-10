package com.muicc.incomes.controller;

import com.muicc.incomes.dao.TaxrateDao;
import com.muicc.incomes.dao.UpdatedateDao;
import com.muicc.incomes.model.RequestTaxrate;
import com.muicc.incomes.pojo.Taxrate;
import com.muicc.incomes.pojo.Updatedate;
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
public class TaxrateController {

    @Autowired
    TaxrateDao taxrateDao;

    @Autowired
    UpdatedateDao updatedateDao;

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
    public Result addTaxrate(@RequestBody RequestTaxrate requestTaxrate) throws ParseException {
        String message = String.format("添加成功！");
        double min =requestTaxrate.getMin();//工资下限
        double max =requestTaxrate.getMax();//工资上限
        double taxrate =requestTaxrate.getTaxrate();//对应税率
        int threshold =requestTaxrate.getThreshold();//起征税点
        String updatedate =requestTaxrate.getUpdatedate();//更新时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Updatedate updatedateByDate = updatedateDao.getUpdatedateByDate(simpleDateFormat.parse(updatedate));
        if(null==updatedateByDate){
            int i = updatedateDao.addUpdatedate(simpleDateFormat.parse(updatedate));
            if (i == 0) {
                message = String.format("添加失败！");
                return ResultFactory.buildFailResult(message);
            }else {
                Updatedate updatedateByDate2 = updatedateDao.getUpdatedateByDate(simpleDateFormat.parse(updatedate));
                int j = taxrateDao.addTaxrate(updatedateByDate2.getId(), min, max, taxrate, threshold);
                if (j == 0) {
                    message = String.format("添加失败！");
                    return ResultFactory.buildFailResult(message);
                }
            }
        }else {
            int j = taxrateDao.addTaxrate(updatedateByDate.getId(), min, max, taxrate, threshold);
            if (j == 0) {
                message = String.format("添加失败！");
                return ResultFactory.buildFailResult(message);
            }
        }
        return ResultFactory.buildSuccessResult(message, updatedateByDate);
    }


    //修改税率信息
//    @CrossOrigin
//    @PutMapping("incomes/updateTaxrate")
//    @ResponseBody
//    public Result updateTaxrate(@RequestBody RequestTaxrate requestTaxrate) throws ParseException {
//        String message = String.format("修改成功！");
//        double min = requestTaxrate.getMin();//工资下限
//        double max =requestTaxrate.getMax();//工资上限
//        double taxrate =requestTaxrate.getTaxrate();//对应税率
//        int threshold =requestTaxrate.getThreshold();//起征税点
//        String updatedate =requestTaxrate.getUpdatedate();//更新时间
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            int i = taxrateDao.deleteTaxrate();
//            if(i==0) {
//                message = String.format("删除失败！");
//                return ResultFactory.buildFailResult(message);
//            }
//            Updatedate updatedateByDate = updatedateDao.getUpdatedateByDate(simpleDateFormat.parse(updatedate));
//            if(null==updatedateByDate){
//                int j = updatedateDao.addUpdatedate(simpleDateFormat.parse(updatedate));
//                if (j == 0) {
//                    message = String.format("添加失败！");
//                    return ResultFactory.buildFailResult(message);
//                }else {
//                    Updatedate updatedateByDate2 = updatedateDao.getUpdatedateByDate(simpleDateFormat.parse(updatedate));
//                    int k = taxrateDao.addTaxrate(updatedateByDate2.getId(), min, max, taxrate, threshold);
//                    if (k == 0) {
//                        message = String.format("添加失败！");
//                        return ResultFactory.buildFailResult(message);
//                    }
//                }
//            }else {
//                int j = taxrateDao.addTaxrate(updatedateByDate.getId(), min, max, taxrate, threshold);
//                if (j == 0) {
//                    message = String.format("添加失败！");
//                    return ResultFactory.buildFailResult(message);
//                }
//            }
//            return ResultFactory.buildSuccessResult(message,updatedateByDate);
//    }



    //删除所有的税率信息
    @CrossOrigin
    @PostMapping(value = "incomes/deleteTaxrate")
    @ResponseBody
    public Result deleteTaxrate() {
        String message = String.format("删除成功！");
        int i = taxrateDao.deleteTaxrate();
        if(i==0){
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }

    }

}
