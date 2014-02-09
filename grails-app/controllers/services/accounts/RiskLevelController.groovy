package services.accounts

class RiskLevelController  extends BaseController{
    
    // 
    def extraLinks() { 
        def links = [:]
        def controllerURL = "$params.host/$params.controller"
        links += ["references":["OFAC Matrix": "www.treasury.gov/resource-center/sanctions/Documents/matrix.pdf‎",
                    "BIS Customer due diligence":"http://www.bis.org/publ/bcbs85.htm"]]
        return links 
    }      
}
