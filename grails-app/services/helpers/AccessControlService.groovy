package helpers
import grails.plugins.rest.client.RestBuilder
import grails.converters.*

class AccessControlService {

    def authenticate(String token) {
        println "Hi, from AccessControl.authenticate"
        def baseURL = entities.Component.findByName("Policies").baseURL
        def resp
        def rest = new RestBuilder()
        try {        
            println "$baseURL/user/authenticate?token=$token" 
            resp = rest.get("$baseURL/user/authenticate?token=$token" ) { 
                accept "application/json"
                contentType "application/json"
                } 
            if (resp.status == 401) {   //Unauthorized. LogIn
                def restLogin = new RestBuilder()
                println "$baseURL/j_spring_security_check" 
//                respLogin = restLogin.get("$baseURL/j_spring_security_check?username='admin'&password='admin'"  ) { 
                respLogin = restLogin.get("$baseURL/j_spring_security_check"  ) { 
                    accept "application/json"
                    contentType "application/json"    
                    json {
                        username: "admin"
                        password : "admin"
                    }
                    println "login status: $respLogin.status"
                    if (respLogin.status>299) {
                        return ["LoginFailureCode": respLogin.status]
                    }
                }
            }
//            println "resp.status = $resp.status and resp.json = $resp.json"
            def status = resp.status 
            if (resp.status < 300 && resp.json.class != null) { 
                resp.json.remove("class") 
            }

            return resp.json 
            
        } 
        catch(Exception e2) {
            return ["status":"503", "possibleCause": "Unavailable Domain Server 'Policies'", "message":e2.toString()] 
        }               

    }
}
