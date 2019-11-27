package br.eng.rodrigoamaro.treinamentotestes

import android.view.KeyEvent
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import org.junit.Ignore
import org.junit.Test
import java.lang.Thread.sleep

class UIAutomation {
    @Test
    @Ignore
    fun openAppLauncher() {
        val device = UiDevice.getInstance(getInstrumentation())
        device.pressHome()

        val allAppsButton: UiObject = device.findObject(UiSelector().description("Apps"))

        allAppsButton.clickAndWaitForNewWindow()
    }

    @Test
    @Ignore
    fun raiseUpTheVolumeAndSleepForTwoSeconds() {
        val device = UiDevice.getInstance(getInstrumentation())
        device.pressHome()
        device.pressKeyCode(KeyEvent.KEYCODE_VOLUME_UP)
        sleep(1000)
        device.sleep()
        sleep(2000)
        device.wakeUp()
    }


    @Test
    fun dialANumber() {
        val device = UiDevice.getInstance(getInstrumentation())
        device.pressHome()

        val allAppsButton: UiObject = device.findObject(UiSelector().description("Apps"))
        allAppsButton.clickAndWaitForNewWindow()

        device.swipe(0, device.displayHeight - 30, 0, 30, 100)
        device.findObject(UiSelector().description("Telefone")).clickAndWaitForNewWindow()
        device.findObject(UiSelector().description("Teclado")).clickAndWaitForNewWindow()
        device.findObject(UiSelector().resourceId("com.google.android.dialer:id/digits")).text =
            "989890000"
        device.findObject(UiSelector().description("discar")).clickAndWaitForNewWindow()
    }
}