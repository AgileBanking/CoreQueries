package entities

import org.springframework.dao.DataIntegrityViolationException

class RepoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [repoInstanceList: Repo.list(params), repoInstanceTotal: Repo.count()]
    }

    def create() {
        [repoInstance: new Repo(params)]
    }

    def save() {
        def repoInstance = new Repo(params)
        if (!repoInstance.save(flush: true)) {
            render(view: "create", model: [repoInstance: repoInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'repo.label', default: 'Repo'), repoInstance.id])
        redirect(action: "show", id: repoInstance.id)
    }

    def show(Long id) {
        def repoInstance = Repo.get(id)
        if (!repoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'repo.label', default: 'Repo'), id])
            redirect(action: "list")
            return
        }

        [repoInstance: repoInstance]
    }

    def edit(Long id) {
        def repoInstance = Repo.get(id)
        if (!repoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'repo.label', default: 'Repo'), id])
            redirect(action: "list")
            return
        }

        [repoInstance: repoInstance]
    }

    def update(Long id, Long version) {
        def repoInstance = Repo.get(id)
        if (!repoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'repo.label', default: 'Repo'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (repoInstance.version > version) {
                repoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'repo.label', default: 'Repo')] as Object[],
                          "Another user has updated this Repo while you were editing")
                render(view: "edit", model: [repoInstance: repoInstance])
                return
            }
        }

        repoInstance.properties = params

        if (!repoInstance.save(flush: true)) {
            render(view: "edit", model: [repoInstance: repoInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'repo.label', default: 'Repo'), repoInstance.id])
        redirect(action: "show", id: repoInstance.id)
    }

    def delete(Long id) {
        def repoInstance = Repo.get(id)
        if (!repoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'repo.label', default: 'Repo'), id])
            redirect(action: "list")
            return
        }

        try {
            repoInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'repo.label', default: 'Repo'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'repo.label', default: 'Repo'), id])
            redirect(action: "show", id: id)
        }
    }
}
