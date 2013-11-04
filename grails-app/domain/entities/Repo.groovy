package entities
import grails.rest.*

class Repo {
    Component   domainApp
    String      tags
    String      title
    String      description
    String      serviceURL
    String      target      = "REST"
    String      body
    String      example
    
    String toString() {"$title"}
    
    static constraints = {
        domainApp       ()
        title           ()
        tags            ()
        description     (nullable:true, size:0..1024)
        serviceURL      ()
        target          (inList:["REST", "SOAP", "ATOM", "JDBC" ])
        body            (nullable:true, size:0..1024)
        example         (nullable:true, size:0..1024)
    }
}
