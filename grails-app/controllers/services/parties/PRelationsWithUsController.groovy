package services.parties

class PRelationsWithUsController extends BaseController{
    def shortList() { redirect(action: "list", params: params) }
}