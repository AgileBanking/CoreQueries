package services.parties

class P2PRelationTypeController extends BaseController{
    def casheControl() {"public, max-age=1800"}
    def shortList() { redirect(action: "list", params: params) }
}