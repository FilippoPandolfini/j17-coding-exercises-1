package eserciziFil;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void basicFilteringAndMapping() {

        List<Integer> ints = List.of(10, 9, 20, 15, 92, 105, 1);
        System.out.println("Lista completa: " + ints);

        List<Integer> filtroDispari = ints.stream().filter(i -> i%2 !=0).toList();
        System.out.println("Numeri dispari filtrati: " + filtroDispari);

        ints.stream().filter(i -> i%2 !=0).forEach(System.out::println);

        int prodottoRimanenti = ints.stream()
                .filter(Main::filtraIntero)
                .map(i -> -i)
                .reduce(1, (a, b) -> a * b);
        System.out.println(prodottoRimanenti);
    }

    public static boolean filtraIntero(int n){
        return n%2==0;
    }

    public static void summmationAndReduction() {
        List<Integer> ints = List.of(10, 9, 20, 15, 92, 105, 1);

        int somma = ints.stream()
                .mapToInt(i -> i)
                .sum();

        int differenza = ints.stream()
                .mapToInt(i -> i)
                .reduce(0, (a, b) -> a - b);

        int differenzaDue = ints.stream()
                .mapToInt(i -> i)
                .reduce((a, b) -> a - b)
                .getAsInt();

        int differenzaConFiltro = ints.stream()
                .mapToInt(i -> i)
                .filter(i -> i%2 != 0)
                .reduce(0, (a, b) -> a - b);

        System.out.println(somma);
        System.out.println(differenza);
        System.out.println(differenzaDue);
        System.out.println(differenzaConFiltro);
    }

    public static void collectingIntoDifferentDataStructure() {

        List<String> listaConsole = new ArrayList<>();

        listaConsole.add("PlayStation 4");
        listaConsole.add("Nintendo Switch 2");
        listaConsole.add("Steam Deck");
        listaConsole.add("PlayStation 5");
        listaConsole.add("XBox 360");
        listaConsole.add("PlayStation 3");
        listaConsole.add("Nintendo 3DS");
        listaConsole.add("XBox One");
        listaConsole.add("Nintendo Switch");

        SortedSet<String> listaP = listaConsole.stream()
                .filter(p -> p.contains("P"))
                .collect(Collectors.toCollection(TreeSet::new));

        Set<Character> inizialiConsentite = Set.of('N', 'X');

        Map<Character, List<String>> listaFiltroIniziale = listaConsole.stream()
                .filter(s -> !s.isEmpty() && inizialiConsentite.contains(s.charAt(0)))
                .collect(Collectors.groupingBy(s ->
                        s.charAt(0),
                        TreeMap::new,
                        Collectors.collectingAndThen(Collectors.toList(), list -> {
                            list.sort(Comparator.naturalOrder());
                            return list;
                        }))
                );

        List<String> listaLunghe = listaConsole.stream()
                .filter(s -> s.length() >= 15)
                .toList();

        System.out.println(listaP);
        System.out.println(listaFiltroIniziale);
        System.out.println(listaLunghe);
    }

    public static void groupingAndPartioning() {

        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee("Pippo", "HR", 21000));
        employees.add(new Employee("Pluto", "Finance", 33000));
        employees.add(new Employee("Topolino", "Stagist", 12000));
        employees.add(new Employee("Paperino", "CEO", 253000));
        employees.add(new Employee("Paperone", "Finance", 50000));

        Map<String, List<Employee>> employeeByDepartment = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment));

        employeeByDepartment.forEach((dept, avg) -> System.out.println(dept + " " + avg));

        System.out.println();

        Map<Boolean, List<Employee>> paritioned = employees.stream().collect(Collectors.groupingBy(e -> e.getSalary() < 40000));

        System.out.println("Dipendenti con RAL < 40000: ");
        paritioned.get(true).forEach(System.out::println);
    }

    public static void flatMapNested() {
        List<Integer> numeriPrimi = Arrays.asList(5, 7, 11, 13);
        List<Integer> numeriDispari = Arrays.asList(1, 3, 5);
        List<Integer> numeriPari = Arrays.asList(2, 4, 6, 8);

        List<List<Integer>> listaDelleListe = Arrays.asList(numeriPrimi, numeriDispari, numeriPari);
        System.out.println("Lista prima di flat: " + listaDelleListe);

        List<Integer> listaFlat = listaDelleListe.stream().flatMap(Collection::stream).sorted().toList();
        System.out.println("Lista flattata: " + listaFlat);
    }

    public static void readingProcessingWithStreams() {

        Path path = Path.of("/Users/filippopandolfini/Desktop/Testodaleggere.rtf");
        String text = "";

        try (InputStream input = Files.newInputStream(path)) {
            RTFEditorKit rtfParser = new RTFEditorKit();
            Document doc = rtfParser.createDefaultDocument();
            rtfParser.read(input, doc, 0);
            text = doc.getText(0, doc.getLength());
            System.out.println(text);
        } catch (IOException | BadLocationException e) {
            e.printStackTrace();
        }

        List<String> parole = Arrays.asList(text.trim().split("\\s+"));
        System.out.println("Trim split di parole: " + parole);

        List<String> listaFlat = parole.stream().sorted().toList();
        System.out.println("Lista flattata: " + listaFlat);

        List<String> listaFiltro = parole.stream().filter(s -> s.length() <= 3).toList();
        System.out.println("Lista filtrata: " + listaFiltro);
    }

    public static void customCollector() {

        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee("Pippo", "HR", 21000));
        employees.add(new Employee("Pluto", "Finance", 33000));
        employees.add(new Employee("Topolino", "Stagist", 12000));
        employees.add(new Employee("Paperino", "CEO", 253000));
        employees.add(new Employee("Paperone", "Finance", 50000));
        employees.add(new Employee("Topo Gigio", "HR", 33000));

        Map<String, List<Employee>> groupedByDept = employees.stream().collect(EmployeeCollectors.groupByDepartment());
        System.out.println(groupedByDept);

        Map<Double, List<Employee>> groupedBySal = employees.stream().collect(EmployeeCollectors.groupBySalary());
        System.out.println(groupedBySal);

        SalaryStats stats = employees.stream().collect(EmployeeCollectors.minMaxSalaryCollector());
        System.out.println(stats);
    }

    public static void parallelStreams() {

        List<Integer> numeri = new Random().ints(50, 0, 500)
                .boxed()
                .collect(Collectors.toList());

        Map<Boolean, List<Integer>> partizionati = numeri.parallelStream().collect(Collectors.partitioningBy(n -> n%2 == 0));

        List<Integer> pari = partizionati.get(true);
        List<Integer> dispari = partizionati.get(false);

        System.out.println("Numeri pari: " + pari);
        System.out.println("Numeri dispari: " + dispari);
    }


    public static void main(String[] args) throws IOException {
        // basicFilteringAndMapping();
        // summmationAndReduction();
        // collectingIntoDifferentDataStructure();
        // groupingAndPartioning();
        // flatMapNested();
        // readingProcessingWithStreams();
        // customCollector();
        parallelStreams();
    }
}
