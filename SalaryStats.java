public class SalaryStats {

    private double minSalary = Double.POSITIVE_INFINITY;
    private double maxSalary = Double.NEGATIVE_INFINITY;

    public void accept(double salary) {
        minSalary = Math.min(minSalary, salary);
        maxSalary = Math.max(maxSalary, salary);
    }

    public void combine (SalaryStats other) {
        minSalary = Math.min(minSalary, other.minSalary);
        maxSalary = Math.max(maxSalary, other.maxSalary);
    }

    public double getMinSalary() {
        return minSalary;
    }

    public double getMaxSalary() {
        return maxSalary;
    }

    @Override
    public String toString() {
        return "Min: " + minSalary + ", max: " + maxSalary;
    }
}
