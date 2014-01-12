package admin

class BuildLinksService {
    static transactional = false

    def controllerLinks(params, request) {
        def links = [:]
        if (params.withlinks==null) { params.withlinks = request.getHeader('withlinks')}
        if (params.withlinks=="true" || params.withlinks==null ) { 
            links  = ["list":["template": true, "fields": ["max":"Long (default=10, maximum 100)","offset":"Long (after page number, default=0)"], \
                "href":"$params.hostApp/$params.controller/list?max={max}&offset={offset}", "description":"List " ]]
            links += ["get":["template": true, "fields": ["id":"Long"], "href": "$params.hostApp/$params.controller/get?id={id}"]]
            links += ["schema": ["href": "$params.hostApp/$params.controller/schema"]]
            links += ["create": ["href": "$params.hostApp/$params.controller/create", "notes":"Returns an empty instance of editable fields."]]
            links += ["save":["template":true, "method":"PUT", "href": entities.Component.findByName('CoreUpdates').baseURL + "/$params.controller/save", \
                "body":"@create", "notes":"If you have not in cache, get the body from the 'create'."]]
            links += ["relatedLinks": ["href": "$params.hostApp/$params.controller/relatedLinks", "notes":"Returns a list with the most frequent links."]]
            return links 
        }        
    } 
}
