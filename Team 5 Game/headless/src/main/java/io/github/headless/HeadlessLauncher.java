package io.github.headless;

import io.github.some_example_name.Main;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

public class HeadlessLauncher {

    public static void main(String[] args) {
        HeadlessApplicationConfiguration config =
                new HeadlessApplicationConfiguration();

        config.updatesPerSecond = -1;


        new HeadlessApplication(new Main(), config);
    }
}