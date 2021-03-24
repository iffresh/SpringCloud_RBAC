package com.demo.springcloud.Controller;

import com.demo.springcloud.api.pojo.Dept;
import com.demo.springcloud.api.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class DeptConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DeptClientService service;


    @RequestMapping("/consumer/dept/add")
    public boolean add(Dept dept){
        return this.service.addDept(dept);
    }

    @RequestMapping("/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id){
        return this.service.queryById(id);
    }

    @RequestMapping("/consumer/dept/list")
    public List<Dept> list(){
        return this.service.queryAll();
    }

}
