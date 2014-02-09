package services.accounts

class AccOwnerController extends BaseController{
    
    def extraLinks() { 
        def links = [:]
        def controllerURL = "$params.host/$params.controller"
        links += ["references":["BIS General Guide to Account Opening and Customer Identification":"http://www.bis.org/publ/bcbs85annex.htm"]]
        return links 
    }    
}
