package eserciziFil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

public class EmployeeCollectors {

    public static Collector<Employee, ?, Map<String, List<Employee>>> groupByDepartment() {
        return Collector.of(
                HashMap::new,
                (map, emp) -> map.computeIfAbsent(emp.getDepartment(), k -> new ArrayList<>()).add(emp),
                (map1, map2) -> {
                    map2.forEach((dept, list) ->
                            map1.merge(dept, list, (l1, l2) -> {
                                l1.addAll(l2);
                                return l1;
                            })
                    );
                    return map1;
                }
        );
    }

    public static Collector<Employee, ?, Map<Double, List<Employee>>> groupBySalary() {
        return Collector.of(
                HashMap::new,
                (map, emp) -> map.computeIfAbsent(emp.getSalary(), k -> new ArrayList<>()).add(emp),
                (map1, map2) -> {
                    map2.forEach((dept, list) ->
                            map1.merge(dept, list, (l1, l2) -> {
                                l1.addAll(l2);
                                return l1;
                            })
                    );
                    return map1;
                }
        );
    }

    public static Collector<Employee, ?, SalaryStats> minMaxSalaryCollector() {
        return Collector.of(
                SalaryStats::new,
                (stats, emp) -> stats.accept(emp.getSalary()),
                (s1, s2) -> { s1.combine(s2); return s1; },
                Collector.Characteristics.UNORDERED
        );
    }
}
