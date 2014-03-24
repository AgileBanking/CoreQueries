package admin

class RepoController {
// to show it in th emain menu as an idepended entry

    def ConfiguratorService
    def index() {
        redirect (controller: "admin", action:"repo")
    }
 
}
