package com.muicc.incomes.controller;

import com.muicc.incomes.dao.AccountantDao;
import com.muicc.incomes.pojo.Accountant;
import com.muicc.incomes.result.Result;
import com.muicc.incomes.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class AccountantController {

    @Autowired
    AccountantDao accountantDao;


    //管理员退出
    @CrossOrigin
    @PostMapping(value = "incomes/exit")
    @ResponseBody
    public Result exit(@RequestBody HttpSession session) {
        session.removeAttribute("accountant");
        session.removeAttribute("employer");
        String message = String.format("退出登录！");
        return ResultFactory.buildSuccessResult(message, null);
    }


    //管理员登录
    @CrossOrigin
    @PostMapping(value = "incomes/aLogin")
    @ResponseBody
    @Transactional
    public Result login(@RequestBody Accountant requestAccountant, HttpSession session) {
        String message = String.format("登陆成功！");
        String name = requestAccountant.getName();
        String password = requestAccountant.getPassword();
        if ( null== name &&  null== password) {
            message = String.format("登陆失败！");
            return ResultFactory.buildFailResult(message);
        }
        List<Accountant> allAccountant = accountantDao.getAllAccountant();
        if(allAccountant.size()==0){
            message = String.format("管理员账号注册成功！");
            int i = accountantDao.addAccountant(name, password);
            if(i==0){
                message = String.format("注册失败！错误代码440");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
            Accountant accountant = accountantDao.getAccountantByNameAndPassword(name,password);
            session.setAttribute("accountant", accountant);
            return ResultFactory.buidResult(100,message,accountant);
        }else{
            Accountant accountant = accountantDao.getAccountantByNameAndPassword(name,password);
            if(null==accountant){
                message = String.format("用户名或密码不正确！");
                return ResultFactory.buildFailResult(message);
            }else {
                session.setAttribute("accountant", accountant);
                return ResultFactory.buildSuccessResult(message, accountant);
            }
        }

    }


    //查找所有的管理员信息
    @CrossOrigin
    @GetMapping(value = "incomes/getAllAccountant")
    @ResponseBody
    public List getAllAccountant() {

        List<Accountant> accountantList = accountantDao.getAllAccountant();
        List list = new ArrayList<>();
        for(int i = 0; i < accountantList.size(); i++){
            Map map = new HashMap();
            Accountant accountant = accountantList.get(i);
            map.put("num", i+1);//序号
            map.put("id", accountant.getId());//ID
            map.put("name",accountant.getName() );//账号
            map.put("password", accountant.getPassword());//密码
            list.add(map);
        }
        return list;
    }

    //添加管理员信息
    @CrossOrigin
    @PostMapping("incomes/addAccountant")
    @ResponseBody
    @Transactional
    public Result addAccountant(@RequestBody Accountant requestAccountant) {
            String name =requestAccountant.getName();//账号
            String password = requestAccountant.getPassword();//密码
            String message = String.format("添加成功！");
            if (null == name||name.equals("")) {
                message = String.format("账号不能为空！");
                return ResultFactory.buildFailResult(message);
            }
            if (password.length()<6) {
            message = String.format("密码长度不足6位！");
            return ResultFactory.buildFailResult(message);
            }
            int i = accountantDao.addAccountant(name,password);
            if(i==0){
                message = String.format("添加失败！错误代码441");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return ResultFactory.buildFailResult(message);
            }
            return ResultFactory.buildSuccessResult(message,i);
    }

    //按管理员账号修改管理员信息
    @CrossOrigin
    @PostMapping("incomes/updateAccountant")
    @ResponseBody
    @Transactional
    public Result updateAccountant(@RequestBody Accountant requestAccountant) {
        String name =requestAccountant.getName();//账号
        String password = requestAccountant.getPassword();//密码
        int id = requestAccountant.getId();//ID
        String message = String.format("修改成功！");
        if (null == name||name.equals("")) {
            message = String.format("账号不能为空！");
            return ResultFactory.buildFailResult(message);
        }
        if (password.length()<6) {
            message = String.format("密码长度不足6位！");
            return ResultFactory.buildFailResult(message);
        }
        if (0 == id) {
            message = String.format("修改失败！错误代码440");
            return ResultFactory.buildFailResult(message);
        }
        int i = accountantDao.updateAccountant(name,password,id);
        if(i==0){
            message = String.format("修改失败！错误代码442");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message,i);
    }

    //按管理员账号删除管理员信息
    @CrossOrigin
    @PostMapping("incomes/deleteAccountant")
    @ResponseBody
    @Transactional
    public Result deleteAccountant(@RequestBody Accountant requestAccountant) {
        String message = String.format("删除成功！");
        int id = requestAccountant.getId();//ID
        if (0 == id) {
            message = String.format("删除失败！错误代码440");
            return ResultFactory.buildFailResult(message);
        }
        int i = accountantDao.deleteAccountant(id);
        if(i==0){
            message = String.format("删除失败！错误代码443");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(message,i);
    }

    //按ID获取账号名
    @CrossOrigin
    @PostMapping(value = "incomes/getAccountantIdByName")
    @ResponseBody
    public Result getAccountantIdByName(@RequestBody Accountant requestAccountant) {
        String message = String.format("查找成功！");
        String name = requestAccountant.getName();//账号名称
        int id = accountantDao.getIdByName(name);
        return ResultFactory.buildSuccessResult(message, id);
    }

}

