package services.parties

class P2PRelationController extends BaseController{
    
    def shortList() { redirect(action: "list", params: params) }
}
