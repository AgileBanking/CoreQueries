package services.parties

class IdStatusController extends BaseController{
    def casheControl() {"public, max-age=72000"}
    
    def shortList() {
        redirect (action:"list", params: params)
    }
}
