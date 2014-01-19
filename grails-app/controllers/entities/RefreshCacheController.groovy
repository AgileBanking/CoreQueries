package entities

class RefreshCacheController {

    def index() { 
        render (text:"Cache refreshed", status:200, "Cache-Control": "must-revalidate" )
    }
}
