package com.xafero.strangectrl.cmd;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import pl.grzeslowski.strangectrl.config.Key;

import com.xafero.strangectrl.input.InputUtils;

public class InputUtilsTest {

    @Test
    public void press_key() throws Exception {

        // given
        final Robot robot = mock(Robot.class);
        final InputUtils inputUtils = new InputUtils(robot);
        final Key keyQ = new Key("Q");

        // when
        inputUtils.pressKey(keyQ);

        // then
        verify(robot).keyPress(KeyEvent.VK_Q);
    }

    @Test
    public void release_key() throws Exception {

        // given
        final Robot robot = mock(Robot.class);
        final InputUtils inputUtils = new InputUtils(robot);
        final Key keyQ = new Key("Q");

        // when
        inputUtils.releaseKey(keyQ);

        // then
        verify(robot).keyRelease(KeyEvent.VK_Q);
    }

    @Test
    public void press_key_combo() throws Exception {

        // given
        final Robot robot = mock(Robot.class);
        final InputUtils inputUtils = new InputUtils(robot);
        final Key keyQ = new Key("Q");
        final Key keyW = new Key("W");

        // when
        inputUtils.pressKeyCombo(keyQ, keyW);

        // then
        final InOrder inOrder = Mockito.inOrder(robot);

        inOrder.verify(robot).keyPress(KeyEvent.VK_Q);
        inOrder.verify(robot).keyPress(KeyEvent.VK_W);

        inOrder.verify(robot).keyRelease(KeyEvent.VK_W);
        inOrder.verify(robot).keyRelease(KeyEvent.VK_Q);

    }

    @Test(expected = NullPointerException.class)
    public void press_key_null() throws Exception {

        // given
        final Robot robot = mock(Robot.class);
        final InputUtils inputUtils = new InputUtils(robot);
        final List<Key> key = null;

        // when
        inputUtils.pressKey(key);
    }

    @Test(expected = NullPointerException.class)
    public void release_key_null() throws Exception {

        // given
        final Robot robot = mock(Robot.class);
        final InputUtils inputUtils = new InputUtils(robot);
        final List<Key> key = null;

        // when
        inputUtils.releaseKey(key);
    }

    @Test(expected = NullPointerException.class)
    public void press_key_combo_null() throws Exception {

        // given
        final Robot robot = mock(Robot.class);
        final InputUtils inputUtils = new InputUtils(robot);
        final List<Key> key = null;

        // when
        inputUtils.pressKeyCombo(key);
    }

    @Test
    public void mouse_move() throws Exception {

        // given
        final Robot robot = mock(Robot.class);
        final InputUtils inputUtils = new InputUtils(robot);
        final Point point = new Point(10, 20);

        // when
        inputUtils.moveMouse(point);

        // then
        verify(robot).mouseMove(10, 20);
    }

    @Test
    public void mouse_press_left() throws Exception {

        // given
        final Robot robot = mock(Robot.class);
        final InputUtils inputUtils = new InputUtils(robot);

        // when
        inputUtils.mousePressLeft();

        // then
        verify(robot).mousePress(InputUtils.MouseButton.LEFT.getButtonMask());
    }

    @Test
    public void mouse_press_right() throws Exception {

        // given
        final Robot robot = mock(Robot.class);
        final InputUtils inputUtils = new InputUtils(robot);

        // when
        inputUtils.mousePressRight();

        // then
        verify(robot).mousePress(InputUtils.MouseButton.RIGHT.getButtonMask());
    }

    @Test
    public void mouse_press_center() throws Exception {

        // given
        final Robot robot = mock(Robot.class);
        final InputUtils inputUtils = new InputUtils(robot);

        // when
        inputUtils.mousePressCenter();

        // then
        verify(robot).mousePress(InputUtils.MouseButton.CENTER.getButtonMask());
    } @Test
    public void mouse_release_left() throws Exception {

        // given
        final Robot robot = mock(Robot.class);
        final InputUtils inputUtils = new InputUtils(robot);

        // when
        inputUtils.mouseReleaseLeft();

        // then
        verify(robot).mouseRelease(InputUtils.MouseButton.LEFT.getButtonMask());
    }

    @Test
    public void mouse_release_right() throws Exception {

        // given
        final Robot robot = mock(Robot.class);
        final InputUtils inputUtils = new InputUtils(robot);

        // when
        inputUtils.mouseReleaseRight();

        // then
        verify(robot).mouseRelease(InputUtils.MouseButton.RIGHT.getButtonMask());
    }

    @Test
    public void mouse_release_center() throws Exception {

        // given
        final Robot robot = mock(Robot.class);
        final InputUtils inputUtils = new InputUtils(robot);

        // when
        inputUtils.mouseReleaseCenter();

        // then
        verify(robot).mouseRelease(InputUtils.MouseButton.CENTER.getButtonMask());
    }
}
