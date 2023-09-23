import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    // класс для записи событий, связанных с розыгрышем, в лог-файл
    private File dataFile = new File("ToyLottery.txt");

    public Logger() {
        createFile();
    }

    // обновляем файл: если был создан - удаляем
    private void createFile(){
        if (dataFile.exists()) {
            dataFile.delete();
        } else {
            try {
                dataFile.createNewFile();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Сохранение данных о выдаче призов в файл (ЛОГ)
     * @param event
     */
    public void writeToLog(String event) {
        if (dataFile.canWrite()) {
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy (EEE), HH:mm:ss:SSS");
            try (FileWriter fwriter = new FileWriter(dataFile, true)) {
                // доп параметр TRUE позволяет ДОПИСЫВАТЬ, а НЕ перезаписывать
                fwriter.write("Время: " + dateFormat.format(new Date()) + "\n");
                fwriter.write("     Событие: " + event + "\n");
                fwriter.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
