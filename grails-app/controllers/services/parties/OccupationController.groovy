package services.parties

class OccupationController extends BaseController{
    def casheControl() {"public, max-age=1800"}
    
    def shortList() { redirect(action: "list", params: params) }
}