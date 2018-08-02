package Backend.Config;

public class DefaultConfig implements ITextEditorConfig {

    private final int WIDTH;
    private final int HEIGHT;

    private final boolean wrapTextEnabled;

    public DefaultConfig(){
        WIDTH  = 1792;
        HEIGHT = 1008;
        wrapTextEnabled = false;
    }


    @Override
    public int getInitialWidth() {
        return WIDTH;
    }

    @Override
    public int getInitialHeight() {
        return HEIGHT;
    }

    @Override
    public boolean isTextWrapEnabled() {
        return wrapTextEnabled;
    }
}
