package com.demo.springcloud.Controller;

import com.demo.springcloud.api.pojo.Dept;
import com.demo.springcloud.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController {
    @Autowired
    private DeptService deptservice;

    @Autowired
    private DiscoveryClient client;

    @PostMapping("/dept/add")
    public boolean addDept(@RequestBody Dept dept){
        return  deptservice.addDept(dept);
    }

    @HystrixCommand(fallbackMethod = "hystrixget")
    @GetMapping("/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id){
        Dept dept = deptservice.queryById(id);
        if(dept==null){
            throw new RuntimeException("不存在用户信息");
        }
        return dept;
    }


    public Dept hystrixget(@PathVariable("id") Long id){
        return  new Dept().setDeptno(id)
                .setDname("id=>"+id+"没有对应信息，null---@Hystrix")
                .setDb_source("no database in mysql");
    }


    @GetMapping("/dept/list")
    public List<Dept> addDept(){
        return  deptservice.queryAll();
    }

    @GetMapping("/dept/discovery")
    public Object discovery(){
        List<String> services=client.getServices();
        System.out.println("discovery=>services"+services);
        List<ServiceInstance> instances = client.getInstances("PROVIDER-DEPT-8001");
        for (ServiceInstance instance : instances) {
            System.out.println(
                    instance.getHost()+"\t"+instance.getPort()+"\t"
                            +instance.getUri()+"\t"+instance.getServiceId()
            );
        }
        return this.client;
    }


}
