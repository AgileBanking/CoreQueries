class UrlMappings {

	static mappings = {
            "/$controller/$action?/$id?(.$format)?"{
//            "/$controller/$action?/$id?"{
                    constraints {
                            // apply constraints here
                    }
            }

            "/"(view:"/index") // 
            /* 
                to bypass the HTML page replace the above line with: 
                "/" (controller:'Home', action:"index") 
            */
            "500"(view:'/error')
	}
}
