import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Currency {
    private String name;
    private double usdRate;

    public Currency(String name, double usdRate) {
        this.name = name;
        this.usdRate = usdRate;
    }

    public String getName() {
        return name;
    }

    public double getUsdRate() {
        return usdRate;
    }
}

class ConversionHistory {
    private String sourceCurrency;
    private String targetCurrency;
    private double amount;
    private double convertedAmount;

    public ConversionHistory(String sourceCurrency, String targetCurrency, double amount, double convertedAmount) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
    }

    @Override
    public String toString() {
        return amount + " " + sourceCurrency + " = " + convertedAmount + " " + targetCurrency;
    }
}

public class CurrencyConverter {
    private static List<Currency> currencies = new ArrayList<>();
    private static List<ConversionHistory> history = new ArrayList<>();

    static {
        currencies.add(new Currency("USD", 1.0));
        currencies.add(new Currency("EUR", 0.85));
        currencies.add(new Currency("GBP", 0.73));
        currencies.add(new Currency("PKR", 165.0));
        currencies.add(new Currency("INR", 74.0));
        // Adding popular digital currencies
        currencies.add(new Currency("BTC", 0.000027));  // Example rate, 1 USD = 0.000027 BTC
        currencies.add(new Currency("ETH", 0.00043));   // Example rate, 1 USD = 0.00043 ETH
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Welcome to the Currency Converter!");
            System.out.println("1. Convert Currency");
            System.out.println("2. Convert to Digital Currency");
            System.out.println("3. Compare Currency Rates");
            System.out.println("4. View Conversion History");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    convertCurrency(scanner, false);
                    break;
                case 2:
                    convertCurrency(scanner, true);
                    break;
                case 3:
                    compareCurrencyRates();
                    break;
                case 4:
                    viewConversionHistory();
                    break;
                case 5:
                    System.out.println("Thank you for using the Currency Converter!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        scanner.close();
    }

    private static void convertCurrency(Scanner scanner, boolean toDigital) {
        System.out.println("Select source currency:");
        displayCurrencies(false);
        int sourceCurrencyChoice = scanner.nextInt();

        System.out.println("Select target currency:");
        displayCurrencies(toDigital);
        int targetCurrencyChoice = scanner.nextInt();

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();

        double exchangeRate = getExchangeRate(sourceCurrencyChoice, targetCurrencyChoice, toDigital);
        double convertedAmount = amount * exchangeRate;

        String sourceCurrencyName = currencies.get(sourceCurrencyChoice - 1).getName();
        String targetCurrencyName = currencies.get(targetCurrencyChoice - 1).getName();

        System.out.println(amount + " " + sourceCurrencyName + " is equal to " + convertedAmount + " " + targetCurrencyName);

        history.add(new ConversionHistory(sourceCurrencyName, targetCurrencyName, amount, convertedAmount));
    }

    private static void compareCurrencyRates() {
        System.out.println("Currency Rates Comparison (relative to USD):");
        for (Currency currency : currencies) {
            System.out.println(currency.getName() + ": " + currency.getUsdRate());
        }
    }

    private static void viewConversionHistory() {
        if (history.isEmpty()) {
            System.out.println("No conversion history available.");
        } else {
            System.out.println("Conversion History:");
            for (ConversionHistory record : history) {
                System.out.println(record);
            }
        }
    }

    private static double getExchangeRate(int sourceCurrency, int targetCurrency, boolean toDigital) {
        double sourceToUsd = currencies.get(sourceCurrency - 1).getUsdRate();
        double targetToUsd = currencies.get(targetCurrency - 1).getUsdRate();
        return targetToUsd / sourceToUsd;
    }

    private static void displayCurrencies(boolean digitalOnly) {
        for (int i = 0; i < currencies.size(); i++) {
            if (digitalOnly && (currencies.get(i).getName().equals("BTC") || currencies.get(i).getName().equals("ETH"))) {
                System.out.println((i + 1) + ". " + currencies.get(i).getName());
            } else if (!digitalOnly && !(currencies.get(i).getName().equals("BTC") || currencies.get(i).getName().equals("ETH"))) {
                System.out.println((i + 1) + ". " + currencies.get(i).getName());
            }
        }
    }
}
