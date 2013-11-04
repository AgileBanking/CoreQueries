package entities



import org.junit.*
import grails.test.mixin.*

@TestFor(ComponentController)
@Mock(Component)
class ComponentControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/component/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.componentInstanceList.size() == 0
        assert model.componentInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.componentInstance != null
    }

    void testSave() {
        controller.save()

        assert model.componentInstance != null
        assert view == '/component/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/component/show/1'
        assert controller.flash.message != null
        assert Component.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/component/list'

        populateValidParams(params)
        def component = new Component(params)

        assert component.save() != null

        params.id = component.id

        def model = controller.show()

        assert model.componentInstance == component
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/component/list'

        populateValidParams(params)
        def component = new Component(params)

        assert component.save() != null

        params.id = component.id

        def model = controller.edit()

        assert model.componentInstance == component
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/component/list'

        response.reset()

        populateValidParams(params)
        def component = new Component(params)

        assert component.save() != null

        // test invalid parameters in update
        params.id = component.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/component/edit"
        assert model.componentInstance != null

        component.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/component/show/$component.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        component.clearErrors()

        populateValidParams(params)
        params.id = component.id
        params.version = -1
        controller.update()

        assert view == "/component/edit"
        assert model.componentInstance != null
        assert model.componentInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/component/list'

        response.reset()

        populateValidParams(params)
        def component = new Component(params)

        assert component.save() != null
        assert Component.count() == 1

        params.id = component.id

        controller.delete()

        assert Component.count() == 0
        assert Component.get(component.id) == null
        assert response.redirectedUrl == '/component/list'
    }
}
