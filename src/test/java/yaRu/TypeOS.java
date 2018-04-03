package yaRu;

import java.util.ArrayList;
import java.util.List;

//Опрелеляем тип и разрядность системы

public class TypeOS {

    public List<String> getTypeOs () {
        List<String> dataOs = new ArrayList<>();

        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            dataOs.add("win");
        } else {
            if (os.contains("mac")) {
                dataOs.add("mac");
            } else {
                if (os.contains("nix") || os.contains("nux")) {
                    dataOs.add("linux");
                }
                else {
                    dataOs.add("unknown");
                }
            }
        }
        dataOs.add(System.getProperty("sun.arch.data.model"));

        return dataOs;
    }
}

