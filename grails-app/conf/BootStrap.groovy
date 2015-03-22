//import grails.plugins.rest.client.RestBuilder
class BootStrap {    

    def init = { servletContext ->
//        def grailsApplication
//        // create auditdb, if not exists
//       try {
//        def restAudit = new RestBuilder()
//        
//        def respAudit = restAudit.put("http://auditdb:5984/auditdb"){ } 
//        println "auditdb created in auditdb"
//       }
//       catch(Exception e){
//           println "auditdb is not accessible in my local environment"
//           grailsApplication.config.custome.auditdb.isOn = true
//       }
       
        // create configdb, if not exists
//       try {
//        def restConfig = new RestBuilder()
//        def respConfig = restConfig.put("http://auditdb:5984/configdb"){} 
//       }
//       catch(Exception e){
//           println "configdb is not accessible in my local environment"
//           grailsApplication.config.custome.auditdb.isOn = false
//       }        

//        servletContext.globalVariable  = anyValue 
    }
    def destroy = {
    }
}
