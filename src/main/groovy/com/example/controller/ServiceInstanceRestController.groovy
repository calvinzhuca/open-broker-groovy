package com.example.controller

import com.example.service.ServiceInstance
import com.example.service.ServiceInstanceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody


@Controller
@RequestMapping("/v2/service_instances/{id}")
class ServiceInstanceRestController {
  @Autowired
  private ServiceInstanceService service

  @RequestMapping(method = RequestMethod.PUT)
  @ResponseBody
  Map update(@PathVariable String id) {
    String msg
    ServiceInstance instance = service.findById(id)
    if (!service.isExists(instance)) {
      service.create(instance)
      msg = "database is created"
    }else{
      msg = "database exists, won't create"
    }
    return [reply:msg]
  }

  @RequestMapping(method = RequestMethod.DELETE)
  @ResponseBody
  Map destroy(@PathVariable String id) {
    String msg
    ServiceInstance instance = service.findById(id)
    if (service.isExists(instance)) {
      service.delete(instance)
      msg = "database is deleted"
    }else{
      msg = "no such database"
    }
    return [reply:msg]
  }
}
