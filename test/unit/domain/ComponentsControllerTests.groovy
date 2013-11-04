package entities



import org.junit.*
import grails.test.mixin.*

@TestFor(ComponentsController)
@Mock(Components)
class ComponentsControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/components/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.componentsInstanceList.size() == 0
        assert model.componentsInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.componentsInstance != null
    }

    void testSave() {
        controller.save()

        assert model.componentsInstance != null
        assert view == '/components/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/components/show/1'
        assert controller.flash.message != null
        assert Components.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/components/list'

        populateValidParams(params)
        def components = new Components(params)

        assert components.save() != null

        params.id = components.id

        def model = controller.show()

        assert model.componentsInstance == components
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/components/list'

        populateValidParams(params)
        def components = new Components(params)

        assert components.save() != null

        params.id = components.id

        def model = controller.edit()

        assert model.componentsInstance == components
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/components/list'

        response.reset()

        populateValidParams(params)
        def components = new Components(params)

        assert components.save() != null

        // test invalid parameters in update
        params.id = components.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/components/edit"
        assert model.componentsInstance != null

        components.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/components/show/$components.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        components.clearErrors()

        populateValidParams(params)
        params.id = components.id
        params.version = -1
        controller.update()

        assert view == "/components/edit"
        assert model.componentsInstance != null
        assert model.componentsInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/components/list'

        response.reset()

        populateValidParams(params)
        def components = new Components(params)

        assert components.save() != null
        assert Components.count() == 1

        params.id = components.id

        controller.delete()

        assert Components.count() == 0
        assert Components.get(components.id) == null
        assert response.redirectedUrl == '/components/list'
    }
}
