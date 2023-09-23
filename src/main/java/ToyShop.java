import java.util.*;

public class ToyShop {
    private Map<String, ArrayList<Toy>> warehouse;      // база всех игрушек на складе
    private Map<String, Integer> raffleToyTypesList;    // список разыгрываемых классов игрушек
    private Deque<Toy> prizesQueue;
    // Очередь разыгранных игрушек - содержит в себе НЕ новые
    // объекты, отминусованные от ХРАНИЛИЩА, а ссылки на остатки
    // в хранилище. Приз попадает в очередь только при условии наличия
    // игрушки на складе. Приз в каждой строчке очереди подразумевается
    // только ОДИН, поэтому ссылки (но не создания нового объекта)
    // достоточно.
    private Logger logger = new Logger();


    /**
     * КОНСТРУКТОР МАГАЗИНА<br>
     * с приёмом внешних данных инициализируются внутренние переменные
     * за создание и ведение лог-файла отвечает класс LOGGER - происходит его инициализация
     *
     * @param raffleClassList список разыгрываемых в лотерее классов игрушек
     */
    public ToyShop(Map<String, Integer> raffleClassList) {
        this.warehouse = new HashMap<>();
        this.raffleToyTypesList = raffleClassList;
        this.prizesQueue = new ArrayDeque<>();
        typesListVerify();
    }


    // на случай, если в списке участвующих классов игрушек будут созданы записи
    // с нулевыми или отицательными шансами - удаляем ненужные строки и работаем с остатком
    private void typesListVerify(){
        Iterator<Map.Entry<String, Integer>> iterator = raffleToyTypesList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> pair = iterator.next();
            Integer chance = pair.getValue();
            if (chance < 1) {

                iterator.remove();
            }
        }
    }

    /**
     * Добавляем в хранилище WAREHOUSE новую игрушку:<br>
     * - если у класса добавляемой игрушки уже есть список, то добавляем в него,<br>
     * - если нет - создаем новый список и добавляем игрушку в новый.
     *
     * @param newToy добавляемая игрушка.
     */
    public void addToy(Toy newToy) {
        String newToyClass = newToy.getClass().getSimpleName();

        if (warehouse.containsKey(newToyClass)) {
            warehouse.get(newToyClass).add(newToy);
        } else {
            warehouse.put(newToyClass, new ArrayList<>(Collections.singletonList(newToy)));
        }
    }

    /**
     * РОЗЫГРЫШ ИГРУШКИ<br>
     * Пока хранилище магазина не пустое...<br>
     * Пока список разыгрываемых классов не пустой...<br>
     * разыгрываем игрушку, помещаем её в очередь на выдачу и возвращаем её.<br>
     * Если список разыгрываемых игрушек пуст, а хранилище нет - сообщение и запись в лог.<br>
     * Если хранилище пустое - сообщение и запись в лог.<br>
     */
    public Toy raffleToy() {
        String message = "";
        if (!warehouse.isEmpty()) {
            while (!raffleToyTypesList.isEmpty()) {
                Toy raffledToy = getToyFromShop(getToyTypeWithChance());
                if (raffledToy != null) {
                    prizesQueue.offer(raffledToy);
                    System.out.println("       Разыграно: " + raffledToy + ", остаток " + raffledToy.getCount());
                    //System.out.println("       ОЧЕРЕДЬ: " + prizesQueue);
                    return raffledToy;
                }
            }
            message = "( < ! > ) Лотерея закончилась. Все призы разыграны.";
        } else {
            message = "( < ! > ) В магазине не осталось игрушек. Разыгрывать больше нечего (>.<)";
        }
        System.out.println(message);
        // logger.writeLogFile(message);
        return null;
    }


    public void raffleAllToys(){
        while(raffleToy() != null){
            raffleToy();
        }
    }


    /**
     * Генерирует случайный класс игрушки, подлежащий выдаче.
     *
     * @return STRING название класса
     */
    private String getToyTypeWithChance() {
        /*
         Развернем входящий HASHMAP наоборот. В качестве ключа будем использовать
         накопительную сумму шансов (шансы текущего предмета + всех предыдущих),
         а значением будет название предмета.
         Накопительные шансы используем для того, чтобы создать шкалу,
         на которую будет опираться генерация случайного числа.
         Ключ-значение поместим их не в HashMap, а в TreeMap,
         т.к. он позволит искать не конкретный ключ, а ближайший больший.
         Генерируем новый список с шансами заново каждый раз на случай,
         если какой-то тип игрушек закончился
        */
        NavigableMap<Integer, String> chances = new TreeMap<>();
        int chancesSum = 0;
        for (Map.Entry<String, Integer> entry : raffleToyTypesList.entrySet()) {
            String item = entry.getKey();
            Integer chance = entry.getValue();
            if (chance > 0) {
                chancesSum += chance;
                chances.put(chancesSum, item);
            }
        }
        if (!chances.isEmpty()){
            return chances.ceilingEntry(new Random().nextInt(chancesSum + 1)).getValue();
            // ceilingEntry ищет в TreeMap ближайший ключ, равный или больший указанного.
            // Есть ещё floorEntry
        } else {
            return null;
        }
    }


    /**
     * Вытаскивает из списка, рандомную игрушку типа, соответствующего искомому,
     * и уменьшает её количество в хранилище.<br>
     * Если игрушка есть, но её количество НОЛЬ, то игрушка удаляется из хранилища - поиск по классу продолжается.<br>
     * Если Классу игрушки соответствует пустой список, этот класс удаляется из хранилища и из списка разыгрываемых,
     * метод возвращает NULL.
     *
     * @param toyType название класса искомой игрушки
     * @return игрушка из хранилища - в случае, если хоть 1 игрушка такого класса существует<br>
     * NULL - если игрушек такого класса нет ни одной
     */
    private Toy getToyFromShop(String toyType) { //
        if (toyType == null){
            return null;
        }
        ArrayList<Toy> currentToysList = warehouse.get(toyType);
        while (true) {
            if (!currentToysList.isEmpty()) {
                int currentToyIndex = new Random().nextInt(currentToysList.size());
                Toy currentToy = currentToysList.get(currentToyIndex);
                System.out.println("Попытка разыграть игрушку " + currentToy + ". ");
                if (currentToy.getCount() > 0) {
                    currentToy.decreaseCount();
                    return currentToy;
                } else {
                    System.out.println("       Игрушки " + currentToy + " закончились. Поищем похожую.");
                    currentToysList.remove(currentToyIndex);
                }
            } else {
                warehouse.remove(toyType);
                raffleToyTypesList.remove(toyType);
                System.out.println("       Игрушки типа " + toyType + ", к сожалению, уже закончились.");
                return null;
            }
        }
    }

    /**
     * Выдаём призы победителям:
     * если список призов наполнен - выдаём первый по очереди и удаляем его POLL
     * иначе - сообщение и запись в лог
     */
    public void giveOutPrize() { // выдать выигранную игрушку
        if (!prizesQueue.isEmpty()) {
            Toy prize = prizesQueue.poll();
            String message = "Выдан приз - " + prize;
            System.out.println(message);
            logger.writeToLog(message);
        } else {
            String message = "Очередь выдачи пуста. Все разыгранные игрушки розданы призёрам.";
            System.out.println(message);
            //logger.writeLogFile(message);
        }
    }


    public void giveOutAllPrizes(){
        while (!prizesQueue.isEmpty()){
            giveOutPrize();
        }
    }


    /**
     * ВЫВОД СОДЕРЖИМОГО МАГАЗИНА
     * @return строка со списком игрушек в хранилище WEREHOUSE
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String line = "--------------";
        sb.append(line)
                .append("\n<< СКЛАД МАГАЗИНА >>\n");
        Integer totalToysCount = 0;
        Integer toysTypesCount = 1;
        for (var list : warehouse.values()) {
            for (int i = 0; i < list.size(); i++) {
                sb.append(toysTypesCount)
                        .append(". ")
                        .append(list.get(i))
                        .append(", остаток ")
                        .append(list.get(i).getCount())
                        .append("\n");
                toysTypesCount++;
                totalToysCount += list.get(i).getCount();
            }
        }
        sb.append("ИТОГО: ")
                .append(totalToysCount)
                .append(" игрушек по ")
                .append(toysTypesCount - 1)
                .append(" наименованиям.\n--------------");
        return sb.toString();
    }


    /**
     * Вывод на экран классов игрушек, участвующих в розыгрыше и их шансов
     */
    public void showTypesAndChances() {
        if (!raffleToyTypesList.isEmpty()) {
            System.out.println("В розыгрыше участвуют следующие типы игрушек: ");
            double totalChance = 0;
            for (Integer chance : raffleToyTypesList.values()) {
                totalChance += chance;
            }
            if (totalChance > 0) {
                for (Map.Entry<String, Integer> entry : raffleToyTypesList.entrySet()) {
                    System.out.println(entry.getKey() + " - шанс выпадения " + String.format("%.0f", entry.getValue() / totalChance * 100) + ".");
                }
            } else {
                System.out.println("В списке типов игрушек для лотереи указаны нулевые шансы.");
            }
        } else {
            System.out.println("Список типов игрушек для лотереи пуст.");
        }
        System.out.println("--------------");
    }
}
