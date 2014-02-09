package services.parties
class ContractualAbilityController extends BaseController{   
    def casheControl() {"public, max-age=72000"}
    
    def shortList() {
        redirect (action:"list", params: params)
    }
}