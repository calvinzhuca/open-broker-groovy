package com.example.controller

import groovyx.net.http.RESTClient
import org.json.JSONObject
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.json.XML
import static groovyx.net.http.ContentType.TEXT
//import static groovyx.net.http.ContentType.XML

@Controller
@RequestMapping("/v2/inquirySvc")
class InquiryServicesRestController {
  def settings

  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  synchronized String getCatalog() {
    println "!!!!!!!!!!!!!!!!!!!!!!here in CatalogRestController"
    if (!settings) {
      //Yaml yaml = new Yaml()
      //settings = yaml.load(this.class.getClassLoader().getResourceAsStream("settings.yml"))

      String baseUrl = "https://3scale-admin.rhmap.ocp.cloud.lab.eng.bos.redhat.com"
      def emptyHeaders = [:]
      emptyHeaders."Accept" = 'application/json'


      def client = new RESTClient(baseUrl)
      //TODO: fix the SSL
      client.ignoreSSLIssues()

      client.contentType = TEXT
      client.headers = [Accept: 'application/xml']

      //note: can't use client.get( path : '/admin/api/services.xml?access_token=...", the 3scale side received the escaped character and couldn't handle it.
      //from 3 scale log:  "GET /admin/api/services.xml%3Faccess_token=xxxx HTTP/1.1" 403 Forbidden
//            def response = client.get(path: '/admin/api/services.xml', query: [access_token: '3bd9a168769132ab4a71cc9795d36731801701665e73adba59685907aaad5174'], contentType: TEXT, headers: [Accept: 'application/xml'])
      def response = client.get(path: '/admin/api/services.xml', query: [access_token: '140bc6cdc1d4f7de2883607aba4a702a53fe6901054162e5433b244a43e7b283'])

      assert response.status == 200  // HTTP response code; 404 means not found, etc.
      println("Content Type: " + response.contentType)

      String replyInXml = response.data.text
      settings = convert(replyInXml)
    }

    return settings
  }


  static String convert(final String inputXML) {
    println "!!!!!!!!!!!! convert() inputXML: " + inputXML
    JSONObject xmlJSONObj = XML.toJSONObject(inputXML)
    int textIndent = 2

    String result = xmlJSONObj.toString(textIndent)
    println "after conversion : " + result
    return result
  }

}
