package yaRu;

import java.util.ArrayList;
import java.util.List;

//Опрелеляем тип и разрядность системы

public class TypeOS {

    private static List<String> getTypeOs () {
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

    public void setProperty () {
        List<String> listType = getTypeOs();
        switch (listType.get(0)) {
            case "win": {
                switch (listType.get(1)){
                    case "32":
                        System.setProperty("webdriver.gecko.driver", "geckodriver32.exe");
                        break;
                    case "64":
                        System.setProperty("webdriver.gecko.driver", "geckodriver64.exe");
                        break;
                    default:
                        System.out.println("Неизвестное значение ");
                        break;
                }
                break;
            }
            case "mac":
                System.setProperty("webdriver.gecko.driver", "geckodriverMac");
                break;
            case "linux":
                switch (listType.get(1)) {
                    case "32":
                        System.setProperty("webdriver.gecko.driver", "geckodriverLinux32");
                        break;
                    case "64":
                        System.setProperty("webdriver.gecko.driver", "geckodriverLinux64");
                        break;
                    default:
                        System.out.println("Неизвестное значение");
                        break;
                }
                break;
            case "unknown":
            default:
                System.out.println("Неизвестное значение");
                break;
        }
    }
}

