package services.parties

class LRepTypeController extends BaseController{
    
    def shortList() {
        redirect (action:"list", params: params)
    }    
}