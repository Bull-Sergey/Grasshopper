import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Grasshopper {
    public static void main(String[] args) throws IOException {
        String inFile = "INPUT.TXT", outFile = "OUTPUT.TXT";
        List<String> strok = readFile(inFile);  // считать все строки из файла


        // Разбираем первую строку
        String[] strok1 = strok.get(0).split("\\s+"); // разбить по пробелам
        int kolJumps = Integer.parseInt(strok1[0]); // количество прыжков
        Frog frog = new Frog(); // создаем лягушку
        frog.x = Integer.parseInt(strok1[1]); // где сидит лягуха
        frog.y = Integer.parseInt(strok1[2]);
        frog.lenTongue = Integer.parseInt(strok1[3]); // язык лягухи

        // Ищем первую точку (по порядку прыжков)
        OptionalInt jump = IntStream.range(0, kolJumps) // хранит либо пусто (не съела), либо число(съела)
                .filter(i -> checkJump(i,strok,frog))
                .map(i -> i + 1) // Переводим индекс в 1-based номер точки
                .findFirst();

        // Формируем ответ
        String result = jump.isPresent() // если есть int значение
                ? String.valueOf(jump.getAsInt()) // допрыгался - съела
                : "Yes";

        // (посмотреть)
        System.out.println(result);
        // Запись результата в OUTPUT.TXT
        Files.writeString(Path.of(outFile), result);
    }

    // считать все строки из файла
    private static List<String> readFile(String inFile) throws IOException {
        List<String> strok = Files.readAllLines(Path.of(inFile))
                .stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
        return strok;
    }
    // Проверяем точку, где расстояние <= L
    public static boolean checkJump(int i, List<String> strok, Frog frog) {
        String[] coords = strok.get(i + 1).split("\\s+"); // строчка с координатами
        int jumpX = Integer.parseInt(coords[0]); // прыгнул кузнечик
        int jumpY = Integer.parseInt(coords[1]);

        // Проверяем условие: (jumpX - frogX)^2 + (jumpY - frogY)^2 <= lenFrogTongue^2
        return (jumpX - frog.x) * (jumpX - frog.x) + (jumpY - frog.y) * (jumpY - frog.y) <= frog.lenTongue * frog.lenTongue;

    }
}