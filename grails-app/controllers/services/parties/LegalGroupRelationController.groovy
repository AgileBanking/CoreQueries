package services.parties

class LegalGroupRelationController extends BaseController{
     
    def shortList() {
        redirect (action:"list", params: params)
    }    
}
