package com.demo.springcloud.Controller;

import com.demo.springcloud.api.pojo.Dept;
import com.demo.springcloud.service.DeptService;
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

    @GetMapping("/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id){
        return  deptservice.queryById(id);
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
