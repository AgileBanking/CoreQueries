package entities



import org.junit.*
import grails.test.mixin.*

@TestFor(RepoController)
@Mock(Repo)
class RepoControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/repo/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.repoInstanceList.size() == 0
        assert model.repoInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.repoInstance != null
    }

    void testSave() {
        controller.save()

        assert model.repoInstance != null
        assert view == '/repo/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/repo/show/1'
        assert controller.flash.message != null
        assert Repo.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/repo/list'

        populateValidParams(params)
        def repo = new Repo(params)

        assert repo.save() != null

        params.id = repo.id

        def model = controller.show()

        assert model.repoInstance == repo
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/repo/list'

        populateValidParams(params)
        def repo = new Repo(params)

        assert repo.save() != null

        params.id = repo.id

        def model = controller.edit()

        assert model.repoInstance == repo
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/repo/list'

        response.reset()

        populateValidParams(params)
        def repo = new Repo(params)

        assert repo.save() != null

        // test invalid parameters in update
        params.id = repo.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/repo/edit"
        assert model.repoInstance != null

        repo.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/repo/show/$repo.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        repo.clearErrors()

        populateValidParams(params)
        params.id = repo.id
        params.version = -1
        controller.update()

        assert view == "/repo/edit"
        assert model.repoInstance != null
        assert model.repoInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/repo/list'

        response.reset()

        populateValidParams(params)
        def repo = new Repo(params)

        assert repo.save() != null
        assert Repo.count() == 1

        params.id = repo.id

        controller.delete()

        assert Repo.count() == 0
        assert Repo.get(repo.id) == null
        assert response.redirectedUrl == '/repo/list'
    }
}
