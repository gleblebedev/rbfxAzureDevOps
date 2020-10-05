package org.libsdl.app;

public interface SDLClipboardHandler {

    public boolean clipboardHasText();
    public String clipboardGetText();
    public void clipboardSetText(String string);

}
