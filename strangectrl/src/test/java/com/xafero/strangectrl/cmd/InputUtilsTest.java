package com.xafero.strangectrl.cmd;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.junit.Test;

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
}
