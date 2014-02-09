package services.parties

class MarketSegmentController extends BaseController{
    def casheControl() {"public, max-age=1800"}
    
    def shortList() { redirect(action: "list", params: params) }
}
