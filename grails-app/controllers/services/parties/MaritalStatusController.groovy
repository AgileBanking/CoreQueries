package services.parties

class MaritalStatusController extends BaseController{
    def casheControl() {"public, max-age=72000"}
    def shortList() {
        redirect (action:"list", params: params)
    }    
}
