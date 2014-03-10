package helpers

class BuildLinksService {
    static transactional = false

    def controllerLinks(params, request) {
        def links = [:] 
        def controllerURL = "$params.host/$params.controller"
        if (params.withlinks==null) { params.withlinks = request.getHeader('withlinks')}
            links  = ["list":["template": true, "fields": ["max":"Long (default=10, maximum 100)","offset":"Long (after page number, default=0)"], \
                "href":"$controllerURL/list?max={max}&offset={offset}", "notes":"It lists a paginated collection of resources. It accepts, optionally, the 'recStatus' parameter" ]]
            links += ["shortList": ["href": "$controllerURL/shortList", "notes" : "It accepts, optionally, the 'recStatus' parameter"]]
            links += ["get":["template": true, "fields": ["id":"String"], "href": "$controllerURL/get?id={id}", "notes":"Retrieves a single resource based on its 'id'. It accepts, optionally, the 'recStatus' parameter"]]
            links += ["schema": ["href": "$controllerURL/schema", "notes":"The schema (properties-fields) of the resource."]]
            links += ["create": ["href": "$controllerURL/create", "notes":"Returns an empty instance of editable fields."]]
            links += ["save":["template":true, "methods":["PUT", "POST"], "href": entities.Component.findByName('CoreCommands').baseURL + "/$params.controller/save", \
                "body":"@create", "notes":"Using the empty resource from 'create' and using PUT create new resource. If you have not in cache, get the body from the 'create'. For update a resource, retrieved with 'get' use POST to save its new state."]]
            links += ["relatedLinks": ["href": "$controllerURL/relatedLinks", "notes":"Returns a list with the most frequent links."]]
            links += ["theBank": ["href": "$params.host/theBank", "notes":"The unique resource that holds most the operational profile of the Bank."]]
            links += ["repo": ["href": "$params.host/admin/repo", "notes": " The repository of hypermedia links of the $params.host"]]
            links += ["refreshCache": ["href": "$params.host/refreshCache", "notes":"Clears all caches (client and proxies). Use it after a system/client disruption."]]
            return links        
    } 
    
    def controllerSingleLink(params, request) {
        return ["$params.controller": ["href": "$params.host/$params.controller"]]              
    }

}
