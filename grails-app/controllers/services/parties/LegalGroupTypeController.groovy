package services.parties

class LegalGroupTypeController extends BaseController{

    def casheControl() {"public, max-age=72000"}
     
    def shortList() {
        redirect (action:"list", params: params)
    }    
}