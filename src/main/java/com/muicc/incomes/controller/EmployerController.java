package com.muicc.incomes.controller;
import com.muicc.incomes.dao.EmployerDao;
import com.muicc.incomes.pojo.Employer;
import com.muicc.incomes.result.Result;
import com.muicc.incomes.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class EmployerController {

    @Autowired
    EmployerDao employerDao;

    @CrossOrigin
    @PostMapping(value = "incomes/eLogin")
    @ResponseBody
    public Result login(@RequestBody Employer employer, HttpSession session) {
        String message = String.format("登陆成功！");
        int id = employer.getId();
        String name = employer.getName();
        if ( 0== id &&  null== name) {
            message = String.format("登陆失败！");
            return ResultFactory.buildFailResult(message);
        }

        employer = employerDao.getEmployerByIdAndName(id,name);
        if(null==employer){
            message = String.format("用户名或密码不正确！");
            return ResultFactory.buildFailResult(message);
        }else {
            session.setAttribute("employer", employer);
            return ResultFactory.buildSuccessResult(message, employer);
        }
    }


    //查找所有的员工信息
    @CrossOrigin
    @GetMapping(value = "incomes/getAllEmployer")
    @ResponseBody
    public List getAllEmployer() {

        List<Employer> employerList = employerDao.getAllEmployer();
        List list = new ArrayList<>();
        for(int i = 0; i < employerList.size(); i++){
            Map map = new HashMap();
            Employer employer = employerList.get(i);
            map.put("num", i+1);//序号
            map.put("id",employer.getId());//工号
            map.put("name", employer.getName());//姓名
            list.add(map);
        }
        return list;
    }

    //根据员工ID查找员工信息
    @CrossOrigin
    @GetMapping(value = "incomes/getEmployerById")
    @ResponseBody
    public List getEmployerById(int id) {

        Employer employer = employerDao.getEmployerById(id);
        List list = new ArrayList<>();
            Map map = new HashMap();
            map.put("name", employer.getName());//姓名
            list.add(map);

        return list;
    }

    //添加员工信息
    @CrossOrigin
    @PostMapping("incomes/addEmployer")
    @ResponseBody
    public Result addEmployer(@RequestBody Employer requestEmployer) {
        int id =requestEmployer.getId();//工号
        String name =requestEmployer.getName();//姓名
        String message = String.format("添加成功！");
        if (null == name || 0 == id) {
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        }
        int i = employerDao.addEmployer(id,name);
        if(i==0){
            message = String.format("添加失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }

    //按员工工号修改员工信息
    @CrossOrigin
    @PutMapping("incomes/updateEmployer")
    @ResponseBody
    public Result updateEmployer(@RequestBody Employer requestEmployer) {
        int id =requestEmployer.getId();//工号
        String name =requestEmployer.getName();//姓名
        String message = String.format("修改成功！");
        if (null == name || 0 == id) {
            message = String.format("修改失败！");
            return ResultFactory.buildFailResult(message);
        }
        int i = employerDao.updateEmployer(id,name);
        if(i==0){
            message = String.format("修改失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }

    //按员工工号和姓名删除员工信息（假删除）
    @CrossOrigin
    @PutMapping("incomes/deleteEmployer")
    @ResponseBody
    public Result deleteEmployer(@RequestBody Employer requestEmployer) {
        int id =requestEmployer.getId();//工号
        String message = String.format("删除成功！");
        if (0 == id) {
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }
        int i = employerDao.deleteEmployer(id);
        if(i==0){
            message = String.format("删除失败！");
            return ResultFactory.buildFailResult(message);
        }else{
            return ResultFactory.buildSuccessResult(message,i);
        }
    }
}
