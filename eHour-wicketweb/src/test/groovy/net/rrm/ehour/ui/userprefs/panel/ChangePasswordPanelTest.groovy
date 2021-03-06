package net.rrm.ehour.ui.userprefs.panel

import net.rrm.ehour.ui.common.AbstractSpringWebAppTester
import net.rrm.ehour.user.service.UserService
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.util.tester.ITestPanelSource
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ChangePasswordPanelTest extends AbstractSpringWebAppTester {
    @Mock
    private UserService userService

    @Before
    void "set up"() {
        MockitoAnnotations.initMocks this
        getMockContext().putBean("userService", userService);
    }

    @Test
    void "should render"() {
        startPanel()

        tester.assertNoErrorMessage()
        def path = makePanelPath(ChangePasswordPanel.BORDER, ChangePasswordPanel.CHANGE_PASSWORD_FORM)
        tester.assertComponent(path, Form.class)
    }

    @Test
    void "should submit"() {
        startPanel()

        def formPath = makePanelPath(ChangePasswordPanel.BORDER, ChangePasswordPanel.CHANGE_PASSWORD_FORM)
        
        def formTester = tester.newFormTester(formPath)
        
        formTester.setValue("password", "a")
        formTester.setValue("confirmPassword", "a")
        
        tester.executeAjaxEvent(formPath +":submitButton", "onclick")
        
        
        tester.assertNoErrorMessage()
        tester.assertComponent(formPath, Form.class)
    }


    void startPanel() {
        tester.startPanel(new ITestPanelSource() {
            @Override
            Panel getTestPanel(String panelId) {
                return new ChangePasswordPanel(panelId, new ChangePasswordBackingBean())


            }
        })
    }
}
