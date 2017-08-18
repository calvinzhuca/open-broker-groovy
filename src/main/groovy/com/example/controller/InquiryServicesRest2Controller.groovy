package com.example.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import com.example.util.RestSvcHelper


@Controller
@RequestMapping("/v2/inquirySvc2")
class InquiryServicesRest2Controller {
  def settings

  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  synchronized String getCatalog() {
    println "!!!!!!!!!!!!!!!!!!!!!!here in CatalogRestController"
    if (!settings) {
      String inputPath = "/admin/api/services.xml"
      settings = RestSvcHelper.invokeRestService(inputPath)
    }

    return settings
  }
}
