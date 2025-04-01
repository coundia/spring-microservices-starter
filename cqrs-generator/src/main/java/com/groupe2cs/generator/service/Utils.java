package com.groupe2cs.generator.service;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

    public static String capitalize(String value) {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    public static String getPackage(String fullPath) {
        Path path = Paths.get(fullPath).normalize();

        int javaIndex = -1;
        for (int i = 0; i < path.getNameCount(); i++) {
            if (path.getName(i).toString().equals("java")) {
                javaIndex = i;
                break;
            }
        }

        Path packagePath;
        if (javaIndex != -1 && javaIndex + 1 < path.getNameCount()) {
            packagePath = path.subpath(javaIndex + 1, path.getNameCount());
        } else {
            packagePath = path;
        }

        return packagePath.toString().replace("/", ".").replace("\\", ".");
    }


    public static Object lowerFirst(String name) {
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }
}
