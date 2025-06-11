package com.allwyn.tool;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EnvManagerUtil {

    private static final Dotenv ENV = Dotenv
            .configure()
            .ignoreIfMissing()
            .load();

    public static final String BASE_URL = (ENV.get("BASE_URL") != null) ? ENV.get("BASE_URL") : System.getenv("BASE_URL");

}
